package com.innovidio.androidbootstrap.db.converters;

import androidx.room.TypeConverter;

import com.innovidio.androidbootstrap.interfaces.TimeLineItem;

public class EnumTypeConverters {

    @TypeConverter
    public static TimeLineItem.Type restoreEnum(String enumName) {
        return enumName == null ? null : TimeLineItem.Type.valueOf(enumName);
    }

    @TypeConverter
    public String saveEnumToString(TimeLineItem.Type type){
        return type == null ? null : type.name();
    }

}
