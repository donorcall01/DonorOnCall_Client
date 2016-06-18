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

import com.donor.oncall.DocSessionManager;
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
    private static DocSessionManager docSessionManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.login_screen, container, false);
        docSessionManager=new DocSessionManager(getActivity());
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
                JsonObject jsonObject = new JsonObject();
                if (validateEmailIdAndPassword()) {
                    progressDialog.show();
                    try {

                        jsonObject.addProperty("userName", username);
                        jsonObject.addProperty("password", password);

                        donorApi.login(jsonObject, new Callback<Response>() {
                            @Override
                            public void success(Response response, Response response2) {
                                String json = new String(((TypedByteArray) response.getBody()).getBytes());
                                JsonParser parser = new JsonParser();
                                JsonObject responseJson = parser.parse(json).getAsJsonObject();
                                String email =responseJson.get("userName").getAsString();
                                String token =responseJson.get("token").getAsString();

                                docSessionManager.createLoginSession(email,token);
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                                progressDialog.dismiss();
                                //signUpResponse(responseJson.getAsJsonObject());
                                Log.d(TAG, "Success " + json);
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                String json =  new String(((TypedByteArray)error.getResponse().getBody()).getBytes());
                                JsonParser parser = new JsonParser();
                                JsonObject errorJson = parser.parse(json).getAsJsonObject();
                                passwordField.setError(errorJson.get("error").getAsString());
                                progressDialog.dismiss();
                            }
                        });


                    } catch (Exception e) {

                    }
                }
            }
        });
    }


    public void setUpRegisteration(){
        rootView.findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceViewFragment(new RegisterationFragment(),false);
            }
        });
    }
}
