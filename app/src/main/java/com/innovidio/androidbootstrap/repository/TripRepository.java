package com.innovidio.androidbootstrap.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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

    @Inject
    public TripRepository(TripDao tripDao) {
        this.tripDao = tripDao;
    }

    public LiveData<List<Trip>> getAllTrips(){

        return this.tripDao.getAllTrips();
    }


    public LiveData<Trip> getTripById(int id){

        return this.tripDao.getTripById(id);
    }

    public LiveData<List<Trip>> getTripByType(String  type){

        return this.tripDao.getTripByTripType(type);
    }


    public MutableLiveData<List<Trip>> getAllTripsTimeline(){

        return this.tripDao.getAllTripsForTimeline();
    }
}
