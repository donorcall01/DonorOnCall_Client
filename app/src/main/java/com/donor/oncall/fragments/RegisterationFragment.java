package com.donor.oncall.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.donor.oncall.R;

/**
 * Created by prashanth on 3/1/16.
 */
public class RegisterationFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.registeration_screen, container, false);
        //return rootView;
    }
}
