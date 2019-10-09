package com.innovidio.androidbootstrap.activity;


import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Switch;


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
import com.innovidio.androidbootstrap.interfaces.TimeLineItem;
import com.innovidio.androidbootstrap.network.dto.CarMakesByYear;
import com.innovidio.androidbootstrap.network.dto.CarModelName;
import com.innovidio.androidbootstrap.network.dto.CarTrimsInfo;
import com.innovidio.androidbootstrap.viewmodel.CarQueryViewModel;
import com.innovidio.androidbootstrap.viewmodel.CarViewModel;
import com.innovidio.androidbootstrap.viewmodel.FuelUpViewModel;
import com.innovidio.androidbootstrap.viewmodel.TimeLineViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

import static com.innovidio.androidbootstrap.interfaces.TimeLineItem.*;
import static com.innovidio.androidbootstrap.interfaces.TimeLineItem.Type.*;


public class MainActivity extends DaggerAppCompatActivity {
    private static final String TAG = "MainActivityLog";

    @Inject
    ViewModelProviderFactory providerFactory;
    @Inject
    AppPreferences appPreferences;

    private ActivityMainBinding mainBinding;
    private ArrayList<SpinnerDataModel> dataList;
    private SpinnerAdapter mAdapter;

    CarViewModel carViewModel = null;
    CarQueryViewModel carQueryViewModel = null;
    TimeLineViewModel timeLineViewModel = null;
    FuelUpViewModel fuelUpViewModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainBinding.setMainSpinnerData(this);

        carQueryViewModel = new ViewModelProvider(this, providerFactory).get(CarQueryViewModel.class);
        timeLineViewModel = new ViewModelProvider(this, providerFactory).get(TimeLineViewModel.class);
        fuelUpViewModel = new ViewModelProvider(this, providerFactory).get(FuelUpViewModel.class);
        carViewModel = new ViewModelProvider(this, providerFactory).get(CarViewModel.class);
//        appPreferences.put(AppPreferences.Key.SAMPLE_INT,100);

        carApiQueries();
        timeLineData();
        fuelUpData();
        getCarsData();

        initList();
        mAdapter = new SpinnerAdapter(this, dataList);
        mainBinding.mainActivitySpinner.setAdapter(mAdapter);

    }

    private void fuelUpData() {
        FuelUp fuelUp = new FuelUp();
        fuelUpViewModel.addFuelUp(fuelUp);

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

    private void getCarsData(){
        carViewModel.getAllCars().observe(this, new Observer<List<Car>>() {
            @Override
            public void onChanged(List<Car> cars) {
                if (cars!=null){
                    Log.d(TAG, "cars: "+cars.size());
                }
            }
        });
    }

    private void timeLineData() {
        timeLineViewModel.getAllTimelineMergerData().observe(this, new Observer<List<? extends TimeLineItem>>() {
            @Override
            public void onChanged(List<? extends TimeLineItem> timeLineItems) {
                if (timeLineItems!=null && timeLineItems.size()>0){

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        timeLineItems.sort(Comparator.comparing(o -> o.getInsertDateTime()));
                    }else{
                        Collections.sort(timeLineItems, new Comparator<TimeLineItem>() {
                            public int compare(TimeLineItem obj1, TimeLineItem obj2) {
                                return obj1.getInsertDateTime().compareTo(obj2.getInsertDateTime());
                            }
                        });
                    }
                    switch(timeLineItems.get(0).getType()){
                        case FUEL:
                            FuelUp fuelUp = (FuelUp) timeLineItems.get(0);
                            Log.d(TAG, "FuelUp: "+fuelUp.getCarname());
                            break;

                        case MAINTENANCE:
                            Maintenance maintenance = (Maintenance) timeLineItems.get(0);
                            Log.d(TAG, "Maintenance: "+maintenance.getMaintenanceName());
                            break;

                        case TRIP:
                            Trip trip = (Trip) timeLineItems.get(0);
                            Log.d(TAG, "Trip: "+trip.getTripTitle());
                            break;
                    }
                }
            }
        });
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
