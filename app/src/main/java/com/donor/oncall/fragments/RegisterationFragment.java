package com.donor.oncall.fragments;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.donor.oncall.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by prashanth on 3/1/16.
 */
public class RegisterationFragment extends BaseFragment {

    private static  View rootView=null;
    private static EditText usrnameField,pwdField,cfrmpwdField,nameField,dobField;
    private static Spinner bloodGroupField;
    Calendar calendar = Calendar.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.registeration_screen, container, false);

        /*Spinner spinner = (Spinner) rootView.findViewById(R.id.bloodgrp);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.blood_group, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);*/
        return rootView;
        //return rootView;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //strict ordering
        setupFields();
        setUpDropDown();
        setupDateTimeField();
        setUpValidationForFields();
    }

    public void setupFields(){
      usrnameField = (EditText) rootView.findViewById(R.id.usrname);
      pwdField = (EditText) rootView.findViewById(R.id.pwd);
      cfrmpwdField = (EditText) rootView.findViewById(R.id.cfrmpwd);
      nameField = (EditText) rootView.findViewById(R.id.name);
      dobField = (EditText) rootView.findViewById(R.id.dob);
      bloodGroupField = (Spinner) rootView.findViewById(R.id.bloodgrp);
    }

    public void setUpDropDown(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.blood_group, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodGroupField.setAdapter(adapter);
    }

    public void setupDateTimeField(){
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDob();
            }

        };

        dobField.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateDob() {

        String dateFormat = "DD/MM/YY"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

        dobField.setText(sdf.format(calendar.getTime()));
    }

    public void setUpValidationForFields(){

    }
}
