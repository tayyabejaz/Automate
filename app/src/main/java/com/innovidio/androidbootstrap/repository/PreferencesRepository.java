package com.innovidio.androidbootstrap.repository;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

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
        }

    }

    public String getCountry() {
        return preferences.getCountry();
    }

    public void setCountry(String country) {
        preferences.setCountry(country);
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
