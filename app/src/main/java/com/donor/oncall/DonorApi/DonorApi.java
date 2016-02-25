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
    @POST("/doc/gateWay")
    void login(@Body JSONObject jsonVal, Callback<Response> callback);


    @Headers("Content-Type: application/json")
    @POST("/doc/register")
    void register(@Body JsonObject jsonVal, Callback<Response> callback);

    @Headers("Content-Type: application/json")
    @POST("/doc/donor")
    void requestDonor(@Body JsonObject jsonVal, Callback<Response> callback);
}
