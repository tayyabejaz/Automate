package com.innovidio.androidbootstrap.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.innovidio.androidbootstrap.entity.FuelAccount;
import com.innovidio.androidbootstrap.entity.FuelUp;

import java.util.Date;
import java.util.List;

@Dao
public abstract class FuelAccountDao extends BaseDao<FuelAccount> {

    @Query("SELECT * FROM FuelAccount ORDER BY id desc")
    public abstract LiveData<List<FuelUp>> getFuelUpsOrderById();

    @Query("SELECT * FROM FuelAccount")
    public abstract LiveData<List<FuelAccount>> getAllFuelUps();

    @Query("SELECT * FROM FuelAccount WHERE id =:id")
    public abstract LiveData<FuelAccount> getFuelUpById(int id);


    @Query("SELECT * FROM FuelAccount  ORDER BY id DESC LIMIT 1")
    public abstract  LiveData<FuelAccount> getRecentFuelUp();

    @Query("SELECT * FROM FuelAccount")
    public abstract  LiveData<List<FuelAccount>> getFuelTankPercentage();


}

