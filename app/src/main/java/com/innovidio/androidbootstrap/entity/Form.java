package com.innovidio.androidbootstrap.entity;


import androidx.room.ColumnInfo;
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

    public Form() {

    }

    public void setId(int id) {
        Id = id;
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
}