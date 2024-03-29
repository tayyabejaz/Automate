package com.innovidio.androidbootstrap.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.innovidio.androidbootstrap.db.converters.AlarmTypeConverters;
import com.innovidio.androidbootstrap.db.converters.DateConverter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Alarm {


    @PrimaryKey(autoGenerate = true)
    private int alarmID;
    private int carId;
    private int maintenanceId;
    private String alarmMessage;
    @ColumnInfo(name = "alarmType")
    @TypeConverters(AlarmTypeConverters.class)
    private AlarmType alarmType;
    @ColumnInfo(name = "creationDate")
    @TypeConverters(DateConverter.class)
    private Date creationDate;
    @ColumnInfo(name = "executionTime")
    @TypeConverters(DateConverter.class)
    private Date executionTime;
    private boolean isActive;
    private boolean isExpired;

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

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
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

    public AlarmType getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(AlarmType alarmType) {
        this.alarmType = alarmType;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getExecutionTime() {
        return executionTime;
    }

    public String getExecutionDateInString() {
        DateFormat format = new SimpleDateFormat("MMM dd");
        return format.format(this.executionTime);

    }

    public String getExecutionTimeInString() {
        DateFormat format = new SimpleDateFormat("hh:mm");
        return format.format(this.executionTime);
    }

    public void setExecutionTime(Date executionTIme) {
        this.executionTime = executionTIme;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;

    }

    public enum AlarmType {
        CUSTOM, MAINTENANCE;
    }
}
