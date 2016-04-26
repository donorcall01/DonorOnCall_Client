package com.donor.oncall;

import android.os.Bundle;

import com.donor.oncall.fragments.MapViewFragment;
import com.donor.oncall.fragments.MyRequestFragment;
import com.google.android.gms.maps.model.LatLng;


public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolBar();
        replaceViewFragment(new MapViewFragment(),false);
    }

}
