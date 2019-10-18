package com.innovidio.androidbootstrap.di.module;

import com.innovidio.androidbootstrap.fragment.AddCarWash;
import com.innovidio.androidbootstrap.fragment.AddCustomCar;
import com.innovidio.androidbootstrap.fragment.AddFuelUp;
import com.innovidio.androidbootstrap.fragment.AddNewCar;
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

    @ContributesAndroidInjector
    abstract AddNewCar contributeAddNewCarFragment();

    @ContributesAndroidInjector
    abstract AddCustomCar contributeAddCustomCarFragment();
    @Provides
    static String firstInjectionString() {
        return "My first Injection String";
    }
}
