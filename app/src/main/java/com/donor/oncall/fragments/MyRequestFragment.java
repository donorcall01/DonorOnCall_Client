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
        RequestItem requestItem = new RequestItem("Mad Max: Fury Road", "Action & Adventure", "2015");
        requestItems.add(requestItem);

        requestItem = new RequestItem("Inside Out", "Animation, Kids & Family", "2015");
        requestItems.add(requestItem);

        requestItem = new RequestItem("Star Wars: Episode VII - The Force Awakens", "Action", "2015");
        requestItems.add(requestItem);

        requestItem = new RequestItem("Shaun the Sheep", "Animation", "2015");
        requestItems.add(requestItem);

        requestItem = new RequestItem("The Martian", "Science Fiction & Fantasy", "2015");
        requestItems.add(requestItem);

        requestItem = new RequestItem("Mission: Impossible Rogue Nation", "Action", "2015");
        requestItems.add(requestItem);

        requestItem = new RequestItem("Up", "Animation", "2009");
        requestItems.add(requestItem);

        requestItem = new RequestItem("Star Trek", "Science Fiction", "2009");
        requestItems.add(requestItem);

        requestItem = new RequestItem("The LEGO Movie", "Animation", "2014");
        requestItems.add(requestItem);

        requestItem = new RequestItem("Iron Man", "Action & Adventure", "2008");
        requestItems.add(requestItem);

        requestItem = new RequestItem("Aliens", "Science Fiction", "1986");
        requestItems.add(requestItem);

        requestItem = new RequestItem("Chicken Run", "Animation", "2000");
        requestItems.add(requestItem);

        requestItem = new RequestItem("Back to the Future", "Science Fiction", "1985");
        requestItems.add(requestItem);

        requestItem = new RequestItem("Raiders of the Lost Ark", "Action & Adventure", "1981");
        requestItems.add(requestItem);
        myRequestAdapter.notifyDataSetChanged();
    }

}
