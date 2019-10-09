package com.innovidio.androidbootstrap.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.innovidio.androidbootstrap.db.converters.DateConverter;

import java.util.Date;

@Entity
public class Alarm {


    @PrimaryKey(autoGenerate = true)
    private int alarmID;
    private int maintenanceId;
    private String alarmMessage;
    private String alarmType;
    @ColumnInfo(name = "creationDate")
    @TypeConverters(DateConverter.class)
    private Date creationDate;
    @ColumnInfo(name = "executionTIme")
    @TypeConverters(DateConverter.class)
    private Date executionTIme;

    public Alarm(){

    }

    /* INFO
    Alarm type defines that which alarm is going to sound. there are two types of alarms:
    1- Alarm for the service
    2- Alarm for Reminders */

    public void setAlarmID(int alarmID) {
        this.alarmID = alarmID;
    }

    public int getAlarmID() {
        return alarmID;
    }


    public int getMaintenanceId() {
        return maintenanceId;
    }

    public void setMaintenanceId(int maintenanceId) {
        this.maintenanceId = maintenanceId;
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
