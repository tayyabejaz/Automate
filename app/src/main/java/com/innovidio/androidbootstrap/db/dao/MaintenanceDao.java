package com.innovidio.androidbootstrap.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Query;

import com.innovidio.androidbootstrap.entity.AlarmModel;
import com.innovidio.androidbootstrap.entity.CarMaintenance;
import com.innovidio.androidbootstrap.entity.MaintenanceWithAlarms;

import java.util.Date;

public abstract class MaintenanceDao extends BaseDao<CarMaintenance>{

    @Query("SELECT * FROM CarMaintenance WHERE maintenancetype =:type")
    public abstract LiveData<CarMaintenance> getMaintenanceByType(int type);


    @Query("SELECT * FROM CarMaintenance")
    public abstract LiveData<CarMaintenance> getAllMaintenanceService(int makeid);

    @Query("SELECT * FROM CarMaintenance WHERE alarmID =:id")
    public abstract LiveData<CarMaintenance> getMaintenanceByAlarmId(int id);

    //ASK: Sir Sajjad about this

    @Query("SELECT * FROM CarMaintenance WHERE maintenanceDate=:date")
    public abstract LiveData<CarMaintenance> getMaintenanceByDate(Date date);

    @Query("SELECT * FROM CarMaintenance WHERE maintenanceDate=:maintenanaceID")
    public abstract LiveData<MaintenanceWithAlarms> getMaintenanceWithAlarms(int maintenanaceID);


}
