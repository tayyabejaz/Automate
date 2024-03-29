package com.innovidio.androidbootstrap.di.module;


import android.app.Application;
import android.content.Context;

import com.innovidio.androidbootstrap.BaseApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

}
