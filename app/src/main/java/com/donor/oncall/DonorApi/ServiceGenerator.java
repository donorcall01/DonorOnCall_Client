package com.donor.oncall.DonorApi;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by prashanth on 16/1/16.
 */
public class ServiceGenerator {

    public static final String production_domain = "http://45.55.137.90:8000";
    public static  final String debug_domain = "http://192.168.0.103:8000";

    private static RestAdapter.Builder builder = new RestAdapter.Builder()
            .setEndpoint(production_domain)
            .setClient(new OkClient(new OkHttpClient()));

    public static <S> S createService(Class<S> serviceClass) {
        RestAdapter adapter = builder.build();
        return adapter.create(serviceClass);
    }
}
