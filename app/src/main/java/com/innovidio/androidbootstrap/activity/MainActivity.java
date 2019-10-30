package com.innovidio.androidbootstrap.activity;


import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.DetectedActivity;
import com.innovidio.androidbootstrap.AppPreferences;
import com.innovidio.androidbootstrap.Constants;
import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.Utils.IconProvider;
import com.innovidio.androidbootstrap.Utils.UtilClass;
import com.innovidio.androidbootstrap.adapter.CustomMainSpinnerAdapter;
import com.innovidio.androidbootstrap.databinding.ActivityMainBinding;
import com.innovidio.androidbootstrap.databinding.DialogFilterListBinding;
import com.innovidio.androidbootstrap.db.dao.FuelDao;
import com.innovidio.androidbootstrap.db.dao.MaintenanceDao;
import com.innovidio.androidbootstrap.db.dao.TripDao;
import com.innovidio.androidbootstrap.entity.Car;
import com.innovidio.androidbootstrap.entity.Form;
import com.innovidio.androidbootstrap.entity.FormWithMaintenance;
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
import com.innovidio.androidbootstrap.viewmodel.FormViewModel;
import com.innovidio.androidbootstrap.viewmodel.FuelUpViewModel;
import com.innovidio.androidbootstrap.viewmodel.MaintenanceViewModel;
import com.innovidio.androidbootstrap.viewmodel.TimeLineViewModel;
import com.innovidio.androidbootstrap.viewmodel.TripViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import io.bloco.faker.Faker;

import static com.innovidio.androidbootstrap.Constants.ACTIVITY;
import static com.innovidio.androidbootstrap.Constants.CAR_WASH_FORM;
import static com.innovidio.androidbootstrap.Constants.FUEL_UP_FORM;
import static com.innovidio.androidbootstrap.Constants.SERVICE_FORM;


public class MainActivity extends DaggerAppCompatActivity implements View.OnClickListener, SpinnerItemClickListener {
    private static final String TAG = "MainActivityLog";
    private static final int MY_PERMISSION_LOCATION = 1000;

    int odoMeter = 10000;

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

    @Inject
    FormViewModel formViewModel;

    private ActivityMainBinding mainBinding;
    private List<Car> carArrayList = new ArrayList<>();
    private CustomMainSpinnerAdapter customMainSpinnerAdapter;
    private NavController navigationController;
    private boolean isUp, isDown = false;

    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        navigationController = Navigation.findNavController(MainActivity.this, R.id.nav_main_host);
        navigationController.navigate(R.id.mainDashboardFragment);
        setSelectionForBottomButton(true, false, false, false);

        // only need if activity is not extends with DaggerAppCompatActivity or not added in di builder module
        // timeLineViewModel = new ViewModelProvider(this, providerFactory).get(TimeLineViewModel.class);

        //Initialize icons for Bottom Sheet
        initializeIcons();
        initializeListeners();
        initializeAdapters();
        initList();
        carApiQueries();
        fuelUpData();
        getCarsData();
        checkLocationAndStoragePermissions();


