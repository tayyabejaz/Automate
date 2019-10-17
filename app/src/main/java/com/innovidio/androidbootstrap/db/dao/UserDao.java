package com.innovidio.androidbootstrap.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.innovidio.androidbootstrap.entity.Car;
import com.innovidio.androidbootstrap.entity.User;

import java.util.List;

@Dao
public abstract class UserDao extends BaseDao<User> {

    @Query("SELECT * FROM User ORDER BY id desc")
    public abstract LiveData<List<User>> getAllCarsOrderById();


    @Query("SELECT * FROM User WHERE id =:id")
    public abstract LiveData<User> getUserById(int id);


    @Query("SELECT * FROM User")
    public abstract LiveData<List<User>> getAllUsers();

}

