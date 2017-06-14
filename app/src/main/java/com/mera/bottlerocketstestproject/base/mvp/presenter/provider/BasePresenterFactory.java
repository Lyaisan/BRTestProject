package com.mera.bottlerocketstestproject.base.mvp.presenter.provider;


import com.mera.bottlerocketstestproject.base.mvp.presenter.BasePresenter;

public interface BasePresenterFactory<P extends BasePresenter> {
    P create();
}
