package com.innovidio.androidbootstrap.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.squareup.moshi.Json;

import java.util.Date;


@Entity
public class Form {

    @PrimaryKey(autoGenerate = true)
    private int Id;

    private String title;
    private String carId;
    private String location;
    private Date startDate;
    private String endDate;


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

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}