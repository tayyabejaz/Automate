package com.innovidio.androidbootstrap.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.innovidio.androidbootstrap.db.dao.FuelDao;
import com.innovidio.androidbootstrap.entity.FuelUp;
import com.innovidio.androidbootstrap.interfaces.TimeLineItem;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FuelUpRepository {

    FuelDao fuelDao;

    @Inject
    public FuelUpRepository(FuelDao fuelDao){
        this.fuelDao = fuelDao;
    }

    public void addFuelUp(FuelUp fuelUp){
        this.fuelDao.insert(fuelUp);
    }

    public LiveData<List<FuelUp>> getAllFuelUps(){
        return this.fuelDao.getAllFuelUps();
    }

    public LiveData<List<FuelUp>> getMonthlyFuelUp(Date startDate, Date endDate){
        return this.fuelDao.getMonthlyFuelConsume(startDate, endDate);
    }

    public LiveData<FuelUp> getRecentFuelUp(){
        return this.fuelDao.getRecentFuelUp();
    }


    public LiveData<List<FuelUp>> getAllFuelUpsForTimeLine(){
        return this.fuelDao.getAllFuelUpsForTimeline();
    }
}
