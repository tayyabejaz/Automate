package com.innovidio.androidbootstrap.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Query;

import com.innovidio.androidbootstrap.entity.AlarmModel;

public abstract class AlarmDao extends BaseDao<AlarmDao> {

//    @Query("SELECT * FROM AlarmModel ORDER BY id desc")
//    public abstract LiveData<List<AlarmModel>> fetchAllCars();


    @Query("SELECT * FROM AlarmModel WHERE alarmType =:type")
    public abstract LiveData<AlarmModel> getAlarmByType(int type);


    @Query("SELECT * FROM AlarmModel")
    public abstract LiveData<AlarmModel> getAllCars(int makeid);

    @Query("SELECT * FROM AlarmModel WHERE alarmID =:id")
    public abstract LiveData<AlarmModel> getAlarmByAlarmId(int id);
}

