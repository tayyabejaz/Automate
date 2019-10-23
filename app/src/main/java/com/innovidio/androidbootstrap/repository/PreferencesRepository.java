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

    @Inject
    public PreferencesRepository(PreferencesDao preferencesDao) {
        this.preferencesDao = preferencesDao;
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

    public LiveData<List<Preferences>> getAllPreferences() {
        return this.preferencesDao.getAllPreferences();
    }


    public LiveData<Preferences> getPreferencesById(int id) {
        return this.preferencesDao.getPreferencesById(id);
    }

}
