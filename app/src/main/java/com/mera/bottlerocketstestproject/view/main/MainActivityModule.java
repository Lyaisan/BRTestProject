package com.mera.bottlerocketstestproject.view.main;

import android.content.Context;

import com.mera.bottlerocketstestproject.base.mvp.presenter.provider.BasePresenterFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    @Provides
    public BasePresenterFactory<MainActivityContract.Presenter> provideMainActivityPresenterFactory(Context context){
        return () -> new MainActivityPresenter(context);
    }
}
