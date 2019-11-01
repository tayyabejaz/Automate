package com.innovidio.androidbootstrap.db.converters;
import androidx.room.TypeConverter;
import com.innovidio.androidbootstrap.entity.Alarm;

public class AlarmTypeConverters {

    @TypeConverter
    public static Alarm.AlarmType restoreEnum(String enumName) {
        return enumName == null ? null : Alarm.AlarmType.valueOf(enumName);
    }

    @TypeConverter
    public String saveEnumToString(Alarm.AlarmType type){
        return type == null ? null : type.name();
    }

    public String getEnumByString(Alarm.AlarmType type){
        return type == null ? null : type.name();
    }

    public static Alarm.AlarmType restoreEnumToString(String enumName) {
        return enumName == null ? null : Alarm.AlarmType.valueOf(enumName);
    }
}
