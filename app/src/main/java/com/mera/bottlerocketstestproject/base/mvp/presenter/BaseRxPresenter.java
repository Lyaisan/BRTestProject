package com.mera.bottlerocketstestproject.base.mvp.presenter;

import com.mera.bottlerocketstestproject.base.mvp.view.BaseView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseRxPresenter<V extends BaseView> implements BasePresenter<V> {

    private CompositeDisposable compositeDisposable;
    protected V view;

    @Override
    public void bind(V view) {
        compositeDisposable = new CompositeDisposable();
        this.view = view;
    }

    @Override
    public void unbind() {
        this.view = null;
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    protected void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Override
    public void onDestroy() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}
