package com.innovidio.androidbootstrap.db.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.innovidio.androidbootstrap.entity.Car;

import java.util.List;

@Dao
public abstract class CarDao extends BaseDao<Car> {

    @Query("SELECT * FROM Car ORDER BY id desc")
    public abstract LiveData<List<Car>> getAllCarsOrderById();

    @Query("SELECT * FROM Car WHERE isEnable =:enable ORDER BY id desc")
    public abstract LiveData<List<Car>> getAllEnableCarsOrderById(boolean enable);

    @Query("SELECT * FROM Car WHERE manufacturer =:makeCompany")
    public abstract LiveData<List<Car>> getCarByMakerId(String makeCompany);

    @Query("SELECT * FROM Car")
    public abstract LiveData<List<Car>> getAllCars();

    @Query("SELECT * FROM Car WHERE id =:id")
    public abstract LiveData<Car> getCarById(int id);
}

