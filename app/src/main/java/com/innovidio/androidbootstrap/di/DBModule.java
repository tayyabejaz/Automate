package com.innovidio.androidbootstrap.di;

import android.app.Application;

import androidx.annotation.NonNull;

import com.innovidio.androidbootstrap.db.AppDatabase;
import com.innovidio.androidbootstrap.db.dao.FeedDao;


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

    @Provides
    @Singleton
    FeedDao provideSongDao(@NonNull AppDatabase appDatabase) {
        return appDatabase.getFeedDao();
    }


}