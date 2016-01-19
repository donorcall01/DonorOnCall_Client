package com.donor.oncall.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.donor.oncall.DonorApi.DonorApi;
import com.donor.oncall.DonorApi.ServiceGenerator;
import com.donor.oncall.R;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

/**
 * Created by prashanth on 3/1/16.
 */
public class RegisterationFragment extends BaseFragment {

    private static  View rootView=null;
    private static EditText usrnameField,pwdField,cfrmpwdField,nameField,dobField;
    private static CheckBox tos,donor,recipient;
    private static Spinner bloodGroupField;
    private static String TAG ="RegisterationFragment";
    private static String password,username,cfrmpassword,name,dob,bloodGrp,type;
    private static ProgressDialog progressDialog;
    Calendar calendar = Calendar.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.registeration_screen, container, false);
        return rootView;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //strict ordering
        setupFields();
        setUpDropDown();
        setupDateTimeField();
        setUpRegisteration();
        setupProgressDialog();
    }

    public void setupProgressDialog(){
        progressDialog= new ProgressDialog(RegisterationFragment.this.getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Hang on ! Registering your Details with us");
        progressDialog.setCancelable(false);
    }

    public void setupFields(){
      usrnameField = (EditText) rootView.findViewById(R.id.usrname);
      pwdField = (EditText) rootView.findViewById(R.id.pwd);
      cfrmpwdField = (EditText) rootView.findViewById(R.id.cfrmpwd);
      nameField = (EditText) rootView.findViewById(R.id.name);
      dobField = (EditText) rootView.findViewById(R.id.dob);
      bloodGroupField = (Spinner) rootView.findViewById(R.id.bloodgrp);
      donor = (CheckBox) rootView.findViewById(R.id.donor);
      recipient = (CheckBox) rootView.findViewById(R.id.recipient);
    }

    public void setUpDropDown(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.blood_group, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodGroupField.setAdapter(adapter);
    }

    public void setupDateTimeField(){
        calendar.set(1991,0,1);
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

        dobField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new DatePickerDialog(getActivity(), date, calendar
                            .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)).show();
                }

            }
        });
    }

    private void updateDob() {
        String dateFormat = "yyyy-MM-DD"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        dobField.setText(sdf.format(calendar.getTime()));
    }

    public  boolean isValidEmail(String value){
        boolean status = true;
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (isNullOrEmpty(value)){
            if (!value.matches(emailPattern)){
                usrnameField.setError("Enter a valid Email-id");
                status = false;
            }
        }else{
            usrnameField.setError("User name cannot be empty");
            status = false;
        }
        return status;
        }


    public boolean isNullOrEmpty(String value){
        boolean status = true;
        if (value == null || value.isEmpty())
            status =false;
        return  status;
    }

    public boolean checkPwd(){
        boolean status = true;
        if (isNullOrEmpty(password)){
            if (isNullOrEmpty(cfrmpassword)){
                  if (!password.equals(cfrmpassword)){
                      cfrmpwdField.setError("Passwords donot match");
                      status = false;
                  }
            }else {
                cfrmpwdField.setError("Confirm password cannot be empty");
                status=false;
            }

        }else {
            pwdField.setError("Password cannot be empty");
            status=false;
        }
        return status;
    }


    public boolean checkName(){
        boolean status = true;
        if (!isNullOrEmpty(name)){
            nameField.setError("Name  cannot be empty");
            status = false;
        }
        return status;
    }

    public boolean checkDob(){
        boolean status = true;
        if (!isNullOrEmpty(dob)){
            dobField.setError("DOB  cannot be empty");
            status = false;
        }
        return status;
    }

    public boolean checkDonorRecipient(){
        boolean status = true;
        if (donor.isChecked()){
            type = "Donor";
        }else {
            status =false;
            donor.setError("Select either donor or recipient");
        }
        if (recipient.isChecked()){
            type = "Recipient";
        }

        return status;
    }

    public boolean validateFields(){
        boolean status = true;

        if (isValidEmail(username)
                && checkPwd()
                && checkName()
                && checkDob()
                && checkDonorRecipient()) {

        }else {
            status = false;
        }
        return status;
    }

    public void setFieldValues(){
        username=usrnameField.getText().toString();
        password=pwdField.getText().toString();
        cfrmpassword=cfrmpwdField.getText().toString();
        dob=dobField.getText().toString();
        name=nameField.getText().toString();
        bloodGrp =bloodGroupField.getSelectedItem().toString();
    }

    public void setUpRegisteration(){
        final DonorApi donorApi = ServiceGenerator.createService(DonorApi.class);
       rootView.findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               setFieldValues();
               JsonObject jsonObject = new JsonObject();
               if (validateFields()){
                 try {
                     progressDialog.show();
                     jsonObject.addProperty("userName", username);
                     jsonObject.addProperty("passWord", password);
                     jsonObject.addProperty("name", name);
                     jsonObject.addProperty("bloodGroup", bloodGrp);
                     jsonObject.addProperty("type", type);
                     jsonObject.addProperty("dob", dob);
                     Log.d(TAG,jsonObject.toString());
                     donorApi.register(jsonObject,new Callback< Response >() {
                         @Override
                         public void success(Response response, Response response2) {
                             Log.d(TAG, "Success " + response.getReason());
                             String json = new String(((TypedByteArray) response.getBody()).getBytes());
                             replaceViewFragment(new LoginFragment(),true);
                             JsonParser parser = new JsonParser();
                             JsonElement responseJson = parser.parse(json);
                             progressDialog.setMessage("Thank you for Signin up");
                             signUpResponse(responseJson.getAsJsonObject());
                             Log.d(TAG, "Success " + json);
                         }

                         @Override
                         public void failure(RetrofitError error) {
                             Log.d(TAG, "Success " + error.getMessage());
                         }
                     });
                 }catch (Exception e){

                 }
               }
           }
       });
    }

    public void signUpResponse(JsonObject jsonObject){

        if (jsonObject.get("registration").getAsString().equals("success"))
        {
            progressDialog.hide();
            replaceViewFragment(new LoginFragment(),true);
        }
    }
}
