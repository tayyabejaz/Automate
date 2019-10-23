package com.innovidio.androidbootstrap.di.module;

import com.innovidio.androidbootstrap.activity.FilterResultActivity;
import com.innovidio.androidbootstrap.activity.FormActivity;
import com.innovidio.androidbootstrap.activity.MainActivity;
<<<<<<< HEAD
import com.innovidio.androidbootstrap.activity.UserProfileActivity;
=======
import com.innovidio.androidbootstrap.dashboard.SpeedDashboardActivity;
>>>>>>> 32bb959a191ce0df4317f24d46eceb1c5f28b97d

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
    abstract UserProfileActivity contributeUserProfileActivity();

    @ContributesAndroidInjector
    abstract SpeedDashboardActivity contributeSpeedDashboardActivity();



}
