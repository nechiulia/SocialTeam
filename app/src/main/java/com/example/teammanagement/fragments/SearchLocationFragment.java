package com.example.teammanagement.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.teammanagement.R;
import com.example.teammanagement.Utils.Constants;
import com.example.teammanagement.Utils.NewLocation;
import com.example.teammanagement.Utils.SharedViewModel;
import com.example.teammanagement.activities.HomeAdminActivity;
import com.example.teammanagement.activities.LoginActivity;
import com.example.teammanagement.activities.MapsActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchLocationFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ImageButton ibtn_logOut;
    private AutoCompleteTextView et_searchText;

    private SharedViewModel model;

    private NewLocation newLocation;

    private static final String TAG = "MapsActivity";

    private Intent intent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_search_location,null);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        et_searchText=view.findViewById(R.id.search_location_et_searchBar);
        ibtn_logOut=view.findViewById(R.id.search_location_ibtn_logout);

        et_searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE){
                    geoLocate();
                }
                return false;
            }
        });

        ibtn_logOut.setOnClickListener(clickLogOut());

        model = ViewModelProviders.of(this.getActivity()).get(SharedViewModel.class);
        if(model.getSelected().getValue()!= null) {
            Log.d("CEVA", String.valueOf(model.getSelected().getValue()));
            newLocation=model.getSelected().getValue();
            et_searchText.setText(newLocation.getLocationName());
            model.setNewLocation(null);
        }
        return view;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng bucharest = new LatLng(44.4268, 26.1025);
        moveCamera(new LatLng(bucharest.latitude,bucharest.longitude), Constants.DEFAULT_ZOOM,"București");
    }



    private void moveCamera(final LatLng latLng, float zoom, String title){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
        MarkerOptions options = new MarkerOptions().position(latLng).title(title);
        mMap.addMarker(options);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return true;
            };
        });
    }

    private void geoLocate(){
        mMap.clear();

        String searchText = et_searchText.getText().toString();

        Geocoder geocoder = new Geocoder(getActivity());

        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(searchText,1);
        }catch (IOException ex){
            ex.printStackTrace();
        }
        if(list.size() != 0){
            Address address = list.get(0);
            Log.d(TAG,"geoLocate found a location "+address.toString());
            moveCamera(new LatLng(address.getLatitude(),
                            address.getLongitude()),
                    Constants.DEFAULT_ZOOM,address.getAddressLine(0));
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setTitle(getString(R.string.maps_alertDialog_errorLocation_title_hint))
                    .setMessage(getString(R.string.maps_alertDialog_errorLocation_message_hint))
                    .setNeutralButton(getString(R.string.maps_alertDialog_errorLocation_positiveButton_hint), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            Dialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }
    }

    private View.OnClickListener clickLogOut() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        };
    }
}
