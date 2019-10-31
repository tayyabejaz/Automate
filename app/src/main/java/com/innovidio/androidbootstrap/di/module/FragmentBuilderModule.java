package com.innovidio.androidbootstrap.di.module;

import com.innovidio.androidbootstrap.fragment.AddCarWashFragment;
import com.innovidio.androidbootstrap.fragment.FragmentAddCustomCar;
import com.innovidio.androidbootstrap.fragment.FragmentAddFuelUp;
import com.innovidio.androidbootstrap.fragment.FragmentAddNewCar;
import com.innovidio.androidbootstrap.fragment.FragmentAddServices;
import com.innovidio.androidbootstrap.fragment.FragmentAddTrip;
import com.innovidio.androidbootstrap.fragment.BaseFragment;
import com.innovidio.androidbootstrap.fragment.FragmentDrive;
import com.innovidio.androidbootstrap.fragment.FragmentMainDashboard;
import com.innovidio.androidbootstrap.fragment.FragmentMaintain;
import com.innovidio.androidbootstrap.fragment.FragmentSettings;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuilderModule {
    @ContributesAndroidInjector
    abstract BaseFragment contributeBaseFragment();

    @ContributesAndroidInjector
    abstract FragmentMainDashboard contributeMainDashboardFragment();

    @ContributesAndroidInjector
    abstract FragmentSettings contributeSettingsFragment();

    @ContributesAndroidInjector
    abstract FragmentMaintain contributeMaintainFragment();

    @ContributesAndroidInjector
    abstract FragmentDrive contributeDriveFragment();

    @ContributesAndroidInjector
    abstract AddCarWashFragment contributeAddCarWashFragment();

    @ContributesAndroidInjector
    abstract FragmentAddFuelUp contributeAddFuelUpFragment();

    @ContributesAndroidInjector
    abstract FragmentAddServices contributeAddServiceFragment();

    @ContributesAndroidInjector
    abstract FragmentAddTrip contributeAddTripFragment();

    @ContributesAndroidInjector
    abstract FragmentAddNewCar contributeAddNewCarFragment();

    @ContributesAndroidInjector
    abstract FragmentAddCustomCar contributeAddCustomCarFragment();

    @Provides
    static String firstInjectionString() {
        return "My first Injection String";
    }
}
