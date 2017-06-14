package com.mera.bottlerocketstestproject.data.source.remote;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class StoresApiFactory {
    final Retrofit requestBuilder;

    public StoresApiFactory() {
        requestBuilder = new Retrofit.Builder()
                .baseUrl(ServerFactory.getUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public StoresApi createApi() {
        return requestBuilder.create(StoresApi.class);
    }
}
