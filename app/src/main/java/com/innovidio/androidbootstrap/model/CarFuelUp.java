package com.innovidio.androidbootstrap.model;

/**
 * Created by MuhammadSalman on 1/24/2019.
 */

public class CarFuelUp {
    private String carname;
    private String odometerreading;
    private String liters;
    private String perunitfuelprice;
    private String totalprice;
    private String location;

    public CarFuelUp(String carname, String odometerreading, String liters, String perunitfuelprice, String totalprice, String location) {
        this.carname = carname;
        this.odometerreading = odometerreading;
        this.liters = liters;
        this.perunitfuelprice = perunitfuelprice;
        this.totalprice = totalprice;
        this.location = location;
    }

    public CarFuelUp() {

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
}
