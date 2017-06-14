package com.mera.bottlerocketstestproject.base.di;

import com.mera.bottlerocketstestproject.view.main.MainActivityComponent;
import com.mera.bottlerocketstestproject.view.main.MainActivityModule;
import com.mera.bottlerocketstestproject.view.main.StoresFragmentComponent;
import com.mera.bottlerocketstestproject.view.main.StoresFragmentModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {ApplicationModule.class,
        DataModule.class,
        UtilsModule.class})
@Singleton
public interface ApplicationComponent {
    MainActivityComponent plusMainActivityComponent(MainActivityModule mainActivityModule, ActivityModule activityModule);
    StoresFragmentComponent plusStoresFragmentComponent(StoresFragmentModule mainActivityModule, FragmentModule activityModule);
}
