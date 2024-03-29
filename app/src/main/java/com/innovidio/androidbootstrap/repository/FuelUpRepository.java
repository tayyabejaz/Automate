package com.innovidio.androidbootstrap.repository;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.innovidio.androidbootstrap.db.dao.FuelDao;
import com.innovidio.androidbootstrap.entity.FuelUp;
import com.innovidio.androidbootstrap.interfaces.TimeLineItem;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FuelUpRepository {

    FuelDao fuelDao;

    @Inject
    public FuelUpRepository(FuelDao fuelDao){
        this.fuelDao = fuelDao;
    }

    public void addFuelUp(FuelUp fuelUp){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                fuelDao.insert(fuelUp);
                return null;
            }
        }.execute();
    }

    public void deleteFuelUp(FuelUp fuelUp){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                fuelDao.delete(fuelUp);
                return null;
            }
        }.execute();
    }


    public void updateFuelUp(FuelUp fuelUp){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                fuelDao.update(fuelUp);
                return null;
            }
        }.execute();
    }

    public LiveData<List<FuelUp>> getAllFuelUps(){
        return this.fuelDao.getAllFuelUps();
    }

    public List<FuelUp> getAllFuelUpsTimeLine(int cardId){
        return this.fuelDao.getAllFuelUpsTimeline(cardId);
    }

    public LiveData<List<FuelUp>> getMonthlyFuelUp(Date month){
        // todo please provide fuel up date range using month
        Date startDate=null;
        Date endDate=null;
        return this.fuelDao.getMonthlyFuelConsume(startDate, endDate);
    }

    public LiveData<FuelUp> getRecentFuelUp(){
        return this.fuelDao.getRecentFuelUp();
    }


    public LiveData<List<FuelUp>> getAllFuelUpsForTimeLine(int carId){
        return this.fuelDao.getAllFuelUpsForTimeline(carId);
    }

    public LiveData<List<FuelUp>> getAllFuelkTankPercentage(){
        return this.fuelDao.getFuelTankPercentage();
    }

    public LiveData<Integer> getFuelUpCountBetweenDateRange(int carId, Date starDate, Date endDate){
        return this.fuelDao.getFuelUpCountBetweenDateRange(carId, starDate, endDate);
    }

    public LiveData<Long> getLittersSumBetweenDateRange(int carId, Date starDate, Date endDate){
        return this.fuelDao.getLittersSumBetweenDateRange(carId, starDate, endDate);
    }

    public LiveData<Float> getFuelAverageBetweenDateRange(int carId, Date starDate, Date endDate){
        return this.fuelDao.getFuelAverageBetweenDateRange(carId, starDate, endDate);
    }

    public MutableLiveData<Float>  getFuelAverage(int carId, Date starDate, Date endDate){
        //TODO Adnan - please provide fuel average
        MutableLiveData<Float> floatLiveData = new MutableLiveData<>();
        floatLiveData.postValue(0.6f);
        return floatLiveData;
    }

    public LiveData<Float>  getFuelTankPercentage(int carId){
        // todo add code for getting percentage of remaining fuel
        //  this.fuelUpRepository.getAllFuelkTankPercentage();
        return getRemainingFuel();
    }

    private LiveData<Float> getRemainingFuel(){
        MutableLiveData<Float> fuelUpLiveData = new MutableLiveData<>();
        fuelUpLiveData.setValue(19f);
        return fuelUpLiveData;
    }
}
