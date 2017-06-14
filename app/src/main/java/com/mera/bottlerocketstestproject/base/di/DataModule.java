package com.mera.bottlerocketstestproject.base.di;

import com.mera.bottlerocketstestproject.data.source.IDataSource;
import com.mera.bottlerocketstestproject.data.source.StoresRepository;
import com.mera.bottlerocketstestproject.data.source.local.LocalDataSource;
import com.mera.bottlerocketstestproject.data.source.remote.RemoteDataSource;
import com.mera.bottlerocketstestproject.data.source.remote.StoresApi;
import com.mera.bottlerocketstestproject.data.source.remote.StoresApiFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    @Provides
    @Singleton
    public LocalDataSource provideICache(){
        return new LocalDataSource();
    }

    @Provides
    @Singleton
    public RemoteDataSource provideIDataSource(StoresApi api) {
        return new RemoteDataSource(api);
    }

    @Provides
    @Singleton
    public StoresApi provideStoresApi() {
        return new StoresApiFactory().createApi();
    }

    @Provides
    @Singleton
    public IDataSource provideStoresRepository(RemoteDataSource remoteDataSource,
                                               LocalDataSource localDataSource) {
        return new StoresRepository(remoteDataSource, localDataSource);
    }
}
