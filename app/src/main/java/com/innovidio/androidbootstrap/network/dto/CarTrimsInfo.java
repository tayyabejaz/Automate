package com.innovidio.androidbootstrap.network.dto;

import com.squareup.moshi.Json;

public class CarTrimsInfo {
    @Json(name = "model_id")
    private String modelId;
    @Json(name = "model_make_id")
    private String modelMakeId;
    @Json(name = "model_name")
    private String modelName;
    @Json(name = "model_trim")
    private String modelTrim;
    @Json(name = "model_year")
    private String modelYear;
    @Json(name = "model_body")
    private String modelBody;
    @Json(name = "model_engine_position")
    private String modelEnginePosition;
    @Json(name = "model_engine_cc")
    private String modelEngineCc;
    @Json(name = "model_engine_cyl")
    private String modelEngineCyl;
    @Json(name = "model_engine_type")
    private String modelEngineType;
    @Json(name = "model_engine_valves_per_cyl")
    private String modelEngineValvesPerCyl;
    @Json(name = "model_engine_power_ps")
    private String modelEnginePowerPs;
    @Json(name = "model_engine_power_rpm")
    private Object modelEnginePowerRpm;
    @Json(name = "model_engine_torque_nm")
    private String modelEngineTorqueNm;
    @Json(name = "model_engine_torque_rpm")
    private Object modelEngineTorqueRpm;
    @Json(name = "model_engine_bore_mm")
    private Object modelEngineBoreMm;
    @Json(name = "model_engine_stroke_mm")
    private Object modelEngineStrokeMm;
    @Json(name = "model_engine_compression")
    private String modelEngineCompression;
    @Json(name = "model_engine_fuel")
    private String modelEngineFuel;
    @Json(name = "model_top_speed_kph")
    private Object modelTopSpeedKph;
    @Json(name = "model_0_to_100_kph")
    private Object model0To100Kph;
    @Json(name = "model_drive")
    private String modelDrive;
    @Json(name = "model_transmission_type")
    private String modelTransmissionType;
    @Json(name = "model_seats")
    private Object modelSeats;
    @Json(name = "model_doors")
    private String modelDoors;
    @Json(name = "model_weight_kg")
    private String modelWeightKg;
    @Json(name = "model_length_mm")
    private Object modelLengthMm;
    @Json(name = "model_width_mm")
    private Object modelWidthMm;
    @Json(name = "model_height_mm")
    private Object modelHeightMm;
    @Json(name = "model_wheelbase_mm")
    private Object modelWheelbaseMm;
    @Json(name = "model_lkm_hwy")
    private String modelLkmHwy;
    @Json(name = "model_lkm_mixed")
    private String modelLkmMixed;
    @Json(name = "model_lkm_city")
    private String modelLkmCity;
    @Json(name = "model_fuel_cap_l")
    private String modelFuelCapL;
    @Json(name = "model_sold_in_us")
    private String modelSoldInUs;
    @Json(name = "model_co2")
    private Object modelCo2;
    @Json(name = "model_make_display")
    private String modelMakeDisplay;
    @Json(name = "make_display")
    private String makeDisplay;
    @Json(name = "make_country")
    private String makeCountry;

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getModelMakeId() {
        return modelMakeId;
    }

