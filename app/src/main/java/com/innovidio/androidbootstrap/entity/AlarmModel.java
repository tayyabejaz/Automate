package com.innovidio.androidbootstrap.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class AlarmModel {


    @PrimaryKey(autoGenerate = true)
    private int alarmID;
    private String alarmMessage;

    /* INFO
    Alarm type defines that which alarm is going to sound. there are two types of alarms:
    1- Alarm for the service
    2- Alarm for Reminders */

    private String alarmType;
    private Date creationDate;
    private Date executionTIme;

    public int getAlarmID() {
        return alarmID;
    }

    public String getAlarmMessage() {
        return alarmMessage;
    }

    public void setAlarmMessage(String alarmMessage) {
        this.alarmMessage = alarmMessage;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getExecutionTIme() {
        return executionTIme;
    }

    public void setExecutionTIme(Date executionTIme) {
        this.executionTIme = executionTIme;
    }
}