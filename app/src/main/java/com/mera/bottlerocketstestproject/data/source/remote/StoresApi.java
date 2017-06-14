package com.mera.bottlerocketstestproject.data.source.remote;

import com.mera.bottlerocketstestproject.data.entity.Stores;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;


public interface StoresApi {

    @GET("BR_Android_CodingExam_2015_Server/stores.json")
    Flowable<Stores> getStores();
}
