package com.innovidio.androidbootstrap.activity;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.innovidio.androidbootstrap.AppPreferences;
import com.innovidio.androidbootstrap.Constants;
import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.Utils.IconProvider;
import com.innovidio.androidbootstrap.adapter.CustomMainSpinnerAdapter;
import com.innovidio.androidbootstrap.dashboard.SetSpeedLimit;
import com.innovidio.androidbootstrap.databinding.ActivityMainBinding;
import com.innovidio.androidbootstrap.databinding.DialogFilterListBinding;
import com.innovidio.androidbootstrap.db.dao.FuelDao;
import com.innovidio.androidbootstrap.db.dao.MaintenanceDao;
import com.innovidio.androidbootstrap.db.dao.TripDao;
import com.innovidio.androidbootstrap.entity.Car;
import com.innovidio.androidbootstrap.entity.FuelUp;
import com.innovidio.androidbootstrap.entity.Maintenance;
import com.innovidio.androidbootstrap.entity.Trip;
import com.innovidio.androidbootstrap.interfaces.SpinnerItemClickListener;
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

import static com.innovidio.androidbootstrap.Constants.ACTIVITY;
import static com.innovidio.androidbootstrap.Constants.CAR_WASH_FORM;
import static com.innovidio.androidbootstrap.Constants.FUEL_UP_FORM;
import static com.innovidio.androidbootstrap.Constants.SERVICE_FORM;
import static com.innovidio.androidbootstrap.Constants.TRIP_FORM;


public class MainActivity extends DaggerAppCompatActivity implements View.OnClickListener, SpinnerItemClickListener {
    private static final String TAG = "MainActivityLog";

    @Inject
    FuelDao fuelDao;
    @Inject
    MaintenanceDao maintenanceDao;
    @Inject
    TripDao tripDao;

    @Inject
    AppPreferences appPreferences;

    @Inject
    CarViewModel carViewModel;
    @Inject
    MaintenanceViewModel maintenanceViewModel;
    @Inject
    TripViewModel tripViewModel;
    @Inject
    CarQueryViewModel carQueryViewModel;
    @Inject
    FuelUpViewModel fuelUpViewModel;
    @Inject
    TimeLineViewModel timeLineViewModel;

    private ActivityMainBinding mainBinding;
    private List<Car> carArrayList = new ArrayList<>();
    private CustomMainSpinnerAdapter customMainSpinnerAdapter;
    private NavController navigationController;
    private boolean isUp, isDown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        navigationController = Navigation.findNavController(MainActivity.this, R.id.nav_main_host);

        // only need if activity is not extends with DaggerAppCompatActivity or not added in di builder module
        // timeLineViewModel = new ViewModelProvider(this, providerFactory).get(TimeLineViewModel.class);

