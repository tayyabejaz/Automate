package com.innovidio.androidbootstrap.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.innovidio.androidbootstrap.entity.FuelTransaction;
import com.innovidio.androidbootstrap.entity.FuelUp;

import java.util.Date;
import java.util.List;

@Dao
public abstract class FuelAccountDao extends BaseDao<FuelTransaction> {

    @Query("SELECT * FROM FuelTransaction ORDER BY id desc")
    public abstract LiveData<List<FuelUp>> getFuelUpsOrderById();

    @Query("SELECT * FROM FuelTransaction")
    public abstract LiveData<List<FuelTransaction>> getAllFuelUps();

    @Query("SELECT * FROM FuelTransaction WHERE id =:id")
    public abstract LiveData<FuelTransaction> getFuelUpById(int id);


    @Query("SELECT * FROM FuelTransaction  ORDER BY id DESC LIMIT 1")
    public abstract  LiveData<FuelTransaction> getRecentFuelUp();

    @Query("SELECT * FROM FuelTransaction")
    public abstract  LiveData<List<FuelTransaction>> getFuelTankPercentage();

}

