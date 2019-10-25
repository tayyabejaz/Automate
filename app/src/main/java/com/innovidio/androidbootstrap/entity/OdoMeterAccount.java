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


@Entity
public class OdoMeterAccount {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int carId;
    private int currentReading;
    private int distanceCovered;
    private int adjustment;
    private int finalReading;
    private String note;
    @ColumnInfo(name = "saveDate")
    @TypeConverters(DateConverter.class)
    private Date saveDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getCurrentReading() {
        return currentReading;
    }

    public void setCurrentReading(int currentReading) {
        this.currentReading = currentReading;
    }

    public int getDistanceCovered() {
        return distanceCovered;
    }

    public void setDistanceCovered(int distanceCovered) {
        this.distanceCovered = distanceCovered;
    }

    public int getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(int adjustment) {
        this.adjustment = adjustment;
    }

    public int getFinalReading() {
        return finalReading;
    }

    public void setFinalReading(int finalReading) {
        this.finalReading = finalReading;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(Date saveDate) {
        this.saveDate = saveDate;
    }
}
