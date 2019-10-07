package com.innovidio.androidbootstrap.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.innovidio.androidbootstrap.db.converters.DateConverter;

import java.util.Date;

/**
 * Created by MuhammadSalman on 1/24/2019.
 */

@Entity
public class FuelUp {
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
    @TypeConverters(DateConverter.class)
    private Date saveDate;

    public FuelUp(String carname, String odometerreading, int tripId, String liters, String perunitfuelprice, String totalprice, String location, Date saveDate) {
        this.carname = carname;
        this.odometerreading = odometerreading;
        this.liters = liters;
        this.perunitfuelprice = perunitfuelprice;
        this.totalprice = totalprice;
        this.location = location;
        this.tripId = tripId;
        this.saveDate =  saveDate;
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
}
