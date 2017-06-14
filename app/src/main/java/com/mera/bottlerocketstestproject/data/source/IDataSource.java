package com.mera.bottlerocketstestproject.data.source;

import com.mera.bottlerocketstestproject.data.entity.Stores;

import io.reactivex.Flowable;

public interface IDataSource {
    Flowable<Stores> getStores();
}
