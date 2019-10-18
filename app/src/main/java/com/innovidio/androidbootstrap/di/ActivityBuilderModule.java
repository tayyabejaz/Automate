package com.innovidio.androidbootstrap.di;

import com.innovidio.androidbootstrap.activity.FormActivity;
import com.innovidio.androidbootstrap.activity.MainActivity;
import com.innovidio.androidbootstrap.fragment.AddCarWash;
import com.innovidio.androidbootstrap.fragment.AddCustomCar;
import com.innovidio.androidbootstrap.fragment.AddFuelUp;
import com.innovidio.androidbootstrap.fragment.AddNewCar;
import com.innovidio.androidbootstrap.fragment.AddServices;
import com.innovidio.androidbootstrap.fragment.AddTrip;
import com.innovidio.androidbootstrap.fragment.BaseFragment;

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
