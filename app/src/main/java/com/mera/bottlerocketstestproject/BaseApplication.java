package com.mera.bottlerocketstestproject;

import com.mera.bottlerocketstestproject.base.di.ApplicationComponent;
import com.mera.bottlerocketstestproject.base.di.ApplicationModule;
import com.mera.bottlerocketstestproject.base.di.DaggerApplicationComponent;
import com.orm.SugarApp;

/**
 * Implementation of global application state
 */
public class BaseApplication extends SugarApp {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public ApplicationComponent getApplicationComponent() {
        if(mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        }
        return mApplicationComponent;
    }

    public void setApplicationComponent(ApplicationComponent component) {
        mApplicationComponent = component;
    }
}
