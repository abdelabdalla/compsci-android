package net.swype.swype;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.herokuapp.swype.Script;
import com.herokuapp.swype.Swype;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ExploreFragment extends Fragment {

    private final Swype swype = new Swype();
    private SwipeRefreshLayout swipeLayout;
    private ListView galleryListView;
    private EditText searchText;
    private Button searchButton;
    private UserAdapter userAdapter;

    public ExploreFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        //set up UI components
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout);
        galleryListView = (ListView) view.findViewById(R.id.galleryListView);
        searchText = (EditText) view.findViewById(R.id.searchText);
        searchButton = (Button) view.findViewById(R.id.searchButton);

        //search when button is clicked
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check if search bar is not empty
                if (searchText.toString().equals("")) {

                    //shake search bar
                    Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.anim_shake);
                    searchText.startAnimation(shake);
                } else {
                    swipeLayout.setRefreshing(true);

                    //send search term to server
                    Call<Script[]> call = swype.service.searchScripts(searchText.getText().toString());
                    call.enqueue(new Callback<Script[]>() {
                        @Override
                        public void onResponse(Call<Script[]> call, Response<Script[]> response) {
                            //put response in listview
                            userAdapter = new UserAdapter(getContext(), response.body());
                            galleryListView.setAdapter(userAdapter);
                            swipeLayout.setRefreshing(false);
                        }

                        @Override
                        public void onFailure(Call<Script[]> call, Throwable t) {
                            //error from server
                            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            swipeLayout.setRefreshing(false);
                        }
                    });
                }
            }
        });

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

        //connect to server
        Call<Script[]> call = swype.service.getHotScripts();
        call.enqueue(new Callback<Script[]>() {
            @Override
            public void onResponse(Call<Script[]> call, @NonNull Response<Script[]> response) {
                //put response in listview
                userAdapter = new UserAdapter(getContext(), response.body());
                galleryListView.setAdapter(userAdapter);
                swipeLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<Script[]> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                swipeLayout.setRefreshing(false);
            }
        });

    }

}
