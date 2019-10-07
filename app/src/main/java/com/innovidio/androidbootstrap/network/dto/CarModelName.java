package com.innovidio.androidbootstrap.network.dto;

import com.squareup.moshi.Json;

public class CarModelName {
    @Json(name = "model_name")
    private String modelName;
    @Json(name = "model_make_id")
    private String modelMakeId;

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelMakeId() {
        return modelMakeId;
    }

    public void setModelMakeId(String modelMakeId) {
        this.modelMakeId = modelMakeId;
    }
}
