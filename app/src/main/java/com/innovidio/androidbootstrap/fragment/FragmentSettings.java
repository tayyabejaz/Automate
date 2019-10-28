package com.innovidio.androidbootstrap.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.innovidio.androidbootstrap.AppPreferences;
import com.innovidio.androidbootstrap.Constants;
import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.Utils.CustomDeleteDialog;
import com.innovidio.androidbootstrap.Utils.UtilClass;
import com.innovidio.androidbootstrap.activity.AddNewCarActivity;
import com.innovidio.androidbootstrap.activity.UserPreferencesActivity;
import com.innovidio.androidbootstrap.activity.UserProfileActivity;
import com.innovidio.androidbootstrap.adapter.CustomMainSpinnerAdapter;
import com.innovidio.androidbootstrap.databinding.DialogCarDetailBinding;
import com.innovidio.androidbootstrap.databinding.FragmentSettingsBinding;
import com.innovidio.androidbootstrap.entity.Car;
import com.innovidio.androidbootstrap.entity.Form;
import com.innovidio.androidbootstrap.entity.FuelUp;
import com.innovidio.androidbootstrap.entity.Maintenance;
import com.innovidio.androidbootstrap.entity.Trip;
import com.innovidio.androidbootstrap.interfaces.OnCarEditDeleteListener;
import com.innovidio.androidbootstrap.interfaces.TimeLineItem;
import com.innovidio.androidbootstrap.viewmodel.CarViewModel;
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
    AppPreferences appPreferences;
    @Inject
    CarViewModel carViewModel;
    @Inject
    FuelUpViewModel fuelUpViewModel;
    @Inject
    MaintenanceViewModel maintenanceViewModel;
    @Inject
    TripViewModel tripViewModel;

    private FragmentSettingsBinding settingsBinding;
    private CustomMainSpinnerAdapter adapter;
    private CustomDeleteDialog resetDataDialog, deleteCarDialog;
    private List<Car> carArrayList = new ArrayList<>();
    private Car carInstance = new Car();

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
        AppPreferences.SELECTED_CAR_ID = appPreferences.getInt(AppPreferences.Key.SAVED_CAR_ID);
        carViewModel.getAllCars().observe(this, cars -> {
            carArrayList = cars;
            adapter.updateAdapterList(carArrayList);
        });

        carViewModel.getCarById(AppPreferences.SELECTED_CAR_ID).observe(this, new Observer<Car>() {
            @Override
            public void onChanged(Car car) {
                if (car != null) {
                    setSpinnerItem(car);
                }
            }

            private void setSpinnerItem(Car car) {
                String name = car.getManufacturer() + " " + car.getModelName() + " " + car.getMakeYear();
                AppPreferences.SELECTED_CAR_ID = car.getId();
            }
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
        //   fuelUp.setId(1);
        fuelUp.setCarname("Honda Civic 2018");
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
        int serviceCost = UtilClass.getRandomNo(1000, 10000);

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
        trip.setDistanceCovered(UtilClass.getRandomNo(100, 2000));
        trip.setFueleconomypertrip(UtilClass.getRandomNo(10, 20));
        trip.setMaxspeed(UtilClass.getRandomNo(50, 100));
        trip.setSaveDate(faker.date.backward(UtilClass.getRandomNo(1, 50)));
        trip.setTripTitle("Trip with " + faker.name.title());
        if (UtilClass.getRandomNo(0, 10) % 2 == 0) {
            trip.setTripType("Personal");
        } else {
            trip.setTripType("Business");
        }
        int noOfLitters = UtilClass.getRandomNo(10, 30);
        int unitPriceinLit = UtilClass.getRandomNo(100, 120);
        trip.setNoOfLitres(noOfLitters);
        trip.setTotalExpenses(unitPriceinLit * noOfLitters);
        trip.setFuelCostPerUnit(unitPriceinLit);

        // tripDao.insert(trip);
        tripViewModel.addTrip(trip);
    }
}


