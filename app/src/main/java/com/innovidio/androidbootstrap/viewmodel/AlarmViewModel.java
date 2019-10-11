package com.innovidio.androidbootstrap.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.innovidio.androidbootstrap.entity.Alarm;
import com.innovidio.androidbootstrap.entity.FuelUp;
import com.innovidio.androidbootstrap.repository.AlarmRepository;

import java.util.List;

import javax.inject.Inject;

public class AlarmViewModel extends ViewModel {
    @Inject
    AlarmRepository alarmRepository;
    @Inject
    public AlarmViewModel(AlarmRepository alarmRepository) {
        this.alarmRepository = alarmRepository ;
    }

    public void addAlarm(Alarm alarm){
        alarmRepository.addAlarm(alarm);
    }

    public void deleteAlarm(Alarm alarm){
        alarmRepository.deleteAlarm(alarm);
    }

    public void updateAlarm(Alarm alarm){
        alarmRepository.updateAlarm(alarm);
    }

    public LiveData<List<Alarm>> getAllAlarms(){
        return this.alarmRepository.getAllAlarms();
    }

    public LiveData<Alarm> getAlarmById(int id){
        return this.alarmRepository.getAlarmById(id);
    }

    public LiveData<List<Alarm>> getAlarmByType(int type){
        return this.alarmRepository.getAlarmByType(type);
    }
}