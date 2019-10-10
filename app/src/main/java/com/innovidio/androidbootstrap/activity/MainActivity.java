package com.innovidio.androidbootstrap.activity;


import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.innovidio.androidbootstrap.AppPreferences;
import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.Utils.IconProvider;
import com.innovidio.androidbootstrap.adapter.SpinnerAdapter;
import com.innovidio.androidbootstrap.adapter.TimelineAdapter;
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
import com.innovidio.androidbootstrap.viewmodel.MaintenanceViewModel;
import com.innovidio.androidbootstrap.viewmodel.TimeLineViewModel;
import com.innovidio.androidbootstrap.viewmodel.TripViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;


public class MainActivity extends DaggerAppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivityLog";

    @Inject
    ViewModelProviderFactory providerFactory;
    @Inject
    AppPreferences appPreferences;

    private ActivityMainBinding mainBinding;
    private ArrayList<SpinnerDataModel> dataList;
    private SpinnerAdapter mAdapter;
    private TimelineAdapter timelineAdapter;
    private List<TimeLine> data;
    private NavController navigationController;
    private boolean isUp;


    CarViewModel carViewModel = null;
    MaintenanceViewModel maintenanceViewModel = null;
    TripViewModel tripViewModel = null;
    CarQueryViewModel carQueryViewModel = null;
    TimeLineViewModel timeLineViewModel = null;
    FuelUpViewModel fuelUpViewModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainBinding.setMainSpinnerData(this);


        navigationController = Navigation.findNavController(MainActivity.this, R.id.nav_main_host);
        mainBinding.bottomNav.ivAdd.setOnClickListener(this);
        mainBinding.bottomNav.llDashboard.setOnClickListener(this);
        mainBinding.bottomNav.llDrive.setOnClickListener(this);
        mainBinding.bottomNav.llMaintain.setOnClickListener(this);
        mainBinding.bottomNav.llSettings.setOnClickListener(this);

        mainBinding.bottomSheet.ivAddCarwash.setOnClickListener(this);
        mainBinding.bottomSheet.ivAddTrip.setOnClickListener(this);
        mainBinding.bottomSheet.ivAddSpeedometer.setOnClickListener(this);
        mainBinding.bottomSheet.ivAddService.setOnClickListener(this);
        mainBinding.bottomSheet.ivAddFuelup.setOnClickListener(this);
        mainBinding.bottomSheet.ivAddTriprec.setOnClickListener(this);

        mainBinding.animatedLayout.setVisibility(View.GONE);
        initList();
        mAdapter = new SpinnerAdapter(this, dataList);
        mainBinding.mainActivitySpinner.setAdapter(mAdapter);

        carQueryViewModel = new ViewModelProvider(this, providerFactory).get(CarQueryViewModel.class);
        timeLineViewModel = new ViewModelProvider(this, providerFactory).get(TimeLineViewModel.class);
        fuelUpViewModel = new ViewModelProvider(this, providerFactory).get(FuelUpViewModel.class);
        carViewModel = new ViewModelProvider(this, providerFactory).get(CarViewModel.class);
        maintenanceViewModel = new ViewModelProvider(this, providerFactory).get(MaintenanceViewModel.class);
        tripViewModel = new ViewModelProvider(this, providerFactory).get(TripViewModel.class);

