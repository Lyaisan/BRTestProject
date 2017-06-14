package com.mera.bottlerocketstestproject.base.mvp.presenter;


import com.mera.bottlerocketstestproject.base.mvp.view.BaseView;

public interface BasePresenter<V extends BaseView> {
    void bind(V view);
    void unbind();
    void onDestroy();
}
