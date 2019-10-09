package com.innovidio.androidbootstrap.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class MaintenanceWithAlarms {
    @Embedded
    public Maintenance carMaintenance;
    @Relation(parentColumn = "id", entityColumn = "maintenanceId", entity = Alarm.class)
    public List<Alarm> alarmModelList;

    public List<Alarm> getAlarmModelList() {
        return alarmModelList;
    }

    public void setAlarmModelList(List<Alarm> alarmModelList) {
        this.alarmModelList = alarmModelList;
    }
}