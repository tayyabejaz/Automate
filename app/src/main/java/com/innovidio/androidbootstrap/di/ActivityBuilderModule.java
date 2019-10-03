package com.innovidio.androidbootstrap.di;

import com.innovidio.androidbootstrap.activity.MainActivity;
import com.innovidio.androidbootstrap.fragment.MyFragment;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector
    abstract MyFragment contributeMyFragment();

    @Provides
    static String firstInjectionString(){
        return "My first Injection String";
    }
}
