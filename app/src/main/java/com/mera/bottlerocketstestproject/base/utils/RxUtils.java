package com.mera.bottlerocketstestproject.base.utils;

import io.reactivex.Scheduler;

public class RxUtils {

    private final Scheduler mMainScheduler;
    private final Scheduler mBackgroundScheduler;

    public RxUtils(Scheduler mainScheduler, Scheduler backgroundScheduler) {
        mMainScheduler = mainScheduler;
        mBackgroundScheduler = backgroundScheduler;
    }

    public Scheduler getMainScheduler() {
        return mMainScheduler;
    }

    public Scheduler getBackgroundScheduler() {
        return mBackgroundScheduler;
    }
}
