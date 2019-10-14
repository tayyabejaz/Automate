package com.innovidio.androidbootstrap.Utils;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UtilClass {

    public static void updateTime(Calendar calendarInstance, TextView timeField) {
        String myFormat = "hh:mm"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        timeField.setText(sdf.format(calendarInstance.getTime()));
    }

    public static void updateDate(Calendar calendar, TextView dateField) {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateField.setText(sdf.format(calendar.getTime()));
    }

    public static boolean checkEmptyField(EditText field) {
        return field.getText().length() > 0;
    }

    public static void showTimePicker(Context context, Calendar calendarInstance, TimePickerDialog.OnTimeSetListener time){
        new TimePickerDialog(context, time, calendarInstance.get(Calendar.HOUR_OF_DAY), calendarInstance.get(Calendar.MINUTE), false).show();
    }

    public static void showDatePicker(Context context,Calendar calenderInstance,DatePickerDialog.OnDateSetListener date){
        new DatePickerDialog(context, date, calenderInstance.get(Calendar.YEAR), calenderInstance.get(Calendar.MONTH), calenderInstance.get(Calendar.DAY_OF_MONTH)).show();
    }

}
