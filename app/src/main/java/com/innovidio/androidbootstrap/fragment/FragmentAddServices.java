package com.innovidio.androidbootstrap.fragment;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.Utils.UtilClass;
import com.innovidio.androidbootstrap.databinding.DialogAddSingleExpenseBinding;
import com.innovidio.androidbootstrap.databinding.DialogAddSingleMaintenanceBinding;
import com.innovidio.androidbootstrap.databinding.DialogAddSinglePartBinding;
import com.innovidio.androidbootstrap.databinding.FragmentAddServicesBinding;

import java.util.Calendar;

import dagger.android.support.DaggerFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAddServices extends DaggerFragment {

    private FragmentAddServicesBinding servicesBinding;
    private Calendar calendar = Calendar.getInstance();

    public FragmentAddServices() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        servicesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_services, container, false);
        View view = servicesBinding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DatePickerDialog.OnDateSetListener date = (datePicker, i, i1, i2) -> {
            calendar.set(Calendar.YEAR, i);
            calendar.set(Calendar.MONTH, i1);
            calendar.set(Calendar.DAY_OF_MONTH, i2);
            UtilClass.updateDate(calendar, servicesBinding.tvServicesDate);
        };

        servicesBinding.tvServicesDate.setOnClickListener(view1 -> {
            UtilClass.showDatePicker(getActivity(), calendar, date);
        });

        servicesBinding.ivCarwashBack.setOnClickListener(view1 -> {
            getActivity().onBackPressed();
        });

        TimePickerDialog.OnTimeSetListener time = (timePicker, i, i1) -> {
            calendar.set(Calendar.HOUR_OF_DAY, i);
            calendar.set(Calendar.MINUTE, i1);
            UtilClass.updateTime(calendar, servicesBinding.tvServicesTime);
        };

        servicesBinding.tvServicesTime.setOnClickListener(view1 -> {
            UtilClass.showTimePicker(getActivity(), calendar, time);
        });

        servicesBinding.btnAddServices.setOnClickListener(view1 -> {
            showSingleAddMaintenanceDialog();
        });

        servicesBinding.btnAddParts.setOnClickListener(view1 -> {
            showSingleAddPartsDialog();
        });

        servicesBinding.btnAddExpenses.setOnClickListener(view1 -> {
            showSingleAddExpenseDialog();
        });
    }

    private void showSingleAddMaintenanceDialog() {
        DialogAddSingleMaintenanceBinding dialogTripDetailsBinding;
        dialogTripDetailsBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.dialog_add_single_maintenance, null, false);
        View dialogView = dialogTripDetailsBinding.getRoot();


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final AlertDialog exitDialog = dialogBuilder.create();
        exitDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        exitDialog.setView(dialogView);

        dialogTripDetailsBinding.buttonAdMaintenance.setOnClickListener(view -> {
            //TODO: Add Maintenance
        });

        exitDialog.show();


    }

    private void showSingleAddPartsDialog() {
        DialogAddSinglePartBinding dialogTripDetailsBinding;
        dialogTripDetailsBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.dialog_add_single_part, null, false);
        View dialogView = dialogTripDetailsBinding.getRoot();


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final AlertDialog exitDialog = dialogBuilder.create();
        exitDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        exitDialog.setView(dialogView);

        dialogTripDetailsBinding.buttonAdMaintenance.setOnClickListener(view -> {
            //TODO: Add Parts
        });

        exitDialog.show();


    }

    private void showSingleAddExpenseDialog() {
        DialogAddSingleExpenseBinding dialogTripDetailsBinding;
        dialogTripDetailsBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.dialog_add_single_expense, null, false);
        View dialogView = dialogTripDetailsBinding.getRoot();


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final AlertDialog exitDialog = dialogBuilder.create();
        exitDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        exitDialog.setView(dialogView);

        dialogTripDetailsBinding.buttonAdMaintenance.setOnClickListener(view -> {
            //TODO: Add Maintenance
        });

        exitDialog.show();


    }
}
