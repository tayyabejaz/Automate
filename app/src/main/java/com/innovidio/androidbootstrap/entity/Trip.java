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
 * Created by MuhammadSalman on 12/6/2018.
 */

@Entity
public class Trip implements TimeLineItem {

    @PrimaryKey(autoGenerate = true)
    private int id;

    int carId;
    private String tripTitle;
    private String destination;
    // private String tripdate;
    private String carname;
    private String tripType;
    private int maxspeed;
    private int avgspeed;
    private int distanceCovered;
    private int fueleconomypertrip;
    private int fuelCostPerUnit;
    private int totalExpenses;
    private int noOfLitres;
    private String origin;
    private int intialOdometer;
    private int finalOdometer;


    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public int getIntialOdometer() {
        return intialOdometer;
    }

    public void setIntialOdometer(int intialOdometer) {
        this.intialOdometer = intialOdometer;
    }

    public int getFinalOdometer() {
        return finalOdometer;
    }

    public void setFinalOdometer(int finalOdometer) {
        this.finalOdometer = finalOdometer;
    }

    //    @ColumnInfo(name = "startTime")
//    @TypeConverters({TimestampConverter.class})
//    private Date startTime;
//    @ColumnInfo(name = "endTime")
//    @TypeConverters({TimestampConverter.class})
//    private Date endTime;
    // todo is save date added here or not?
    @ColumnInfo(name = "saveDate")
    @TypeConverters(DateConverter.class)
    private Date saveDate;


    public Trip() {

    }

    @Ignore
    public Trip(String tripTitle, String destination, String carname, String triptype, int maxspeed, int avgspeed, int distanceCovered, int fueleconomypertrip, Date saveDate, int fuelCostPerUnit, int totalExpenses, int noOfLitres) {
        this.tripTitle = tripTitle;
        this.destination = destination;
        this.carname = carname;
        this.tripType = triptype;
        this.maxspeed = maxspeed;
        this.avgspeed = avgspeed;
        this.distanceCovered = distanceCovered;
//        this.tripdate = tripdate;
//        this.startTime = startTime;
//        this.endTime = endtime;
        this.fueleconomypertrip = fueleconomypertrip;
        this.saveDate = saveDate;
        this.fuelCostPerUnit = fuelCostPerUnit;
        this.totalExpenses = totalExpenses;
        this.noOfLitres = noOfLitres;
    }

    public int getFuelCostPerUnit() {
        return fuelCostPerUnit;
    }

    public void setFuelCostPerUnit(int fuelCostPerUnit) {
        this.fuelCostPerUnit = fuelCostPerUnit;
    }

    public int getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(int totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public int getNoOfLitres() {
        return noOfLitres;
    }

    public void setNoOfLitres(int noOfLitres) {
        this.noOfLitres = noOfLitres;
    }

    public String getCarname() {
        return carname;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTripTitle(String tripTitle) {
        this.tripTitle = tripTitle;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

//    public void setTripdate(String tripdate) {
//        this.tripdate = tripdate;
//    }

    public void setCarname(String carname) {
        this.carname = carname;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public void setMaxspeed(int maxspeed) {
        this.maxspeed = maxspeed;
    }

    public void setAvgspeed(int avgspeed) {
        this.avgspeed = avgspeed;
    }

    public void setDistanceCovered(int distanceCovered) {
        this.distanceCovered = distanceCovered;
    }

    public void setFueleconomypertrip(int fueleconomypertrip) {
        this.fueleconomypertrip = fueleconomypertrip;
    }


//    public void setStarttime(Date startTime) {
//        this.startTime = startTime;
//    }
//
//    public void setEndtime(Date endtime) {
//        this.endTime = endtime;
//    }

    public void setSaveDate(Date saveDate) {
        this.saveDate = saveDate;
    }

    public int getId() {
        return this.id;
    }

    public String getcarname() {
        return carname;
    }

    public String getTripType() {
        return tripType;
    }

//    public Date getStarttime() {
//        return startTime;
//    }
//
//    public Date getEndtime() {
//        return endTime;
//    }

    public int getMaxspeed() {
        return maxspeed;
    }

    public int getAvgspeed() {
        return avgspeed;
    }

    public int getDistanceCovered() {
        return distanceCovered;
    }
//
//    public Date getTripdate() {
//        return tripdate;
//    }

    public int getFueleconomypertrip() {
        return fueleconomypertrip;
    }

    public String getTripTitle() {
        return tripTitle;
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

    public String getDestination() {
        return destination;
    }

    @Override
    public Date getInsertDateTime() {
        return this.saveDate;
    }

    @Override
    public Type getType() {
        return Type.TRIP;
    }


    public enum TripType {
        PERSONAL, COMMERCIAL, CUSTOM, OFFICIAL;
    }
}
