package com.innovidio.androidbootstrap.entity.models;

import java.util.Date;

public class TimeLine {
    private int id;
    private Date saveDate;
    private String location;
    private long totalPayment;
    private String type;
    private long meterStart;
    private long meterEnd;
    private long meterCurrentValue;
    private String serviceDetails;

    public TimeLine( int id, Date saveDate, String location, long totalPayment,
                     String type, long meterStart, long meterEnd, long meterCurrentValue, String serviceDetails){

        this.id = id;
        this.saveDate =  saveDate;
        this.location = location;
        this.totalPayment = totalPayment;
        this.type = type;
        this.meterStart = meterStart;
        this.meterEnd = meterEnd;
        this.meterCurrentValue =  meterCurrentValue;
        this.serviceDetails =  serviceDetails;

    }

    public int getId() {
        return id;
    }

    public Date getSaveDate() {
        return saveDate;
    }

    public String getLocation() {
        return location;
    }

    public long getTotalPayment() {
        return totalPayment;
    }

    public String getType() {
        return type;
    }

    public long getMeterStart() {
        return meterStart;
    }

    public long getMeterEnd() {
        return meterEnd;
    }

    public long getMeterCurrentValue() {
        return meterCurrentValue;
    }

    public String getServiceDetails() {
        return serviceDetails;
    }
}
