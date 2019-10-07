package com.innovidio.androidbootstrap.repository;

import androidx.lifecycle.MutableLiveData;

import com.innovidio.androidbootstrap.db.dao.TripDao;
import com.innovidio.androidbootstrap.entity.Trip;
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

    public MutableLiveData<List<Trip>> getTrips(){

        return this.tripDao.getAllTrips();

    }
}