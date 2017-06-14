package com.mera.bottlerocketstestproject.view.main;

import com.mera.bottlerocketstestproject.base.mvp.presenter.BaseRxPresenter;
import com.mera.bottlerocketstestproject.base.mvp.view.BaseView;

public interface MainActivityContract {

    interface View extends BaseView {
    }

    abstract class Presenter extends BaseRxPresenter<View> {
    }
}
