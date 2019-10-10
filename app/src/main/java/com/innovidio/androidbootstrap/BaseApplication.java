package com.innovidio.androidbootstrap;

import com.facebook.stetho.Stetho;
import com.innovidio.androidbootstrap.di.AppComponent;
import com.innovidio.androidbootstrap.di.DaggerAppComponent;


import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class BaseApplication extends DaggerApplication {

    private static final String TAG = "BaseApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }


    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        AppComponent appComponent = DaggerAppComponent.builder().applicattion(this).build();
        return appComponent;
    }
}
