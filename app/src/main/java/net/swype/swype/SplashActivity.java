package net.swype.swype;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.herokuapp.swype.Swype;
import com.herokuapp.swype.User;
import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //get stored username and password
        SharedPreferences settings = getSharedPreferences("pass", MODE_PRIVATE);
        String id = settings.getString("username", null);
        String pass = settings.getString("password", null);

        Swype swype = new Swype();

        //check if username and password exist
        if (id == null || pass == null) {
            //open login activity
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            try {
                //decrypt password
                String password = AESCrypt.decrypt(id, pass);

                User idObj = new User(id, password);
                //attempt to verify credntials with server
                Call<User> call = swype.service.loginUser(idObj);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, @NonNull Response<User> response) {
                        if (response.code() == 200) {
                            //verification succeeded
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            //verification failed
                            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

            } catch (GeneralSecurityException e) {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
