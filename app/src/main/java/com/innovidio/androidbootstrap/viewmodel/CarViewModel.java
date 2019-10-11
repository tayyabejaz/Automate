package com.innovidio.androidbootstrap.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.innovidio.androidbootstrap.entity.Alarm;
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


    public void addCar(Car car){
        carRepository.addCar(car);
    }

    public void deleteCar(Car car){
        carRepository.deleteCar(car);
    }

    public void updateCar(Car car){
        carRepository.updateCar(car);
    }

    public LiveData<Car> getCarById(int id){
        return this.carRepository.getCarById(id);
    }

    public LiveData<List<Car>> getAllCars(){
        return this.carRepository.getAllCars();
    }

    public LiveData<List<Car>> getAllCarsOrderById(){
        return this.carRepository.getAllCarsOrderById();
    }
}