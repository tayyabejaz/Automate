package com.innovidio.androidbootstrap.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Query;

import com.innovidio.androidbootstrap.entity.CarFuelUp;

import java.util.List;

public abstract class FuelDao extends BaseDao<FuelDao> {

    @Query("SELECT * FROM CarFuelUp ORDER BY id desc")
    public abstract LiveData<List<CarFuelUp>> getFuelUpsOrderById();

    @Query("SELECT * FROM CarFuelUp")
    public abstract LiveData<List<CarFuelUp>> getAllFuelUps();

    @Query("SELECT * FROM CarFuelUp WHERE carname =:id")
    public abstract LiveData<CarFuelUp> getFuelUpbyId(int id);


    @Query("SELECT * FROM CarFuelUp WHERE carname =:carName")
    public abstract LiveData<List<CarFuelUp>> getFuelUpById(String carName);
}

