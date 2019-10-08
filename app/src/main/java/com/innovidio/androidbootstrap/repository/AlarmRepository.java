package com.innovidio.androidbootstrap.repository;

import androidx.lifecycle.LiveData;

import com.innovidio.androidbootstrap.db.dao.AlarmDao;
import com.innovidio.androidbootstrap.entity.Alarm;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AlarmRepository {


    AlarmDao alarmDao;
    @Inject
    public AlarmRepository(AlarmDao alarmDao){
        this.alarmDao = alarmDao;
    }

    public LiveData<List<Alarm>> getAllAlarms(){
        return this.alarmDao.getAllAlarms();
    }

    public LiveData<Alarm> getAlarmById(int id){
        return this.alarmDao.getAlarmByAlarmId(id);
    }

    public LiveData<List<Alarm>> getAlarmByType(int type){
        return this.alarmDao.getAlarmByType(type);
    }
}