package com.innovidio.androidbootstrap.entity.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeLine {
    private int id;
    private Date saveDate;
    private String location;
    private long totalPayment;
    private String type;
    private long meterStart;
    private long meterEnd;
    private String meterCurrentValue;
    private String serviceDetails;

    public TimeLine() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSaveDate(Date saveDate) {
        this.saveDate = saveDate;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTotalPrice(long totalPayment) {
        this.totalPayment = totalPayment;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMeterStart(long meterStart) {
        this.meterStart = meterStart;
    }

    public void setMeterEnd(long meterEnd) {
        this.meterEnd = meterEnd;
    }

    public void setMeterCurrentValue(String meterCurrentValue) {
        this.meterCurrentValue = meterCurrentValue;
    }

    public void setServiceDetails(String serviceDetails) {
        this.serviceDetails = serviceDetails;
    }

    public TimeLine(int id, Date saveDate, String location, long totalPayment,
                    String type, long meterStart, long meterEnd, String meterCurrentValue, String serviceDetails) {

        this.id = id;
        this.saveDate = saveDate;
        this.location = location;
        this.totalPayment = totalPayment;
        this.type = type;
        this.meterStart = meterStart;
        this.meterEnd = meterEnd;
        this.meterCurrentValue = meterCurrentValue;
        this.serviceDetails = serviceDetails;

    }

    public int getId() {
        return id;
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

    public String getLocation() {
        return location;
    }

    public long getTotalPayment() {
        return totalPayment;
    }

    public String getTotalPaymentInString() {
        return String.valueOf(this.totalPayment);
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

    public String getMeterCurrentValue() {
        return meterCurrentValue;
    }

    public String getMeterCurrentValueinString() {
        return String.valueOf(this.meterCurrentValue);
    }

    public String getServiceDetails() {
        return serviceDetails;
    }
}
