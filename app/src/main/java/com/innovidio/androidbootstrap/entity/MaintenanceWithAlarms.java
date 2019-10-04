package com.innovidio.androidbootstrap.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class MaintenanceWithAlarms {
    @Embedded
    public CarMaintenance carMaintenance;
    @Relation(parentColumn = "id", entityColumn = "maintenanceId", entity = AlarmModel.class)
    public List<AlarmModel> alarmModelList;

    public List<AlarmModel> getAlarmsList() {
        return alarmModelList;
    }

    public void setMovieList(List<AlarmModel> alarmModelList) {
        this.alarmModelList = alarmModelList;
    }
}