package com.innovidio.androidbootstrap.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Query;

import com.innovidio.androidbootstrap.entity.FuelUp;
import com.innovidio.androidbootstrap.entity.Maintenance;

import java.util.List;

public abstract class FuelDao extends BaseDao<FuelDao> {

    @Query("SELECT * FROM FuelUp ORDER BY id desc")
    public abstract LiveData<List<FuelUp>> getFuelUpsOrderById();

    @Query("SELECT * FROM FuelUp")
    public abstract LiveData<List<FuelUp>> getAllFuelUps();

    @Query("SELECT * FROM FuelUp WHERE carname =:id")
    public abstract LiveData<FuelUp> getFuelUpbyId(int id);


    @Query("SELECT * FROM FuelUp WHERE carname =:carName")
    public abstract LiveData<List<FuelUp>> getFuelUpById(String carName);


    @Query("SELECT * FROM FuelUp")
    public abstract  LiveData<List<FuelUp>> getAllFuelUpsForTimeline();
}

