package com.innovidio.androidbootstrap.db.converters;

import androidx.room.TypeConverter;

import com.innovidio.androidbootstrap.entity.FuelTransaction;
import com.innovidio.androidbootstrap.interfaces.TimeLineItem;

public class TransactionTypeConverters {

    @TypeConverter
    public static FuelTransaction.TransactionType restoreEnum(String enumName) {
        return enumName == null ? null : FuelTransaction.TransactionType.valueOf(enumName);
    }

    @TypeConverter
    public String saveEnumToString(FuelTransaction.TransactionType type){
        return type == null ? null : type.name();
    }
}
