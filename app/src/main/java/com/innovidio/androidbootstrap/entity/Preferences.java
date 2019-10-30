package com.innovidio.androidbootstrap.entity;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Preferences {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private boolean isAutoDetect = false;
    private String country;
    private String currency;
   // @ColumnInfo(name = "fuelUnit")
   // @TypeConverters(UnitTypeEnumConverters.class)
    private String fuelUnit;
  //  @ColumnInfo(name = "distanceUnit")
  //  @TypeConverters(UnitTypeEnumConverters.class)
    private String distanceUnit;
  //  @ColumnInfo(name = "speedUnit")
 //   @TypeConverters(UnitTypeEnumConverters.class)
    private String speedUnit;
    private int speedLimit = 0;
    private Double fuelUnitPrice;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAutoDetect() {
        return isAutoDetect;
    }

    public void setAutoDetect(boolean autoDetect) {
        isAutoDetect = autoDetect;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getFuelUnit() {
        return fuelUnit;
    }

    public void setFuelUnit(String fuelUnit) {
        this.fuelUnit = fuelUnit;
    }

    public String getDistanceUnit() {
        return distanceUnit;
    }

    public void setDistanceUnit(String distanceUnit) {
        this.distanceUnit = distanceUnit;
    }

    public String getSpeedUnit() {
        return speedUnit;
    }

    public void setSpeedUnit(String speedUnit) {
        this.speedUnit = speedUnit;
    }

    public int getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(int speedLimit) {
        this.speedLimit = speedLimit;
    }

    public Double getFuelUnitPrice() {
        return fuelUnitPrice;
    }

    public void setFuelUnitPrice(Double fuelUnitPrice) {
        this.fuelUnitPrice = fuelUnitPrice;
    }


    public enum UnitTypeEnum {
        Gallons,Liters, KM,Miles, M_HR,KM_HR;;
    }

}
