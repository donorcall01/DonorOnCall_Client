package com.donor.oncall.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.donor.oncall.R;

/**
 * Created by prashanth on 29/1/16.
 */
public class RequestBloodFragment extends BaseFragment {

    private  View rootView=null;
    private  EditText hospitalField,patientField,purposeField,unitsField,howsoonField;
    private  Spinner bloodGroupField;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.request_blood, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //strict ordering
        setUpViews();
        setUpRequestButton();
        setUpDropDown();

    }

    public void setUpDropDown(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.blood_group, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodGroupField.setAdapter(adapter);
    }

    public void setUpViews(){
        hospitalField = (EditText) rootView.findViewById(R.id.hospital);
        patientField = (EditText) rootView.findViewById(R.id.patient);
        purposeField = (EditText) rootView.findViewById(R.id.purpose);
        unitsField = (EditText) rootView.findViewById(R.id.units);
        howsoonField = (EditText) rootView.findViewById(R.id.howsoon);
        bloodGroupField = (Spinner) rootView.findViewById(R.id.bloodgrp);
    }

    public void setUpRequestButton(){
        rootView.findViewById(R.id.requestBlood).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
