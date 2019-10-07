package com.innovidio.androidbootstrap.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.innovidio.androidbootstrap.db.converters.DateConverter;
import com.squareup.moshi.Json;

import java.util.Date;


@Entity
public class Form {

    @PrimaryKey(autoGenerate = true)
    private int Id;
    private String title;
    private String carId;
    private String location;
    @TypeConverters(DateConverter.class)
    private Date startDate;
    @TypeConverters(DateConverter.class)
    private Date endDate;
    // todo is save date added here or not?
    @TypeConverters(DateConverter.class)
    private Date saveDate;


    public Form(){

    }


    public int getId() {
        return Id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
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

    public Date setEndDate(Date endDate) {
       return this.endDate = endDate;
    }

    public Date getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(Date saveDate) {
        this.saveDate = saveDate;
    }
}