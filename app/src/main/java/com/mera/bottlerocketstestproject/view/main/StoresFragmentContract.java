package com.mera.bottlerocketstestproject.view.main;

import com.mera.bottlerocketstestproject.base.mvp.presenter.BaseRxPresenter;
import com.mera.bottlerocketstestproject.base.mvp.view.BaseView;
import com.mera.bottlerocketstestproject.data.entity.JStore;

import java.util.List;

public interface StoresFragmentContract {
    interface View<T> extends BaseView {
        void setVisibleList(boolean visible);
        void showProgress(boolean show);
        void showErrorEmptyViewSwitcher(boolean show, Throwable e);
        void showStores(List<JStore> data);
    }

    abstract class Presenter<T> extends BaseRxPresenter<View> {
        public abstract void onRefresh();
        public abstract void loadData();
    }
}
