package com.innovidio.androidbootstrap.repository;


import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveData;

import com.innovidio.androidbootstrap.db.dao.CarDao;
import com.innovidio.androidbootstrap.entity.Car;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CarsRepository {

    private final CarDao carDao;


    @Inject
    public CarsRepository(CarDao carDao) {
        this.carDao = carDao;
    }

    public LiveData<List<Car>> getAllCardWithLiveData() {
        return carDao.getAllCars();
    }

    public LiveData<List<Car>> getCarByMakerIdLiveData(String makerId) {
        return carDao.getCarByMakerId(makerId);
    }

    public LiveData<Car> getCardByIdLiveData(int id) {
        return carDao.getCardById(id);
    }
}
