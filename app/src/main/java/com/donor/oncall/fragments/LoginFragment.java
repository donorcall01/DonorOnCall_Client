package com.donor.oncall.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.donor.oncall.DonorApi.DonorApi;
import com.donor.oncall.DonorApi.ServiceGenerator;
import com.donor.oncall.MainActivity;
import com.donor.oncall.R;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

/**
 * Created by prashanth on 3/1/16.
 */
public class LoginFragment extends BaseFragment {
    private static String TAG = "LoginActivity";
    private static String errorMess="The Username or Password is wrong";
    private static EditText passwordField,usernameField;
    private static String password,username;
    private static ProgressDialog progressDialog;
    private static  View rootView=null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.login_screen, container, false);
        setupValueField();
        setUpLogin();
        setUpRegisteration();
        setupProgressDialog();
        return rootView;

        //return rootView;
    }

    public void setupValueField(){
        usernameField = (EditText) rootView.findViewById(R.id.usrname);
        passwordField = (EditText) rootView.findViewById(R.id.pwd);
    }

    public void setupProgressDialog(){
        progressDialog= new ProgressDialog(LoginFragment.this.getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Logging you into app");
        progressDialog.setCancelable(false);
    }

    public  boolean validateEmailId(String email){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        boolean status = false;
        if (email == null || email.isEmpty()){
            usernameField.setError("User name cannot be empty");
        }else {
            if (email.matches(emailPattern)){
               status = true;
            }else {
                usernameField.setError("Not a valid email Address");
            }
        }
     return status;
    }

    public  boolean validatePassword(String password){
        //return password != null || !password.isEmpty();
        boolean status = true;
        if (password == null || password.isEmpty()){
            passwordField.setError("Password cannot be empty");
            status = false;
        }
        return status;
    }

    public boolean  validateEmailIdAndPassword(){
        username = usernameField.getText().toString();
        password =passwordField.getText().toString();
        boolean status = false;
        if (validateEmailId(username) && validatePassword(password))
            status = true;
        return status;
    }
    public  void setUpLogin(){
        final DonorApi donorApi = ServiceGenerator.createService(DonorApi.class);
        rootView.findViewById(R.id.signin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();
                if (validateEmailIdAndPassword()) {
                    try {

                        jsonObject.put("userName", username);
                        jsonObject.put("passWord", password);


                        donorApi.login(jsonObject, new Callback<Response>() {
                            @Override
                            public void success(Response response, Response response2) {
                                Log.d(TAG, "Success " + response.getReason());
                                String json = new String(((TypedByteArray) response.getBody()).getBytes());
                                JsonParser parser = new JsonParser();
                                JsonElement responseJson = parser.parse(json);
                                signUpResponse(responseJson.getAsJsonObject());
                                Log.d(TAG, "Success " + json);
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Log.d(TAG, "Success " + error.getMessage());
                            }
                        });


                    } catch (Exception e) {

                    }
                }
            }
        });
    }

    public void signUpResponse(JsonObject jsonObject){
        if (!jsonObject.get("success").getAsBoolean()){
            progressDialog.show();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(getActivity(),errorMess,Toast.LENGTH_LONG).show();
        }
    }

    public void setUpRegisteration(){
        rootView.findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceViewFragment(new RegisterationFragment(),true);
            }
        });
    }
}
