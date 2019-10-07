package com.innovidio.androidbootstrap.network.dto;

import com.squareup.moshi.Json;

public class CarMakesByYear {

    @Json(name = "make_id")
    private String makeId;
    @Json(name = "make_display")
    private String makeDisplay;
    @Json(name = "make_is_common")
    private String makeIsCommon;
    @Json(name = "make_country")
    private String makeCountry;

    public String getMakeId() {
        return makeId;
    }

    public void setMakeId(String makeId) {
        this.makeId = makeId;
    }

    public String getMakeDisplay() {
        return makeDisplay;
    }

    public void setMakeDisplay(String makeDisplay) {
        this.makeDisplay = makeDisplay;
    }

    public String getMakeIsCommon() {
        return makeIsCommon;
    }

    public void setMakeIsCommon(String makeIsCommon) {
        this.makeIsCommon = makeIsCommon;
    }

    public String getMakeCountry() {
        return makeCountry;
    }

    public void setMakeCountry(String makeCountry) {
        this.makeCountry = makeCountry;
    }

}

