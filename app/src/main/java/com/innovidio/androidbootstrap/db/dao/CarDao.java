package com.innovidio.androidbootstrap.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Query;

import com.innovidio.androidbootstrap.entity.CarModel;

import java.util.List;

public abstract class CarDao extends BaseDao<CarDao> {

    @Query("SELECT * FROM CarModel ORDER BY id desc")
    public abstract LiveData<List<CarModel>> fetchAllCarsOrderById();


    //todo optional
    @Query("SELECT * FROM CarModel WHERE makeid =:makeid")
    public abstract LiveData<CarModel> getCarByMakerId(String makeid);


    @Query("SELECT * FROM CarModel")
    public abstract LiveData<List<CarModel>> getAllCars();

    @Query("SELECT * FROM CarModel WHERE id =:id")
    public abstract LiveData<CarModel> getCardById(int id);
}

