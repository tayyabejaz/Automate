package com.innovidio.androidbootstrap.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.innovidio.androidbootstrap.entity.Alarm;
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