package com.innovidio.androidbootstrap.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Query;

import com.innovidio.androidbootstrap.entity.CarFuelUp;

import java.util.List;

public abstract class FuelDao extends BaseDao<FuelDao> {

    @Query("SELECT * FROM CarFuelUp ORDER BY id desc")
    public abstract LiveData<List<CarFuelUp>> getFuelUpById();



    @Query("SELECT * FROM CarFuelUp")
    public abstract LiveData<CarFuelUp> getAllCars(int makeid);


    // todo ask by sir sajjad relation
    @Query("SELECT * FROM CarFuelUp WHERE id =:id")
    public abstract LiveData<CarFuelUp> getCardByTripId(int id);
}

