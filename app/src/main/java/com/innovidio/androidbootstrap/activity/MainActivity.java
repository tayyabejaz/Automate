package com.innovidio.androidbootstrap.activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.innovidio.androidbootstrap.AppPreferences;
import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.Utils.IconProvider;
import com.innovidio.androidbootstrap.Utils.Sorting;
import com.innovidio.androidbootstrap.adapter.SpinnerAdapter;
import com.innovidio.androidbootstrap.adapter.TimelineAdapter;
import com.innovidio.androidbootstrap.databinding.ActivityMainBinding;
import com.innovidio.androidbootstrap.db.dao.FuelDao;
import com.innovidio.androidbootstrap.db.dao.MaintenanceDao;
import com.innovidio.androidbootstrap.db.dao.TripDao;
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
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;


public class MainActivity extends DaggerAppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivityLog";

    @Inject
    FuelDao fuelDao;

    @Inject
    MaintenanceDao maintenanceDao;

    @Inject
    TripDao tripDao;

    @Inject
    ViewModelProviderFactory providerFactory;
    @Inject
    AppPreferences appPreferences;

    private ActivityMainBinding mainBinding;
    private ArrayList<SpinnerDataModel> dataList;
    private SpinnerAdapter spinnerAdapter;
    private TimelineAdapter timelineAdapter;
    private List<TimeLine> data;
    private NavController navigationController;
    private boolean isUp, isDown = false;

    CarViewModel carViewModel = null;
    MaintenanceViewModel maintenanceViewModel = null;
    TripViewModel tripViewModel = null;
    CarQueryViewModel carQueryViewModel = null;
    TimeLineViewModel timeLineViewModel = null;
    FuelUpViewModel fuelUpViewModel = null;

    List<TimeLineItem> timeLineItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        navigationController = Navigation.findNavController(MainActivity.this, R.id.nav_main_host);

        //Initialize icons for Bottom Sheet
        initializeIcons();
        initializeListeners();
        initializeVIewModels();
        initializeAdapters();

        initList();
       // addDummyValues();


        carApiQueries();
        //timeLineData();
        fuelUpData();
        getCarsData();

    }

    private void initializeAdapters() {
        initList();
        spinnerAdapter = new SpinnerAdapter(this, dataList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        mainBinding.spinnerCustomLayout.rvCarSpinnerLayout.setLayoutManager(layoutManager);
        mainBinding.spinnerCustomLayout.rvCarSpinnerLayout.setAdapter(spinnerAdapter);
    }

    private void initializeVIewModels() {
        carQueryViewModel = new ViewModelProvider(this, providerFactory).get(CarQueryViewModel.class);
        timeLineViewModel = new ViewModelProvider(this, providerFactory).get(TimeLineViewModel.class);
        fuelUpViewModel = new ViewModelProvider(this, providerFactory).get(FuelUpViewModel.class);
        carViewModel = new ViewModelProvider(this, providerFactory).get(CarViewModel.class);
        maintenanceViewModel = new ViewModelProvider(this, providerFactory).get(MaintenanceViewModel.class);
        tripViewModel = new ViewModelProvider(this, providerFactory).get(TripViewModel.class);
    }

    private void initializeListeners() {
        mainBinding.bottomNav.ivAdd.setOnClickListener(this);
        mainBinding.bottomNav.llDashboard.setOnClickListener(this);
        mainBinding.bottomNav.llDrive.setOnClickListener(this);
        mainBinding.bottomNav.llMaintain.setOnClickListener(this);
        mainBinding.bottomNav.llSettings.setOnClickListener(this);

        //Hide these two Menus initially
        mainBinding.animatedLayout.setVisibility(View.GONE);
        mainBinding.flCustomSpinnerLayout.setVisibility(View.GONE);

        mainBinding.bottomSheet.ivAddSpeedometer.setOnClickListener(this);
        mainBinding.bottomSheet.ivAddService.setOnClickListener(this);
        mainBinding.bottomSheet.ivAddFuelup.setOnClickListener(this);
        mainBinding.bottomSheet.ivAddCarwash.setOnClickListener(this);
        mainBinding.bottomSheet.ivAddTrip.setOnClickListener(this);
        mainBinding.bottomSheet.ivAddTriprec.setOnClickListener(this);

        mainBinding.mainActivitySpinner.setOnClickListener(this);
        mainBinding.spinnerCustomLayout.ivCancelLayout.setOnClickListener(this);
        mainBinding.spinnerCustomLayout.btnCarSpinnerLayout.setOnClickListener(this);
    }

    private void addDummyValues() {
        FuelUp fuelUp = new FuelUp();
        //   fuelUp.setId(1);
        fuelUp.setCarname("Honda");
        fuelUp.setLiters(10);
        fuelUp.setSaveDate(new Date());
        fuelUp.setLocation("Lahore");
        fuelUp.setOdometerreading(252000);
        fuelUp.setPerunitfuelprice(113);
        fuelUp.setTotalprice(2000);
        fuelUp.setTripId(12);
        fuelUp.setFuelType("Petrol");

        //fuelDao.insert(fuelUp);
        fuelUpViewModel.addFuelUp(fuelUp);

        Maintenance maintenance = new Maintenance();
        // maintenance.setId(122);
        maintenance.setSaveDate(new Date());
        maintenance.setCarId(1);
        maintenance.setMaintenanceCost(1200);
        maintenance.setMaintenanceLocation("Lahore");
        maintenance.setMaintenanceOdometerReading("2520");
        maintenance.setAlarmON(true);
        maintenance.setFormId(10);
        maintenance.setMaintenanceType(TimeLineItem.Type.MAINTENANCE);
        maintenance.setMaintenanceLife(new Date());
        maintenance.setMaintenanceName("Service2");

        //maintenanceDao.insert(maintenance);
        maintenanceViewModel.addMaintenanceService(maintenance);

        Trip trip = new Trip();
        // trip.setId(22);
        trip.setAvgspeed(150);
        trip.setCarname("Honda2");
        trip.setDestination("Islamabad");
        trip.setDistanceCovered(100);
        trip.setFueleconomypertrip(10);
        trip.setMaxspeed(200);
        trip.setSaveDate(new Date());
        trip.setTripTitle("lhr_to_islamabad");
        trip.setTripType("Personal");

        // tripDao.insert(trip);
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
        timeLineViewModel.getAllTimelineMergerData().observe(this, timeLineItems -> {
            if (timeLineItems != null && timeLineItems.size() > 0) {

                timeLineItemList.addAll(timeLineItems);
                timeLineItemList = Sorting.sortList(timeLineItemList);

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
                    slideDown(mainBinding.animatedLayout, 700);
                    // myButton.setText("Slide up");
                } else {
                    mainBinding.animatedLayout.bringToFront();
                    slideUp(mainBinding.animatedLayout);
                    // myButton.setText("Slide down");
                }
                isUp = !isUp;
                break;

            case R.id.iv_add_carwash:
                Intent carwash = new Intent(getBaseContext(), FormActivity.class);
                carwash.putExtra("activity", "carwash");
                startActivity(carwash);
                break;

            case R.id.iv_add_fuelup:
                Intent fuelup = new Intent(getBaseContext(), FormActivity.class);
                fuelup.putExtra("activity", "fuelup");
                startActivity(fuelup);
                break;

            case R.id.iv_add_service:
                Intent service = new Intent(getBaseContext(), FormActivity.class);
                service.putExtra("activity", "service");
                startActivity(service);
                break;

            case R.id.iv_add_trip:
                Intent trip = new Intent(getBaseContext(), FormActivity.class);
                trip.putExtra("activity", "trip");
                startActivity(trip);
                break;

            case R.id.main_activity_spinner:
                if (isDown) {
//                    mainBinding.flCustomSpinnerLayout.bringToFront();
                    slideBackToTop(mainBinding.flCustomSpinnerLayout, mainBinding.mainActivitySpinner, 500);
                } else {
                    mainBinding.flCustomSpinnerLayout.bringToFront();
                    slideFromTop(mainBinding.flCustomSpinnerLayout, 500);
                }
                isDown = !isDown;
                break;

            case R.id.iv_cancel_layout:
                if (isDown) {
                    mainBinding.mainActivitySpinner.bringToFront();
                    slideBackToTop(mainBinding.flCustomSpinnerLayout, mainBinding.mainActivitySpinner, 500);
                    isDown = !isDown;
                }
                break;

            case R.id.btn_car_spinner_layout:
                startActivity(new Intent(MainActivity.this, AddNewCarActivity.class));
                break;
        }
    }

    private void initList() {
        dataList = new ArrayList<>();
        dataList.add(new SpinnerDataModel("Honda Civic", "Honda", "CIVIC", "LRF 4567"));
        dataList.add(new SpinnerDataModel("Toyota Corolla", "Toyota", "Corolla", "GAL 9510"));
        dataList.add(new SpinnerDataModel("Suzuki CIAZ", "Suzuki", "Ciaz", "SLL 6541"));
    }

    public void slideUp(View view) {
        startAnimation();
        // reference link https://stackoverflow.com/questions/19765938/show-and-hide-a-view-with-a-slide-up-down-animation
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight() - 10,  // fromYDelta
                0);                // toYDelta
        animate.setDuration(700);
        animate.setFillAfter(true);
        view.startAnimation(animate);
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
    }

    public void slideBackToTop(View view, View view2, int duration) {

        view.setVisibility(View.INVISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                -view2.getHeight(),  // fromYDelta
                -view.getHeight() - 150);                // toYDelta

        animate.setDuration(duration);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    public void slideFromTop(View view, int duration) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                -view.getHeight(),
                0                 // fromYDelta
        ); // toYDelta

        animate.setDuration(duration);
        animate.setFillAfter(true);
        view.startAnimation(animate);
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

    private void initializeIcons() {

        mainBinding.bottomSheet.ivAddTriprec.setImageDrawable(IconProvider.getTripRecording(this).getDrawable());
        mainBinding.bottomSheet.ivAddTriprec.setBackground(IconProvider.getTripRecording(this).getBackground());

        mainBinding.bottomSheet.ivAddTrip.setImageDrawable(IconProvider.getTrip(this).getDrawable());
        mainBinding.bottomSheet.ivAddTrip.setBackground(IconProvider.getTrip(this).getBackground());

        mainBinding.bottomSheet.ivAddCarwash.setImageDrawable(IconProvider.getCarWash(this).getDrawable());
        mainBinding.bottomSheet.ivAddCarwash.setBackground(IconProvider.getCarWash(this).getBackground());

        mainBinding.bottomSheet.ivAddFuelup.setImageDrawable(IconProvider.getFuelUp(this).getDrawable());
        mainBinding.bottomSheet.ivAddFuelup.setBackground(IconProvider.getFuelUp(this).getBackground());

        mainBinding.bottomSheet.ivAddService.setImageDrawable(IconProvider.getServices(this).getDrawable());
        mainBinding.bottomSheet.ivAddService.setBackground(IconProvider.getServices(this).getBackground());

        mainBinding.bottomSheet.ivAddSpeedometer.setImageDrawable(IconProvider.getSpeedometer(this).getDrawable());
        mainBinding.bottomSheet.ivAddSpeedometer.setBackground(IconProvider.getSpeedometer(this).getBackground());
    }


}
