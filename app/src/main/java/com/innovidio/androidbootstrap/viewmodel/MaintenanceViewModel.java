package com.innovidio.androidbootstrap.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.innovidio.androidbootstrap.entity.Maintenance;
import com.innovidio.androidbootstrap.entity.MaintenanceWithAlarm;
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

    public void updateMaintenanceService(Maintenance maintenance){
        this.maintenanceRepository.updateMaintenanceService(maintenance);
    }

    public LiveData<Maintenance> getNextComingMaintenance(int carId, Date currentdate){
        return this.maintenanceRepository.getNextComingMaintenance(carId, currentdate);
    }

    public LiveData<Maintenance> getLastMaintenance(int carId){
        return this.maintenanceRepository.getLastMaintenance(carId);
    }

    public LiveData<Integer> getMaintenanceCountBetweenDateRange(int carId, Date starDate, Date endDate){
        return this.maintenanceRepository.getMaintenanceCountBetweenDateRange(carId, starDate, endDate);
    }

    public LiveData<Long> getMaintenanceCostBetweenDateRange(int carId, Date starDate, Date endDate){
        return this.maintenanceRepository.getMaintenanceCostBetweenDateRange(carId, starDate, endDate);
    }

//    public LiveData<List<TimeLine>> getAllMaintenanceTripsAndFuelUps(){
//        return this.maintenanceRepository.getAllMaintenanceTripsAndFuelUps();
//    }

}