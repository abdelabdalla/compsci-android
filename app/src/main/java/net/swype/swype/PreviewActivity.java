package net.swype.swype;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.gson.Gson;
import com.herokuapp.swype.Script;

import io.github.kbiakov.codeview.CodeView;

public class PreviewActivity extends AppCompatActivity {

    private final Utils utils = new Utils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        CodeView codeView = (CodeView) findViewById(R.id.codeView);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        //convert JSON into script
        Gson gson = new Gson();
        final Script script = gson.fromJson(getIntent().getStringExtra("script"), Script.class);

        //set activity title to script name
        getSupportActionBar().setTitle(script.getName());
        //load script
        codeView.setCode(script.getScript());
        //print when floating action button is pressed
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utils.print(script.getId(), getApplicationContext());
            }
        });

    }
}
