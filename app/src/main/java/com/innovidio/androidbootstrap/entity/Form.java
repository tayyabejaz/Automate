package com.innovidio.androidbootstrap.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.innovidio.androidbootstrap.db.converters.DateConverter;
import com.squareup.moshi.Json;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@Entity
public class Form {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private int carId;
    private String location;
    @ColumnInfo(name = "startDate")
    @TypeConverters(DateConverter.class)
    private Date startDate;

    @ColumnInfo(name = "endDate")
    @TypeConverters(DateConverter.class)
    private Date endDate;
    @ColumnInfo(name = "saveDate")
    @TypeConverters(DateConverter.class)
    private Date saveDate;

    private int odoMeterReading;
    private int totalCost;


    public Form() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(Date saveDate) {
        this.saveDate = saveDate;
    }

    public String getSaveDateInString() {
        DateFormat format = new SimpleDateFormat("MMM dd");
        return format.format(this.saveDate);

    }

    public String getSaveTimeInString() {
        DateFormat format = new SimpleDateFormat("hh:mm");
        return format.format(this.saveDate);
    }

    public int getOdoMeterReading() {
        return odoMeterReading;
    }

    public void setOdoMeterReading(int odoMeterReading) {
        this.odoMeterReading = odoMeterReading;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }
}