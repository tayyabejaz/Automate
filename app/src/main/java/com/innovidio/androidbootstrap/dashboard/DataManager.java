package com.innovidio.androidbootstrap.dashboard;

import android.location.Location;
import android.util.Log;


public class DataManager {
    private boolean isFirstTime;
    private double distanceM;
    private double curSpeed;
    private int maxSpeed;
    private long time;
    private int currentIncomingSpeed = 0;
    private int currentIncomingSpeedCounter = 0;
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

    public Double getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(Double currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    private Double currentSpeed;


    public int getTotalAvgSpeed() {
        return totalAvgSpeed;
    }

    public void setTotalAvgSpeed(String totalAvgSpeed) {

        int finalavgspeed;
        currentIncomingSpeed = currentIncomingSpeed + (int)(Double.parseDouble(totalAvgSpeed));
        currentIncomingSpeedCounter++;
        if (currentIncomingSpeed >= Integer.MAX_VALUE-500)
        {
            finalavgspeed = currentIncomingSpeed / currentIncomingSpeedCounter;
            currentIncomingSpeedCounter = 1;
        }
        else
        {
            finalavgspeed = currentIncomingSpeed / currentIncomingSpeedCounter;

        }
       // this.totalAvgSpeed = String.valueOf(finalavgspeed);
        this.totalAvgSpeed = finalavgspeed;
        Log.d("incoming speed: ", "" + totalAvgSpeed + "\t" + " total speed: " + "" + currentIncomingSpeed + "\t" + "final avg speed: " + ""+ finalavgspeed);
    }

    private int totalAvgSpeed;

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

    public int getMaxSpeed() {
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

    public void setCurSpeed(int curSpeed) {
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
