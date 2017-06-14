package com.mera.bottlerocketstestproject.data.source;

import com.mera.bottlerocketstestproject.data.entity.Stores;
import com.mera.bottlerocketstestproject.data.source.local.ICache;

import io.reactivex.Flowable;

public class StoresRepository implements IDataSource {

    private IDataSource remoteDataSource;
    private ICache localDataSource;

    public StoresRepository(IDataSource remoteDataSource, ICache localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    @Override
    public Flowable<Stores> getStores() {
        return remoteDataSource.getStores()
                .doOnNext(stores -> localDataSource.save(stores.getStores()))
                .onErrorResumeNext(throwable -> {
                    return getStoresFromLocal();
                });
    }

    private Flowable<Stores> getStoresFromLocal() {
        Stores localStores = localDataSource.getStores();
        return Flowable.just(localStores);
    }
}
