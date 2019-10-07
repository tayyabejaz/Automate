package com.innovidio.androidbootstrap.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.innovidio.androidbootstrap.network.dto.CarMakesByYear;
import com.innovidio.androidbootstrap.network.dto.CarModelName;
import com.innovidio.androidbootstrap.network.dto.CarTrimsInfo;
import com.innovidio.androidbootstrap.repository.CarQueryRepository;

import java.util.List;

import javax.inject.Inject;

public class CarQueryViewModel extends ViewModel {
    @Inject
    CarQueryRepository carQueryRepository;
    @Inject
    public CarQueryViewModel(CarQueryRepository carQueryRepository) {
        this.carQueryRepository = carQueryRepository ;
    }

    public MutableLiveData<List<CarMakesByYear>> getCarMakesByYear(String year){
        return this.carQueryRepository.getCarMakesByYear(year);
    }

    public MutableLiveData<List<CarModelName>> getCarModelsByYearAndMake(String year, String make){
        return this.carQueryRepository.getCarModelsByYearAndMake(year, make);
    }

    public MutableLiveData<List<CarTrimsInfo>> getCarTrimsByYearMakeModel(String year, String make, String model){
        return this.carQueryRepository.getCarTrimsByYearMakeModel(year, make, model);
    }

}
