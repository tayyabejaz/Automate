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
 * Created by MuhammadSalman on 1/24/2019.
 */

@Entity
public class FuelUp implements TimeLineItem {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String carname;
    // optional for trips and without trip/ regular use
    private int tripId;
    private int odometerreading;
    private int liters;
    private int perunitfuelprice;
    private int totalprice;
    private String location;
    // todo is save date added here or not?
    @ColumnInfo(name = "saveDate")
    @TypeConverters(DateConverter.class)
    private Date saveDate;

    public FuelUp() {

    }

    @Ignore
    public FuelUp(String carname, int odometerreading, int tripId, int liters, int perunitfuelprice, int totalprice, String location, Date saveDate) {
        this.carname = carname;
        this.odometerreading = odometerreading;
        this.liters = liters;
        this.perunitfuelprice = perunitfuelprice;
        this.totalprice = totalprice;
        this.location = location;
        this.tripId = tripId;
        this.saveDate =  saveDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCarname(String carname) {
        this.carname = carname;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public void setOdometerreading(int odometerreading) {
        this.odometerreading = odometerreading;
    }

    public void setLiters(int liters) {
        this.liters = liters;
    }

    public void setPerunitfuelprice(int perunitfuelprice) {
        this.perunitfuelprice = perunitfuelprice;
    }

    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getSaveDate() {
        return saveDate;
    }

    public String getSaveDateInString() {
        DateFormat format = new SimpleDateFormat("MMM dd");
        return format.format(this.saveDate);

    }

    public void setSaveDate(Date saveDate) {
        this.saveDate = saveDate;
    }


    public int getId() {
        return id;
    }

    public String getCarname() {
        return carname;
    }

    public int getOdometerreading() {
        return odometerreading;
    }

    public int getLiters() {
        return liters;
    }

    public int getPerunitfuelprice() {
        return perunitfuelprice;
    }

    public int getTotalprice() {
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
