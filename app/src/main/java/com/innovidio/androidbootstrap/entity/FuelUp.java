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
    private int tripId;
    private int carId;
    private int odometerreading;
    private int liters;
    private int perunitfuelprice;
    private int totalprice;
    private String location;
    @ColumnInfo(name = "saveDate")
    @TypeConverters(DateConverter.class)
    private Date saveDate;

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    private String fuelType;
    @Ignore
    private int totalFuelUps;

    public int getTotalFuelUps() {
        return totalFuelUps;
    }

    public void setTotalFuelUps(int totalFuelUps) {
        this.totalFuelUps = totalFuelUps;
    }

    public float getFuelAverage() {
        return fuelAverage;
    }

    public void setFuelAverage(float fuelAverage) {
        this.fuelAverage = fuelAverage;
    }

    @Ignore
    private float fuelAverage;

    public FuelUp() {

    }

    @Ignore
    public FuelUp(String carname, int odometerreading, int tripId, int liters, int perunitfuelprice, int totalprice, String location, Date saveDate, String fuelType) {
        this.odometerreading = odometerreading;
        this.liters = liters;
        this.perunitfuelprice = perunitfuelprice;
        this.totalprice = totalprice;
        this.location = location;
        this.tripId = tripId;
        this.saveDate = saveDate;
        this.fuelType = fuelType;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setSaveDate(Date saveDate) {
        this.saveDate = saveDate;
    }


    public int getId() {
        return id;
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
        return Type.FUEL;
    }
}
