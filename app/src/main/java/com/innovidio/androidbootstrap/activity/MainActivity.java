package com.innovidio.androidbootstrap.activity;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.innovidio.androidbootstrap.AppPreferences;
import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.entity.FuelUp;
import com.innovidio.androidbootstrap.entity.Maintenance;
import com.innovidio.androidbootstrap.entity.Trip;
import com.innovidio.androidbootstrap.entity.models.TimeLine;
import com.innovidio.androidbootstrap.network.dto.CarMakesByYear;
import com.innovidio.androidbootstrap.network.dto.CarModelName;
import com.innovidio.androidbootstrap.network.dto.CarTrimsInfo;
import com.innovidio.androidbootstrap.viewmodel.CarQueryViewModel;
import com.innovidio.androidbootstrap.db.dao.FeedDao;
import com.innovidio.androidbootstrap.di.viewmodel.ViewModelProviderFactory;
import com.innovidio.androidbootstrap.viewmodel.TimeLineViewModel;

import java.util.List;

import javax.inject.Inject;
import dagger.android.support.DaggerAppCompatActivity;


public class MainActivity extends DaggerAppCompatActivity {
    private static final String TAG = "MainActivityLog";

    @Inject
    ViewModelProviderFactory providerFactory;
    @Inject
    AppPreferences appPreferences;



    CarQueryViewModel carQueryViewModel = null;
    TimeLineViewModel timeLineViewModel = null;
    List<TimeLine> timeLineList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        carQueryViewModel =  new ViewModelProvider(this, providerFactory).get(CarQueryViewModel.class);
        timeLineViewModel =  new ViewModelProvider(this, providerFactory).get(TimeLineViewModel.class);
    //    appPreferences.put(AppPreferences.Key.SAMPLE_INT,100);


        carApiQueries();
        timeLineData();

    }

    private void timeLineData(){
        timeLineViewModel.getAllFuelUps().observe(this, new Observer<List<FuelUp>>() {
            @Override
            public void onChanged(List<FuelUp> fuelUps) {
                if (fuelUps!=null){

                }
            }
        });

        timeLineViewModel.getTrips().observe(this, new Observer<List<Trip>>() {
            @Override
            public void onChanged(List<Trip> trips) {
                if (trips!=null){
                    setAllTripsValues(trips);
                }
            }
        });

        timeLineViewModel.getAllMaintenanceService().observe(this, new Observer<List<Maintenance>>() {
            @Override
            public void onChanged(List<Maintenance> maintenances) {
                if (maintenances!=null){

                }
            }
        });

    }

    private void carApiQueries(){
        carQueryViewModel.getCarMakesByYear("2019").observe(this, new Observer<List<CarMakesByYear>>() {
            @Override
            public void onChanged(List<CarMakesByYear> carMakesByYears) {
                if (carMakesByYears!=null){
                    Log.e("response","CarMakesByYear: "+carMakesByYears.get(0).getMakeDisplay());
                }
            }
        });

        carQueryViewModel.getCarModelsByYearAndMake("2019", "Acura" ).observe(this, new Observer<List<CarModelName>>() {
            @Override
            public void onChanged(List<CarModelName> carModelNames) {
                if (carModelNames!=null) {
                    Log.e("response", "CarModelName: " + carModelNames.get(0).getModelName());
                }
            }
        });

        carQueryViewModel.getCarTrimsByYearMakeModel("2019", "Acura" , "ILX").observe(this, new Observer<List<CarTrimsInfo>>() {
            @Override
            public void onChanged(List<CarTrimsInfo> carTrimsInfos) {
                if (carTrimsInfos!=null){
                    Log.e("response","CarTrimsInfo: "+carTrimsInfos.get(0).getModelEngineCc());
                }
            }
        });
    }

    private void setAllTripsValues(List<Trip> tripList){
        for (int i=0; i<tripList.size(); i++){
            Trip trip =  tripList.get(i);
            // parameters --> int id, Date saveDate, String location, long totalPayment,String type, long meterStart, long meterEnd, Date meterCurrentValue, String serviceDetails){
            TimeLine timeLine = new TimeLine(trip.getId(),trip.getSaveDate(), trip.getDestination(), -1, TimeLineViewModel.TRIP,
                    -1, -1, -1, TimeLineViewModel.NONE );

            timeLineList.add(timeLine);
        }
    }
}
