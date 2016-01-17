package com.donor.oncall.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.donor.oncall.DonorApi.LoginApi;
import com.donor.oncall.DonorApi.ServiceGenerator;
import com.donor.oncall.R;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by prashanth on 3/1/16.
 */
public class LoginFragment extends BaseFragment {
    String TAG = "LoginActivity";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.login_screen, container, false);
        final LoginApi loginApi = ServiceGenerator.createService(LoginApi.class);
        rootView.findViewById(R.id.signin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("userName","pontiyaraja@gmail.com");
                    jsonObject.put("passWord","Pan");
                }catch (Exception e){

                }

                loginApi.login(jsonObject, new Callback<Response>() {
                    @Override
                    public void success(Response response, Response response2) {
                        Log.d(TAG, "Success " + response.getReason());
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d(TAG, "Success " + error.getMessage());
                        Log.d(TAG, "url " + error.getUrl());
                        //Log.d(TAG, "Body " + error.getBody().toString());
                        Log.d(TAG, "Error " + error.toString() );
                    }
                });
            }
        });
        return rootView;
        //return rootView;
    }
}
