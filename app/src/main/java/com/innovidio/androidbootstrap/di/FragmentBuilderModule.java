package com.innovidio.androidbootstrap.di;

import com.innovidio.androidbootstrap.fragment.AddCarWash;
import com.innovidio.androidbootstrap.fragment.AddFuelUp;
import com.innovidio.androidbootstrap.fragment.AddServices;
import com.innovidio.androidbootstrap.fragment.AddTrip;
import com.innovidio.androidbootstrap.fragment.BaseFragment;
import com.innovidio.androidbootstrap.fragment.MainDashboardFragment;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuilderModule {
    @ContributesAndroidInjector
    abstract BaseFragment contributeBaseFragment();

    @ContributesAndroidInjector
    abstract MainDashboardFragment contributeMainDashboardFragment();

    @ContributesAndroidInjector
    abstract AddCarWash contributeAddCarWashFragment();

    @ContributesAndroidInjector
    abstract AddFuelUp contributeAddFuelUpFragment();

    @ContributesAndroidInjector
    abstract AddServices contributeAddServiceFragment();

    @ContributesAndroidInjector
    abstract AddTrip contributeAddTripFragment();

    @Provides
    static String firstInjectionString() {
        return "My first Injection String";
    }
}
