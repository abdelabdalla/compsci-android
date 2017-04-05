package net.swype.swype;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.herokuapp.swype.Swype;
import com.herokuapp.swype.User;
import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity {

    private final Swype swype = new Swype();
    Utils utils = new Utils();
    private EditText nameText;
    private EditText passText;
    private EditText emailText;
    private Button sendButton;
    private CheckBox regBox;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //set up logo
        TextView textView = (TextView) findViewById(R.id.textView);
        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/anon.ttf");
        textView.setTypeface(type);

        //set up UI elements
        nameText = (EditText) findViewById(R.id.nameText);
        passText = (EditText) findViewById(R.id.passText);
        emailText = (EditText) findViewById(R.id.emailText);
        sendButton = (Button) findViewById(R.id.sendButton);
        regBox = (CheckBox) findViewById(R.id.regBox);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        //check if in register mode
        if (!regBox.isChecked()) {
            emailText.setVisibility(View.GONE);
        }
        //toggles between login and register mode
        regBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (regBox.isChecked()) {
                    emailText.setVisibility(View.VISIBLE);
                    sendButton.setText(R.string.register);
                } else {
                    emailText.setVisibility(View.GONE);
                    sendButton.setText(R.string.login);
                }
            }
        });
        //login/register button is pressed
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checks if internet connection is available
                if (utils.isConnected(LoginActivity.this)) {
                    //checks if fields are filled
                    if (nameText.getText().toString().equals("")) {
                        Animation shake = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.anim_shake);
                        nameText.startAnimation(shake);
                    } else if (passText.getText().toString().equals("")) {
                        Animation shake = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.anim_shake);
                        passText.startAnimation(shake);
                    } else if (passText.getText().toString().length() < 8) {
                        Toast.makeText(LoginActivity.this,"password must have at least 8 characters",Toast.LENGTH_SHORT).show();
                        Animation shake = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.anim_shake);
                        emailText.startAnimation(shake);
                    } else if (regBox.isChecked() && emailText.getText().toString().equals("")) {
                        Animation shake = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.anim_shake);
                        emailText.startAnimation(shake);
                    } else {
                        //register
                        if (regBox.isChecked()) {

                            progressBar.setVisibility(View.VISIBLE);

                            User user = new User(nameText.getText().toString(), passText.getText().toString(), emailText.getText().toString());

                            //send to server
                            Call<User> call = swype.service.registerUser(user);
                            call.enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, @NonNull Response<User> response) {
                                    if (response.code() == 200) {
                                        //attempt login
                                        login(nameText.getText().toString(), passText.getText().toString());
                                    } else {
                                        //error from server
                                        Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }

                                @Override
                                public void onFailure(Call<User> call, @NonNull Throwable t) {
                                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            });
                        } else {
                            progressBar.setVisibility(View.VISIBLE);
                            login(nameText.getText().toString(), passText.getText().toString());
                        }
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this,getString(R.string.no_internet),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void login(String id, String pass) {
        User idObj = new User(id, pass);

        Call<User> call = swype.service.loginUser(idObj);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, @NonNull Response<User> response) {
                if (response.code() == 200) {

                    String username = nameText.getText().toString();
                    String pass;

                    try {
                        //encrypt password for storage
                        pass = AESCrypt.encrypt(username, passText.getText().toString());
                        //save credentials
                        SharedPreferences settings = getSharedPreferences("pass", MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("username", username);
                        editor.putString("password", pass);
                        editor.apply();
                    } catch (GeneralSecurityException e) {
                        e.printStackTrace();
                    }
                    //enter main activity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<User> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

    }

}
