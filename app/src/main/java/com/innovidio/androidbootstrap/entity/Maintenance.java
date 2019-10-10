package com.innovidio.androidbootstrap.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.innovidio.androidbootstrap.db.converters.DateConverter;
import com.innovidio.androidbootstrap.interfaces.TimeLineItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    private int maintenanceCost;
    @ColumnInfo(name = "maintenanceLife")
    @TypeConverters(DateConverter.class)
    private Date maintenanceLife;
    private boolean alarmON;
    private String maintenanceType;

    // todo is save date added here or not?
    @ColumnInfo(name = "saveDate")
    @TypeConverters(DateConverter.class)
    private Date saveDate;

    public Maintenance() {

    }

    @Ignore
    public Maintenance(int formId, String maintenancename, int maintenanceprice, Date maintenancelifetime, boolean alramOn, String maintenancetype, Date saveDate) {
        this.maintenanceName = maintenancename;
        this.formId = formId;
        this.maintenanceCost = maintenanceprice;
        this.maintenanceLife = maintenancelifetime;
        this.alarmON = alramOn;
        this.maintenanceType = maintenancetype;
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

    public void setMaintenanceCost(int maintenanceCost) {
        this.maintenanceCost = maintenanceCost;
    }

    public void setMaintenanceLife(Date maintenanceLife) {
        this.maintenanceLife = maintenanceLife;
    }


    public void setAlarmON(boolean alarmON) {
        this.alarmON = alarmON;
    }

    public void setMaintenanceType(String maintenanceType) {
        this.maintenanceType = maintenanceType;
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

    public int getMaintenanceCost() {
        return maintenanceCost;
    }

    public Date getMaintenanceLife() {
        return maintenanceLife;
    }


    public boolean isAlarmON() {
        return alarmON;
    }

    public void setAlarmOn(boolean alarmon) {
        this.alarmON = alarmon;
    }

    public String getMaintenanceType() {
        return maintenanceType;
    }

    public Date getSaveDate() {
        return saveDate;
    }

    public String getSaveDateInString() {
        DateFormat format = new SimpleDateFormat("MMM dd");
        return format.format(this.saveDate);

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
