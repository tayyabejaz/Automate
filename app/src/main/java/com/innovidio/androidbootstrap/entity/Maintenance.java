package com.innovidio.androidbootstrap.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.innovidio.androidbootstrap.db.converters.DateConverter;
import com.innovidio.androidbootstrap.interfaces.TimeLineItem;

import java.util.Date;

/**
 * Created by Adnan Naeem on 10/09/2019.
 */

@Entity
public class Maintenance implements TimeLineItem {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int formId;
    private String maintenanceName;
    private String maintenanceCost;
    private String maintenanceLife;
    private int maintenanceAlerts;
   // private int alarmID;
    private boolean alarmON;
    private String maintenanceType;

    @ColumnInfo(name = "maintenanceDate")
    @TypeConverters(DateConverter.class)
    private String maintenanceDate;

    // todo is save date added here or not?
    @ColumnInfo(name = "saveDate")
    @TypeConverters(DateConverter.class)
    private Date saveDate;

    public Maintenance() {

    }

    @Ignore
    public Maintenance(int formId, String maintenancename, String maintenanceprice, String maintenancelifetime, int maintenancealerts, String maintenancetype, String date, Date saveDate) {
        this.maintenanceName = maintenancename;
        this.formId = formId;
        this.maintenanceCost = maintenanceprice;
        this.maintenanceLife = maintenancelifetime;
        this.maintenanceAlerts = maintenancealerts;

        this.maintenanceType = maintenancetype;
        this.maintenanceDate = date;
        this.saveDate =  saveDate;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public void setMaintenanceName(String maintenanceName) {
        this.maintenanceName = maintenanceName;
    }

    public void setMaintenanceCost(String maintenanceCost) {
        this.maintenanceCost = maintenanceCost;
    }

    public void setMaintenanceLife(String maintenanceLife) {
        this.maintenanceLife = maintenanceLife;
    }

    public void setMaintenanceAlerts(int maintenanceAlerts) {
        this.maintenanceAlerts = maintenanceAlerts;
    }

    public void setAlarmON(boolean alarmON) {
        this.alarmON = alarmON;
    }

    public void setMaintenanceType(String maintenanceType) {
        this.maintenanceType = maintenanceType;
    }

    public void setMaintenanceDate(String maintenanceDate) {
        this.maintenanceDate = maintenanceDate;
    }

    public void setSaveDate(Date saveDate) {
        this.saveDate = saveDate;
    }

    public int getId() {
        return this.id;
    }

    public int getFormId() {
        return formId;
    }

    public String getMaintenanceName() {
        return maintenanceName;
    }

    public String getMaintenanceCost() {
        return maintenanceCost;
    }

    public String getMaintenanceLife() {
        return maintenanceLife;
    }

    public int getMaintenanceAlerts() {
        return maintenanceAlerts;
    }

    public boolean isAlarmON() {
        return alarmON;
    }

    public void setAlarmon(boolean alarmon) {
        this.alarmON = alarmon;
    }

    public String getMaintenanceType() {
        return maintenanceType;
    }

    public String getMaintenanceDate() {
        return maintenanceDate;
    }

    public Date getSaveDate() {
        return saveDate;
    }

    @Override
    public Date getInsertDateTime() {
        return saveDate;
    }

    @Override
    public Type getType() {
        return Type.MAINTENANCE;
    }
}
