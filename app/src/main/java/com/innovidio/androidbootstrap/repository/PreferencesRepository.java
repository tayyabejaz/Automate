package com.innovidio.androidbootstrap.repository;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.innovidio.androidbootstrap.Utils.UtilClass;
import com.innovidio.androidbootstrap.db.AppDatabase;
import com.innovidio.androidbootstrap.db.dao.PreferencesDao;
import com.innovidio.androidbootstrap.db.dao.UserDao;
import com.innovidio.androidbootstrap.entity.Preferences;
import com.innovidio.androidbootstrap.entity.User;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PreferencesRepository {

    private PreferencesDao preferencesDao;
    private Preferences preferences;

    @Inject
    public PreferencesRepository(PreferencesDao preferencesDao) {
        this.preferencesDao = preferencesDao;
        List<Preferences> allPreferences = this.preferencesDao.getAllPreferences();
        if (allPreferences != null && allPreferences.size() > 0) {
            preferences = allPreferences.get(0);
        }else{
            // todo for first time usage default values added
            preferences = UtilClass.getDefaultPreferences();
            preferencesDao.insert(preferences);
        }

    }

    public Preferences getPreferences() {
        return this.preferences;
    }

    public Double getFuelUnitPrice() {
        return preferences.getFuelUnitPrice();
    }

    public void setFuelUnitPrice(Double fuelUnitPrice) {
        preferences.setFuelUnitPrice(fuelUnitPrice);
        preferencesDao.update(preferences);
    }

    public int getSpeedLimit() {
        return preferences.getSpeedLimit();
    }

    public void setSpeedLimit(int distanceUnit) {
        preferences.setSpeedLimit(distanceUnit);
        preferencesDao.update(preferences);
    }


    public String getSpeedUnit() {
        return preferences.getSpeedUnit();
    }

    public void setSpeedUnit(String distanceUnit) {
        preferences.setSpeedUnit(distanceUnit);
        preferencesDao.update(preferences);
    }


    public String getDistanceUnit() {
        return preferences.getDistanceUnit();
    }

    public void setDistanceUnit(String distanceUnit) {
        preferences.setDistanceUnit(distanceUnit);
        preferencesDao.update(preferences);
    }

    public String getFuelUnit() {
        return preferences.getFuelUnit();
    }

    public void setFuelUnit(String fuelUnit) {
        preferences.setFuelUnit(fuelUnit);
        preferencesDao.update(preferences);
    }

    public String getCountry() {
        return preferences.getCountry();
    }

    public void setCountry(String country) {
        preferences.setCountry(country);
        preferencesDao.update(preferences);
    }

    public String getCurrency() {
        return preferences.getCurrency();
    }

    public void setCurrency(String currency) {
        preferences.setCurrency(currency);
        preferencesDao.update(preferences);
    }

    public boolean isAutoDriveDetect() {
        return preferences.isAutoDetect();
    }

    public void setAutoDriveDetect(boolean isAuto) {
        preferences.setAutoDetect(isAuto);
        preferencesDao.update(preferences);
    }


    public void addPreferences(Preferences preferences) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                preferencesDao.insert(preferences);
                return null;
            }
        }.execute();
    }

    public void deletePreferences(Preferences preferences) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                preferencesDao.delete(preferences);
                return null;
            }
        }.execute();
    }

    public void updatePreferences(Preferences preferences) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                preferencesDao.update(preferences);
                return null;
            }
        }.execute();
    }

//    public LiveData<List<Preferences>> getAllPreferences() {
//        return this.preferencesDao.getAllPreferences();
//    }


    public LiveData<Preferences> getPreferencesById(int id) {
        return this.preferencesDao.getPreferencesById(id);
    }

}
