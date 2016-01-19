package com.donor.oncall;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MainActivity extends BaseActivity {
    static final LatLng IITChennai = new LatLng(12.991992, 80.237090);
    static final LatLng TidelPark = new LatLng(12.989504, 80.248420);
    private GoogleMap map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolBar();
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        map.setMyLocationEnabled(true);
        if (map!=null){
            map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

                @Override
                public void onMyLocationChange(Location arg0) {
                    map.addMarker(new MarkerOptions().position(new LatLng(arg0.getLatitude(), arg0.getLongitude())).title("It's Me!"));
                    map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
                }
            });
            // Move the camera instantly to hamburg with a zoom of 15.
           // map.moveCamera(CameraUpdateFactory.newLatLngZoom(IITChennai, 15));

            // Zoom in, animating the camera.
            //map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

           /* Marker hamburg = map.addMarker(new MarkerOptions().position(IITChennai)
                    .title("IITChennai"));
            Marker kiel = map.addMarker(new MarkerOptions()
                    .position(TidelPark)
                    .title("TidelPark")
                    .snippet("TidelPark is cool"));*/
        }

    }

}
