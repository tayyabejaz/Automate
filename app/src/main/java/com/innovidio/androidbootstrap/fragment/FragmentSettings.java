package com.innovidio.androidbootstrap.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.innovidio.androidbootstrap.AppPreferences;
import com.innovidio.androidbootstrap.BuildConfig;
import com.innovidio.androidbootstrap.Constants;
import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.Utils.CustomDeleteDialog;
import com.innovidio.androidbootstrap.Utils.UtilClass;
import com.innovidio.androidbootstrap.activity.AddNewCarActivity;
import com.innovidio.androidbootstrap.activity.PrivacyPolicyActivity;
import com.innovidio.androidbootstrap.activity.UserPreferencesActivity;
import com.innovidio.androidbootstrap.activity.UserProfileActivity;
import com.innovidio.androidbootstrap.adapter.CustomMainSpinnerAdapter;
import com.innovidio.androidbootstrap.alarms.SetAlarm;
import com.innovidio.androidbootstrap.databinding.DialogCarDetailBinding;
import com.innovidio.androidbootstrap.databinding.FragmentSettingsBinding;
import com.innovidio.androidbootstrap.entity.Alarm;
import com.innovidio.androidbootstrap.entity.Car;
import com.innovidio.androidbootstrap.entity.Form;
import com.innovidio.androidbootstrap.entity.FuelUp;
import com.innovidio.androidbootstrap.entity.Maintenance;
import com.innovidio.androidbootstrap.entity.Trip;
import com.innovidio.androidbootstrap.interfaces.OnCarEditDeleteListener;
import com.innovidio.androidbootstrap.interfaces.TimeLineItem;
import com.innovidio.androidbootstrap.viewmodel.AlarmViewModel;
import com.innovidio.androidbootstrap.viewmodel.CarViewModel;
import com.innovidio.androidbootstrap.viewmodel.FormViewModel;
import com.innovidio.androidbootstrap.viewmodel.FuelUpViewModel;
import com.innovidio.androidbootstrap.viewmodel.MaintenanceViewModel;
import com.innovidio.androidbootstrap.viewmodel.TripViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import io.bloco.faker.Faker;

public class FragmentSettings extends DaggerFragment implements OnCarEditDeleteListener {

    @Inject
    AlarmViewModel alarmViewModel;
    @Inject
    AppPreferences appPreferences;
    @Inject
    CarViewModel carViewModel;
    @Inject
    FuelUpViewModel fuelUpViewModel;
    @Inject
    FormViewModel formViewModel;
    @Inject
    MaintenanceViewModel maintenanceViewModel;
    @Inject
    TripViewModel tripViewModel;

    private FragmentSettingsBinding settingsBinding;
    private CustomMainSpinnerAdapter adapter;
    private CustomDeleteDialog resetDataDialog, deleteCarDialog;
    private List<Car> carArrayList = new ArrayList<>();

    int odoMeter = 10000;

