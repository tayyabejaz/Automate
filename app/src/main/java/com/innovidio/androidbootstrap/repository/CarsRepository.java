package com.innovidio.androidbootstrap.repository;


import android.content.Context;
import android.os.AsyncTask;

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

    public void addCar(Car car){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                carDao.insert(car);
                return null;
            }
        }.execute();

    }


    public void deleteCar(Car car){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                carDao.delete(car);
                return null;
            }
        }.execute();

    }

    public void updateCar(Car car){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                carDao.update(car);
                return null;
            }
        }.execute();
    }

    public LiveData<List<Car>> getAllCars() {
        return carDao.getAllCars();
    }

    public LiveData<List<Car>> getAllEnableCarsOrderById(boolean isEnable) {
        return carDao.getAllEnableCarsOrderById(isEnable);
    }

    public LiveData<Car> getCarById(int id) {
        return carDao.getCarById(id);
    }
}
