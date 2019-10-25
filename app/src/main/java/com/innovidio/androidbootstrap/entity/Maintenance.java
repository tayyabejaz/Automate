package com.innovidio.androidbootstrap.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.innovidio.androidbootstrap.db.converters.DateConverter;
import com.innovidio.androidbootstrap.db.converters.EnumTypeConverters;
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
    private int carId;
    private String maintenanceName;
    private int maintenanceCost;
    @ColumnInfo(name = "nextMaintenanceDate")
    @TypeConverters(DateConverter.class)
    private Date nextMaintenanceDate;
    private int maintenanceLife;
    private boolean alarmON;
    @ColumnInfo(name = "maintenanceType")
    @TypeConverters(EnumTypeConverters.class)
    private Type maintenanceType;
    @ColumnInfo(name = "saveDate")
    @TypeConverters(DateConverter.class)
    private Date saveDate;
    private String maintenanceLocation;
    private int maintenanceOdometerReading;

    public Maintenance(){

    }

    @Ignore
    public Maintenance(int formId, int carId, String maintenancename, int maintenanceprice, Date maintenancelifetime, boolean alramOn, TimeLineItem.Type maintenancetype, Date saveDate, String location, int odometerReading) {
        this.maintenanceName = maintenancename;
        this.formId = formId;
        this.carId = carId;
        this.maintenanceCost = maintenanceprice;
        this.nextMaintenanceDate = maintenancelifetime;
        this.alarmON = alramOn;
        this.maintenanceType = maintenancetype;
        this.saveDate = saveDate;
        this.maintenanceLocation = location;
        this.maintenanceOdometerReading = odometerReading;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getMaintenanceLocation() {
        return maintenanceLocation;
    }

    public void setMaintenanceLocation(String maintenanceLocation) {
        this.maintenanceLocation = maintenanceLocation;
    }

    public int getMaintenanceOdometerReading() {
        return maintenanceOdometerReading;
    }

    public void setMaintenanceOdometerReading(int maintenanceOdometerReading) {
        this.maintenanceOdometerReading = maintenanceOdometerReading;
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

    public void setNextMaintenanceDate(Date nextMaintenanceDate) {
        this.nextMaintenanceDate = nextMaintenanceDate;
    }


    public void setAlarmON(boolean alarmON) {
        this.alarmON = alarmON;
    }

    public void setMaintenanceType(TimeLineItem.Type maintenanceType) {
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

    public Date getNextMaintenanceDate() {
        return nextMaintenanceDate;
    }


    public boolean isAlarmON() {
        return alarmON;
    }

    public void setAlarmOn(boolean alarmon) {
        this.alarmON = alarmon;
    }

    public TimeLineItem.Type getMaintenanceType() {
        return maintenanceType;
    }

    public Date getSaveDate() {
        return saveDate;
    }

    public String getSaveDateInString() {
        DateFormat format = new SimpleDateFormat("MMM dd");
        return format.format(this.saveDate);

    }

    public String getSaveTimeInString() {
        DateFormat format = new SimpleDateFormat("hh:mm");
        return format.format(this.saveDate);
    }

    @Override
    public Date getInsertDateTime() {
        return saveDate;
    }

    @Override
    public Type getType() {
        return maintenanceType;
    }

    public int getMaintenanceLife() {
        return maintenanceLife;
    }

    public void setMaintenanceLife(int maintenanceLife) {
        this.maintenanceLife = maintenanceLife;
    }
}
