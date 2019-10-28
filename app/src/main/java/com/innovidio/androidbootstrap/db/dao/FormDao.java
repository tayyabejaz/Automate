package com.innovidio.androidbootstrap.db.dao;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.innovidio.androidbootstrap.entity.Form;
import com.innovidio.androidbootstrap.entity.MaintenanceWithAlarms;

import java.util.Date;
import java.util.List;

@Dao
public abstract class FormDao extends BaseDao<Form>{


    @Query("SELECT * FROM Form")
    public abstract LiveData<List<Form>> getAllFormsMaintenance();


    @Query("SELECT * FROM Form WHERE id =:id")
    public abstract LiveData<Form> getFormMaintenanceById(int id);


    @Query("SELECT * FROM Form WHERE carId=:carId")
    public abstract LiveData<List<Form>> getAllFormByCardId(int carId);

    @Query("SELECT * FROM FuelUp  ORDER BY id DESC LIMIT 1")
    public abstract  LiveData<Form> getRecentForm();
}
