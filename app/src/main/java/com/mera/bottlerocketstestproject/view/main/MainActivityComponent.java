package com.mera.bottlerocketstestproject.view.main;

import com.mera.bottlerocketstestproject.base.di.ActivityModule;

import dagger.Subcomponent;

@Subcomponent(modules = { MainActivityModule.class, ActivityModule.class})
public interface MainActivityComponent {
    void inject(MainActivity mainActivity);
}
