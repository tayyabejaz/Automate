package com.innovidio.androidbootstrap.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.innovidio.androidbootstrap.entity.Car;
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

    public void deleteFuelUp(FuelUp fuelUp){
        fuelUpRepository.deleteFuelUp(fuelUp);
    }

    public void updateFuelUp(FuelUp fuelUp){
        fuelUpRepository.updateFuelUp(fuelUp);
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

    public LiveData<Float> getFuelTankPercentage(){
        // todo add code for getting percentage of remaining fuel
      //  this.fuelUpRepository.getAllFuelkTankPercentage();
        return getRemainingFuel();
    }

    private LiveData<Float> getRemainingFuel(){
        MutableLiveData<Float> fuelUpLiveData = new MutableLiveData<>();
        fuelUpLiveData.setValue(19f);
        return fuelUpLiveData;
    }

    public LiveData<Integer> getFuelUpCountBetweenDateRange(int carId, Date starDate, Date endDate){
        return this.fuelUpRepository.getFuelUpCountBetweenDateRange(carId, starDate, endDate);
    }

    public LiveData<Long> getLittersSumBetweenDateRange(int carId, Date starDate, Date endDate){
        return this.fuelUpRepository.getLittersSumBetweenDateRange(carId, starDate, endDate);
    }

    public LiveData<Float> getFuelAverageBetweenDateRange(int carId, Date starDate, Date endDate){
        return this.fuelUpRepository.getFuelAverageBetweenDateRange(carId, starDate, endDate);
    }
}
