package com.innovidio.androidbootstrap.db.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Query;

import com.innovidio.androidbootstrap.entity.Form;
import com.innovidio.androidbootstrap.entity.MaintenanceWithAlarms;

import java.util.Date;

public abstract class FormDao extends BaseDao<Form>{


    @Query("SELECT * FROM Form")
    public abstract LiveData<Form> getAllFormsMaintenance(int makeid);

    @Query("SELECT * FROM Form WHERE id =:id")
    public abstract LiveData<Form> getFormMaintenanceById(int id);


    @Query("SELECT * FROM Form WHERE carId=:carId")
    public abstract LiveData<Form> geFormMaintenanceCarId(int carId);

    @Query("SELECT * FROM Form WHERE startDate=:date")
    public abstract LiveData<MaintenanceWithAlarms> getFormMaintenanceByStartDate(int date);


}
