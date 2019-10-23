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
import com.innovidio.androidbootstrap.Utils.CustomDialog;
import com.innovidio.androidbootstrap.activity.AddNewCarActivity;
import com.innovidio.androidbootstrap.activity.UserPreferencesActivity;
import com.innovidio.androidbootstrap.activity.UserProfileActivity;
import com.innovidio.androidbootstrap.adapter.CustomMainSpinnerAdapter;
import com.innovidio.androidbootstrap.databinding.DialogCarDetailBinding;
import com.innovidio.androidbootstrap.databinding.FragmentSettingsBinding;
import com.innovidio.androidbootstrap.entity.Car;
import com.innovidio.androidbootstrap.interfaces.OnCarEditDeleteListener;
import com.innovidio.androidbootstrap.viewmodel.CarViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class FragmentSettings extends DaggerFragment implements OnCarEditDeleteListener {

    @Inject
    AppPreferences appPreferences;
    @Inject
    CarViewModel carViewModel;

    private FragmentSettingsBinding settingsBinding;
    private CustomMainSpinnerAdapter adapter;
    private CustomDialog resetDataDialog, deleteCarDialog;
    private List<Car> carArrayList = new ArrayList<>();
    private Car carInstance = new Car();

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
    }

    private void createDialogs() {
        resetDataDialog = new CustomDialog(getActivity(), "Reset Data", "Are you sure you want to reset all your app data?", "No", "Yes", R.drawable.automate_reset_app_dialog_icon) {
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

        deleteCarDialog = new CustomDialog(getActivity(), "Delete Car", "Are you sure you want to delete Dialog", "No", "Yes", R.drawable.automate_delete_car_dialog_icon) {
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
}


