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
public class LoginFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.login_screen, container, false);
        rootView.findViewById(R.id.signin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceViewFragment(new RegisterationFragment(), true);
            }
        });
        return rootView;
        //return rootView;
    }
}
