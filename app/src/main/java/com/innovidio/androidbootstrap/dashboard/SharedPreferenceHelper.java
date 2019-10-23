package com.innovidio.androidbootstrap.dashboard;

import android.content.Context;
import android.content.SharedPreferences;

import com.innovidio.androidbootstrap.BaseApplication;
import com.innovidio.androidbootstrap.Constants;

public class SharedPreferenceHelper {
    public static SharedPreferenceHelper sharedPreferenceHelper;
    SharedPreferences sharedPreferences;


    public SharedPreferenceHelper() {

        sharedPreferences = BaseApplication.context.getSharedPreferences(Constants.FILE_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferenceHelper getInstance() {
        if (sharedPreferenceHelper == null) {
            sharedPreferenceHelper = new SharedPreferenceHelper();
        }
        return sharedPreferenceHelper;
    }

    public void setIntegerValue(String key, int value) {
        sharedPreferences.edit().putInt(key, value).apply();
    }

    public void setFloatValue(String key, float value) {
        sharedPreferences.edit().putFloat(key, value).apply();
    }

    /*public float getFloatValue(String key, Float defaultValue) {
        return sharedPreferences.getFloat(key , defaultValue);
    }*/

    public int getIntegerValue(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    public void setBooleanValue(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public boolean getBooleanValue(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public void setStringValue(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public String getStringValue(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }
}
