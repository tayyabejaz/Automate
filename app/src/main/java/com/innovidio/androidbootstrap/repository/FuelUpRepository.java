package com.innovidio.androidbootstrap.repository;

import androidx.lifecycle.LiveData;

import com.innovidio.androidbootstrap.db.dao.FuelDao;
import com.innovidio.androidbootstrap.entity.FuelUp;

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

    public LiveData<List<FuelUp>> getAllFuelUps(){

        return this.fuelDao.getAllFuelUps();

    }
}
