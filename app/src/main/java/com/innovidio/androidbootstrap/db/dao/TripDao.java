package com.innovidio.androidbootstrap.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.innovidio.androidbootstrap.entity.Trip;

import java.util.Date;
import java.util.List;

@Dao
public abstract class TripDao extends BaseDao<Trip> {

    @Query("SELECT * FROM Trip ORDER BY id desc")
    public abstract LiveData<List<Trip>> fetchAllTripsOrderById();

    @Query("SELECT * FROM Trip")
    public abstract LiveData<List<Trip>> getAllTrips();

    @Query("SELECT * FROM Trip WHERE id =:id")
    public abstract LiveData<Trip> getTripById(int id);

    @Query("SELECT * FROM Trip WHERE tripType =:tripType")
    public abstract LiveData<List<Trip>> getTripByTripType(String tripType);

    @Query("SELECT * FROM Trip WHERE carId =:carId")
    public abstract  List<Trip> getAllTripsTimeline(int carId);

    @Query("SELECT * FROM Trip WHERE carId =:carId")
    public abstract  LiveData<List<Trip>> getAllTripsLiveDataForTimeline(int carId);

    @Query("SELECT * FROM Trip WHERE carId=:carId AND id = (SELECT MAX(ID) FROM Trip)")
    public abstract  LiveData<Trip> getLastTrip(int carId);

    @Query("SELECT COUNT(id) FROM Trip WHERE carId=:carId AND saveDate BETWEEN :startDate AND :endDate")
    public abstract  LiveData<Integer> getTripsCountBetweenDateRange(int carId, Date startDate, Date endDate );

    @Query("SELECT SUM(distanceCovered) FROM Trip where carId =:carId AND saveDate BETWEEN :startDay AND :endDay")
    public abstract  LiveData<Long> getDistanceCoveredBetweenDateRange(int carId, Date startDay, Date endDay);

    // "SELECT COUNT(id) FROM table WHERE is_checked = 1
}

