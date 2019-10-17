package com.innovidio.androidbootstrap.repository;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.innovidio.androidbootstrap.db.dao.TripDao;
import com.innovidio.androidbootstrap.db.dao.UserDao;
import com.innovidio.androidbootstrap.entity.Trip;
import com.innovidio.androidbootstrap.entity.User;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserRepository {

    private UserDao userDao;

    @Inject
    public UserRepository(UserDao userDao) {
        this.userDao = userDao;
    }

    public void addUser(User user) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                userDao.insert(user);
                return null;
            }
        }.execute();
    }

    public void deleteUser(User user) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                userDao.delete(user);
                return null;
            }
        }.execute();
    }

    public void updateUser(User user) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                userDao.update(user);
                return null;
            }
        }.execute();
    }

    public LiveData<List<User>> getAllUser() {
        return this.userDao.getAllUsers();
    }


    public LiveData<User> getUserById(int id) {
        return this.userDao.getUserById(id);
    }

}
