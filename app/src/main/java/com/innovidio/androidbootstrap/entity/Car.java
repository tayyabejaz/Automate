package com.innovidio.androidbootstrap.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Car {

    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private boolean isEnable=true;
    private String registrationNo;
    private int currentOdomaterReading;
    private int makeYear;
    private String manufacturer;
    private String modelName;
    private String subModel;
    private String engineFuel;
    private int enginecc;
    private int fuelEconomyCityPer100km;
    private int fuelEconomyMixedPer100km;
    private int fuelCapacityInLiters;
    private String modelDrive;
    private String transmissionType;
    String carImageUrl ="none";

    public Car() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public int getCurrentOdomaterReading() {
        return currentOdomaterReading;
    }

    public void setCurrentOdomaterReading(int currentOdomaterReading) {
        this.currentOdomaterReading = currentOdomaterReading;
    }

    public int getMakeYear() {
        return makeYear;
    }

    public void setMakeYear(int makeYear) {
        this.makeYear = makeYear;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getSubModel() {
        return subModel;
    }

    public void setSubModel(String subModel) {
        this.subModel = subModel;
    }

    public String getEngineFuel() {
        return engineFuel;
    }

    public void setEngineFuel(String engineFuel) {
        this.engineFuel = engineFuel;
    }

    public int getEnginecc() {
        return enginecc;
    }

    public void setEnginecc(int enginecc) {
        this.enginecc = enginecc;
    }

    public int getFuelEconomyCityPer100km() {
        return fuelEconomyCityPer100km;
    }

    public void setFuelEconomyCityPer100km(int fuelEconomyCityPer100km) {
        this.fuelEconomyCityPer100km = fuelEconomyCityPer100km;
    }

    public int getFuelEconomyMixedPer100km() {
        return fuelEconomyMixedPer100km;
    }

    public void setFuelEconomyMixedPer100km(int fuelEconomyMixedPer100km) {
        this.fuelEconomyMixedPer100km = fuelEconomyMixedPer100km;
    }

    public int getFuelCapacityInLiters() {
        return fuelCapacityInLiters;
    }

    public void setFuelCapacityInLiters(int fuelCapacityInLiters) {
        this.fuelCapacityInLiters = fuelCapacityInLiters;
    }

    public String getModelDrive() {
        return modelDrive;
    }

    public void setModelDrive(String modelDrive) {
        this.modelDrive = modelDrive;
    }

    public String getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(String transmissionType) {
        this.transmissionType = transmissionType;
    }

    public String getCarImageUrl() {
        return carImageUrl;
    }

    public void setCarImageUrl(String carImageUrl) {
        this.carImageUrl = carImageUrl;
    }
}