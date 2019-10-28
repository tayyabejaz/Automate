package com.innovidio.androidbootstrap.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.innovidio.androidbootstrap.entity.FuelTransaction;
import com.innovidio.androidbootstrap.repository.FuelAccountRepository;

import java.util.List;

import javax.inject.Inject;

public class FuelAccountViewModel extends ViewModel {
    @Inject
    FuelAccountRepository fuelAccountRepository;
    @Inject
    public FuelAccountViewModel(FuelAccountRepository fuelAccountRepository) {
        this.fuelAccountRepository = fuelAccountRepository ;
    }


    public void addFuelAccount(FuelTransaction fuelUp){
        fuelAccountRepository.addFuelUp(fuelUp);
    }

    public void deleteFuelAccount(FuelTransaction fuelUp){
        fuelAccountRepository.deleteFuelUp(fuelUp);
    }

    public void updateFuelAccount(FuelTransaction fuelUp){
        fuelAccountRepository.updateFuelUp(fuelUp);
    }

    public LiveData<List<FuelTransaction>> getAllFuelAccount(){
        return this.fuelAccountRepository.getAllFuelUps();
    }

    public LiveData<FuelTransaction> getRecentAccount(){
        return this.fuelAccountRepository.getRecentFuelUp();
    }

    public LiveData<Float> getFuelTankPercentage(int carId){
      return fuelAccountRepository.getFuelTankPercentage(carId);
    }
}