    public void setModelMakeId(String modelMakeId) {
        this.modelMakeId = modelMakeId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelTrim() {
        return modelTrim;
    }

    public void setModelTrim(String modelTrim) {
        this.modelTrim = modelTrim;
    }

    public String getModelYear() {
        return modelYear;
    }

    public void setModelYear(String modelYear) {
        this.modelYear = modelYear;
    }

    public String getModelBody() {
        return modelBody;
    }

    public void setModelBody(String modelBody) {
        this.modelBody = modelBody;
    }

    public String getModelEnginePosition() {
        return modelEnginePosition;
    }

    public void setModelEnginePosition(String modelEnginePosition) {
        this.modelEnginePosition = modelEnginePosition;
    }

    public String getModelEngineCc() {
        return modelEngineCc;
    }

    public void setModelEngineCc(String modelEngineCc) {
        this.modelEngineCc = modelEngineCc;
    }

    public String getModelEngineCyl() {
        return modelEngineCyl;
    }

    public void setModelEngineCyl(String modelEngineCyl) {
        this.modelEngineCyl = modelEngineCyl;
    }

    public String getModelEngineType() {
        return modelEngineType;
    }

    public void setModelEngineType(String modelEngineType) {
        this.modelEngineType = modelEngineType;
    }

    public String getModelEngineValvesPerCyl() {
        return modelEngineValvesPerCyl;
    }

    public void setModelEngineValvesPerCyl(String modelEngineValvesPerCyl) {
        this.modelEngineValvesPerCyl = modelEngineValvesPerCyl;
    }

    public String getModelEnginePowerPs() {
        return modelEnginePowerPs;
    }

    public void setModelEnginePowerPs(String modelEnginePowerPs) {
        this.modelEnginePowerPs = modelEnginePowerPs;
    }

    public Object getModelEnginePowerRpm() {
        return modelEnginePowerRpm;
    }

    public void setModelEnginePowerRpm(Object modelEnginePowerRpm) {
        this.modelEnginePowerRpm = modelEnginePowerRpm;
    }

    public String getModelEngineTorqueNm() {
        return modelEngineTorqueNm;
    }

    public void setModelEngineTorqueNm(String modelEngineTorqueNm) {
        this.modelEngineTorqueNm = modelEngineTorqueNm;
    }

    public Object getModelEngineTorqueRpm() {
        return modelEngineTorqueRpm;
    }

    public void setModelEngineTorqueRpm(Object modelEngineTorqueRpm) {
        this.modelEngineTorqueRpm = modelEngineTorqueRpm;
    }

    public Object getModelEngineBoreMm() {
        return modelEngineBoreMm;
    }

    public void setModelEngineBoreMm(Object modelEngineBoreMm) {
        this.modelEngineBoreMm = modelEngineBoreMm;
    }

    public Object getModelEngineStrokeMm() {
        return modelEngineStrokeMm;
    }

    public void setModelEngineStrokeMm(Object modelEngineStrokeMm) {
        this.modelEngineStrokeMm = modelEngineStrokeMm;
    }

    public String getModelEngineCompression() {
        return modelEngineCompression;
    }

    public void setModelEngineCompression(String modelEngineCompression) {
        this.modelEngineCompression = modelEngineCompression;
    }

    public String getModelEngineFuel() {
        return modelEngineFuel;
    }

    public void setModelEngineFuel(String modelEngineFuel) {
        this.modelEngineFuel = modelEngineFuel;
    }

    public Object getModelTopSpeedKph() {
        return modelTopSpeedKph;
    }

    public void setModelTopSpeedKph(Object modelTopSpeedKph) {
        this.modelTopSpeedKph = modelTopSpeedKph;
    }

    public Object getModel0To100Kph() {
        return model0To100Kph;
    }

    public void setModel0To100Kph(Object model0To100Kph) {
        this.model0To100Kph = model0To100Kph;
    }

    public String getModelDrive() {
        return modelDrive;
    }

    public void setModelDrive(String modelDrive) {
        this.modelDrive = modelDrive;
    }

    public String getModelTransmissionType() {
        return modelTransmissionType;
    }

    public void setModelTransmissionType(String modelTransmissionType) {
        this.modelTransmissionType = modelTransmissionType;
    }

    public Object getModelSeats() {
        return modelSeats;
    }

    public void setModelSeats(Object modelSeats) {
        this.modelSeats = modelSeats;
    }

    public String getModelDoors() {
        return modelDoors;
    }

    public void setModelDoors(String modelDoors) {
        this.modelDoors = modelDoors;
    }

    public String getModelWeightKg() {
        return modelWeightKg;
    }

    public void setModelWeightKg(String modelWeightKg) {
        this.modelWeightKg = modelWeightKg;
    }

    public Object getModelLengthMm() {
        return modelLengthMm;
    }

    public void setModelLengthMm(Object modelLengthMm) {
        this.modelLengthMm = modelLengthMm;
    }

    public Object getModelWidthMm() {
        return modelWidthMm;
    }

    public void setModelWidthMm(Object modelWidthMm) {
        this.modelWidthMm = modelWidthMm;
    }

    public Object getModelHeightMm() {
        return modelHeightMm;
    }

    public void setModelHeightMm(Object modelHeightMm) {
        this.modelHeightMm = modelHeightMm;
    }

    public Object getModelWheelbaseMm() {
        return modelWheelbaseMm;
    }

    public void setModelWheelbaseMm(Object modelWheelbaseMm) {
        this.modelWheelbaseMm = modelWheelbaseMm;
    }

    public String getModelLkmHwy() {
        return modelLkmHwy;
    }

    public void setModelLkmHwy(String modelLkmHwy) {
        this.modelLkmHwy = modelLkmHwy;
    }

    public String getModelLkmMixed() {
        return modelLkmMixed;
    }

    public void setModelLkmMixed(String modelLkmMixed) {
        this.modelLkmMixed = modelLkmMixed;
    }

    public String getModelLkmCity() {
        return modelLkmCity;
    }

    public void setModelLkmCity(String modelLkmCity) {
        this.modelLkmCity = modelLkmCity;
    }

    public String getModelFuelCapL() {
        return modelFuelCapL;
    }

    public void setModelFuelCapL(String modelFuelCapL) {
        this.modelFuelCapL = modelFuelCapL;
    }

    public String getModelSoldInUs() {
        return modelSoldInUs;
    }

    public void setModelSoldInUs(String modelSoldInUs) {
        this.modelSoldInUs = modelSoldInUs;
    }

    public Object getModelCo2() {
        return modelCo2;
    }

    public void setModelCo2(Object modelCo2) {
        this.modelCo2 = modelCo2;
    }

    public String getModelMakeDisplay() {
        return modelMakeDisplay;
    }

    public void setModelMakeDisplay(String modelMakeDisplay) {
        this.modelMakeDisplay = modelMakeDisplay;
    }

    public String getMakeDisplay() {
        return makeDisplay;
    }

    public void setMakeDisplay(String makeDisplay) {
        this.makeDisplay = makeDisplay;
    }

    public String getMakeCountry() {
        return makeCountry;
    }

    public void setMakeCountry(String makeCountry) {
        this.makeCountry = makeCountry;
    }
}
