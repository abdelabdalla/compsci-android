package net.swype.swype;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.herokuapp.swype.Script;
import com.herokuapp.swype.Swype;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class GalleryFragment extends Fragment {

    private SwipeRefreshLayout swipeLayout;
    private ListView galleryListView;

    private UserAdapter userAdapter;

    private final Swype swype = new Swype();

    public GalleryFragment() {}


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        //set up UI components
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout);
        galleryListView = (ListView) view.findViewById(R.id.galleryListView);

        //refresh requested
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getScripts();
            }
        });

        swipeLayout.setRefreshing(true);
        //get scripts from server
        getScripts();

        return view;
    }


    private void getScripts() {

        //get username
        SharedPreferences settings = getContext().getSharedPreferences("pass", MODE_PRIVATE);
        String id = settings.getString("username", null);

        //connect to server
        Call<Script[]> call = swype.service.getUserScripts(id);
        call.enqueue(new Callback<Script[]>() {
            @Override
            public void onResponse(Call<Script[]> call, @NonNull Response<Script[]> response) {
                //put response in listview
                userAdapter = new UserAdapter(getContext(),response.body());
                galleryListView.setAdapter(userAdapter);
                swipeLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<Script[]> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(),Toast.LENGTH_SHORT).show();
                swipeLayout.setRefreshing(false);
            }
        });
    }
}
