package com.innovidio.androidbootstrap.db.converters;

import androidx.room.TypeConverter;

import com.innovidio.androidbootstrap.entity.Trip;
import com.innovidio.androidbootstrap.interfaces.TimeLineItem;

public class TripTypeConverters {

    @TypeConverter
    public static Trip.TripType restoreEnum(String enumName) {
        return enumName == null ? null : Trip.TripType.valueOf(enumName);
    }

    @TypeConverter
    public String saveEnumToString(Trip.TripType type){
        return type == null ? null : type.name();
    }

    public static Trip.TripType restoreEnumFromString(String enumName) {
        return enumName == null ? null : Trip.TripType.valueOf(enumName);
    }


    public String getEnumToString(Trip.TripType type){
        return type == null ? null : type.name();
    }
}