        //Initialize icons for Bottom Sheet
        initializeIcons();
        initializeListeners();
        initializeAdapters();
        initList();
//        addDummyValues();
        carApiQueries();
        fuelUpData();
        getCarsData();
    }

    private void initializeAdapters() {
        initList();
        customMainSpinnerAdapter = new CustomMainSpinnerAdapter(this,this::onSpinnerItemClick,null, carArrayList,0);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        mainBinding.spinnerCustomLayout.rvCarSpinnerLayout.setLayoutManager(layoutManager);
        mainBinding.spinnerCustomLayout.rvCarSpinnerLayout.setAdapter(customMainSpinnerAdapter);
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
        mainBinding.toolbarFilterIcon.setOnClickListener(this);
    }

    private void addDummyValues() {
        Car car = new Car();
        car.setId(1);
        car.setModelName("Carrola");
        car.setManufacturer("Toyota");
        car.setRegistrationNo("LXA 5039");
        car.setMakeYear(2019);
        car.setSubModel("1.3");
        car.setEngineFuel("Petrol");
        car.setFuelCapacityInLiters(20);
        car.setEnginecc(1000);
        car.setCurrentOdomaterReading(23403);
        car.setFuelEconomyCityPer100km(13);
        car.setFuelEconomyMixedPer100km(15);
        car.setModelDrive("Front Wheel");
        car.setTransmissionType("Manual");
        carViewModel.addCar(car);


        FuelUp fuelUp = new FuelUp();
        //   fuelUp.setId(1);
        fuelUp.setCarname("Honda");
        fuelUp.setCarId(1);
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
        maintenance.setNextMaintenanceDate(new Date());
        maintenance.setMaintenanceName("Service2");

        //maintenanceDao.insert(maintenance);
        maintenanceViewModel.addMaintenanceService(maintenance);

        Trip trip = new Trip();
        // trip.setId(22);
        trip.setAvgspeed(150);
        trip.setCarId(1);
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
        carViewModel.getAllCars().observe(this, cars -> {
            if (cars != null) {
                Log.d(TAG, "cars: " + cars.size());
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
                startFormActivity(CAR_WASH_FORM);
                break;

            case R.id.iv_add_fuelup:
                startFormActivity(FUEL_UP_FORM);
                break;

            case R.id.iv_add_service:
                startFormActivity(SERVICE_FORM);
                break;

            case R.id.iv_add_trip:
                startFormActivity(TRIP_FORM);
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
                closeSpinnerLayout();
                break;

            case R.id.btn_car_spinner_layout:
                startActivity(new Intent(MainActivity.this, AddNewCarActivity.class));
                break;

            case R.id.toolbar_filter_icon:
                showFilterDialog();
                break;
        }
    }

    private void closeSpinnerLayout() {
        if (isDown) {
            mainBinding.mainActivitySpinner.bringToFront();
            slideBackToTop(mainBinding.flCustomSpinnerLayout, mainBinding.mainActivitySpinner, 500);
            isDown = !isDown;
        }
    }

    private void initList() {
        AppPreferences.SELECTED_CAR_ID = appPreferences.getInt(AppPreferences.Key.SAVED_CAR_ID);
        carViewModel.getAllCars().observe(this, cars -> {
            carArrayList = cars;
            customMainSpinnerAdapter.updateAdapterList(carArrayList);
        });

        carViewModel.getCarById(AppPreferences.SELECTED_CAR_ID).observe(this, new Observer<Car>() {
            @Override
            public void onChanged(Car car) {
                if (car != null) {
                    setSpinnerItem(car);
                }
            }
        });
    }

    private void setSpinnerItem(Car car) {
        String name = car.getManufacturer() + " " + car.getModelName() + " " + car.getMakeYear();
        mainBinding.mainActivitySpinner.setText(name);
        AppPreferences.SELECTED_CAR_ID =  car.getId();
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


    public void startFormActivity(String formType) {
        Intent intent = new Intent(this, FormActivity.class);
        intent.putExtra(ACTIVITY, formType);
        startActivity(intent);
    }

    @Override
    public void onSpinnerItemClick(Car car) {
        closeSpinnerLayout();
        appPreferences.put(AppPreferences.Key.SAVED_CAR_ID, car.getId());
        setSpinnerItem(car);
    }

    private void showFilterDialog() {
        DialogFilterListBinding binding;
        binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_filter_list, null, false);
        View dialogView = binding.getRoot();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final AlertDialog exitDialog = dialogBuilder.create();
        exitDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        exitDialog.setView(dialogView);
        exitDialog.show();

        Intent filterIntent = new Intent(MainActivity.this, FilterResultActivity.class);

        binding.checkboxCarwash.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                filterIntent.putExtra(Constants.FILTER_CARWASH, true);
            } else {
                filterIntent.removeExtra(Constants.FILTER_CARWASH);
            }
        });

        binding.checkboxFuelups.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                filterIntent.putExtra(Constants.FILTER_FUEL_UPS, true);
            } else {
                filterIntent.removeExtra(Constants.FILTER_FUEL_UPS);
            }
        });

        binding.checkboxMaintenance.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                filterIntent.putExtra(Constants.FILTER_MAINTENANCE, true);
            } else {
                filterIntent.removeExtra(Constants.FILTER_MAINTENANCE);
            }
        });

        binding.checkboxTrips.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                filterIntent.putExtra(Constants.FILTER_TRIPS, true);
            } else {
                filterIntent.removeExtra(Constants.FILTER_TRIPS);
            }
        });

        binding.btnFilteredResult.setOnClickListener(view -> {
            startActivity(filterIntent);
        });
    }
}
