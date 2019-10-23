package com.innovidio.androidbootstrap.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateConverter {

    public static String getTimeFormateForUS(String pattern, Locale locale ){
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.US); //dd-MM-yyyy
        Calendar calender = Calendar.getInstance();
        TimeZone ccme = calender.getTimeZone();
        timeFormat.setTimeZone(ccme);
        String startTime = timeFormat.format(new Date());
        return startTime;
    }
}
