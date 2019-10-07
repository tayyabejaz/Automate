package com.innovidio.androidbootstrap.db.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Query;

import com.innovidio.androidbootstrap.entity.Alarm;

import java.util.List;

public abstract class AlarmDao extends BaseDao<AlarmDao> {

//    @Query("SELECT * FROM Alarm ORDER BY id desc")
//    public abstract LiveData<List<Alarm>> fetchAllCars();


    @Query("SELECT * FROM Alarm WHERE alarmType =:type")
    public abstract LiveData<List<Alarm>> getAlarmByType(int type);


    @Query("SELECT * FROM Alarm")
    public abstract LiveData<List<Alarm>> getAllAlarms();

    @Query("SELECT * FROM Alarm WHERE alarmID =:id")
    public abstract LiveData<Alarm> getAlarmByAlarmId(int id);
}

