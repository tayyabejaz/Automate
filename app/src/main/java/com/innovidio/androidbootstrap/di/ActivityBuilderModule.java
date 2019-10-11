package com.innovidio.androidbootstrap.di;

import com.innovidio.androidbootstrap.activity.FormActivity;
import com.innovidio.androidbootstrap.activity.MainActivity;
import com.innovidio.androidbootstrap.fragment.AddCarWash;
import com.innovidio.androidbootstrap.fragment.BaseFragment;
import com.innovidio.androidbootstrap.fragment.DriveFragment;
import com.innovidio.androidbootstrap.fragment.MainDashboardFragment;
import com.innovidio.androidbootstrap.fragment.MaintainFragment;
import com.innovidio.androidbootstrap.fragment.NavigationStartingFragment;
import com.innovidio.androidbootstrap.fragment.SettingsFragment;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector
    abstract FormActivity contributeFormActivity();

    @ContributesAndroidInjector
    abstract BaseFragment contributeBaseFragment();

    @ContributesAndroidInjector
    abstract AddCarWash contributeAddCarWashFragment();
//    @ContributesAndroidInjector
//    abstract DriveFragment contributeDriveFragment();
//    @ContributesAndroidInjector
//    abstract MainDashboardFragment contributeMainDashboardFragment();
//    @ContributesAndroidInjector
//    abstract MaintainFragment contributeMaintainFragment();
//    @ContributesAndroidInjector
//    abstract SettingsFragment contributeSettingsFragment();
//    @ContributesAndroidInjector
//    abstract NavigationStartingFragment contributeNavigationStartFragment();

    @Provides
    static String firstInjectionString(){
        return "My first Injection String";
    }
}
