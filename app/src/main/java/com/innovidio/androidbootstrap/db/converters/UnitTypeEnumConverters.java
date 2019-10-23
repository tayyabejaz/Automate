package com.innovidio.androidbootstrap.db.converters;

import androidx.room.TypeConverter;

import com.innovidio.androidbootstrap.entity.Preferences;
import com.innovidio.androidbootstrap.interfaces.TimeLineItem;

public class UnitTypeEnumConverters {

    @TypeConverter
    public static Preferences.UnitTypeEnum restoreEnum(String enumName) {
        return enumName == null ? null : Preferences.UnitTypeEnum .valueOf(enumName);
    }

    @TypeConverter
    public String saveEnumToString(Preferences.UnitTypeEnum type){
        return type == null ? null : type.name();
    }
}
