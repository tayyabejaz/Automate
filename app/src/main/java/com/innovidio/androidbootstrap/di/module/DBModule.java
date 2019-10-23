package com.innovidio.androidbootstrap.di.module;

import android.app.Application;

import androidx.annotation.NonNull;

import com.innovidio.androidbootstrap.db.AppDatabase;
import com.innovidio.androidbootstrap.db.dao.AlarmDao;
import com.innovidio.androidbootstrap.db.dao.CarDao;
import com.innovidio.androidbootstrap.db.dao.FeedDao;
import com.innovidio.androidbootstrap.db.dao.FormDao;
import com.innovidio.androidbootstrap.db.dao.FuelDao;
import com.innovidio.androidbootstrap.db.dao.MaintenanceDao;
import com.innovidio.androidbootstrap.db.dao.PreferencesDao;
import com.innovidio.androidbootstrap.db.dao.TripDao;
import com.innovidio.androidbootstrap.db.dao.UserDao;
import com.innovidio.androidbootstrap.entity.Preferences;
import com.innovidio.androidbootstrap.entity.User;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DBModule {

    @Provides
    @Singleton
    AppDatabase provideDatabase(Application application) {
        return AppDatabase.getInstance(application);

//        return Room.databaseBuilder(application,
//                AppDatabase.class, "music.db")
//                .fallbackToDestructiveMigration()
//                .allowMainThreadQueries().build();
    }


    // todo add providers for dao here #Adnan

    @Provides
    @Singleton
    FeedDao provideFeedDao(@NonNull AppDatabase appDatabase) {
        return appDatabase.getFeedDao();
    }

    @Provides
    @Singleton
    UserDao provideUserDao(@NonNull AppDatabase appDatabase) {
        return appDatabase.getUserDao();
    }

    @Provides
    @Singleton
    PreferencesDao providePreferencesDao(@NonNull AppDatabase appDatabase) {
        return appDatabase.getPreferencesDao();
    }

    @Provides
    @Singleton
    AlarmDao provideAlarmDao(@NonNull AppDatabase appDatabase) {
        return appDatabase.getAlarmDao();
    }
//
    @Provides
    @Singleton
    CarDao provideCarDao(@NonNull AppDatabase appDatabase) {
        return appDatabase.getCarDao();
    }

    @Provides
    @Singleton
    FormDao provideFormDao(@NonNull AppDatabase appDatabase) {
        return appDatabase.getFormDao();
    }

    @Provides
    @Singleton
    FuelDao provideFuelDao(@NonNull AppDatabase appDatabase) {
        return appDatabase.getFuelDao();
    }

    @Provides
    @Singleton
    MaintenanceDao provideMaintenanceDao(@NonNull AppDatabase appDatabase) {
        return appDatabase.getMaintenanceDao();
    }

    @Provides
    @Singleton
    TripDao provideTripDao(@NonNull AppDatabase appDatabase) {
        return appDatabase.getTripDao();
    }

}