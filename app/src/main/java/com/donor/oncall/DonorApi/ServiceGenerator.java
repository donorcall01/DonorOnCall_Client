package com.donor.oncall.DonorApi;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by prashanth on 16/1/16.
 */
public class ServiceGenerator {

    public static final String API_BASE_URL = "http://ec2-52-74-63-14.ap-southeast-1.compute.amazonaws.com";

    private static RestAdapter.Builder builder = new RestAdapter.Builder()
            .setEndpoint(API_BASE_URL)
            .setClient(new OkClient(new OkHttpClient()));

    public static <S> S createService(Class<S> serviceClass) {
        RestAdapter adapter = builder.build();
        return adapter.create(serviceClass);
    }
}