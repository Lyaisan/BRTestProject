package com.mera.bottlerocketstestproject.data.source.remote;

import com.mera.bottlerocketstestproject.data.entity.Stores;
import com.mera.bottlerocketstestproject.data.source.IDataSource;

import io.reactivex.Flowable;


public class RemoteDataSource implements IDataSource {

    private StoresApi mApi;

    public RemoteDataSource(StoresApi api) {
        mApi = api;
    }

    @Override
    public Flowable<Stores> getStores() {
        return mApi.getStores();
    }
}
