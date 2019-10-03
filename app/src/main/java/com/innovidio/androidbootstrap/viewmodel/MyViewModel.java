package com.innovidio.androidbootstrap.viewmodel;

import androidx.lifecycle.ViewModel;

import com.innovidio.androidbootstrap.repository.CarQueryRepository;

import javax.inject.Inject;

public class MyViewModel extends ViewModel {
    CarQueryRepository carQueryRepository;

    @Inject
    public MyViewModel(CarQueryRepository carQueryRepository) {
        this.carQueryRepository = carQueryRepository ;
    }

    public void getCarModels(String year,String make) {
        this.carQueryRepository.getCarModels(year, make);
    }
}
