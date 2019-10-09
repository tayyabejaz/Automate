package com.innovidio.androidbootstrap.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.innovidio.androidbootstrap.entity.FuelUp;
import com.innovidio.androidbootstrap.repository.FuelUpRepository;


import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class FuelUpViewModel extends ViewModel {
    @Inject
    FuelUpRepository fuelUpRepository;
    @Inject
    public FuelUpViewModel(FuelUpRepository fuelUpRepository) {
        this.fuelUpRepository = fuelUpRepository ;
    }


    public void addFuelUp(FuelUp fuelUp){
        fuelUpRepository.addFuelUp(fuelUp);
    }

    public LiveData<List<FuelUp>> getAllFuelUps(){
        return this.fuelUpRepository.getAllFuelUps();
    }


    public MutableLiveData<Float> getFeulAverage() {
        //TODO Adnan - please provide feul average
        MutableLiveData<Float> floatLiveData = new MutableLiveData<>();
        floatLiveData.postValue(0.6f);
        return floatLiveData;
    }
    public LiveData<List<FuelUp>> getMonthlyFuelUp(Date month){
        Date startDate=null;
        Date endDate=null;
        return this.fuelUpRepository.getMonthlyFuelUp(startDate, endDate);
    }

    public LiveData<FuelUp> getRecentFuelUp(){
        return this.fuelUpRepository.getRecentFuelUp();
    }
}
