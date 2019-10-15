package com.innovidio.androidbootstrap.db.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.innovidio.androidbootstrap.entity.Maintenance;
import com.innovidio.androidbootstrap.entity.MaintenanceWithAlarms;
import com.innovidio.androidbootstrap.entity.models.TimeLine;
import com.innovidio.androidbootstrap.interfaces.TimeLineItem;

import java.util.Date;
import java.util.List;

@Dao
public abstract class MaintenanceDao extends BaseDao<Maintenance>{

    @Query("SELECT * FROM Maintenance WHERE maintenancetype =:type")
    public abstract LiveData<List<Maintenance>> getMaintenanceByType(int type);


    @Query("SELECT * FROM Maintenance")
    public abstract LiveData<List<Maintenance>> getAllMaintenanceService();

//    @Query("SELECT * FROM Maintenance WHERE alarmID =:id")
//    public abstract LiveData<Maintenance> getMaintenanceByAlarmId(int id);

    //ASK: Sir Sajjad about this

    @Query("SELECT * FROM Maintenance WHERE saveDate=:date")
    public abstract LiveData<Maintenance> getMaintenanceByDate(Date date);
    @Transaction
    @Query("SELECT * FROM Maintenance")
    public abstract LiveData<MaintenanceWithAlarms> getMaintenanceWithAlarms();

    // Todo how to get data from all tables discus with sir sajjad
//    @Query("SELECT * FROM Maintenance UNION" +
//            " SELECT * FROM FuelUp UNION" +
//            " SELECT * FROM Trip UNION" +
//            " ORDER BY date ASC")
//    public abstract LiveData<List<TimeLine>> getAllFromMaintenanceTripsAndFuelUp();


    @Query("SELECT * FROM Maintenance")
    public abstract LiveData<List<Maintenance>> getAllMaintenanceForTimeline();

    @Query("SELECT * FROM Maintenance")
    public abstract List<Maintenance> getAllMaintenanceTimeline();

}
