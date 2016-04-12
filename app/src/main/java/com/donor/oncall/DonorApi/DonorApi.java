package com.donor.oncall.DonorApi;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * Created by prashanth on 16/1/16.
 */
public interface DonorApi {
    @Headers("Content-Type: application/json")
    @POST("/login")
    void login(@Body JsonObject jsonVal, Callback<Response> callback);


    @Headers("Content-Type: application/json")
    @POST("/register")
    void register(@Body JsonObject jsonVal, Callback<Response> callback);

    @Headers("Content-Type: application/json")
    @POST("/doc/donor")
    void requestDonor(@Body JsonObject jsonVal, Callback<Response> callback);
}
