package com.innovidio.androidbootstrap.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.innovidio.androidbootstrap.db.converters.DateConverter;
import com.innovidio.androidbootstrap.db.converters.TimestampConverter;
import com.innovidio.androidbootstrap.interfaces.TimeLineItem;

import java.util.Date;

/**
 * Created by MuhammadSalman on 12/6/2018.
 */

@Entity
public class Trip implements TimeLineItem {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String tripTitle;
    private String destination;
   // private String tripdate;
    private String carname;
    private String triptype;
    private int maxspeed;
    private int avgspeed;
    private int distanceCovered;
    private int fueleconomypertrip;
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
    public Trip(String tripTitle, String destination, String carname, String triptype,/* Date starttime, Date endtime,*/ int maxspeed, int avgspeed, int distanceCovered, int fueleconomypertrip, Date saveDate) {
        this.tripTitle = tripTitle;
        this.destination = destination;
        this.carname = carname;
        this.triptype = triptype;
        this.maxspeed = maxspeed;
        this.avgspeed = avgspeed;
        this.distanceCovered = distanceCovered;
//        this.tripdate = tripdate;
//        this.startTime = starttime;
//        this.endTime = endtime;
        this.fueleconomypertrip = fueleconomypertrip;
        this.saveDate = saveDate;
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

    public void setTriptype(String triptype) {
        this.triptype = triptype;
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


//    public void setStarttime(Date starttime) {
//        this.startTime = starttime;
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

    public String getTriptype() {
        return triptype;
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
}
