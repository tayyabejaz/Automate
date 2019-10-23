package com.innovidio.androidbootstrap.dashboard;

import android.location.Location;
import android.util.Log;

/**
 * Created by RanaTalal on 28/o3/2018.
 */

public class DataManager {
    private boolean isFirstTime;
    private double distanceM;
    private double curSpeed;
    private double maxSpeed;
    private long time;
    private int currentincomingspeed = 0;
    private int currentincomingspeedcounter = 0;
    private double speedLimit;

    public double getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(double speedLimit) {
        this.speedLimit = speedLimit;
    }

    private Location location;

    public Location getLocation() {
        return location;
    }

    public String getCurrentspeed() {
        return currentspeed;
    }

    public void setCurrentspeed(String currentspeed) {
        this.currentspeed = currentspeed;
    }

    private String currentspeed;


    public String getTotalAvgSpeed() {
        return totalAvgSpeed;
    }

    public void setTotalAvgSpeed(String totalAvgSpeed) {

        int finalavgspeed;
        currentincomingspeed = currentincomingspeed + (Integer.parseInt(totalAvgSpeed));
        currentincomingspeedcounter++;
        if (currentincomingspeed >= Integer.MAX_VALUE-500)
        {
            finalavgspeed = currentincomingspeed / currentincomingspeedcounter;
            currentincomingspeedcounter = 1;
        }
        else
        {
            finalavgspeed = currentincomingspeed / currentincomingspeedcounter;

        }
        this.totalAvgSpeed = String.valueOf(finalavgspeed);
        Log.d("incoming speed: ", "" + totalAvgSpeed + "\t" + " total speed: " + "" + currentincomingspeed + "\t" + "final avg speed: " + ""+ finalavgspeed);
    }

    private String totalAvgSpeed;

    public double getTotalavg() {
        return totalavg;
    }

    public void setTotalavg(double totalavg) {
        this.totalavg = totalavg;
    }

    private double totalavg;

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    private String speed;

    public String getSpeedunits() {
        return speedunits;
    }

    public void setSpeedunits(String speedunits) {
        this.speedunits = speedunits;
    }

    private String speedunits;


    public DataManager() {
        distanceM = 0;
        curSpeed = 0;
        maxSpeed = 0;
    }



    public void addDistance(double distance){

        distanceM = distanceM + distance;
    }

    public double getDistance(){
        return distanceM;

    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public double getAverageSpeed(){
        double average;
        String units;
        if (time <= 0) {
            average = 0.0;
        } else {

            average = (distanceM / (time / 1000)) * 3.6;
        }
        return average;
    }
   /* public double getAverageSpeedMotion(){
        double motionTime = time - timeStopped;
        double average;
        String units;
        if (motionTime <= 0){
            average = 0.0;
        } else {
            average = (distanceM / (motionTime / 1000)) * 3.6;
        }
        return average;
    }*/

    public void setCurSpeed(double curSpeed) {
        this.curSpeed = curSpeed;
        if (curSpeed > maxSpeed){
            maxSpeed = curSpeed;
        }
    }

    public boolean isFirstTime() {
        return isFirstTime;
    }

    public void setFirstTime(boolean isFirstTime) {
        this.isFirstTime = isFirstTime;
    }
    public double getCurSpeed() {
        return curSpeed;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        Log.d("talal", "setTime: "+time);
        this.time = time;
    }

    public void setLocation(Location location) {
        this.location=location;
    }
}
