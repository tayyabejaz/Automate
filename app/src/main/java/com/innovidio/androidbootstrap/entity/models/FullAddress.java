package com.innovidio.androidbootstrap.entity.models;

public class FullAddress {

    private String address;
    private String city;
    private String state ;
    private String country ;
    private String postalCode;
    private String knownName;

    public FullAddress(String address,String city, String state, String  country, String  postalCode, String knownName){
        this.address = address;
        this.city =  city;
        this.state =  state;
        this.country = country;
        this.postalCode = postalCode;
        this.knownName = knownName;
    }

    public FullAddress(){

    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getKnownName() {
        return knownName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setKnownName(String knownName) {
        this.knownName = knownName;
    }
}
