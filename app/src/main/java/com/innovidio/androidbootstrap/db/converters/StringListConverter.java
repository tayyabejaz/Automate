package com.innovidio.androidbootstrap.db.converters;



import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.List;

public class StringListConverter {
    @TypeConverter
    public List<String> gettingListFromString(String genreIds) {
        List<String> list = new ArrayList<>();

        String[] array = genreIds.split(",");

        for (String s : array) {
            if (!s.isEmpty()) {
                list.add(s);
            }
        }
        return list;
    }

    @TypeConverter
    public String writingStringFromList(List<String> list) {
        String genreIds = "";
        for (String i : list) {
            genreIds += "," + i;
        }
        return genreIds;
    }
}