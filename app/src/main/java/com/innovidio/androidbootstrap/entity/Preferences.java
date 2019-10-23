package com.innovidio.androidbootstrap.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.innovidio.androidbootstrap.db.converters.DateConverter;
import com.innovidio.androidbootstrap.db.converters.EnumTypeConverters;
import com.innovidio.androidbootstrap.db.converters.UnitTypeEnumConverters;
import com.innovidio.androidbootstrap.interfaces.TimeLineItem;

import java.util.Date;

@Entity
public class Preferences {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private boolean isAutoDetect = false;
    private String country;
    private String currency;
    @ColumnInfo(name = "fuelUnit")
    @TypeConverters(UnitTypeEnumConverters.class)
    private UnitTypeEnum fuelUnit;
    @ColumnInfo(name = "distanceUnit")
    @TypeConverters(UnitTypeEnumConverters.class)
    private UnitTypeEnum distanceUnit;
    @ColumnInfo(name = "speedUnit")
    @TypeConverters(UnitTypeEnumConverters.class)
    private UnitTypeEnum speedUnit;
    private int speedLimit = 0;
    private float fuelUnitPrice;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAutoDetect() {
        return isAutoDetect;
    }

    public void setAutoDetect(boolean autoDetect) {
        isAutoDetect = autoDetect;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public UnitTypeEnum getFuelUnit() {
        return fuelUnit;
    }

    public void setFuelUnit(UnitTypeEnum fuelUnit) {
        this.fuelUnit = fuelUnit;
    }

    public UnitTypeEnum getDistanceUnit() {
        return distanceUnit;
    }

    public void setDistanceUnit(UnitTypeEnum distanceUnit) {
        this.distanceUnit = distanceUnit;
    }

    public UnitTypeEnum getSpeedUnit() {
        return speedUnit;
    }

    public void setSpeedUnit(UnitTypeEnum speedUnit) {
        this.speedUnit = speedUnit;
    }

    public int getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(int speedLimit) {
        this.speedLimit = speedLimit;
    }

    public float getFuelUnitPrice() {
        return fuelUnitPrice;
    }

    public void setFuelUnitPrice(float fuelUnitPrice) {
        this.fuelUnitPrice = fuelUnitPrice;
    }


    public enum UnitTypeEnum {
        Gallons,Liters, KM,Miles, M_HR,KM_HR;;
    }

}
