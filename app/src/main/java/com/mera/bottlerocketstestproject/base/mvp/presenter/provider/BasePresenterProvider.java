package com.mera.bottlerocketstestproject.base.mvp.presenter.provider;

import com.mera.bottlerocketstestproject.base.mvp.presenter.BasePresenter;

public interface BasePresenterProvider {

    <P extends BasePresenter> void requestPresenter(BasePresenterFactory<P> presenterFactory, Listener<P> presenterListener);

    interface Listener<P extends BasePresenter> {
        void onPresenterCreated(P presenter);
    }
}
