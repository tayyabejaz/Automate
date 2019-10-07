package com.innovidio.androidbootstrap.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class MaintenanceWithAlarms {
    @Embedded
    public CarMaintenance carMaintenance;
    @Relation(parentColumn = "id", entityColumn = "maintenanceId", entity = Alarm.class)
    public List<Alarm> alarmModelList;

    public List<Alarm> getAlarmsList() {
        return alarmModelList;
    }

    public void setMovieList(List<Alarm> alarmModelList) {
        this.alarmModelList = alarmModelList;
    }
}