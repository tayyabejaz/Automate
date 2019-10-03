package com.innovidio.androidbootstrap.model;


import com.google.gson.annotations.SerializedName;

public class CarModel {

    public String getMakeid() {
        return makeid;
    }

    public void setMakeid(String makeid) {
        this.makeid = makeid;
    }

    @SerializedName("make_id")
    private String makeid;

    public String getModelname() {
        return modelname;
    }

    public void setModelname(String modelname) {
        this.modelname = modelname;
    }

    @SerializedName("model_name")
    private String modelname;

    public String getTrim() {
        return trim;
    }

    public void setTrim(String trim) {
        this.trim = trim;
    }

    //
    @SerializedName("model_trim")
    private String trim;

    public String getEnginefuel() {
        return enginefuel;
    }

    public void setEnginefuel(String enginefuel) {
        this.enginefuel = enginefuel;
    }

    @SerializedName("model_engine_fuel")
    private String enginefuel;

    public String getEnginecc() {
        return enginecc;
    }

    public void setEnginecc(String enginecc) {
        this.enginecc = enginecc;
    }

    @SerializedName("model_engine_cc")
    private String enginecc;

    public CarModel() {
    }

    @Override
    public String toString() {
        return this.makeid;
    }


    public String getFueleconomycityper100km() {
        return fueleconomycityper100km;
    }

    public void setFueleconomycityper100km(String fueleconomycityper100km) {
        this.fueleconomycityper100km = fueleconomycityper100km;
    }

    @SerializedName("model_lkm_city")
    private String fueleconomycityper100km;

    public String getFueleconomymixedper100km() {
        return fueleconomymixedper100km;
    }

    public void setFueleconomymixedper100km(String fueleconomymixedper100km) {
        this.fueleconomymixedper100km = fueleconomymixedper100km;
    }

    @SerializedName("model_lkm_mixed")
    private String fueleconomymixedper100km;

    public String getFuelcapacityinliters() {
        return fuelcapacityinliters;
    }

    public void setFuelcapacityinliters(String fuelcapacityinliters) {
        this.fuelcapacityinliters = fuelcapacityinliters;
    }

    @SerializedName("model_fuel_cap_l")

    private String fuelcapacityinliters;

    public String getModeldrive() {
        return modeldrive;
    }

    public void setModeldrive(String modeldrive) {
        this.modeldrive = modeldrive;
    }

    @SerializedName("model_drive")
    private String modeldrive;

}