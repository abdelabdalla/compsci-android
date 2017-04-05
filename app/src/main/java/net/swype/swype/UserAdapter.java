package net.swype.swype;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.herokuapp.swype.Script;

class UserAdapter extends ArrayAdapter<Script> {

    private final Utils utils = new Utils();

    //Adapter helps put data into ListView in the correct format

    UserAdapter(@NonNull Context c, @NonNull Script[] scripts) {
        super(c, 0, scripts);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        final Script script = getItem(position);

        //convert script into JSON
        Gson gson = new Gson();
        final String json = gson.toJson(script);

        if (view == null) {
            //use layout R.layout.item_script
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_script, parent, false);
        }

        //set up UI components
        TextView nameText = (TextView) view.findViewById(R.id.nameText);
        final TextView userText = (TextView) view.findViewById(R.id.userText);
        TextView printText = (TextView) view.findViewById(R.id.printText);

        Button printButton = (Button) view.findViewById(R.id.printButton);

        ConstraintLayout layout = (ConstraintLayout) view.findViewById(R.id.layout);

        assert script != null;
        //put data into UI components
        nameText.setText(script.getName());
        userText.setText(script.getCreator());
        printText.setText(script.getPrint_count() + " prints");

        //print button is pressed
        printButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send script to print
                utils.print(script.getId(), getContext());
            }
        });

        //list item is pressed
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open script in preview activity
                Intent intent = new Intent(getContext(), PreviewActivity.class);
                intent.putExtra("script", json);
                getContext().startActivity(intent);
            }
        });

        return view;
    }
}
