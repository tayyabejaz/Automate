package com.innovidio.androidbootstrap.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Query;

import com.innovidio.androidbootstrap.entity.Trip;

import java.util.List;

public abstract class TripDao extends BaseDao<TripDao> {

    @Query("SELECT * FROM Trip ORDER BY id desc")
    public abstract LiveData<List<Trip>> fetchAllTripsOrderById();


    @Query("SELECT * FROM Trip")
    public abstract LiveData<List<Trip>> getAllTrips();

    @Query("SELECT * FROM Trip WHERE id =:id")
    public abstract LiveData<Trip> getTripById(int id);

    @Query("SELECT * FROM Trip WHERE triptype =:tripType")
    public abstract LiveData<List<Trip>> getTripByTripType(String tripType);
}
