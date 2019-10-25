package com.innovidio.androidbootstrap.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.innovidio.androidbootstrap.entity.FuelAccount;
import com.innovidio.androidbootstrap.entity.OdoMeterAccount;
import com.innovidio.androidbootstrap.repository.FuelAccountRepository;
import com.innovidio.androidbootstrap.repository.OdoMeterAccountRepository;

import java.util.List;

import javax.inject.Inject;

public class OdoMeterViewModel extends ViewModel {
    @Inject
    OdoMeterAccountRepository odoMeterAccountRepository;
    @Inject
    public OdoMeterViewModel(OdoMeterAccountRepository odoMeterAccountRepository) {
        this.odoMeterAccountRepository = odoMeterAccountRepository ;
    }


    public void addOdoMeter(OdoMeterAccount fuelUp){
        odoMeterAccountRepository.addFuelUp(fuelUp);
    }

    public void deleteOdoMeter(OdoMeterAccount odoMeterAccount){
        odoMeterAccountRepository.deleteOdoMeter(odoMeterAccount);
    }

    public void updateOdoMeter(OdoMeterAccount fuelUp){
        odoMeterAccountRepository.updateOdoMeter(fuelUp);
    }

    public LiveData<List<FuelAccount>> getAllOdoMeter(){
        return this.odoMeterAccountRepository.getAllOdoMeterAccount();
    }

    public LiveData<OdoMeterAccount> getRecentOdoMeter(){
        return this.odoMeterAccountRepository.getRecentOdoMeter();
    }

}
