package com.donor.oncall.DonorApi;

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
    void register(@Body JSONObject jsonVal, Callback<Response> callback);
}
