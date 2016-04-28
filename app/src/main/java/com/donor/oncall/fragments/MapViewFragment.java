package com.donor.oncall.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.donor.oncall.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * Created by prashanth on 29/1/16.
 */
public class MapViewFragment extends BaseFragment implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,LocationListener,GoogleMap.OnMyLocationButtonClickListener,GoogleMap.OnMarkerDragListener {
    private GoogleMap mMap;
    private   View rootView=null;
    private boolean showRecipientAlert;
    private double lattitue,longitude;
    private GoogleApiClient googleApiClient;
    private LocationRequest mLocationRequest;

    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.map_screen, container, false);
        SupportMapFragment mapFragment =
                (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        if (getArguments() !=null){
        showRecipientAlert = getArguments().getBoolean("showRecipientAlert",false);
        }
        mapFragment.getMapAsync(this);

        googleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
            return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView.findViewById(R.id.requestBlood).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Bundle message = new Bundle();
                    message.putDouble("longitude", longitude);
                    message.putDouble("lattitude", lattitue);
                    RequestBloodFragment requestBloodFragment = new RequestBloodFragment();
                    requestBloodFragment.setArguments(message);
                    replaceViewFragment(requestBloodFragment, false);
                    replaceViewFragment(new RequestBloodFragment(), false);


            }
        });


    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMarkerDragListener(this);
        if (showRecipientAlert)
            setShowRecipientAlertDialog();

    }

    public void setShowRecipientAlertDialog() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                new AlertDialog.Builder(getContext())
                        .setTitle("Your Request has been approved !")
                        .setMessage("Your donor request has been approved.You're all set.Track your current donor using map.")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                updateLayoutAsRecipientScreen();
                            }
                        })
                        .show();
            }
        }, 10000);
    }
            /**
             * Enables the My Location layer if the fine location permission has been granted.
             */

            @Override
            public void onResume() {
                super.onResume();
            }

    @Override
    public void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        getCurrentLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
    public void updateLayoutAsRecipientScreen(){
       Button requestblood = (Button) rootView.findViewById(R.id.requestBlood);
        requestblood.setText("Call Donor");
        showDonorOnMap();
    }
    @Override
    public void onLocationChanged(Location location) {
    // New location has now been determined
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        // You can now create a LatLng Object for use with maps
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    private void getCurrentLocation() {
        mMap.clear();
        //Creating a location object
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location != null) {
            //Getting longitude and latitude
            longitude = location.getLongitude();
            lattitue = location.getLatitude();

            //moving the map to location
            moveMap();
        }
        // Begin polling for new location updates.
        startLocationUpdates();
    }

    // Trigger new location updates at interval
    protected void startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        // Request location updates
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,
                mLocationRequest, this);
    }

    private void moveMap() {

        LatLng latLng = new LatLng(lattitue, longitude);
        //Adding marker to map
        mMap.addMarker(new MarkerOptions()
                .position(latLng) //setting position
                .draggable(true) //Making the marker draggable
                .title("Current Location")); //Adding a title
        //Moving the camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        //Animating the camera
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

    }

    private void showDonorOnMap(){
        //Creating a LatLng Object to store Coordinates
        LatLng latLng = new LatLng(lattitue, longitude);

        //Adding marker to map
        mMap.addMarker(new MarkerOptions()
                .position(latLng) //setting position
                .draggable(true) //Making the marker draggable
                .title("Rakesh Agarwal")
                .snippet("Age : 55 ,ETA : 7.55pm at Hospital")); //Adding a title

        //Moving the camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        //Animating the camera
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }


    public boolean checkGpsIsEnabled(){
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public void showAlertDialogOnGPS(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Location services not Enabled");  // GPS not found
        builder.setMessage("Enable Gps Location to get your current location"); // Want to enable?
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });
        builder.setNegativeButton(android.R.string.no, null);
        builder.create().show();
    }
    @Override
    public boolean onMyLocationButtonClick() {
        if (!checkGpsIsEnabled()) {
            showAlertDialogOnGPS();
        }
        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        LatLng dragPosition = marker.getPosition();
        lattitue = dragPosition.latitude;
        longitude = dragPosition.longitude;
    }
}
