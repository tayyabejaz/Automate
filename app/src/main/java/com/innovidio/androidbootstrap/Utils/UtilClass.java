package com.innovidio.androidbootstrap.Utils;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.innovidio.androidbootstrap.AppPreferences;
import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.dashboard.SpeedDashboardActivity;
import com.innovidio.androidbootstrap.databinding.DialogDriveSelectionBinding;
import com.innovidio.androidbootstrap.driveDetect.BackgroundDetectedActivitiesService;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

public class UtilClass {

    public static String updateTime(Calendar calendarInstance, TextView timeField) {
        String myFormat = "HH:mm"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        timeField.setText(sdf.format(calendarInstance.getTime()));
        return sdf.format(calendarInstance.getTime());
    }

    public static String updateDate(Calendar calendar, TextView dateField) {
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        dateField.setText(sdf.format(calendar.getTime()));
        return sdf.format(calendar.getTime());
    }

    public static Date convertToDate(String dateInString, String timeInString) {

        String finalDate = dateInString + " " + timeInString;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm", Locale.getDefault());

        Date date = null;

        try {
            date = formatter.parse(finalDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;

    }


    public static void showTimePicker(Context context, Calendar calendarInstance, TimePickerDialog.OnTimeSetListener time) {
        new TimePickerDialog(context, time, calendarInstance.get(Calendar.HOUR_OF_DAY), calendarInstance.get(Calendar.MINUTE), true).show();
    }

    public static void showDatePicker(Context context, Calendar calenderInstance, DatePickerDialog.OnDateSetListener date) {
        new DatePickerDialog(context, date, calenderInstance.get(Calendar.YEAR), calenderInstance.get(Calendar.MONTH), calenderInstance.get(Calendar.DAY_OF_MONTH)).show();
    }


    public static int getRandomNo(int min, int max) {
//        final int min = 20;
//        final int max = 80;
        final int random = new Random().nextInt((max - min) + 1) + min;

        return random;
    }

    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

    public static void showStartTripDialog(Context context) {
        DialogDriveSelectionBinding dialogBinding;
        dialogBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_drive_selection, null, false);
        View dialogView = dialogBinding.getRoot();


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        final AlertDialog exitDialog = dialogBuilder.create();
        exitDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        exitDialog.setView(dialogView);
        exitDialog.show();

        dialogBinding.numberpickerSpeedLimit.setMinValue(60);
        dialogBinding.numberpickerSpeedLimit.setMaxValue(240);
        dialogBinding.numberpickerSpeedLimit.computeScroll();
        dialogBinding.numberpickerSpeedLimit.setFormatter(i -> String.format("%03d kmph", i));

        dialogBinding.numberpickerSpeedLimit.setOnValueChangedListener((numberPicker, i, i1) -> {
            AppPreferences.SPEED_LIMIT = "" + i1;
        });

        dialogBinding.numberpickerSpeedLimit.setOnValueChangedListener((numberPicker, i, i1) -> {
            dialogBinding.numberpickerSpeedLimit.setValue((i1 < i) ? i - 10 : i + 10);
        });

        dialogBinding.btnCommercial.setOnClickListener(view -> {
            AppPreferences.TRIP_TYPE = "Commercial";
            dialogBinding.btnCommercial.setSelected(true);
            dialogBinding.btnCommercial.setTextColor(context.getResources().getColor(R.color.whiteColor));

            dialogBinding.btnPersonal.setSelected(false);
            dialogBinding.btnPersonal.setTextColor(context.getResources().getColor(R.color.blackColor));

            dialogBinding.btnOfficial.setSelected(false);
            dialogBinding.btnOfficial.setTextColor(context.getResources().getColor(R.color.blackColor));

            dialogBinding.btnCustom.setSelected(false);
            dialogBinding.btnCustom.setTextColor(context.getResources().getColor(R.color.blackColor));
        });

        dialogBinding.btnPersonal.setOnClickListener(view -> {
            AppPreferences.TRIP_TYPE = "Personal";
            dialogBinding.btnCommercial.setSelected(false);
            dialogBinding.btnCommercial.setTextColor(context.getResources().getColor(R.color.blackColor));

            dialogBinding.btnPersonal.setSelected(true);
            dialogBinding.btnPersonal.setTextColor(context.getResources().getColor(R.color.whiteColor));

            dialogBinding.btnOfficial.setSelected(false);
            dialogBinding.btnOfficial.setTextColor(context.getResources().getColor(R.color.blackColor));

            dialogBinding.btnCustom.setSelected(false);
            dialogBinding.btnCustom.setTextColor(context.getResources().getColor(R.color.blackColor));
        });

        dialogBinding.btnOfficial.setOnClickListener(view -> {
            AppPreferences.TRIP_TYPE = "Official";
            dialogBinding.btnCommercial.setSelected(false);
            dialogBinding.btnCommercial.setTextColor(context.getResources().getColor(R.color.blackColor));

            dialogBinding.btnPersonal.setSelected(false);
            dialogBinding.btnPersonal.setTextColor(context.getResources().getColor(R.color.blackColor));

            dialogBinding.btnOfficial.setSelected(true);
            dialogBinding.btnOfficial.setTextColor(context.getResources().getColor(R.color.whiteColor));

            dialogBinding.btnCustom.setSelected(false);
            dialogBinding.btnCustom.setTextColor(context.getResources().getColor(R.color.blackColor));
        });

        dialogBinding.btnCustom.setOnClickListener(view -> {
            AppPreferences.TRIP_TYPE = "Custom";
            dialogBinding.btnCommercial.setSelected(false);
            dialogBinding.btnCommercial.setTextColor(context.getResources().getColor(R.color.blackColor));

            dialogBinding.btnPersonal.setSelected(false);
            dialogBinding.btnPersonal.setTextColor(context.getResources().getColor(R.color.blackColor));

            dialogBinding.btnOfficial.setSelected(false);
            dialogBinding.btnOfficial.setTextColor(context.getResources().getColor(R.color.blackColor));

            dialogBinding.btnCustom.setSelected(true);
            dialogBinding.btnCustom.setTextColor(context.getResources().getColor(R.color.whiteColor));
        });

        dialogBinding.btnStartDrive.setOnClickListener(view -> {
            startDrive(context);
        });

        dialogBinding.switchDriveDetect.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                AppPreferences.AUTO_DRIVE_DETECT = true;
                startTracking(context);
            } else {
                AppPreferences.AUTO_DRIVE_DETECT = false;
                stopTracking(context);
            }
        });
    }

    private static void startDrive(Context context) {
        Intent i = new Intent(context, SpeedDashboardActivity.class);
        context.startActivity(i);
    }


    public static void startTracking(Context context) {
        Intent intent = new Intent(context, BackgroundDetectedActivitiesService.class);
        context.startService(intent);
    }

    public static void stopTracking(Context context) {
        Intent intent = new Intent(context, BackgroundDetectedActivitiesService.class);
        context.stopService(intent);
    }


    public static Date getTime(String time) {
        Date startTimeDate = null, endTimeDate = null;
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss a", Locale.US);
        try {
            startTimeDate = formatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return startTimeDate;
    }

    public static String getTimeFormateForUS(String pattern, Locale locale) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.US); //dd-MM-yyyy
        Calendar calender = Calendar.getInstance();
        TimeZone ccme = calender.getTimeZone();
        timeFormat.setTimeZone(ccme);
        String startTime = timeFormat.format(new Date());
        return startTime;
    }


    public static Double getRoundFigureValue(Double number) {
        NumberFormat formatter = new DecimalFormat("#0.00");
        Double roundFigureNo = Double.parseDouble(formatter.format(number));
        return roundFigureNo;
    }


}