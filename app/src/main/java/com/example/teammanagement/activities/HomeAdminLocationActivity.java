package com.example.teammanagement.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.teammanagement.R;
import com.example.teammanagement.fragments.EditLocationFragment;
import com.example.teammanagement.fragments.LocationProfileFragment;
import com.example.teammanagement.fragments.LocationsReservationsFragment;
import com.example.teammanagement.fragments.NewLocationsFragment;
import com.example.teammanagement.fragments.ReportsFragment;
import com.example.teammanagement.fragments.SettingsFragment;

public class HomeAdminLocationActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin_location);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.home_admin_location_bottomNavigationView);
        navigation.setOnNavigationItemSelectedListener(this);
        loadFragment(new LocationProfileFragment());
    }
    private boolean loadFragment(Fragment fragment){
        if(fragment != null){

            getSupportFragmentManager().beginTransaction().replace(R.id.home_admin_location_fragment_containerAdmin,fragment).commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        switch (menuItem.getItemId()){
            case R.id.navigation_locationProfile:
                fragment = new LocationProfileFragment();
                break;
            case R.id.navigation_editProfile:
                fragment = new EditLocationFragment();
                break;
            case R.id.navigation_reservations:
                fragment = new LocationsReservationsFragment();
                break;

        }
        return loadFragment(fragment);
    }
}
