package com.innovidio.androidbootstrap.repository;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.innovidio.androidbootstrap.db.dao.FuelAccountDao;
import com.innovidio.androidbootstrap.db.dao.OdoMeterAccountDao;
import com.innovidio.androidbootstrap.entity.FuelAccount;
import com.innovidio.androidbootstrap.entity.OdoMeterAccount;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class OdoMeterAccountRepository {

    OdoMeterAccountDao odoMeterAccountDao;

    @Inject
    public OdoMeterAccountRepository(OdoMeterAccountDao odoMeterAccountDao){
        this.odoMeterAccountDao = odoMeterAccountDao;
    }

    public void addFuelUp(OdoMeterAccount odoMeterAccount){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                odoMeterAccountDao.insert(odoMeterAccount);
                return null;
            }
        }.execute();
    }

    public void deleteOdoMeter(OdoMeterAccount odoMeterAccount){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                odoMeterAccountDao.delete(odoMeterAccount);
                return null;
            }
        }.execute();
    }


    public void updateOdoMeter(OdoMeterAccount odoMeterAccount){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                odoMeterAccountDao.update(odoMeterAccount);
                return null;
            }
        }.execute();
    }

    public LiveData<List<FuelAccount>> getAllOdoMeterAccount(){
        return this.odoMeterAccountDao.getAllOdoMeterAccount();
    }

    public LiveData<OdoMeterAccount> getRecentOdoMeter(){
        return this.odoMeterAccountDao.getRecentOdoMeterAccountValue();
    }


}
