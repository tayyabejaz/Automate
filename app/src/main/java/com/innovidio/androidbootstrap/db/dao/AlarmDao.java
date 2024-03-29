package com.innovidio.androidbootstrap.db.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.innovidio.androidbootstrap.entity.Alarm;
import com.innovidio.androidbootstrap.entity.Form;

import java.util.List;

@Dao
public abstract class AlarmDao extends BaseDao<Alarm> {

//    @Query("SELECT * FROM Alarm ORDER BY id desc")
//    public abstract LiveData<List<Alarm>> fetchAllCars();


    @Query("SELECT * FROM Alarm WHERE alarmType =:type")
    public abstract LiveData<List<Alarm>> getAlarmByType(int type);


    @Query("SELECT * FROM Alarm")
    public abstract LiveData<List<Alarm>> getAllAlarmsLiveData();

    @Query("SELECT * FROM Alarm")
    public abstract List<Alarm> getAllAlarms();

    @Query("SELECT * FROM Alarm WHERE alarmID =:id")
    public abstract LiveData<Alarm> getAlarmByAlarmId(int id);

    @Query("SELECT * FROM Alarm  ORDER BY alarmID DESC LIMIT 1")
    public abstract  LiveData<Alarm> getRecentAlarm();

}

