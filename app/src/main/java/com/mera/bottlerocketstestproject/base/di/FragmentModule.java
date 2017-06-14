package com.mera.bottlerocketstestproject.base.di;

import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.content.Context;

import com.mera.bottlerocketstestproject.base.mvp.presenter.provider.BasePresenterFactory;
import com.mera.bottlerocketstestproject.base.mvp.presenter.provider.BasePresenterLoaderProvider;
import com.mera.bottlerocketstestproject.base.mvp.presenter.provider.BasePresenterProvider;
import com.mera.bottlerocketstestproject.base.utils.RxUtils;
import com.mera.bottlerocketstestproject.data.entity.JStore;
import com.mera.bottlerocketstestproject.data.source.IDataSource;
import com.mera.bottlerocketstestproject.view.main.StoresFragmentContract;
import com.mera.bottlerocketstestproject.view.main.StoresFragmentPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {

    private final LoaderManager mLoaderManager;

    public FragmentModule(Fragment fragment) {
        mLoaderManager = fragment.getLoaderManager();
    }

    @Provides
    public BasePresenterProvider providePresenterProvider(Context context) {
        return new BasePresenterLoaderProvider(context, mLoaderManager);
    }

    @Provides
    public BasePresenterFactory<StoresFragmentContract.Presenter<JStore>> provideStoresPresenterFactory(RxUtils rxUtils,
                                                                                                        IDataSource iDataSource) {
        return () -> new StoresFragmentPresenter(rxUtils, iDataSource);
    }
}
