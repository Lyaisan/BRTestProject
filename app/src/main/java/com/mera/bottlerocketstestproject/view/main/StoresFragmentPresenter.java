package com.mera.bottlerocketstestproject.view.main;

import com.mera.bottlerocketstestproject.base.utils.RxUtils;
import com.mera.bottlerocketstestproject.data.entity.JStore;
import com.mera.bottlerocketstestproject.data.entity.Stores;
import com.mera.bottlerocketstestproject.data.source.IDataSource;

import io.reactivex.functions.Consumer;

public class StoresFragmentPresenter extends StoresFragmentContract.Presenter<JStore> {

    private RxUtils mRxUtils;
    private IDataSource mRepository;

    private Stores stores;
    private Throwable error;

    public StoresFragmentPresenter(RxUtils rxUtils, IDataSource repository) {
        this.mRxUtils = rxUtils;
        this.mRepository = repository;
    }

    private final Consumer<Stores> onNext = stores -> {
        this.stores = stores;
        this.error = null;
        showStores(stores);
    };

    private final Consumer<Throwable> onError = e -> {
        this.stores = null;
        this.error = e;
        showError(e);
    };

    private void showStores(Stores stores) {
        if (view != null) {
            view.showProgress(false);
            view.setVisibleList(true);
            if (!stores.getStores().isEmpty()) {
                view.showErrorEmptyViewSwitcher(false, null);
                view.showStores(stores.getStores());
            } else {
                view.showErrorEmptyViewSwitcher(true, null);
            }
        }
    }

    private void showError(Throwable e) {
        if (view != null) {
            view.showProgress(false);
            view.setVisibleList(false);
            view.showErrorEmptyViewSwitcher(true, e);
        }
    }

    @Override
    public void onRefresh() {
        addDisposable(mRepository.getStores()
                .doOnSubscribe(disposable -> {
                    view.showProgress(true);
                    view.setVisibleList(false);
                })
                .doAfterTerminate(() -> {
                    view.showProgress(false);
                    view.setVisibleList(true);
                })
                .subscribeOn(mRxUtils.getBackgroundScheduler())
                .observeOn(mRxUtils.getMainScheduler())
                .subscribe(onNext, onError));
    }

    @Override
    public void loadData() {
        if (stores != null && stores.getStores() != null) {
            showStores(stores);
        } else if (error != null) {
            showError(error);
        } else {
            onRefresh();
        }
    }
}
