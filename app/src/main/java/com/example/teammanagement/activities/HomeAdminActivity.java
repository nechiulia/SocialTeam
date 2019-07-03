package com.example.teammanagement.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.teammanagement.R;
import com.example.teammanagement.fragments.NewLocationsFragment;
import com.example.teammanagement.fragments.ReportsFragment;
import com.example.teammanagement.fragments.SearchLocationFragment;

public class HomeAdminActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    TextView tv_title;
    ImageButton ibtn_aprove;
    ImageButton ibtn_remove;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);


        BottomNavigationView navigation = findViewById(R.id.home_admin_bottomNavigationView);
        navigation.setOnNavigationItemSelectedListener(this);

        loadFragment(new ReportsFragment());
    }


    private boolean loadFragment(Fragment fragment){
        if(fragment != null){

            getSupportFragmentManager().beginTransaction().replace(R.id.home_admin_fragment_containerAdmin,fragment).commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        switch (menuItem.getItemId()){
            case R.id.navigation_reports:
                fragment = new ReportsFragment();
                break;
            case R.id.navigation_newLocations:
                fragment = new NewLocationsFragment();
                break;
            case R.id.navigation_map:
                fragment = new SearchLocationFragment();
                break;

        }
        return loadFragment(fragment);
    }
}
