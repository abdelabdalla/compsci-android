package net.swype.swype;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.herokuapp.swype.Swype;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

class Utils {

    private final Swype swype = new Swype();

    public Utils() {
    }

    void print(int id, @NonNull final Context context) {
        //get username
        SharedPreferences settings = context.getSharedPreferences("pass", MODE_PRIVATE);
        String requester = settings.getString("username", null);

        //send script to print
        Call<String> call = swype.service.printScript(id, requester);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, @NonNull Response<String> response) {
                //inform user of status
                Toast.makeText(context, response.code() == 200 ? "sent to print" : response.code() + " " + response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, @NonNull Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //check if device has internet access
    boolean isConnected(Context context) {
        try {
            //method recommended by Google
            //http://developer.android.com/training/monitoring-device-state/connectivity-monitoring.html
            ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                Log.v("neterror","true");
                return true;
            } else {
                Log.v("neterror","false");
                return false;
            }
        } catch (NullPointerException e) {
            Log.v("neterror","false");
            return false;
        }
    }
}
