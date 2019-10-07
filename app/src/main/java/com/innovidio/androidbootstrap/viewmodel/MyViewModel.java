package com.innovidio.androidbootstrap.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.innovidio.androidbootstrap.activity.MainActivity;
import com.innovidio.androidbootstrap.network.dto.CarModelName;
import com.innovidio.androidbootstrap.repository.CarQueryRepository;
import java.util.List;

import javax.inject.Inject;

public class MyViewModel extends ViewModel {
    @Inject
    CarQueryRepository carQueryRepository;
    MutableLiveData<List<CarModelName>> responseObserver = new MutableLiveData<>();
    @Inject
    public MyViewModel(CarQueryRepository carQueryRepository) {
        this.carQueryRepository = carQueryRepository ;
    }




    public MutableLiveData<List<CarModelName>>  getCarModels(String year, String make){
        return this.carQueryRepository.getCarModels(year, make);
    }


//    public MutableLiveData<List<CarModelName>> getResponseObserver(MainActivity mainActivity, String year, String make) {
//        carQueryRepository.getCarModelNamesLiveData().observe(mainActivity, new Observer<List<CarModelName>>() {
//            @Override
//            public void onChanged(List<CarModelName> carModelNames) {
//                if (carModelNames!=null){
//                    responseObserver.setValue(carModelNames);
//                }
//
//            }
//        });
//        this.carQueryRepository.getCarModels(year, make);
//        return responseObserver;
//    }

}
