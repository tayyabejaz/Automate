package com.innovidio.androidbootstrap.repository;

import androidx.lifecycle.LiveData;

import com.innovidio.androidbootstrap.db.dao.MaintenanceDao;
import com.innovidio.androidbootstrap.entity.Maintenance;
import com.innovidio.androidbootstrap.entity.MaintenanceWithAlarms;
import com.innovidio.androidbootstrap.entity.models.TimeLine;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MaintenanceRepository {

    MaintenanceDao maintenanceDao;

    @Inject
    public MaintenanceRepository(MaintenanceDao maintenanceDao){
        this.maintenanceDao =  maintenanceDao;
    }

    public LiveData<List<Maintenance>> getAllMaintenanceService(){
        return this.maintenanceDao.getAllMaintenanceService();
    }

    public LiveData<List<TimeLine>> getAllMaintenanceTripsAndFuelUps(){
        return this.maintenanceDao.getAllFromMaintenanceTripsAndFuelUp();
    }
}


