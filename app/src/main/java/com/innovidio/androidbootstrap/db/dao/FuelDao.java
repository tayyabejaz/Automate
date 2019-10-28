package com.innovidio.androidbootstrap.db.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.innovidio.androidbootstrap.entity.FuelUp;
import com.innovidio.androidbootstrap.interfaces.TimeLineItem;

import java.util.Date;
import java.util.List;

@Dao
public abstract class FuelDao extends BaseDao<FuelUp> {

    @Query("SELECT * FROM FuelUp ORDER BY id desc")
    public abstract LiveData<List<FuelUp>> getFuelUpsOrderById();

    @Query("SELECT * FROM FuelUp")
    public abstract LiveData<List<FuelUp>> getAllFuelUps();

    @Query("SELECT * FROM FuelUp WHERE id =:id")
    public abstract LiveData<FuelUp> getFuelUpById(int id);


    @Query("SELECT * FROM FuelUp WHERE carId =:carId")
    public abstract LiveData<List<FuelUp>> getAllFuelUpsForTimeline(int carId);

    @Query("SELECT * FROM FuelUp WHERE carId =:carId")
    public abstract List<FuelUp> getAllFuelUpsTimeline(int carId);

    @Query("SELECT * FROM FuelUp WHERE saveDate BETWEEN :startDate AND :endDate")
    public abstract  LiveData<List<FuelUp>> getMonthlyFuelConsume(Date startDate, Date endDate);


    @Query("SELECT * FROM FuelUp  ORDER BY id DESC LIMIT 1")
    public abstract  LiveData<FuelUp> getRecentFuelUp();

    @Query("SELECT * FROM FuelUp")
    public abstract  LiveData<List<FuelUp>> getFuelTankPercentage();

    @Query("SELECT COUNT(id) FROM FuelUp WHERE carId=:carId AND saveDate BETWEEN :startDate AND :endDate")
    public abstract  LiveData<Integer> getFuelUpCountBetweenDateRange(int carId, Date startDate, Date endDate );

    @Query("SELECT SUM(liters) FROM FuelUp where carId =:carId AND saveDate BETWEEN :startDay AND :endDay")
    public abstract  LiveData<Long> getLittersSumBetweenDateRange(int carId, Date startDay, Date endDay);


    // todo get fuel percenatge here
    @Query("SELECT SUM(liters) FROM FuelUp where carId =:carId AND saveDate BETWEEN :startDay AND :endDay")
    public abstract  LiveData<Float> getFuelAverageBetweenDateRange(int carId, Date startDay, Date endDay);

}

