package com.mera.bottlerocketstestproject.base.di;

import com.mera.bottlerocketstestproject.base.utils.RxUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
@Singleton
public class UtilsModule {

    @Provides
    @Singleton
    public RxUtils provideRxUtils() {
        return new RxUtils(AndroidSchedulers.mainThread(), Schedulers.io());
    }
}
