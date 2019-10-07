package com.innovidio.androidbootstrap.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.innovidio.androidbootstrap.db.converters.DateConverter;

/**
 * Created by MuhammadSalman on 12/6/2018.
 */

@Entity
public class CarMaintenance {
    @PrimaryKey(autoGenerate = true)
    private int id;


    private int formId;
    private String maintenanceName;
    private String maintenanceCost;
    private String maintenanceLife;
    private int maintenanceAlerts;
    private int alarmID;
    private boolean alarmON;
    private String maintenanceType;

    @TypeConverters(DateConverter.class)
    private String maintenanceDate;

    public CarMaintenance() {

    }

    public CarMaintenance(int formId, String maintenancename, String maintenanceprice, String maintenancelifetime, int maintenancealerts, int alarmid, String maintenancetype, String date) {
        this.maintenanceName = maintenancename;
        this.formId = formId;
        this.maintenanceCost = maintenanceprice;
        this.maintenanceLife = maintenancelifetime;
        this.maintenanceAlerts = maintenancealerts;
        this.alarmID = alarmid;
        this.maintenanceType = maintenancetype;
        this.maintenanceDate = date;
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

    public int getAlarmID() {
        return alarmID;
    }

    public void setAlarmID(int alarmid) {
        this.alarmID = alarmid;
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

}
