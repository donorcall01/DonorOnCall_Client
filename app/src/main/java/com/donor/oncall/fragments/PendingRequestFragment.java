package com.donor.oncall.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.donor.oncall.R;

/**
 * Created by prashanth on 4/1/16.
 */
public class PendingRequestFragment extends BaseFragment {
    private   View rootView=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.pending_request, container, false);
        return rootView;
    }
}