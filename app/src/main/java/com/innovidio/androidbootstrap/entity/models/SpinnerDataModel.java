package com.innovidio.androidbootstrap.entity.models;

import com.innovidio.androidbootstrap.R;

public class SpinnerDataModel {

    int carImage;
    String carName;
    String carManufacturer;
    String carTitle;

    public String getCarManufacturer() {
        return carManufacturer;
    }

    public void setCarManufacturer(String carManufacturer) {
        this.carManufacturer = carManufacturer;
    }

    public String getCarTitle() {
        return carTitle;
    }

    public void setCarTitle(String carTitle) {
        this.carTitle = carTitle;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    String carPlate;


    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public SpinnerDataModel() {
    }

    public SpinnerDataModel(String carName, String carManufacturer, String carTitle, String carPlate) {
        this.carImage = R.drawable.automate_select_car_placeholder;
        this.carName = carName;
        this.carManufacturer = carManufacturer;
        this.carTitle = carTitle;
        this.carPlate = carPlate;
    }
}