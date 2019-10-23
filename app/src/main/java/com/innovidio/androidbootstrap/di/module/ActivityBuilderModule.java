package com.innovidio.androidbootstrap.di.module;

import com.innovidio.androidbootstrap.activity.FilterResultActivity;
import com.innovidio.androidbootstrap.activity.FormActivity;
import com.innovidio.androidbootstrap.activity.MainActivity;
import com.innovidio.androidbootstrap.dashboard.SpeedDashboardActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector
    abstract FormActivity contributeFormActivity();

    @ContributesAndroidInjector
    abstract FilterResultActivity contributeFilteredResultActivity();

    @ContributesAndroidInjector
    abstract SpeedDashboardActivity contributeSpeedDashboardActivity();


}