        // todo just to check if it works
//        Form form = new Form();
//        Maintenance maintenance = new Maintenance();
//        List<Maintenance> maintenanceList = new ArrayList<>();
//        maintenanceList.add(maintenance);
//        FormWithMaintenance formWithMaintenance = new FormWithMaintenance();
//        formWithMaintenance.form =  form;
//        formWithMaintenance.setMaintenanceList(maintenanceList);
//        formViewModel.insertFormWithMaintenance(formWithMaintenance);


        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Constants.BROADCAST_DETECTED_ACTIVITY)) {
                    int type = intent.getIntExtra("type", -1);
                    int confidence = intent.getIntExtra("confidence", 0);
                    handleUserActivity(type, confidence);
                }
            }
        };
    }

    private void initializeAdapters() {
        initList();
        customMainSpinnerAdapter = new CustomMainSpinnerAdapter(this, this::onSpinnerItemClick, null, carArrayList, 0);

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
        mainBinding.toolbarNotificationIcon.setOnClickListener(this);
    }

    private void runDummyData() {
        for (int i = 0; i < 10; i++)
            addDummyValues(i);
    }

    private void addDummyValues(int i) {
        Faker faker = new Faker();

        if (i < 3) {
            String[] makeList = getResources().getStringArray(R.array.makeList);
            String[] modelList = getResources().getStringArray(R.array.modelList);
            int[] yearList = getResources().getIntArray(R.array.yearList);
            String makeName = makeList[i];
            String modelName = modelList[i];
            int yearName = yearList[i];
            Car car = new Car();

            car.setModelName(modelName);
            car.setManufacturer(makeName);
            car.setRegistrationNo("LXA " + UtilClass.getRandomNo(2250, 9999));
            car.setMakeYear(yearName);
            car.setSubModel("1.3");
            car.setEngineFuel("Petrol");
            car.setFuelCapacityInLiters(UtilClass.getRandomNo(10, 20));
            car.setEnginecc(UtilClass.getRandomNo(660, 2000));
            car.setCurrentOdomaterReading(UtilClass.getRandomNo(10000, 30000));
            car.setFuelEconomyCityPer100km(UtilClass.getRandomNo(8, 19));
            car.setFuelEconomyMixedPer100km(UtilClass.getRandomNo(8, 19));
            car.setModelDrive("Front Wheel");
            car.setTransmissionType("Manual");
            carViewModel.addCar(car);
        }


        FuelUp fuelUp = new FuelUp();
        //   fuelUp.setId(1)
        fuelUp.setCarId(1);

        fuelUp.setSaveDate(faker.date.backward(UtilClass.getRandomNo(1, 50)));
        fuelUp.setLocation(faker.address.streetAddress());
        fuelUp.setOdometerreading(UtilClass.getRandomNo(10000, 50000));
        int unitPrice = UtilClass.getRandomNo(100, 120);
        int totalLitters = UtilClass.getRandomNo(1, 10);
        fuelUp.setPerunitfuelprice(unitPrice);
        fuelUp.setLiters(totalLitters);
        fuelUp.setTotalprice(unitPrice * totalLitters);
        fuelUp.setFuelType("Petrol");
        //fuelDao.insert(fuelUp);
        fuelUpViewModel.addFuelUp(fuelUp);


        odoMeter += UtilClass.getRandomNo(1000, 2000);
        Date saveDate = faker.date.backward(UtilClass.getRandomNo(1, 50));

        Date nextDate = UtilClass.addDays(saveDate, UtilClass.getRandomNo(20, 100));
        String[] servicecategories = getResources().getStringArray(R.array.service_list);
        String serviceName = servicecategories[UtilClass.getRandomNo(0, 32)];

        Date DateForForm = UtilClass.addDays(saveDate, UtilClass.getRandomNo(20, 100));
        Form form = new Form();
        form.setId(i);
        form.setCarId(1);
        form.setLocation(faker.address.streetAddress());
        form.setStartDate(DateForForm);
        Date dateForm = UtilClass.addDays(DateForForm, UtilClass.getRandomNo(1, 5));
        form.setEndDate(dateForm);
        form.setSaveDate(dateForm);
        form.setTitle(serviceName);


        Maintenance maintenance = new Maintenance();
        // maintenance.setId(122);
        maintenance.setSaveDate(saveDate);
        maintenance.setCarId(1);
        maintenance.setMaintenanceName(serviceName);
        maintenance.setMaintenanceCost(UtilClass.getRandomNo(1000, 10000));
        maintenance.setMaintenanceLocation(faker.address.streetAddress());
        maintenance.setMaintenanceOdometerReading(odoMeter);
        maintenance.setAlarmON(true);
        maintenance.setFormId(i);
        if (UtilClass.getRandomNo(0, 10) % 2 == 0) {
            maintenance.setMaintenanceType(TimeLineItem.Type.CAR_WASH);
            maintenance.setMaintenanceName("Clean/Wash");
        } else {
            maintenance.setMaintenanceType(TimeLineItem.Type.MAINTENANCE);
            maintenance.setMaintenanceName(serviceName);
        }
        maintenance.setNextMaintenanceDate(nextDate);


        //maintenanceDao.insert(maintenance);
        maintenanceViewModel.addMaintenanceService(maintenance);

        Trip trip = new Trip();
        // trip.setId(22);
        trip.setAvgspeed(UtilClass.getRandomNo(50, 80));
        trip.setCarId(1);
        trip.setOrigin(faker.address.city());
        trip.setCarname("Honda Civic 2018");
        trip.setDestination(faker.address.city());
        trip.setIntialOdometer(odoMeter + UtilClass.getRandomNo(1000, 2000));
        odoMeter += odoMeter + UtilClass.getRandomNo(1000, 2000);
        trip.setFinalOdometer(odoMeter);
        trip.setDistanceCovered((double) UtilClass.getRandomNo(100, 2000));
        trip.setFueleconomypertrip((double) UtilClass.getRandomNo(10, 20));
        trip.setMaxspeed(UtilClass.getRandomNo(50, 100));
        trip.setSaveDate(faker.date.backward(UtilClass.getRandomNo(1, 50)));
        trip.setTripTitle("Trip with " + faker.name.title());
        if (UtilClass.getRandomNo(0, 10) % 2 == 0) {
            trip.setTripType("Personal");
        } else {
            trip.setTripType("Business");
        }
        int noOfLitters = UtilClass.getRandomNo(10, 30);
        Double unitPriceinLit = Double.valueOf(UtilClass.getRandomNo(100, 120));
        trip.setNoOfLitres(noOfLitters);
        trip.setTotalExpenses((unitPriceinLit * noOfLitters));
        trip.setFuelCostPerUnit(unitPriceinLit);

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
                if (navigationController.getCurrentDestination().getId() != R.id.mainDashboardFragment) {
                    navigationController.navigate(R.id.mainDashboardFragment);
                    setSelectionForBottomButton(true, false, false, false);
                }
                break;

            case R.id.ll_drive:
                if (navigationController.getCurrentDestination().getId() != R.id.driveFragment) {
                    navigationController.navigate(R.id.driveFragment);
                    setSelectionForBottomButton(false, true, false, false);
                }
                break;

            case R.id.ll_maintain:
                if (navigationController.getCurrentDestination().getId() != R.id.maintainFragment) {
                    navigationController.navigate(R.id.maintainFragment);
                    setSelectionForBottomButton(false, false, true, false);
                }
                break;

            case R.id.ll_settings:
                if (navigationController.getCurrentDestination().getId() != R.id.settingsFragment) {
                    navigationController.navigate(R.id.settingsFragment);
                    setSelectionForBottomButton(false, false, false, true);
                }
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
                UtilClass.showStartTripDialog(this);
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

            case R.id.toolbar_notification_icon:
                startActivity(new Intent(this, ReminderActivity.class));
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
        AppPreferences.SELECTED_CAR_ID = car.getId();
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
        view.setVisibility(View.GONE);
    }

    public void slideBackToTop(View view, View view2, int duration) {

        view.setVisibility(View.GONE);
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

    // fencing api code
    // todo fencing api

    private void checkLocationAndStoragePermissions() {
        if ((ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)
        ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Log.i(TAG, "Permission previously denied and app shouldn't ask again.  Skipping" +
                        " weather snapshot.");
            } else {
                ActivityCompat.requestPermissions(
                        MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSION_LOCATION
                );
            }
        }
    }


    private void handleUserActivity(int type, int confidence) {


        switch (type) {
            case DetectedActivity.IN_VEHICLE:
            case DetectedActivity.RUNNING: {
                if (confidence > Constants.CONFIDENCE) {
                    UtilClass.showStartTripDialog(this);
                }
                break;
            }
            case DetectedActivity.ON_BICYCLE: {

                break;
            }
//            case DetectedActivity.ON_FOOT: {
//                break;
//            }

//            case DetectedActivity.STILL: {
//                break;
//            }
//            case DetectedActivity.TILTING: {
//                break;
//            }
//            case DetectedActivity.WALKING: {
//                startDrive();
//                break;
//            }
//            case DetectedActivity.UNKNOWN: {
//                break;
//            }

        }

        Log.e(TAG, "Confidence: " + confidence);


    }

    @Override
    protected void onResume() {
        super.onResume();
      //  UtilClass.startTracking(this);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                new IntentFilter(Constants.BROADCAST_DETECTED_ACTIVITY));
    }

    @Override
    protected void onPause() {
        super.onPause();

        // LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    private void setSelectionForBottomButton(boolean dashboard, boolean drive, boolean maintain, boolean settings) {
        mainBinding.bottomNav.llDashboard.setSelected(dashboard);
        mainBinding.bottomNav.llDrive.setSelected(drive);
        mainBinding.bottomNav.llMaintain.setSelected(maintain);
        mainBinding.bottomNav.llSettings.setSelected(settings);

        if (dashboard) {
            mainBinding.bottomNav.tvDashboard.setTextColor(this.getResources().getColor(R.color.blueTextColor));
            mainBinding.bottomNav.tvDrive.setTextColor(this.getResources().getColor(R.color.gray_dialog_colour));
            mainBinding.bottomNav.tvMaintain.setTextColor(this.getResources().getColor(R.color.gray_dialog_colour));
            mainBinding.bottomNav.tvSettings.setTextColor(this.getResources().getColor(R.color.gray_dialog_colour));
        } else if (drive) {
            mainBinding.bottomNav.tvDashboard.setTextColor(this.getResources().getColor(R.color.gray_dialog_colour));
            mainBinding.bottomNav.tvDrive.setTextColor(this.getResources().getColor(R.color.blueTextColor));
            mainBinding.bottomNav.tvMaintain.setTextColor(this.getResources().getColor(R.color.gray_dialog_colour));
            mainBinding.bottomNav.tvSettings.setTextColor(this.getResources().getColor(R.color.gray_dialog_colour));
        } else if (maintain) {
            mainBinding.bottomNav.tvDashboard.setTextColor(this.getResources().getColor(R.color.gray_dialog_colour));
            mainBinding.bottomNav.tvDrive.setTextColor(this.getResources().getColor(R.color.gray_dialog_colour));
            mainBinding.bottomNav.tvMaintain.setTextColor(this.getResources().getColor(R.color.blueTextColor));
            mainBinding.bottomNav.tvSettings.setTextColor(this.getResources().getColor(R.color.gray_dialog_colour));
        } else if (settings) {
            mainBinding.bottomNav.tvDashboard.setTextColor(this.getResources().getColor(R.color.gray_dialog_colour));
            mainBinding.bottomNav.tvDrive.setTextColor(this.getResources().getColor(R.color.gray_dialog_colour));
            mainBinding.bottomNav.tvMaintain.setTextColor(this.getResources().getColor(R.color.gray_dialog_colour));
            mainBinding.bottomNav.tvSettings.setTextColor(this.getResources().getColor(R.color.blueTextColor));
        }


    }


}
