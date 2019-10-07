package com.innovidio.androidbootstrap.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Query;

import com.innovidio.androidbootstrap.entity.Alarm;

public abstract class AlarmDao extends BaseDao<AlarmDao> {

//    @Query("SELECT * FROM Alarm ORDER BY id desc")
//    public abstract LiveData<List<Alarm>> fetchAllCars();


    @Query("SELECT * FROM Alarm WHERE alarmType =:type")
    public abstract LiveData<Alarm> getAlarmByType(int type);


    @Query("SELECT * FROM Alarm")
    public abstract LiveData<Alarm> getAllCars(int makeid);

    @Query("SELECT * FROM Alarm WHERE alarmID =:id")
    public abstract LiveData<Alarm> getAlarmByAlarmId(int id);
}

