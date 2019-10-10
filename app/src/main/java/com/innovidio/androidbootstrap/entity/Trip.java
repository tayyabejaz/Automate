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
    private String tripdate;
    private String carname;
    private String triptype;
    private String maxspeed;
    private String avgspeed;
    private String distanceCovered;
    private String fueleconomypertrip;
    @ColumnInfo(name = "datetimeinmillis")
    @TypeConverters(DateConverter.class)
    private Date datetimeinmillis;
    @ColumnInfo(name = "starttime")
    @TypeConverters({TimestampConverter.class})
    private Date starttime;
    @ColumnInfo(name = "endtime")
    @TypeConverters({TimestampConverter.class})
    private Date endtime;
    // todo is save date added here or not?
    @ColumnInfo(name = "saveDate")
    @TypeConverters(DateConverter.class)
    private Date saveDate;


    public Trip() {

    }

    @Ignore
    public Trip(String tripTitle, String destination, String carname, String triptype, Date starttime, Date endtime, String maxspeed, String avgspeed, String distanceCovered, String tripdate, Date datetimeinmillis, String fueleconomypertrip, Date saveDate) {
        this.tripTitle = tripTitle;
        this.destination = destination;
        this.carname = carname;
        this.triptype = triptype;
        this.maxspeed = maxspeed;
        this.avgspeed = avgspeed;
        this.distanceCovered = distanceCovered;
        this.tripdate = tripdate;
        this.starttime = starttime;
        this.endtime = endtime;
        this.datetimeinmillis = datetimeinmillis;
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

    public void setTripdate(String tripdate) {
        this.tripdate = tripdate;
    }

    public void setCarname(String carname) {
        this.carname = carname;
    }

    public void setTriptype(String triptype) {
        this.triptype = triptype;
    }

    public void setMaxspeed(String maxspeed) {
        this.maxspeed = maxspeed;
    }

    public void setAvgspeed(String avgspeed) {
        this.avgspeed = avgspeed;
    }

    public void setDistanceCovered(String distanceCovered) {
        this.distanceCovered = distanceCovered;
    }

    public void setFueleconomypertrip(String fueleconomypertrip) {
        this.fueleconomypertrip = fueleconomypertrip;
    }

    public void setDatetimeinmillis(Date datetimeinmillis) {
        this.datetimeinmillis = datetimeinmillis;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

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

    public Date getStarttime() {
        return starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public String getMaxspeed() {
        return maxspeed;
    }

    public String getAvgspeed() {
        return avgspeed;
    }

    public String getDistanceCovered() {
        return distanceCovered;
    }

    public String getTripdate() {
        return tripdate;
    }

    public Date getDatetimeinmillis() {
        return datetimeinmillis;
    }

    public String getFueleconomypertrip() {
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