    public FragmentSettings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        settingsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false);
        View view = settingsBinding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Creating a Dialog
        createDialogs();
        initializeAdapters();
        setVersionInfo();

        settingsBinding.buttonEditProfile.setOnClickListener(view1 -> {
            startActivity(new Intent(getActivity(), UserProfileActivity.class));
        });

        settingsBinding.llPreferences.setOnClickListener(view1 -> {
            startActivity(new Intent(getActivity(), UserPreferencesActivity.class));
        });

        settingsBinding.llResetData.setOnClickListener(view1 -> {
            resetDataDialog.showDialog();
        });

        settingsBinding.llGenerateDemoData.setOnClickListener(view1 -> {
            runDummyData();
        });

        settingsBinding.llPrivacyPolicy.setOnClickListener(view1 -> {
            startActivity(new Intent(getContext(), PrivacyPolicyActivity.class));
        });

        settingsBinding.llRateUs.setOnClickListener(view1 -> {
            UtilClass.rateUs(getContext());
        });

        settingsBinding.llShare.setOnClickListener(view1 -> {
            UtilClass.shareApp(getContext());
        });

    }

    private void setVersionInfo() {
        int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;
        settingsBinding.tvVersionCodeValue.setText(versionCode + "");
        settingsBinding.tvVersionValue.setText(versionName);
    }

    private void createDialogs() {
        resetDataDialog = new CustomDeleteDialog(getActivity(), "Reset Data", "Are you sure you want to reset all your app data?", "No", "Yes", R.drawable.automate_reset_app_dialog_icon) {
            @Override
            public void onNegativeBtnClick(Dialog dialog) {
                resetDataDialog.hideDialog();
            }

            @Override
            public void onPositiveBtnClick(Dialog dialog) {
                //TODO: Reset Data ---- Delete Database
                UtilClass.clearAppData(getContext());
                appPreferences.clear();
                //  UtilClass.restartApplication(getContext());
            }
        };

        resetDataDialog.createDialog();

        deleteCarDialog = new CustomDeleteDialog(getActivity(), "Delete Car", "Are you sure you want to delete Dialog", "No", "Yes", R.drawable.automate_delete_car_dialog_icon) {
            @Override
            public void onNegativeBtnClick(Dialog dialog) {
                deleteCarDialog.hideDialog();
            }

            @Override
            public void onPositiveBtnClick(Dialog dialog) {
                //TODO: Delete the CAR
            }
        };

        deleteCarDialog.createDialog();
    }

    private void initializeAdapters() {
        initList();
        adapter = new CustomMainSpinnerAdapter(getActivity(), null, this, carArrayList, Constants.SPINNER_ADPTER_TYPE);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);

        settingsBinding.rvMyCars.setLayoutManager(layoutManager);
        settingsBinding.rvMyCars.setAdapter(adapter);
    }

    private void initList() {
        carViewModel.getAllCars().observe(this, cars -> {
            carArrayList = cars;
            adapter.updateAdapterList(carArrayList);
        });
    }

    @Override
    public void onEditClicked() {
        Intent formIntent = new Intent(getActivity(), AddNewCarActivity.class);
        startActivity(formIntent);
    }

    @Override
    public void onDeleteClicked(Car car) {
        //TODO: Delete the car
        deleteCarDialog.showDialog();
    }

    @Override
    public void onDetailClicked(Car car) {
        showDetailsDialog(car);
    }

    private void showDetailsDialog(Car car) {

        DialogCarDetailBinding dialogTripDetailsBinding;
        dialogTripDetailsBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.dialog_car_detail, null, false);
        dialogTripDetailsBinding.setCarData(car);
        View dialogView = dialogTripDetailsBinding.getRoot();


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final AlertDialog exitDialog = dialogBuilder.create();
        exitDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        exitDialog.setView(dialogView);

        dialogTripDetailsBinding.btnClose.setOnClickListener(view -> {
            exitDialog.dismiss();
        });

        exitDialog.show();


    }

    private void runDummyData() {
        for (int i = 1; i <= 10; i++)
            addDummyValues(i);

        Toast.makeText(getContext(), "Dummy Data added.", Toast.LENGTH_SHORT).show();
    }

    private void addDummyValues(int i) {
        Faker faker = new Faker();
        if (i < 4) {
            String[] makeList = getResources().getStringArray(R.array.makeList);
            String[] modelList = getResources().getStringArray(R.array.modelList);
            int[] yearList = getResources().getIntArray(R.array.yearList);
            String makeName = makeList[i-1];
            String modelName = modelList[i-1];
            int yearName = yearList[i-1];
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
        form.setOdoMeterReading(odoMeter);
        int limit =  UtilClass.getRandomNo(2, 6);
        int totalCost =0;
        for (int j=1; j<limit; j++){
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
            totalCost+=maintenance.getMaintenanceCost();
        }

        form.setTotalCost(totalCost);
        formViewModel.addForm(form);

        Trip trip = new Trip();
        // trip.setId(22);
        trip.setAvgspeed(UtilClass.getRandomNo(50, 80));
        trip.setCarId(1);
        trip.setOrigin(faker.address.city());
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
            trip.setTripType(Trip.TripType.PERSONAL);
        } else {
            trip.setTripType(Trip.TripType.OFFICIAL);
        }
        int noOfLitters = UtilClass.getRandomNo(10, 30);
        Double unitPriceinLit = Double.valueOf(UtilClass.getRandomNo(100, 120));
        trip.setNoOfLitres(noOfLitters);
        trip.setTotalExpenses((unitPriceinLit * noOfLitters));
        trip.setFuelCostPerUnit(unitPriceinLit);

        // tripDao.insert(trip);
        tripViewModel.addTrip(trip);


        if (i < 4) {
            Alarm alarm = new Alarm();
            alarm.setAlarmID(i + 1);
            alarm.setMaintenanceId(0);
            alarm.setAlarmMessage("Time to eat " + faker.food.dish());
            alarm.setActive(true);
            alarm.setAlarmType(Alarm.AlarmType.CUSTOM);
            alarm.setCreationDate(new Date());
            alarm.setExecutionTime(SetAlarm.addTimeInDate(i + 1, 0, 0, 0, 0));
            alarmViewModel.addAlarm(alarm);

            SetAlarm.addReminder(getContext(), alarm);
        }

    }
}