//        appPreferences.put(AppPreferences.Key.SAMPLE_INT,100);

        carApiQueries();
        timeLineData();
        fuelUpData();
        getCarsData();

        initializeBottomSheetIcon();
    }

    private void addDummyValues() {
        FuelUp fuelUp = new FuelUp();
        fuelUpViewModel.addFuelUp(fuelUp);

        Maintenance maintenance = new Maintenance();
        maintenanceViewModel.addMaintenanceService(maintenance);

        Trip trip = new Trip();
        tripViewModel.addTrip(trip);
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

    private void getCarsData() {
        carViewModel.getAllCars().observe(this, new Observer<List<Car>>() {
            @Override
            public void onChanged(List<Car> cars) {
                if (cars != null) {
                    Log.d(TAG, "cars: " + cars.size());
                }
            }
        });
    }

    private void timeLineData() {
        timeLineViewModel.getAllTimelineMergerData().observe(this, new Observer<List<? extends TimeLineItem>>() {
            @Override
            public void onChanged(List<? extends TimeLineItem> timeLineItems) {
                if (timeLineItems != null && timeLineItems.size() > 0) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        timeLineItems.sort(Comparator.comparing(o -> o.getInsertDateTime()));
                    } else {
                        Collections.sort(timeLineItems, new Comparator<TimeLineItem>() {
                            public int compare(TimeLineItem obj1, TimeLineItem obj2) {
                                return obj1.getInsertDateTime().compareTo(obj2.getInsertDateTime());
                            }
                        });
                    }
                    switch (timeLineItems.get(0).getType()) {
                        case FUEL:
                            FuelUp fuelUp = (FuelUp) timeLineItems.get(0);
                            Log.d(TAG, "FuelUp: " + fuelUp.getCarname());
                            break;

                        case MAINTENANCE:
                            Maintenance maintenance = (Maintenance) timeLineItems.get(0);
                            Log.d(TAG, "Maintenance: " + maintenance.getMaintenanceName());
                            break;

                        case TRIP:
                            Trip trip = (Trip) timeLineItems.get(0);
                            Log.d(TAG, "Trip: " + trip.getTripTitle());
                            break;
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_dashboard:
                navigationController.navigate(R.id.mainDashboardFragment);
                break;

            case R.id.ll_drive:
                navigationController.navigate(R.id.driveFragment);
                break;

            case R.id.ll_maintain:
                navigationController.navigate(R.id.maintainFragment);
                break;

            case R.id.ll_settings:
                navigationController.navigate(R.id.settingsFragment);
                break;

            case R.id.iv_add:
                if (isUp) {
                    mainBinding.bottomNavigationLayout.bringToFront();
                    slideDown(mainBinding.animatedLayout, 500);
                    // myButton.setText("Slide up");
                } else {
                    mainBinding.bottomNavigationLayout.bringToFront();
                    slideUp(mainBinding.animatedLayout);
                    // myButton.setText("Slide down");
                }
                isUp = !isUp;
                break;

            case R.id.iv_add_carwash:
                Toast.makeText(this, "Car Wash Clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.iv_add_fuelup:
                Toast.makeText(this, "Fuel Up Pressed", Toast.LENGTH_SHORT).show();
                break;

            case R.id.iv_add_service:
                Toast.makeText(this, "Service Button", Toast.LENGTH_SHORT).show();
                break;

            case R.id.iv_add_speedometer:
                Toast.makeText(this, "Speedometer Pressed", Toast.LENGTH_SHORT).show();
                break;

            case R.id.iv_add_trip:
                Toast.makeText(this, "Trip Button Pressed", Toast.LENGTH_SHORT).show();
                break;

            case R.id.iv_add_triprec:
                Toast.makeText(this, "Trip Recording Pressed", Toast.LENGTH_SHORT).show();
                break;
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

    private void slideUp(View view) {
        startAnimation();
        // reference link https://stackoverflow.com/questions/19765938/show-and-hide-a-view-with-a-slide-up-down-animation
        view.setVisibility(View.VISIBLE);

        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        mainBinding.bottomNav.llSettings.setVisibility(View.INVISIBLE);
        mainBinding.bottomNav.llMaintain.setVisibility(View.INVISIBLE);
        mainBinding.bottomNav.llDrive.setVisibility(View.INVISIBLE);
        mainBinding.bottomNav.llDashboard.setVisibility(View.INVISIBLE);
    }

    // slide the view from its current position to below itself
    public void slideDown(View view, int duration) {
        stopAnimation();

        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight() + 150); // toYDelta

        animate.setDuration(duration);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        mainBinding.bottomNav.llSettings.setVisibility(View.VISIBLE);
        mainBinding.bottomNav.llMaintain.setVisibility(View.VISIBLE);
        mainBinding.bottomNav.llDrive.setVisibility(View.VISIBLE);
        mainBinding.bottomNav.llDashboard.setVisibility(View.VISIBLE);
    }

    private void startAnimation() {
        Animation rotation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotation);
        rotation.setFillAfter(true);
        mainBinding.bottomNav.ivAdd.startAnimation(rotation);
    }

    private void stopAnimation() {
        Animation rotation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotation2);
        rotation.setFillAfter(true);
        mainBinding.bottomNav.ivAdd.startAnimation(rotation);
    }

    private void initializeBottomSheetIcon() {
        mainBinding.bottomSheet.ivAddTriprec.setBackground(IconProvider.getTripRecording(this).getBackground());
        mainBinding.bottomSheet.ivAddTriprec.setImageDrawable(IconProvider.getTripRecording(this).getDrawable());

        mainBinding.bottomSheet.ivAddTrip.setImageDrawable(IconProvider.getTrip(this).getDrawable());
        mainBinding.bottomSheet.ivAddTrip.setBackground(IconProvider.getTrip(this).getBackground());

        mainBinding.bottomSheet.ivAddCarwash.setBackground(IconProvider.getCarWash(this).getBackground());
        mainBinding.bottomSheet.ivAddCarwash.setImageDrawable(IconProvider.getCarWash(this).getDrawable());

        mainBinding.bottomSheet.ivAddFuelup.setImageDrawable(IconProvider.getFuelUp(this).getDrawable());
        mainBinding.bottomSheet.ivAddFuelup.setBackground(IconProvider.getFuelUp(this).getBackground());

        mainBinding.bottomSheet.ivAddService.setBackground(IconProvider.getServices(this).getBackground());
        mainBinding.bottomSheet.ivAddService.setImageDrawable(IconProvider.getServices(this).getDrawable());

        mainBinding.bottomSheet.ivAddSpeedometer.setImageDrawable(IconProvider.getSpeedometer(this).getDrawable());
        mainBinding.bottomSheet.ivAddSpeedometer.setBackground(IconProvider.getSpeedometer(this).getBackground());
    }


}
