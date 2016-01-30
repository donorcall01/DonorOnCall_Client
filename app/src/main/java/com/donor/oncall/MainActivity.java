package com.donor.oncall;

import android.os.Bundle;

import com.donor.oncall.fragments.RequestBloodFragment;
import com.google.android.gms.maps.model.LatLng;


public class MainActivity extends BaseActivity {
    static final LatLng IITChennai = new LatLng(12.991992, 80.237090);
    static final LatLng TidelPark = new LatLng(12.989504, 80.248420);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolBar();
        replaceViewFragment(new RequestBloodFragment(),false);
    }

}
