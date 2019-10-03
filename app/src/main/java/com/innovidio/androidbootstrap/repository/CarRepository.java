package com.innovidio.androidbootstrap.repository;


import android.content.Context;

import androidx.lifecycle.LiveData;

import com.innovidio.androidbootstrap.db.dao.CarDao;
import com.innovidio.androidbootstrap.entity.CarModel;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CarRepository {

    private final CarDao carDao;
    private Context context;


    @Inject
    public CarRepository(Context context, CarDao carDao) {
        this.context = context;
        this.carDao = carDao;
    }

    public LiveData<List<CarModel>> getAllCardWithLiveData() {
        LiveData<List<CarModel>> allCars = carDao.getAllCars();
        return allCars;
    }

    public LiveData<CarModel> getCarByMakerIdLiveData(String makerId) {
        LiveData<CarModel> car = carDao.getCarByMakerId(makerId);
        return car;
    }

    public LiveData<CarModel> getCardByIdLiveData(int id) {
        LiveData<CarModel> car = carDao.getCardById(id);
        return car;
    }
}
