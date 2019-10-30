package com.innovidio.androidbootstrap.repository;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.innovidio.androidbootstrap.db.dao.FuelTransactionDao;
import com.innovidio.androidbootstrap.entity.FuelTransaction;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FuelAccountRepository {

    FuelTransactionDao fuelAccountDao;

    @Inject
    public FuelAccountRepository(FuelTransactionDao fuelAccountDao){
        this.fuelAccountDao = fuelAccountDao;
    }

    public void addFuelUp(FuelTransaction fuelTransaction){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                fuelAccountDao.insert(fuelTransaction);
                return null;
            }
        }.execute();
    }

    public void deleteFuelUp(FuelTransaction fuelTransaction){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                fuelAccountDao.delete(fuelTransaction);
                return null;
            }
        }.execute();
    }


    public void updateFuelUp(FuelTransaction fuelTransaction){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                fuelAccountDao.update(fuelTransaction);
                return null;
            }
        }.execute();
    }

    public LiveData<List<FuelTransaction>> getAllFuelUps(){
        return this.fuelAccountDao.getAllFuelUps();
    }

    public LiveData<FuelTransaction> getRecentFuelUp(){
        return this.fuelAccountDao.getRecentFuelUp();
    }


    public LiveData<Float>  getFuelTankPercentage(int carId){
        return getRemainingFuel();
    }

    private LiveData<Float> getRemainingFuel(){
        MutableLiveData<Float> fuelUpLiveData = new MutableLiveData<>();
        fuelUpLiveData.setValue(19f);
        return fuelUpLiveData;
    }
}
