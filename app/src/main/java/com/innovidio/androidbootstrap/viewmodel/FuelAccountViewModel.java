package com.innovidio.androidbootstrap.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.innovidio.androidbootstrap.entity.FuelAccount;
import com.innovidio.androidbootstrap.entity.FuelUp;
import com.innovidio.androidbootstrap.repository.FuelAccountRepository;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class FuelAccountViewModel extends ViewModel {
    @Inject
    FuelAccountRepository fuelAccountRepository;
    @Inject
    public FuelAccountViewModel(FuelAccountRepository fuelAccountRepository) {
        this.fuelAccountRepository = fuelAccountRepository ;
    }


    public void addFuelAccount(FuelAccount fuelUp){
        fuelAccountRepository.addFuelUp(fuelUp);
    }

    public void deleteFuelAccount(FuelAccount fuelUp){
        fuelAccountRepository.deleteFuelUp(fuelUp);
    }

    public void updateFuelAccount(FuelAccount fuelUp){
        fuelAccountRepository.updateFuelUp(fuelUp);
    }

    public LiveData<List<FuelAccount>> getAllFuelAccount(){
        return this.fuelAccountRepository.getAllFuelUps();
    }

    public LiveData<FuelAccount> getRecentAccount(){
        return this.fuelAccountRepository.getRecentFuelUp();
    }

    public LiveData<Float> getFuelTankPercentage(int carId){
      return fuelAccountRepository.getFuelTankPercentage(carId);
    }
}
