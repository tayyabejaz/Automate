package com.innovidio.androidbootstrap.model;

/**
 * Created by MuhammadSalman on 11/19/2018.
 */

public class UserInformation {
    private String id;
   // private String name;
    private String email;
    private String userid;

    private String drivinglicense;
   // private String userlicensenumber;
    private String licenseexpirydate;
    private String violation;
    private String numberofviolations;
    private String country;
    private String currency;
    private String fuelunits;
    private String speedunits;
    private String drivedetect;

    public String getSpeedunits() {
        return speedunits;
    }

    public String getDrivedetect() {
        return drivedetect;
    }

    public String getPerunitfuelprice() {
        return perunitfuelprice;
    }

    private String perunitfuelprice;

    public String getId() {
        return id;
    }

    /*public String getName() {
        return name;
    }*/

    public String getEmail() {
        return email;
    }

    public String getUserid() {
        return userid;
    }

    private String distance;

    public UserInformation() {

    }

    public UserInformation(String id/* , String name */, String email , String userid, String drivinglicense/*, String userlicensenumber*/, String licenseexpirydate, String violation, String numberofviolations, String country, String currency, String fuelunits, String distance, String drivedetect, String speedunits, String perunitfuelprice)
    {
        this.drivinglicense = drivinglicense;
       // this.userlicensenumber = userlicensenumber;
        this.licenseexpirydate = licenseexpirydate;
        this.violation = violation;
        this.numberofviolations = numberofviolations;
        this.country = country;
        this.currency = currency;
        this.fuelunits = fuelunits;
        this.distance = distance;
        this.id = id;
       // this.name = name;
        this.email = email;
        this.userid = userid;
        this.drivedetect = drivedetect;
        this.speedunits = speedunits;
        this.perunitfuelprice = perunitfuelprice;
    }

    public String getDrivinglicense() {
        return drivinglicense;
    }

    /*public String getUserlicensenumber() {
        return userlicensenumber;
    }*/

    public String getLicenseexpirydate() {
        return licenseexpirydate;
    }

    public String getViolation() {
        return violation;
    }

    public String getNumberofviolations() {
        return numberofviolations;
    }

    public String getCountry() {
        return country;
    }

    public String getCurrency() {
        return currency;
    }

    public String getFuelunits() {
        return fuelunits;
    }

    public String getDistance() {
        return distance;
    }
}
