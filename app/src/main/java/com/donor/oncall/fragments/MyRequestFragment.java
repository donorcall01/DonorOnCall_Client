package com.donor.oncall.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.donor.oncall.R;
import com.donor.oncall.RequestItem;
import com.donor.oncall.adapters.MyRequestAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prashanth on 4/1/16.
 */
public class MyRequestFragment extends BaseFragment {
    private   View rootView=null;
    private RecyclerView mRecyclerView;
    private MyRequestAdapter myRequestAdapter;
    private List<RequestItem> requestItems = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.my_request, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myRequestAdapter = new MyRequestAdapter(getContext(),requestItems);
        // Initialize recycler view
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.myRequestList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(myRequestAdapter);
        prepareMovieData();
        //strict ordering

    }

    private void prepareMovieData() {
        RequestItem requestItem = new RequestItem("Ramesh Chowdry","Male","12:12","10/12,second street","O+","52","2.5 units");
        requestItems.add(requestItem);

        requestItem = new RequestItem("Ramesh Chowdry","Male","12:12","10/12,second street","b+","53","2 units");
        requestItems.add(requestItem);

        requestItem = new RequestItem("Suresh Chowdry","Male","10:12","10/12,second street","AB+","42","1 units");
        requestItems.add(requestItem);

        requestItem = new RequestItem("Dinesh","Male","02:12","10/12,second street","O+","22","6 units");
        requestItems.add(requestItem);

        requestItem = new RequestItem("Ramesh","FeMale","13:12","10/12,second street","B-","32","3 units");
        requestItems.add(requestItem);

        requestItem = new RequestItem("Ankit Agarwal","Male","12:50","10/12,second street","AB+","22","5 units");
        requestItems.add(requestItem);

        requestItem = new RequestItem("Ramesh Chowdry","Male","00:12","10/12,second street","AB-","562","2 units");
        requestItems.add(requestItem);

        myRequestAdapter.notifyDataSetChanged();
    }

}
