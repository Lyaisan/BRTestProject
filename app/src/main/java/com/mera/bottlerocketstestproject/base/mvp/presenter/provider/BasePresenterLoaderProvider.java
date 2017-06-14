package com.mera.bottlerocketstestproject.base.mvp.presenter.provider;

import android.support.v4.app.LoaderManager;
import android.content.Context;
import android.support.v4.content.Loader;
import android.os.Bundle;

import com.mera.bottlerocketstestproject.base.mvp.presenter.BasePresenter;

public class BasePresenterLoaderProvider implements BasePresenterProvider {
    private static final int PRESENTER_LOADER_ID = 1486109620;

    private final Context mContext;
    private final LoaderManager mLoaderManager;

    public BasePresenterLoaderProvider(Context context, LoaderManager loaderManager) {
        mContext = context;
        mLoaderManager = loaderManager;
    }

    @Override
    public <P extends BasePresenter> void requestPresenter(final BasePresenterFactory<P> presenterFactory,
                                                           final Listener<P> presenterListener) {
        mLoaderManager.initLoader(PRESENTER_LOADER_ID, null, new LoaderManager.LoaderCallbacks<P>() {
            @Override
            public Loader<P> onCreateLoader(int id, Bundle args) {
                return new BaseLoader<>(mContext, presenterFactory);
            }

            @Override
            public void onLoadFinished(Loader<P> loader, P data) {
                if (loader.getId() == PRESENTER_LOADER_ID) {
                    presenterListener.onPresenterCreated(data);
                }
            }

            @Override
            public void onLoaderReset(Loader<P> loader) {
            }
        });
    }

    private static class BaseLoader<P extends BasePresenter> extends Loader<P> {

        private P mPresenter;
        private BasePresenterFactory<P> mPresenterFactory;

        public BaseLoader(Context context, BasePresenterFactory<P> factory) {
            super(context);
            mPresenterFactory = factory;
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            if (mPresenter != null) {
                deliverResult(mPresenter);
            } else {
                forceLoad();
            }
        }

        @Override
        protected void onForceLoad() {
            mPresenter = mPresenterFactory.create();
            deliverResult(mPresenter);
        }

        @Override
        protected void onReset() {
            if (mPresenter != null) {
                mPresenter.onDestroy();
                mPresenter = null;
            }
        }
    }
}
