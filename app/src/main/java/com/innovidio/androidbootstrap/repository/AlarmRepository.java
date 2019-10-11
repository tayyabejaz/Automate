package com.innovidio.androidbootstrap.repository;

import android.os.AsyncTask;

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

    public void addAlarm(Alarm alarm){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                alarmDao.insert(alarm);
                return null;
            }
        }.execute();

    }


    public void deleteAlarm(Alarm alarm){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                alarmDao.delete(alarm);
                return null;
            }
        }.execute();

    }

    public void updateAlarm(Alarm alarm){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                alarmDao.update(alarm);
                return null;
            }
        }.execute();

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