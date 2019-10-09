package com.innovidio.androidbootstrap.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.innovidio.androidbootstrap.db.converters.DateConverter;
import com.innovidio.androidbootstrap.interfaces.TimeLineItem;

import java.util.Date;

/**
 * Created by MuhammadSalman on 1/24/2019.
 */

@Entity
public class FuelUp implements TimeLineItem {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String carname;
    // optional for trips and without trip/ regular use
    private int tripId;
    private String odometerreading;
    private String liters;
    private String perunitfuelprice;
    private String totalprice;
    private String location;
    // todo is save date added here or not?
    @ColumnInfo(name = "saveDate")
    @TypeConverters(DateConverter.class)
    private Date saveDate;

//    public FuelUp(String carname, String odometerreading, int tripId, String liters, String perunitfuelprice, String totalprice, String location, Date saveDate) {
//        this.carname = carname;
//        this.odometerreading = odometerreading;
//        this.liters = liters;
//        this.perunitfuelprice = perunitfuelprice;
//        this.totalprice = totalprice;
//        this.location = location;
//        this.tripId = tripId;
//        this.saveDate =  saveDate;
//    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCarname(String carname) {
        this.carname = carname;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public void setOdometerreading(String odometerreading) {
        this.odometerreading = odometerreading;
    }

    public void setLiters(String liters) {
        this.liters = liters;
    }

    public void setPerunitfuelprice(String perunitfuelprice) {
        this.perunitfuelprice = perunitfuelprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(Date saveDate) {
        this.saveDate = saveDate;
    }

    public FuelUp() {

    }

    public int getId() {
        return id;
    }

    public String getCarname() {
        return carname;
    }

    public String getOdometerreading() {
        return odometerreading;
    }

    public String getLiters() {
        return liters;
    }

    public String getPerunitfuelprice() {
        return perunitfuelprice;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public String getLocation() {
        return location;
    }

    public int getTripId() {
        return tripId;
    }

    @Override
    public Date getInsertDateTime() {
        return saveDate;
    }

    @Override
    public Type getType() {
        return Type.FUEL;
    }
}
