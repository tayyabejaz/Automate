package com.innovidio.androidbootstrap.viewmodel;

import android.widget.Toast;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.innovidio.androidbootstrap.entity.Maintenance;
import com.innovidio.androidbootstrap.entity.MaintenanceWithAlarms;
import com.innovidio.androidbootstrap.entity.models.TimeLine;
import com.innovidio.androidbootstrap.repository.MaintenanceRepository;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class MaintenanceViewModel extends ViewModel {
    @Inject
    MaintenanceRepository maintenanceRepository;
    @Inject
    public MaintenanceViewModel(MaintenanceRepository maintenanceRepository) {
        this.maintenanceRepository = maintenanceRepository ;
    }

    public LiveData<List<Maintenance>> getAllMaintenanceService(){
        return this.maintenanceRepository.getAllMaintenanceService();
    }


    public void addMaintenanceService(Maintenance maintenance){
        this.maintenanceRepository.addMaintenanceService(maintenance);
    }

    public void deleteMaintenanceService(Maintenance maintenance){
        this.maintenanceRepository.deleteMaintenanceService(maintenance);
    }

    public void updatMaintenanceService(Maintenance maintenance){
        this.maintenanceRepository.updateMaintenanceService(maintenance);
    }

    public LiveData<MaintenanceWithAlarms> getNextComingMaintenanceWithAlarm(int carId, Date currentdate){
        return this.maintenanceRepository.getNextComingMaintenanceWithAlarm(carId, currentdate);
    }


//    public LiveData<List<TimeLine>> getAllMaintenanceTripsAndFuelUps(){
//        return this.maintenanceRepository.getAllMaintenanceTripsAndFuelUps();
//    }

}