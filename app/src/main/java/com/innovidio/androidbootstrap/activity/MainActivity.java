package com.innovidio.androidbootstrap.activity;


import android.os.Bundle;
import android.util.Log;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.innovidio.androidbootstrap.AppPreferences;
import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.adapter.SpinnerAdapter;
import com.innovidio.androidbootstrap.databinding.ActivityMainBinding;
import com.innovidio.androidbootstrap.di.viewmodel.ViewModelProviderFactory;
import com.innovidio.androidbootstrap.entity.Car;
import com.innovidio.androidbootstrap.entity.FuelUp;
import com.innovidio.androidbootstrap.entity.Maintenance;
import com.innovidio.androidbootstrap.entity.Trip;
import com.innovidio.androidbootstrap.entity.models.SpinnerDataModel;
import com.innovidio.androidbootstrap.entity.models.TimeLine;
import com.innovidio.androidbootstrap.network.dto.CarMakesByYear;
import com.innovidio.androidbootstrap.network.dto.CarModelName;
import com.innovidio.androidbootstrap.network.dto.CarTrimsInfo;
import com.innovidio.androidbootstrap.viewmodel.CarQueryViewModel;
import com.innovidio.androidbootstrap.viewmodel.CarViewModel;
import com.innovidio.androidbootstrap.viewmodel.FuelUpViewModel;
import com.innovidio.androidbootstrap.viewmodel.TimeLineViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;


public class MainActivity extends DaggerAppCompatActivity {
    private static final String TAG = "MainActivityLog";

    @Inject
    ViewModelProviderFactory providerFactory;
    @Inject
    AppPreferences appPreferences;


    CarViewModel carViewModel = null;
    CarQueryViewModel carQueryViewModel = null;
    TimeLineViewModel timeLineViewModel = null;
    FuelUpViewModel fuelUpViewModel = null;
    List<TimeLine> timeLineList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        carQueryViewModel =  new ViewModelProvider(this, providerFactory).get(CarQueryViewModel.class);
//        timeLineViewModel =  new ViewModelProvider(this, providerFactory).get(TimeLineViewModel.class);
//        fuelUpViewModel =  new ViewModelProvider(this, providerFactory).get(FuelUpViewModel.class);
//        carViewModel =  new ViewModelProvider(this, providerFactory).get(CarViewModel.class);
    //    appPreferences.put(AppPreferences.Key.SAMPLE_INT,100);

        carApiQueries();
        //timeLineData();
        //fuelUpData();


//        carViewModel.getAllCars().observe(this, new Observer<List<Car>>() {
//            @Override
//            public void onChanged(List<Car> cars) {
//                if (cars!=null){
//                    Log.d(TAG, "cars: "+cars.size());
//                }
//            }
//        });


        initList();
        mAdapter = new SpinnerAdapter(this, dataList);
        mainBinding.mainActivitySpinner.setAdapter(mAdapter);

        mainBinding.mainActivitySpinner.setAdapter(mAdapter);
    }

    private void fuelUpData() {
        Date currentMonth = null;
        fuelUpViewModel.getMonthlyFuelUp(currentMonth).observe(this, new Observer<List<FuelUp>>() {
            @Override
            public void onChanged(List<FuelUp> fuelUps) {

            }
        });

        fuelUpViewModel.getRecentFuelUp().observe(this, new Observer<FuelUp>() {
            @Override
            public void onChanged(FuelUp fuelUp) {
                if (fuelUp != null) {
                    Log.d(TAG, "Litters: " + fuelUp.getLiters());
                }
            }
        });
    }

    private void timeLineData() {
        timeLineViewModel.getAllFuelUps().observe(this, new Observer<List<FuelUp>>() {
            @Override
            public void onChanged(List<FuelUp> fuelUps) {
                if (fuelUps != null) {

                }
            }
        });

        timeLineViewModel.getTrips().observe(this, new Observer<List<Trip>>() {
            @Override
            public void onChanged(List<Trip> trips) {
                if (trips != null) {
                    setAllTripsValues(trips);
                }
            }
        });

        timeLineViewModel.getAllMaintenanceService().observe(this, new Observer<List<Maintenance>>() {
            @Override
            public void onChanged(List<Maintenance> maintenances) {
                if (maintenances != null) {

                }
            }
        });

    }

    private void carApiQueries() {
        carQueryViewModel.getCarMakesByYear("2019").observe(this, new Observer<List<CarMakesByYear>>() {
            @Override
            public void onChanged(List<CarMakesByYear> carMakesByYears) {
                if (carMakesByYears != null) {
                    Log.e("response", "CarMakesByYear: " + carMakesByYears.get(0).getMakeDisplay());
                }
            }
        });

        carQueryViewModel.getCarModelsByYearAndMake("2019", "Acura").observe(this, new Observer<List<CarModelName>>() {
            @Override
            public void onChanged(List<CarModelName> carModelNames) {
                if (carModelNames != null) {
                    Log.e("response", "CarModelName: " + carModelNames.get(0).getModelName());
                }
            }
        });

        carQueryViewModel.getCarTrimsByYearMakeModel("2019", "Acura", "ILX").observe(this, new Observer<List<CarTrimsInfo>>() {
            @Override
            public void onChanged(List<CarTrimsInfo> carTrimsInfos) {
                if (carTrimsInfos != null) {
                    Log.e("response", "CarTrimsInfo: " + carTrimsInfos.get(0).getModelEngineCc());
                }
            }
        });
    }

    private void setAllTripsValues(List<Trip> tripList) {
        for (int i = 0; i < tripList.size(); i++) {
            Trip trip = tripList.get(i);
            // parameters --> int id, Date saveDate, String location, long totalPayment,String type, long meterStart, long meterEnd, Date meterCurrentValue, String serviceDetails){
            TimeLine timeLine = new TimeLine(trip.getId(), trip.getSaveDate(), trip.getDestination(), -1, TimeLineViewModel.TRIP,
                    -1, -1, -1, TimeLineViewModel.NONE);

            timeLineList.add(timeLine);
        }
    }


    private void initList() {
        dataList = new ArrayList<SpinnerDataModel>();
        dataList.add(new SpinnerDataModel("Honda Civic"));
        dataList.add(new SpinnerDataModel("Toyota Corolla"));
        dataList.add(new SpinnerDataModel("Suzuki Ciaz"));
        dataList.add(new SpinnerDataModel("Daihatsu Move"));
        dataList.add(new SpinnerDataModel("Nissan Juke"));
        dataList.add(new SpinnerDataModel("Ford Focus"));

    }
}
