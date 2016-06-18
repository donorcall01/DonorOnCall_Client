package com.donor.oncall.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.donor.oncall.DocSessionManager;
import com.donor.oncall.DonorApi.DonorApi;
import com.donor.oncall.DonorApi.ServiceGenerator;
import com.donor.oncall.R;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

/**
 * Created by prashanth on 29/1/16.
 */
public class RequestBloodFragment extends BaseFragment {

    private  View rootView=null;
    private  EditText hospitalField,patientField,purposeField,unitsField,howsoonField,phnnumberField;
    private  Spinner bloodGroupField;
    private  String hospital,patient,purpose,bloodGrp,phnnumber;
    private double lattitude,longitude;
    private  int units,howsoon;
    private static ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.request_blood, container, false);
        if (getArguments() !=null){
            lattitude = getArguments().getDouble("lattitude");
            longitude = getArguments().getDouble("longitude");
        }
        Log.d("GeoSpace",String.valueOf(lattitude));
        Log.d("GeoSpace",String.valueOf(longitude));
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("GeoSpace",String.valueOf(lattitude));
        Log.d("GeoSpace",String.valueOf(longitude));
        //strict ordering
        setUpViews();
        setUpRequestButton();
        setUpDropDown();
        setupProgressDialog();
    }
    public void setupProgressDialog(){
        progressDialog= new ProgressDialog(RequestBloodFragment.this.getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Hang on ! Registering your request with us");
        progressDialog.setCancelable(false);
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
        phnnumberField = (EditText) rootView.findViewById(R.id.phnnumber);
    }

    public void setFields(){
        hospital=hospitalField.getText().toString();
        patient=patientField.getText().toString();
        purpose=purposeField.getText().toString();
        bloodGrp =bloodGroupField.getSelectedItem().toString();
        phnnumber =phnnumberField.getText().toString();
    }

    public boolean checkPhoneNumber(){
        boolean status =true;
        if (!isNullOrEmpty(phnnumber))
        {
            phnnumberField.setError("Phone Number cannot be empty");
            status =false;
        }
        return status;
    }

    public boolean checkHospital(){
        boolean status = true;
        if (!isNullOrEmpty(hospital)){
            hospitalField.setError("Hospital Name / Doctor Name cannot be empty");
            status = false;
        }
        return status;
    }
    public boolean checkPatient(){
        boolean status = true;
        if (!isNullOrEmpty(patient)){
            patientField.setError("Patient Name cannot be empty");
            status = false;
        }
        return status;
    }
    public boolean checkPurpose(){
        boolean status = true;
        if (!isNullOrEmpty(purpose)){
            purposeField.setError("Hospital Name / Doctor Name cannot be empty");
            status = false;
        }
        return status;
    }

    public boolean checkUnits(){
        boolean status = true;
        String _units = unitsField.getText().toString();
        if (!isNullOrEmpty(_units)){
            unitsField.setError("Units  cannot be empty");
            status = false;
        }else{
            try {
                units=Integer.parseInt(_units);
            }catch (NumberFormatException e){
                status = false;
                unitsField.setError("Units Field cannot String");
            }
        }

        return status;
    }


    public boolean checkHowsoon(){
        boolean status = true;
        String _units = howsoonField.getText().toString();
        if (!isNullOrEmpty(_units)){
            howsoonField.setError("Howsoon  cannot be empty");
            status = false;
        }else {
            try {
                howsoon = Integer.parseInt(_units);
            } catch (NumberFormatException e) {
                status = false;
                howsoonField.setError("Howsoon Field cannot String");
            }
        }
        return status;
    }


    public boolean validateFields(){
        boolean status = true;

        if (checkHospital()
                && checkPatient()
                && checkPhoneNumber()
                && checkPurpose()
                && checkUnits()
                && checkHowsoon()) {

        }else {
            status = false;
        }
        return status;
    }

    public void setUpRequestButton(){
        final DonorApi donorApi = ServiceGenerator.createService(DonorApi.class);
        rootView.findViewById(R.id.requestBlood).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFields();
               if (validateFields()){
                   progressDialog.show();
                   JsonObject json = new JsonObject();
                   json.addProperty("token",DocSessionManager.getValueKey(DocSessionManager.KEY_ACESS_TOKEN));
                   JsonObject jsonObject = new JsonObject();
                   jsonObject.addProperty("bloodGroup", bloodGrp);
                   jsonObject.addProperty("hospitalName", hospital);
                   jsonObject.addProperty("hospitalAddress","hospitalAddress");
                   jsonObject.addProperty("patientName",patient);
                   jsonObject.addProperty("comment","");
                   jsonObject.addProperty("purpose", purpose);
                   jsonObject.addProperty("requiredUnits",units);
                   jsonObject.addProperty("requiredWithin", howsoon);
                   jsonObject.addProperty("contactNumber", phnnumber);
                   jsonObject.addProperty("lat",lattitude);
                   jsonObject.addProperty("lon",longitude);
                   json.add("data",jsonObject);
                   Log.d("requestDonor", json.toString());


                   donorApi.requestDonor(json, new Callback<Response>() {
                       @Override
                       public void success(Response response, Response response2) {
                           String json = new String(((TypedByteArray) response.getBody()).getBytes());
                           JsonParser parser = new JsonParser();
                           JsonElement responseJson = parser.parse(json);
                           progressDialog.hide();
                           new AlertDialog.Builder(getContext())
                                   .setTitle("Donors have been contacted")
                                   .setMessage("You're new donor will be trackable in map once your request has been approved.")
                                   .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                       public void onClick(DialogInterface dialog, int which) {
                                           dialog.dismiss();
                                         /*  Bundle message = new Bundle();
                                           message.putBoolean("showRecipientAlert", true);
                                           MapViewFragment mapViewFragment = new MapViewFragment();
                                           mapViewFragment.setArguments(message);
                                           replaceViewFragment(mapViewFragment, false);*/
                                       }
                                   })
                                   .show();

                       }

                       @Override
                       public void failure(RetrofitError error) {
                           //Log.d(TAG, "Success " + error.getMessage());
                       }
                   });
               }
            }
        });
    }
}
