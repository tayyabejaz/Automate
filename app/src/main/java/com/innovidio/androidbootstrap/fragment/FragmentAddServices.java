package com.innovidio.androidbootstrap.fragment;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.Utils.UtilClass;
import com.innovidio.androidbootstrap.adapter.SingleMaintenanceAdapter;
import com.innovidio.androidbootstrap.databinding.DialogAddSingleExpenseBinding;
import com.innovidio.androidbootstrap.databinding.DialogAddSingleMaintenanceBinding;
import com.innovidio.androidbootstrap.databinding.DialogAddSinglePartBinding;
import com.innovidio.androidbootstrap.databinding.FragmentAddServicesBinding;
import com.innovidio.androidbootstrap.entity.Maintenance;
import com.innovidio.androidbootstrap.interfaces.OnSingleServiceCardListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dagger.android.support.DaggerFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAddServices extends DaggerFragment implements OnSingleServiceCardListener {

    private FragmentAddServicesBinding servicesBinding;
    private SingleMaintenanceAdapter adapter;
    private Calendar calendar = Calendar.getInstance();
    private List<Maintenance> maintenances = new ArrayList<>();
    private String life;

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

        initializeAdapters();

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
            showSingleAddMaintenanceDialog(null);
        });

        servicesBinding.btnAddParts.setOnClickListener(view1 -> {
            showSingleAddPartsDialog(null);
        });

        servicesBinding.btnAddExpenses.setOnClickListener(view1 -> {
            showSingleAddExpenseDialog(null);
        });
    }

    private void showSingleAddMaintenanceDialog(Maintenance maintenanceFromParameters) {

        DialogAddSingleMaintenanceBinding singleMaintenance;
        singleMaintenance = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.dialog_add_single_maintenance, null, false);
        View dialogView = singleMaintenance.getRoot();
        if (maintenanceFromParameters == null) {


            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
            final AlertDialog singleMaintenanceDialog = dialogBuilder.create();
            singleMaintenanceDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            singleMaintenanceDialog.setView(dialogView);

            singleMaintenance.buttonAddMaintenance.setOnClickListener(view -> {

                Maintenance maintenance = new Maintenance();
                //Gatherng the Data From Form
                singleMaintenance.spinnerServiceList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String name = adapterView.getItemAtPosition(i).toString();
                        Toast.makeText(getContext(), "Maintenance Name: " + name, Toast.LENGTH_SHORT).show();
                        maintenance.setMaintenanceName(name);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                if (!TextUtils.isEmpty(singleMaintenance.etServiceLife.getText().toString())) {
                    life = singleMaintenance.etServiceLife.getText().toString();
                }

                singleMaintenance.spinnerServiceTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String lifeDays = adapterView.getItemAtPosition(i).toString();
                        life = life + lifeDays;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                if (TextUtils.isEmpty(singleMaintenance.etServiceLife.getText().toString())) {
                    maintenance.setMaintenanceLife(Integer.parseInt(life));
                }

                if (!TextUtils.isEmpty(singleMaintenance.etPrice.getText())) {
                    maintenance.setMaintenanceCost(Integer.parseInt(singleMaintenance.etPrice.getText().toString()));
                }

                maintenances.add(maintenance);
                adapter.updateList(maintenances);

                singleMaintenanceDialog.dismiss();
            });
            singleMaintenanceDialog.show();
        }

    }

    private void showSingleAddPartsDialog(Maintenance maintenance) {
        DialogAddSinglePartBinding dialogTripDetailsBinding;
        dialogTripDetailsBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.dialog_add_single_part, null, false);
        View dialogView = dialogTripDetailsBinding.getRoot();


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final AlertDialog singlePartDialog = dialogBuilder.create();
        singlePartDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        singlePartDialog.setView(dialogView);

        dialogTripDetailsBinding.buttonAdMaintenance.setOnClickListener(view -> {
            //TODO: Add Parts
        });

        singlePartDialog.show();


    }

    private void showSingleAddExpenseDialog(Maintenance maintenance) {
        DialogAddSingleExpenseBinding dialogTripDetailsBinding;
        dialogTripDetailsBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.dialog_add_single_expense, null, false);
        View dialogView = dialogTripDetailsBinding.getRoot();


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final AlertDialog singleExpenseDialog = dialogBuilder.create();
        singleExpenseDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        singleExpenseDialog.setView(dialogView);

        dialogTripDetailsBinding.buttonAdMaintenance.setOnClickListener(view -> {
            //TODO: Add Maintenance
        });

        singleExpenseDialog.show();


    }

    private void initializeAdapters() {
        adapter = new SingleMaintenanceAdapter(getActivity(), this, maintenances);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        servicesBinding.rvAddServices.setAdapter(adapter);
        servicesBinding.rvAddServices.setLayoutManager(layoutManager);
    }

    @Override
    public void onEditClick(Maintenance maintenance) {

    }

    @Override
    public void onDeleteClick(Maintenance maintenance) {

    }
}
