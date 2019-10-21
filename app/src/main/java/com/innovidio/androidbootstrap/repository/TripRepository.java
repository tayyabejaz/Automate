package com.innovidio.androidbootstrap.repository;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.innovidio.androidbootstrap.db.dao.TripDao;
import com.innovidio.androidbootstrap.entity.Trip;

import java.util.Date;
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

    public void deleteTrip(Trip trip) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                tripDao.delete(trip);
                return null;
            }
        }.execute();
    }

    public void updateTrip(Trip trip) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                tripDao.update(trip);
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


    public List<Trip> getAllTripsTimeline(int cardId) {

        return this.tripDao.getAllTripsTimeline(cardId);
    }

    public LiveData<List<Trip>> getAllTripsLiveDataTimeline(int cardId) {

        return this.tripDao.getAllTripsLiveDataForTimeline(cardId);
    }

    public LiveData<Trip> getLastTrip(int cardId) {
        return this.tripDao.getLastTrip(cardId);
    }

    public LiveData<Integer> getTripsCountBetweenDateRange(int carId, Date starDate, Date endDate) {
        return this.tripDao.getTripsCountBetweenDateRange(carId, starDate, endDate);
    }

    public LiveData<Long> getDistanceCoveredBetweenDateRange(int carId, Date starDate, Date endDate) {
        return this.tripDao.getDistanceCoveredBetweenDateRange(carId, starDate, endDate);
    }
}
