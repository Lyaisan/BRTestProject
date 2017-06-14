package com.mera.bottlerocketstestproject.base.mvp.view;

import android.support.v4.app.Fragment;
import android.os.Bundle;

import com.mera.bottlerocketstestproject.BaseApplication;
import com.mera.bottlerocketstestproject.base.di.ApplicationComponent;
import com.mera.bottlerocketstestproject.base.mvp.presenter.BasePresenter;
import com.mera.bottlerocketstestproject.base.mvp.presenter.provider.BasePresenterFactory;
import com.mera.bottlerocketstestproject.base.mvp.presenter.provider.BasePresenterProvider;

import javax.inject.Inject;

public abstract class BaseFragment<V extends BaseView, P extends BasePresenter<V>> extends Fragment
        implements BasePresenterProvider.Listener<P>, BaseView {

    @Inject
    BasePresenterProvider presenterProvider;

    @Inject
    BasePresenterFactory<P> presenterFactory;

    protected P presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies(((BaseApplication) getActivity().getApplication()).getApplicationComponent());
    }

    /**
     * Inject dependencies for whole class.
     * NOTE: presenterFactory and presenterProvider classes must be injected!
     */
    protected abstract void injectDependencies(ApplicationComponent applicationComponent);

    @Override
    public void onResume() {
        super.onResume();
        presenterProvider.requestPresenter(presenterFactory, this);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (presenter != null) {
            presenter.unbind();
        }
    }

    @Override
    public void onPresenterCreated(P presenter) {
        if (presenter != null) {
            presenter.unbind();
        }
        this.presenter = presenter;
        this.presenter.bind((V) this);
    }
}
