package com.innovidio.androidbootstrap.db.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Query;

import com.innovidio.androidbootstrap.entity.Car;

import java.util.List;

public abstract class CarDao extends BaseDao<CarDao> {

    @Query("SELECT * FROM Car ORDER BY id desc")
    public abstract LiveData<List<Car>> fetchAllCarsOrderById();


    @Query("SELECT * FROM Car WHERE makeid =:makeid")
    public abstract LiveData<List<Car>> getCarByMakerId(String makeid);


    @Query("SELECT * FROM Car")
    public abstract LiveData<List<Car>> getAllCars();

    @Query("SELECT * FROM Car WHERE id =:id")
    public abstract LiveData<Car> getCardById(int id);
}

