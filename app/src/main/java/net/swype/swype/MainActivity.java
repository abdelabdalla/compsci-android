package net.swype.swype;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private final ExploreFragment exploreFragment = new ExploreFragment();
    private final CreateFragment createFragment = new CreateFragment();
    private final GalleryFragment galleryFragment = new GalleryFragment();

    private FragmentTransaction transaction;

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_explore:
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content, exploreFragment)
                            .addToBackStack(null)
                            .commit();
                    return true;
                case R.id.navigation_create:
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content, createFragment)
                            .addToBackStack(null)
                            .commit();
                    return true;
                case R.id.navigation_gallery:
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content, galleryFragment)
                            .addToBackStack(null)
                            .commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //set up first fragment
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, exploreFragment)
                .addToBackStack(null)
                .commit();
        //set up navigation bar
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    //create drop-down menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        //sign out option pressed
        if (id == R.id.action_signout) {
            //remove credentials
            SharedPreferences settings = getSharedPreferences("pass", MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.remove("username");
            editor.remove("password");
            editor.apply();
            //go to login activity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
