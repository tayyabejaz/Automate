package com.innovidio.androidbootstrap.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by MuhammadSalman on 12/6/2018.
 */

@Entity
public class Trip {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String tripTitle;
    private String tripdate;
    private String carname;
    private String triptype;
    private String starttime;
    private String endtime;
    private String maxspeed;
    private String avgspeed;
    private String distancecovered;
    private long datetimeinmillis;
    private String fueleconomypertrip;


    public Trip(){

    }

    public Trip(String tripTitle, String carname, String triptype, String starttime, String endtime, String maxspeed, String avgspeed, String distancecovered, String tripdate , long datetimeinmillis , String fueleconomypertrip) {
        this.tripTitle =  tripTitle;
        this.carname = carname;
        this.triptype = triptype;
        this.starttime = starttime;
        this.endtime = endtime;
        this.maxspeed = maxspeed;
        this.avgspeed = avgspeed;
        this.distancecovered = distancecovered;
        this.tripdate = tripdate;
        this.datetimeinmillis = datetimeinmillis;
        this.fueleconomypertrip = fueleconomypertrip;
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

    public String getStarttime() {
        return starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public String getMaxspeed() {
        return maxspeed;
    }

    public String getAvgspeed() {
        return avgspeed;
    }

    public String getDistancecovered() {
        return distancecovered;
    }

    public String getTripdate() {
        return tripdate;
    }

    public long getDatetimeinmillis() {
        return datetimeinmillis;
    }

    public String getFueleconomypertrip() {
        return fueleconomypertrip;
    }

    public String getTripTitle() {
        return tripTitle;
    }
}
