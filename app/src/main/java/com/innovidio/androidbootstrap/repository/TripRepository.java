package com.innovidio.androidbootstrap.repository;

import android.os.AsyncTask;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.innovidio.androidbootstrap.BaseApplication;
import com.innovidio.androidbootstrap.db.dao.CarDao;
import com.innovidio.androidbootstrap.db.dao.TripDao;
import com.innovidio.androidbootstrap.entity.Car;
import com.innovidio.androidbootstrap.entity.Trip;
import com.innovidio.androidbootstrap.interfaces.TimeLineItem;
import com.innovidio.androidbootstrap.network.dto.CarModelName;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TripRepository {

    private TripDao tripDao;

    private List<Trip> allTrips;


    @Inject
    public TripRepository(TripDao tripDao) {
        this.tripDao = tripDao;

        tripDao.getAllTripsLiveDataForTimeline().observeForever(new Observer<List<Trip>>() {
            @Override
            public void onChanged(List<Trip> trips) {
                allTrips = trips;
            }
        });
    }

    public List<Trip> getTrips() {
        return allTrips;
    }

    public void addTrip(Trip trip) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                tripDao.insert(trip);
                return null;
            }
        }.execute();
    }

    public LiveData<List<Trip>> getAllTrips() {
        return this.tripDao.getAllTrips();
    }


    public LiveData<Trip> getTripById(int id) {
        return this.tripDao.getTripById(id);
    }

    public LiveData<List<Trip>> getTripByType(String type) {

        return this.tripDao.getTripByTripType(type);
    }


    public LiveData<List<Trip>> getAllTripsTimeline() {

        return this.tripDao.getAllTripsForTimeline();
    }

    public LiveData<List<Trip>> getAllTripsLiveDataTimeline() {

        return this.tripDao.getAllTripsLiveDataForTimeline();
    }
}
