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

    public LiveData<List<Car>> getAllCars() {
        return carDao.getAllCars();
    }

    public LiveData<List<Car>> getAllCarsOrderById() {
        return carDao.getAllCarsOrderById();
    }

    public LiveData<Car> getCarById(int id) {
        return carDao.getCarById(id);
    }
}
