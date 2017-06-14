package com.mera.bottlerocketstestproject.base.di;

import android.content.Context;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;

import com.mera.bottlerocketstestproject.base.mvp.presenter.provider.BasePresenterLoaderProvider;
import com.mera.bottlerocketstestproject.base.mvp.presenter.provider.BasePresenterProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private final LoaderManager mLoaderManager;

    public ActivityModule(AppCompatActivity activity) {
        mLoaderManager = activity.getSupportLoaderManager();
    }

    @Provides
    public BasePresenterProvider providePresenterProvider(Context context) {
        return new BasePresenterLoaderProvider(context, mLoaderManager);
    }
}
