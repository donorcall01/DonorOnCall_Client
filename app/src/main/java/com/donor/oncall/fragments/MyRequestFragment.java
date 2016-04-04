package com.donor.oncall.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.donor.oncall.R;

/**
 * Created by prashanth on 4/1/16.
 */
public class MyRequestFragment extends BaseFragment {
    private   View rootView=null;
    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.my_request, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize recycler view
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.myRequestList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //strict ordering

    }

}
