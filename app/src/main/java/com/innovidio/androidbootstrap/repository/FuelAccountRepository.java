package com.innovidio.androidbootstrap.repository;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.innovidio.androidbootstrap.db.dao.FuelAccountDao;
import com.innovidio.androidbootstrap.db.dao.FuelDao;
import com.innovidio.androidbootstrap.entity.FuelAccount;
import com.innovidio.androidbootstrap.entity.FuelUp;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FuelAccountRepository {

    FuelAccountDao fuelAccountDao;

    @Inject
    public FuelAccountRepository(FuelAccountDao fuelAccountDao){
        this.fuelAccountDao = fuelAccountDao;
    }

    public void addFuelUp(FuelAccount fuelAccount){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                fuelAccountDao.insert(fuelAccount);
                return null;
            }
        }.execute();
    }

    public void deleteFuelUp(FuelAccount fuelAccount){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                fuelAccountDao.delete(fuelAccount);
                return null;
            }
        }.execute();
    }


    public void updateFuelUp(FuelAccount fuelAccount){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                fuelAccountDao.update(fuelAccount);
                return null;
            }
        }.execute();
    }

    public LiveData<List<FuelAccount>> getAllFuelUps(){
        return this.fuelAccountDao.getAllFuelUps();
    }

    public LiveData<FuelAccount> getRecentFuelUp(){
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
