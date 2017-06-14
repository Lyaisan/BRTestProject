package com.mera.bottlerocketstestproject.view.main;

import com.mera.bottlerocketstestproject.base.di.FragmentModule;

import dagger.Subcomponent;

@Subcomponent(modules = { StoresFragmentModule.class, FragmentModule.class})
public interface StoresFragmentComponent {
    void inject(StoresFragment storesFragment);
}
