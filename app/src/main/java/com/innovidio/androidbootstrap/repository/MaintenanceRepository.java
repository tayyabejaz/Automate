package com.innovidio.androidbootstrap.repository;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.innovidio.androidbootstrap.db.dao.MaintenanceDao;
import com.innovidio.androidbootstrap.entity.Maintenance;
import com.innovidio.androidbootstrap.entity.MaintenanceWithAlarms;
import com.innovidio.androidbootstrap.entity.models.TimeLine;
import com.innovidio.androidbootstrap.interfaces.TimeLineItem;

import java.util.Date;
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


    public void addMaintenanceService(Maintenance maintenance){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                maintenanceDao.insert(maintenance);
                return null;
            }
        }.execute();
    }

    public void deleteMaintenanceService(Maintenance maintenance){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                maintenanceDao.delete(maintenance);
                return null;
            }
        }.execute();
    }

    public void updateMaintenanceService(Maintenance maintenance){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                maintenanceDao.update(maintenance);
                return null;
            }
        }.execute();
    }

//    public LiveData<List<MaintenanceWithAlarms>> getAllMaintenanceTripsAndFuelUps(){
//        return this.maintenanceDao.getAllFromMaintenanceTripsAndFuelUp();
//    }

    public LiveData<List<Maintenance>> getAllMaintenanceForTimeLine(int carId){
        return this.maintenanceDao.getAllMaintenanceForTimeline(carId);
    }

    public List<Maintenance> getAllMaintenanceTimeLine(int carId){
        return this.maintenanceDao.getAllMaintenanceTimeline(carId);
    }

    public List<Maintenance> getAllMaintenanceWithTypeTimeLine(int carId, TimeLineItem.Type type){
        return this.maintenanceDao.getAllMaintenanceWithTypeTimeLine(carId, type);
    }

    public LiveData<MaintenanceWithAlarms> getNextComingMaintenanceWithAlarm(int carId, Date currentDate){
        return this.maintenanceDao.getNextMaintenanceWithAlarm(carId, currentDate);
    }

    public LiveData<Maintenance> getLastMaintenance(int carId){
        return this.maintenanceDao.getLastMaintenance(carId);
    }

    public LiveData<Integer> getMaintenanceCountBetweenDateRange(int carId, Date starDate, Date endDate){
        return this.maintenanceDao.getMaintenanceCountBetweenDateRange(carId, starDate, endDate);
    }
}


