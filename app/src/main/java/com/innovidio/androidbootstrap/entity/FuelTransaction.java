package com.innovidio.androidbootstrap.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.innovidio.androidbootstrap.db.converters.DateConverter;
import com.innovidio.androidbootstrap.db.converters.TransactionTypeConverters;

import java.util.Date;


@Entity
public class FuelTransaction {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int carId;
    private int fuelQuantity;
    @ColumnInfo(name = "transactionType")
    @TypeConverters(TransactionTypeConverters.class)
    private TransactionType transactionType; //(Debit, Credit)
    private int odoMeterReading;
    private int feulBallance;
    private String note;
    @ColumnInfo(name = "saveDate")
    @TypeConverters(DateConverter.class)
    private Date saveDate;


    public enum TransactionType{
        Credit,Debit
    }

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

    public int getFuelQuantity() {
        return fuelQuantity;
    }

    public void setFuelQuantity(int fuelQuantity) {
        this.fuelQuantity = fuelQuantity;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public int getOdoMeterReading() {
        return odoMeterReading;
    }

    public void setOdoMeterReading(int odoMeterReading) {
        this.odoMeterReading = odoMeterReading;
    }

    public int getFeulBallance() {
        return feulBallance;
    }

    public void setFeulBallance(int feulBallance) {
        this.feulBallance = feulBallance;
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
