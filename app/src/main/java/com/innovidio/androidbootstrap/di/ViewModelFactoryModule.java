package com.innovidio.androidbootstrap.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.innovidio.androidbootstrap.viewmodel.AlarmViewModel;
import com.innovidio.androidbootstrap.viewmodel.CarQueryViewModel;
import com.innovidio.androidbootstrap.di.viewmodel.ViewModelKey;
import com.innovidio.androidbootstrap.di.viewmodel.ViewModelProviderFactory;
import com.innovidio.androidbootstrap.viewmodel.CarViewModel;
import com.innovidio.androidbootstrap.viewmodel.FuelUpViewModel;
import com.innovidio.androidbootstrap.viewmodel.MaintenanceViewModel;
import com.innovidio.androidbootstrap.viewmodel.TimeLineViewModel;
import com.innovidio.androidbootstrap.viewmodel.TripViewModel;
import com.innovidio.androidbootstrap.viewmodel.UserViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;


@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory viewModelProviderFactory);


    // todo add all viewModels here
    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel.class)
    abstract ViewModel bindUserViewModel(UserViewModel userViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CarQueryViewModel.class)
    abstract ViewModel bindCarQueryViewModel(CarQueryViewModel carQueryViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CarViewModel.class)
    abstract ViewModel bindCarViewModel(CarViewModel carViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AlarmViewModel.class)
    abstract ViewModel bindAlarmViewModel(AlarmViewModel alarmViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(FuelUpViewModel.class)
    abstract ViewModel bindFuelUpViewModel(FuelUpViewModel fuelUpViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MaintenanceViewModel.class)
    abstract ViewModel bindMaintenanceViewModel( MaintenanceViewModel maintenanceViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(TripViewModel.class)
    abstract ViewModel bindTripViewModel(TripViewModel tripViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(TimeLineViewModel.class)
    abstract ViewModel bindTimeLineViewModel( TimeLineViewModel timeLineViewModel);
}