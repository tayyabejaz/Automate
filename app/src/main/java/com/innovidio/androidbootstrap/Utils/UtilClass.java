package com.innovidio.androidbootstrap.Utils;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.os.ConfigurationCompat;
import androidx.databinding.DataBindingUtil;

import com.innovidio.androidbootstrap.AppPreferences;
import com.innovidio.androidbootstrap.BuildConfig;
import com.innovidio.androidbootstrap.Constants;
import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.activity.FormActivity;
import com.innovidio.androidbootstrap.activity.SplashActivity;
import com.innovidio.androidbootstrap.entity.models.FullAddress;
import com.innovidio.androidbootstrap.activity.SpeedDashboardActivity;
import com.innovidio.androidbootstrap.databinding.DialogDriveSelectionBinding;
import com.innovidio.androidbootstrap.driveDetect.BackgroundDetectedActivitiesService;
import com.innovidio.androidbootstrap.entity.Preferences;
import com.innovidio.androidbootstrap.entity.Trip;
import com.innovidio.androidbootstrap.entity.models.FullAddress;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TimeZone;
import java.util.TreeMap;

import static com.innovidio.androidbootstrap.AppPreferences.Key.SPEED_LIMIT;

import static android.content.Context.ACTIVITY_SERVICE;
import static com.innovidio.androidbootstrap.Constants.ACTIVITY;

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
        AppPreferences appPreferences = new AppPreferences(context);

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

        appPreferences.put(SPEED_LIMIT, 60);
        dialogBinding.btnCommercial.setSelected(true);
        dialogBinding.btnCommercial.setTextColor(context.getResources().getColor(R.color.whiteColor));
        dialogBinding.numberpickerSpeedLimit.setOnValueChangedListener((numberPicker, i, i1) -> {
            dialogBinding.numberpickerSpeedLimit.setValue((i1 < i) ? i - 10 : i + 10);

            appPreferences.put(SPEED_LIMIT, numberPicker.getValue());
        });


        dialogBinding.btnCommercial.setOnClickListener(view -> {
            appPreferences.put(AppPreferences.Key.TRIP_TYPE, Trip.TripType.COMMERCIAL.name());
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
            appPreferences.put(AppPreferences.Key.TRIP_TYPE, Trip.TripType.PERSONAL.name());
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
            appPreferences.put(AppPreferences.Key.TRIP_TYPE, Trip.TripType.OFFICIAL.name());
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
            appPreferences.put(AppPreferences.Key.TRIP_TYPE, Trip.TripType.CUSTOM.name());
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
            appPreferences.put(AppPreferences.Key.START_ODOMETER, Integer.parseInt(dialogBinding.etInitialOdometer.getText().toString()));

            startDrive(context);
        });

        dialogBinding.switchDriveDetect.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                appPreferences.put(AppPreferences.Key.DRIVE_DETECT, true);
                startTracking(context);
            } else {
                appPreferences.put(AppPreferences.Key.DRIVE_DETECT, false);
                stopTracking(context);
            }
        });
    }

    public static FullAddress getAddressFromLatLon(Context context, Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        Geocoder geocoder;
        FullAddress fullAddress = new FullAddress();
        List<Address> addresses = null;
        geocoder = new Geocoder(context, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            if (addresses == null) {
                return null;
            }
            if (addresses.size() < 1) {
                return null;
            }
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

            fullAddress.setAddress(address);
            fullAddress.setCity(city);
            fullAddress.setState(state);
            fullAddress.setCountry(country);
            fullAddress.setPostalCode(postalCode);
            fullAddress.setKnownName(knownName);
            return fullAddress;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void startFormActivity(Context context, String formType) {
        Intent intent = new Intent(context, FormActivity.class);
        intent.putExtra(ACTIVITY, formType);
        context.startActivity(intent);
    }

    private static void startDrive(Context context) {
        Intent i = new Intent(context, SpeedDashboardActivity.class);
//        i.putExtra(Constants.SPEED_LIMIT, speedLimit);
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


    public static Preferences getDefaultPreferences() {
        Preferences preferences = new Preferences();
        preferences.setId(1);
        preferences.setSpeedLimit(90);
        preferences.setCountry("Pakistan");
        preferences.setAutoDetect(false);
        preferences.setCurrency("PKR");
        preferences.setDistanceUnit(Constants.KM);
        preferences.setSpeedUnit(Constants.KM_HR);
        preferences.setFuelUnit(Constants.LITTERS);
        preferences.setFuelUnitPrice(113.09d);
        return preferences;
    }


    public static ArrayList<String> getCountriesListFromLocale() {
        Locale[] locales = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<String>();
        for (Locale locale : locales) {
            String country = locale.getDisplayCountry();
            if (country.trim().length() > 0 && !countries.contains(country)) {
                countries.add(country);
            }
        }
        Collections.sort(countries);
        for (String country : countries) {
            System.out.println(country);
        }
        System.out.println("# countries found: " + countries.size());
        return countries;
    }

    public static ArrayList<String> getCurrenciesListFromLocale() {
        Locale[] locales = Locale.getAvailableLocales();
        ArrayList<String> currencies = new ArrayList<String>();
        for (Locale locale : locales) {
            currencies.add(Utils.getCurrencySymbol(Currency.getInstance(locale).getCurrencyCode()));
        }
        return currencies;
    }

    public static String getMyCountry() {
        Locale locale = ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0);
        return locale.getDisplayCountry();
    }


    static class Utils {
        public static SortedMap<Currency, Locale> currencyLocaleMap;

        static {
            currencyLocaleMap = new TreeMap<Currency, Locale>(new Comparator<Currency>() {
                public int compare(Currency c1, Currency c2) {
                    return c1.getCurrencyCode().compareTo(c2.getCurrencyCode());
                }
            });
            for (Locale locale : Locale.getAvailableLocales()) {
                try {
                    Currency currency = Currency.getInstance(locale);
                    currencyLocaleMap.put(currency, locale);
                } catch (Exception e) {
                }
            }
        }


        public static String getCurrencySymbol(String currencyCode) {
            Currency currency = Currency.getInstance(currencyCode);
            System.out.println(currencyCode + ":-" + currency.getSymbol(currencyLocaleMap.get(currency)));
            return currency.getSymbol(currencyLocaleMap.get(currency));
        }
    }


    public static ArrayList<String> getCurrenciesList() {
        // reference  https://android-er.blogspot.com/2014/05/display-available-currencies.html
        Set<Currency> availableCurrenciesSet;
        List<Currency> availableCurrenciesList;
        ArrayList<String> currenciesList = new ArrayList<>();
        availableCurrenciesSet = Currency.getAvailableCurrencies();
        availableCurrenciesList = new ArrayList<Currency>(availableCurrenciesSet);

        for (Currency currency : availableCurrenciesList) {
            currenciesList.add(currency.getSymbol());
        }

        Collections.sort(currenciesList);
        for (String country : currenciesList) {
            System.out.println(country);
        }

        return currenciesList;
    }


    public static void clearAppData(Context context) {
        try {
            // clearing app data
            if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
                ((ActivityManager)context.getSystemService(ACTIVITY_SERVICE)).clearApplicationUserData(); // note: it has a return value!
            } else {
                String packageName = context.getApplicationContext().getPackageName();
                Runtime runtime = Runtime.getRuntime();
                runtime.exec("pm clear "+packageName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void restartApplication(Context context) {
        Intent mStartActivity = new Intent(context, SplashActivity.class);
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(context, mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
        System.exit(0);
    }

    public static void gotoPrivacyPolicy(Context context, String url){
        try{
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            context.startActivity(i);
        }catch (ActivityNotFoundException ex){
            Toast.makeText(context, "Not Found any Browser.\n"+ex, Toast.LENGTH_SHORT).show();
        }
    }

    public static void rateUs(Context context){
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri
                .parse("market://details?id=" + context.getPackageName())));
    }

    public static void shareApp(Context context) {
        // Preventing multiple clicks, using threshold of 1 second
//        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
//            return;
//        }
//        mLastClickTime = SystemClock.elapsedRealtime();

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));
        String sAux = "\nShare our 'Automate' with your friends and family.\n\n";
        sAux = sAux + "https://play.google.com/store/apps/details?id=" + context.getPackageName() + " \n\n";
        i.putExtra(Intent.EXTRA_TEXT, sAux);
        context.startActivity(Intent.createChooser(i, "choose one"));
    }

    public static void feedback_click(Context context) {
        int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;

        // Preventing multiple clicks, using threshold of 1 second
//        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
//            return;
//        }
//        mLastClickTime = SystemClock.elapsedRealtime();

 //       Utility.firebaseCustomClickEvent(mContext, "email_support_click"); // send click event
        //  Toast.makeText(context, "Feedback", Toast.LENGTH_SHORT).show();
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "contentarcadeapps@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "App version: "+versionName + " Feedback!");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Please Give us the Feedback About this app." );
        //  emailIntent.putExtra(Intent.EXTRA_EMAIL, addresses); // String[] addresses
        context.startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }


//    public static void firebaseCustomClickEvent(Context context, String eventName){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params = new Bundle();
//        params.putString("cur_time", getCurrentTime());
//        // params.putString("msg", msg);
//        mFirebaseAnalytics.logEvent(eventName, params);
//    }
//
//    public static void firebaseCustomClickEvent(Context context, String eventName, String msg){
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle params = new Bundle();
//        params.putString("cur_time", getCurrentTime());
//        params.putString("msg", msg);
//        mFirebaseAnalytics.logEvent(eventName, params);
//    }


    public static Date getCurrentMonthLastDayDate(){
        Calendar calendarEnd = Calendar.getInstance();
        int monthMaxDays = calendarEnd.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendarEnd.set(Calendar.DAY_OF_MONTH, monthMaxDays);
        calendarEnd.set(Calendar.HOUR_OF_DAY, 0);
        calendarEnd.set(Calendar.MINUTE, 0);
        return calendarEnd.getTime();
    }

    public static Date getCurrentMonthFirstDayDate(){
        Calendar calendarStart = Calendar.getInstance();   // this takes current date
        calendarStart.set(Calendar.DAY_OF_MONTH, 1);
        calendarStart.set(Calendar.HOUR_OF_DAY, 0);
        calendarStart.set(Calendar.MINUTE, 0);
        return calendarStart.getTime();
    }

    public static Date getCurrentDayFrom0AM(){
        Calendar calendarStart = Calendar.getInstance();   // this takes current date
        calendarStart.add(Calendar.DAY_OF_MONTH, 0);
        calendarStart.set(Calendar.HOUR_OF_DAY, 0);
        calendarStart.set(Calendar.MINUTE, 0);
        calendarStart.set(Calendar.SECOND, 0);
        return calendarStart.getTime();
    }

    public static Date getCurrentPreviousDay(){
        Calendar calendarStart = Calendar.getInstance();   // this takes current date
        calendarStart.add(Calendar.DAY_OF_MONTH, -5);
        calendarStart.set(Calendar.HOUR_OF_DAY, 0);
        calendarStart.set(Calendar.MINUTE, 0);
        calendarStart.set(Calendar.SECOND, 0);
        return calendarStart.getTime();
    }


}