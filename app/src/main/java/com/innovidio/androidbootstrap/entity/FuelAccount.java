package com.innovidio.androidbootstrap.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.innovidio.androidbootstrap.db.converters.DateConverter;

import java.util.Date;


@Entity
public class FuelAccount {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int carId;
    private int fuelAdd;
    private int fuelConsume;
    private int odoMeterReading;
    private int reserveFuel;
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

    public int getFuelAdd() {
        return fuelAdd;
    }

    public void setFuelAdd(int fuelAdd) {
        this.fuelAdd = fuelAdd;
    }

    public int getFuelConsume() {
        return fuelConsume;
    }

    public void setFuelConsume(int fuelConsume) {
        this.fuelConsume = fuelConsume;
    }

    public int getOdoMeterReading() {
        return odoMeterReading;
    }

    public void setOdoMeterReading(int odoMeterReading) {
        this.odoMeterReading = odoMeterReading;
    }

    public int getReserveFuel() {
        return reserveFuel;
    }

    public void setReserveFuel(int reserveFuel) {
        this.reserveFuel = reserveFuel;
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
