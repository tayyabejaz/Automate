package com.innovidio.androidbootstrap.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.squareup.moshi.Json;


@Entity
public class CarModel {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMakeid() {
        return makeid;
    }

    public void setMakeid(String makeid) {
        this.makeid = makeid;
    }

    @Json(name = "make_id")
    private String makeid;

    public String getModelname() {
        return modelname;
    }

    public void setModelname(String modelname) {
        this.modelname = modelname;
    }

    @Json(name = "model_name")
    private String modelname;

    public String getTrim() {
        return trim;
    }

    public void setTrim(String trim) {
        this.trim = trim;
    }

    //
    @Json(name = "model_trim")
    private String trim;

    public String getEnginefuel() {
        return enginefuel;
    }

    public void setEnginefuel(String enginefuel) {
        this.enginefuel = enginefuel;
    }

    @Json(name = "model_engine_fuel")
    private String enginefuel;

    public String getEnginecc() {
        return enginecc;
    }

    public void setEnginecc(String enginecc) {
        this.enginecc = enginecc;
    }

    @Json(name = "model_engine_cc")
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

    @Json(name = "model_lkm_city")
    private String fueleconomycityper100km;

    public String getFueleconomymixedper100km() {
        return fueleconomymixedper100km;
    }

    public void setFueleconomymixedper100km(String fueleconomymixedper100km) {
        this.fueleconomymixedper100km = fueleconomymixedper100km;
    }

    @Json(name = "model_lkm_mixed")
    private String fueleconomymixedper100km;

    public String getFuelcapacityinliters() {
        return fuelcapacityinliters;
    }

    public void setFuelcapacityinliters(String fuelcapacityinliters) {
        this.fuelcapacityinliters = fuelcapacityinliters;
    }

    @Json(name = "model_fuel_cap_l")

    private String fuelcapacityinliters;

    public String getModeldrive() {
        return modeldrive;
    }

    public void setModeldrive(String modeldrive) {
        this.modeldrive = modeldrive;
    }

    @Json(name = "model_drive")
    private String modeldrive;

}