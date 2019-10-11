package com.innovidio.androidbootstrap.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.innovidio.androidbootstrap.entity.Maintenance;
import com.innovidio.androidbootstrap.entity.models.TimeLine;
import com.innovidio.androidbootstrap.repository.MaintenanceRepository;

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

    public void updateaintenanceService(Maintenance maintenance){
        this.maintenanceRepository.updateMaintenanceService(maintenance);
    }


//    public LiveData<List<TimeLine>> getAllMaintenanceTripsAndFuelUps(){
//        return this.maintenanceRepository.getAllMaintenanceTripsAndFuelUps();
//    }
}