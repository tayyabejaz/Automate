package com.innovidio.androidbootstrap.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.innovidio.androidbootstrap.entity.FuelUp;
import com.innovidio.androidbootstrap.entity.OdoMeterAccount;

import java.util.List;

@Dao
public abstract class OdoMeterAccountDao extends BaseDao<OdoMeterAccount> {

    @Query("SELECT * FROM OdoMeterAccount ORDER BY id desc")
    public abstract LiveData<List<FuelUp>> getOdoMeterAccountOrderById();

    @Query("SELECT * FROM OdoMeterAccount")
    public abstract LiveData<List<OdoMeterAccount>> getAllOdoMeterAccount();

    @Query("SELECT * FROM OdoMeterAccount WHERE id =:id")
    public abstract LiveData<OdoMeterAccount> getOdoMeterAccountById(int id);


    @Query("SELECT * FROM OdoMeterAccount  ORDER BY id DESC LIMIT 1")
    public abstract  LiveData<OdoMeterAccount> getRecentOdoMeterAccountValue();

}

