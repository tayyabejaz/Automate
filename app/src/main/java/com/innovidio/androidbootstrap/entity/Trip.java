package com.innovidio.androidbootstrap.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.innovidio.androidbootstrap.db.converters.DateConverter;
import com.innovidio.androidbootstrap.db.converters.EnumTypeConverters;
import com.innovidio.androidbootstrap.db.converters.TripTypeConverters;
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
    @ColumnInfo(name = "tripType")
    @TypeConverters(TripTypeConverters.class)
    private TripType tripType;
    private String origin;
    private String destination;
    private int intialOdometer;
    private int finalOdometer;
    private int maxspeed;
    private int avgspeed;
    @ColumnInfo(name = "startTime")
    @TypeConverters(DateConverter.class)
    private Date startTime;
    @ColumnInfo(name = "endTime")
    @TypeConverters(DateConverter.class)
    private Date endTime;
    private Double distanceCovered;
    private Double fueleconomypertrip;
    private Double fuelCostPerUnit;
    private int noOfLitres;
    private Double totalExpenses;


    @Ignore
    private int totalTrips;

    public int getTotalTrips() {
        return totalTrips;
    }

    public void setTotalTrips(int totalTrips) {
        this.totalTrips = totalTrips;
    }

    public int getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(int totalDistance) {
        this.totalDistance = totalDistance;
    }

    @Ignore
    private int totalDistance;


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
    // todo is save date added here or not?
    @ColumnInfo(name = "saveDate")
    @TypeConverters(DateConverter.class)
    private Date saveDate;


    public Trip() {

    }

    @Ignore
    public Trip(String tripTitle, String destination,TripType triptype, int maxspeed, int avgspeed, Double distanceCovered, Double fueleconomypertrip, Date saveDate, Double fuelCostPerUnit, Double totalExpenses, int noOfLitres) {
        this.tripTitle = tripTitle;
        this.destination = destination;
        this.tripType = triptype;
        this.maxspeed = maxspeed;
        this.avgspeed = avgspeed;
        this.distanceCovered = distanceCovered;
        this.fueleconomypertrip = fueleconomypertrip;
        this.saveDate = saveDate;
        this.fuelCostPerUnit = fuelCostPerUnit;
        this.totalExpenses = totalExpenses;
        this.noOfLitres = noOfLitres;
    }

    public Double getFuelCostPerUnit() {
        return fuelCostPerUnit;
    }

    public void setFuelCostPerUnit(Double fuelCostPerUnit) {
        this.fuelCostPerUnit = fuelCostPerUnit;
    }

    public Double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(Double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public int getNoOfLitres() {
        return noOfLitres;
    }

    public void setNoOfLitres(int noOfLitres) {
        this.noOfLitres = noOfLitres;
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


    public void setTripType(TripType tripType) {
        this.tripType = tripType;
    }

    public void setMaxspeed(int maxspeed) {
        this.maxspeed = maxspeed;
    }

    public void setAvgspeed(int avgspeed) {
        this.avgspeed = avgspeed;
    }

    public void setDistanceCovered(Double distanceCovered) {
        this.distanceCovered = distanceCovered;
    }

    public void setFueleconomypertrip(Double fueleconomypertrip) {
        this.fueleconomypertrip = fueleconomypertrip;
    }


    public void setSaveDate(Date saveDate) {
        this.saveDate = saveDate;
    }

    public int getId() {
        return this.id;
    }


    public TripType getTripType() {
        return tripType;
    }

    public int getMaxspeed() {
        return maxspeed;
    }

    public int getAvgspeed() {
        return avgspeed;
    }

    public Double getDistanceCovered() {
        return distanceCovered;
    }

    public Double getFueleconomypertrip() {
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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
