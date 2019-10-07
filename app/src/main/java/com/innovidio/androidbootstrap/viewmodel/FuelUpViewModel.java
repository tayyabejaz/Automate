package com.innovidio.androidbootstrap.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.innovidio.androidbootstrap.entity.Alarm;
import com.innovidio.androidbootstrap.entity.Maintenance;
import com.innovidio.androidbootstrap.entity.FuelUp;
import com.innovidio.androidbootstrap.repository.AlarmRepository;
import com.innovidio.androidbootstrap.repository.FuelUpRepository;
import com.innovidio.androidbootstrap.repository.MaintenanceRepository;

import java.util.List;

import javax.inject.Inject;

public class FuelUpViewModel extends ViewModel {
    @Inject
    FuelUpRepository fuelUpRepository;
    @Inject
    public FuelUpViewModel(FuelUpRepository fuelUpRepository) {
        this.fuelUpRepository = fuelUpRepository ;
    }


    public LiveData<List<FuelUp>> getAllFuelUps(){
        return this.fuelUpRepository.getAllFuelUps();
    }
}
