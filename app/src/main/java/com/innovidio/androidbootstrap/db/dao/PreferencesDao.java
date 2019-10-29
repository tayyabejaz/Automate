package com.innovidio.androidbootstrap.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.innovidio.androidbootstrap.entity.Preferences;
import com.innovidio.androidbootstrap.entity.User;

import java.util.List;

@Dao
public abstract class PreferencesDao extends BaseDao<Preferences> {

    @Query("SELECT * FROM Preferences ORDER BY id desc")
    public abstract LiveData<List<Preferences>> getAllPreferencesOrderById();


    @Query("SELECT * FROM Preferences WHERE id =:id")
    public abstract LiveData<Preferences> getPreferencesById(int id);


    @Query("SELECT * FROM Preferences")
    public abstract List<Preferences> getAllPreferences();

}

