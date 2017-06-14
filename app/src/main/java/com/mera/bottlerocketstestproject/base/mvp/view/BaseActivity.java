package com.mera.bottlerocketstestproject.base.mvp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mera.bottlerocketstestproject.BaseApplication;
import com.mera.bottlerocketstestproject.base.di.ApplicationComponent;
import com.mera.bottlerocketstestproject.base.mvp.presenter.BasePresenter;
import com.mera.bottlerocketstestproject.base.mvp.presenter.provider.BasePresenterFactory;
import com.mera.bottlerocketstestproject.base.mvp.presenter.provider.BasePresenterProvider;

import javax.inject.Inject;

public abstract class BaseActivity<V extends BaseView, P extends BasePresenter<V>> extends AppCompatActivity
        implements BasePresenterProvider.Listener<P>, BaseView {

    @Inject
    BasePresenterProvider presenterProvider;

    @Inject
    BasePresenterFactory<P> presenterFactory;

    protected P presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies(((BaseApplication)getApplication()).getApplicationComponent());
    }

    /**
     *  Inject dependencies for whole class.
     *  NOTE: presenterFactory and presenterProvider classes must to be injected!
     *
     * @param applicationComponent
     */
    protected abstract void injectDependencies(ApplicationComponent applicationComponent);

    @Override
    protected void onStart() {
        super.onStart();
        presenterProvider.requestPresenter(presenterFactory, this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (presenter != null) {
            presenter.unbind();
        }
    }

    @Override
    public void onPresenterCreated(P presenter) {
        this.presenter = presenter;
        this.presenter.bind((V) this);
    }
}
