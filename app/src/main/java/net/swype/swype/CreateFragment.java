package net.swype.swype;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.herokuapp.swype.Interpreter;
import com.herokuapp.swype.Script;
import com.herokuapp.swype.Swype;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class CreateFragment extends Fragment {

    private final Swype swype = new Swype();
    Interpreter interpreter = new Interpreter();
    private EditText nameText;
    private EditText codeText;
    private CheckBox checkBox;
    private FloatingActionButton fab;

    public CreateFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create, container, false);

        //set up UI components
        nameText = (EditText) view.findViewById(R.id.nameText);
        codeText = (EditText) view.findViewById(R.id.codeText);
        checkBox = (CheckBox) view.findViewById(R.id.checkBox);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);

        //toggle between print and save modes
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    fab.setImageResource(R.drawable.ic_print);
                } else {
                    fab.setImageResource(R.drawable.ic_save_white);
                }
            }
        });

        //floating action button is pressed - begin print/save
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get mode
                boolean toPrint = checkBox.isChecked();
                //split input into lines
                String[] commands = codeText.getText().toString().split("\n");

                ArrayList<Integer> lineErrors = new ArrayList<>();
                //check for illegal commands
                for (int i = 0; i < commands.length; i++) {
                    if (interpreter.getCommandType(commands[i]) == Interpreter.command.INVALID) {
                        lineErrors.add(i + 1);
                    }
                }

                //check if any fields are empty
                if (nameText.getText().toString().equals("")) {
                    //shake field
                    Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.anim_shake);
                    nameText.startAnimation(shake);
                } else if (codeText.getText().toString().equals("")) {
                    Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.anim_shake);
                    codeText.startAnimation(shake);
                    //check for errors in input
                } else if (lineErrors.size() != 0) {
                    String message = "error in line";
                    message += lineErrors.size() > 1 ? "s" : "" + ":";

                    for (int l : lineErrors) {
                        message += "\n" + l;
                    }
                    //output error locations
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                } else {
                    if (toPrint) {
                        //send to print
                        saveScript(nameText.getText().toString(), codeText.getText().toString(), true, getContext());
                    } else {
                        //send to save
                        saveScript(nameText.getText().toString(), codeText.getText().toString(), false, getContext());
                    }
                }
            }
        });
        return view;
    }

    private void saveScript(String name, String code, boolean print, final Context context) {
        //get username
        SharedPreferences settings = getContext().getSharedPreferences("pass", MODE_PRIVATE);
        String id = settings.getString("username", null);

        Script script = new Script(name, id, code, print ? 1 : 0);
        //send to server
        Call<Script> call = swype.service.saveScript(script);
        call.enqueue(new Callback<Script>() {
            @Override
            public void onResponse(Call<Script> call, @NonNull Response<Script> response) {

                Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Script> call, @NonNull Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}

