package com.innovidio.androidbootstrap.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.innovidio.androidbootstrap.entity.Car;
import com.innovidio.androidbootstrap.repository.CarsRepository;

import java.util.List;

import javax.inject.Inject;

public class CarViewModel extends ViewModel {
    @Inject
    CarsRepository carRepository;
    @Inject
    public CarViewModel(CarsRepository carRepository) {
        this.carRepository = carRepository ;
    }


    public LiveData<List<Car>> getAllCars(){
        return this.carRepository.getAllCardWithLiveData();
    }
}