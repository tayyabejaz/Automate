package com.innovidio.androidbootstrap.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.innovidio.androidbootstrap.db.converters.DateConverter;

import java.util.Date;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String country;
    private String city;
    private boolean isDrivingLicense;
    @ColumnInfo(name = "licenseExpiryDate")
    @TypeConverters(DateConverter.class)
    private Date licenseExpiryDate;
    private boolean anyViolation;
    private int noOfViolations;
    private String email;
    private String userImageUrl = "none";


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isDrivingLicense() {
        return isDrivingLicense;
    }

    public void setDrivingLicense(boolean drivingLicense) {
        isDrivingLicense = drivingLicense;
    }

    public Date getLicenseExpiryDate() {
        return licenseExpiryDate;
    }

    public void setLicenseExpiryDate(Date licenseExpiryDate) {
        this.licenseExpiryDate = licenseExpiryDate;
    }

    public boolean isAnyViolation() {
        return anyViolation;
    }

    public void setAnyViolation(boolean anyViolation) {
        this.anyViolation = anyViolation;
    }

    public int getNoOfViolations() {
        return noOfViolations;
    }

    public void setNoOfViolations(int noOfViolations) {
        this.noOfViolations = noOfViolations;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }
}
