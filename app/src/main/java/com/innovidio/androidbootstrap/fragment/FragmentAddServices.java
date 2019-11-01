package com.innovidio.androidbootstrap.fragment;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.innovidio.androidbootstrap.adapter.GeneralCarSpinnerAdapter;
import com.innovidio.androidbootstrap.adapter.ServiceAdapter;
import com.innovidio.androidbootstrap.adapter.SingleMaintenanceAdapter;
import com.innovidio.androidbootstrap.databinding.DialogAddSingleExpenseBinding;
import com.innovidio.androidbootstrap.databinding.DialogAddSingleMaintenanceBinding;
import com.innovidio.androidbootstrap.databinding.DialogAddSinglePartBinding;
import com.innovidio.androidbootstrap.databinding.FragmentAddServicesBinding;
import com.innovidio.androidbootstrap.entity.Car;
import com.innovidio.androidbootstrap.entity.Maintenance;
import com.innovidio.androidbootstrap.interfaces.OnSingleServiceCardListener;
import com.innovidio.androidbootstrap.interfaces.TimeLineItem;
import com.innovidio.androidbootstrap.viewmodel.CarViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAddServices extends DaggerFragment implements OnSingleServiceCardListener {

    @Inject
    CarViewModel carViewModel;

    private FragmentAddServicesBinding servicesBinding;
    private SingleMaintenanceAdapter adapter;
    private GeneralCarSpinnerAdapter carAdapter;
    private ArrayList<Car> carList = new ArrayList<>();
    private Calendar calendar = Calendar.getInstance();
    private List<Maintenance> maintenances = new ArrayList<>();
    private String life, sDate, sTime, maintenanceService, lifeSpan;
    private int carID, odometerReading, serviceLife, serviceCost;
    private boolean isEmpty;

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
        initValues();
        initializeAdapters();

        carViewModel.getAllCars().observe(this, cars -> {
            if (cars != null) {
                if (cars.size() > 0) {
                    carList.addAll(cars);
                    carAdapter.notifyDataSetChanged();
                    carID = cars.get(0).getId();
                }
            }
        });

        DatePickerDialog.OnDateSetListener date = (datePicker, i, i1, i2) -> {
            calendar.set(Calendar.YEAR, i);
            calendar.set(Calendar.MONTH, i1);
            calendar.set(Calendar.DAY_OF_MONTH, i2);
            sDate = UtilClass.updateDate(calendar, servicesBinding.tvServicesDate);
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
            sTime = UtilClass.updateTime(calendar, servicesBinding.tvServicesTime);
        };

        servicesBinding.tvServicesTime.setOnClickListener(view1 -> {
            UtilClass.showTimePicker(getActivity(), calendar, time);
        });

        servicesBinding.btnSaveCarWashData.setOnClickListener(view1 -> {
            if (checkEmptyEntries()) {
                Maintenance maintenance = new Maintenance();
            } else {

            }
        });

        servicesBinding.btnAddServices.setOnClickListener(view1 -> {
            String[] serviceArray = getActivity().getResources().getStringArray(R.array.service_list);
            showSingleAddMaintenanceDialog(null, serviceArray);
        });

        servicesBinding.btnAddParts.setOnClickListener(view1 -> {
            String[] partsArray = getActivity().getResources().getStringArray(R.array.parts_categories);
            showSingleAddMaintenanceDialog(null, partsArray);
        });

        servicesBinding.btnAddExpenses.setOnClickListener(view1 -> {
            String[] otherExpensesArray = getActivity().getResources().getStringArray(R.array.otherexpenses_categories);
            showSingleAddMaintenanceDialog(null, otherExpensesArray);
        });
    }

    private void initValues() {
        servicesBinding.tvServicesTime.setText(UtilClass.getCurrentTime());
        servicesBinding.tvServicesDate.setText(UtilClass.getTodayDate());
        sDate = UtilClass.getTodayDate();
        sTime = UtilClass.getCurrentTime();
    }

    private void showSingleAddMaintenanceDialog(Maintenance maintenanceFromParameters, String[] servicesArray) {

        Maintenance maintenance = new Maintenance();

        DialogAddSingleMaintenanceBinding singleMaintenance;
        singleMaintenance = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.dialog_add_single_maintenance, null, false);
        View dialogView = singleMaintenance.getRoot();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final AlertDialog singleMaintenanceDialog = dialogBuilder.create();
        singleMaintenanceDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        singleMaintenanceDialog.setView(dialogView);
        singleMaintenanceDialog.show();

        ServiceAdapter adapterlist = new ServiceAdapter(getActivity(), servicesArray);
        singleMaintenance.spinnerServiceList.setAdapter(adapterlist);
        adapterlist.notifyDataSetChanged();

        singleMaintenance.spinnerServiceList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                Log.d("TAYYAB", "SERVICE NAME: " + name);
                maintenanceService = name;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        lifeSpan = "weeks";
        singleMaintenance.spinnerServiceTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                lifeSpan = adapterView.getItemAtPosition(i).toString();
                Log.d("TAYYAB", "SERVICE NAME: " + lifeSpan);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        singleMaintenance.buttonAddMaintenance.setOnClickListener(view ->

        {

            if (TextUtils.isEmpty(singleMaintenance.etServiceLife.getText().toString())) {
                isEmpty = true;
                singleMaintenance.etServiceLife.setError("Set your Service Life");
            } else {
                switch (lifeSpan) {
                    case "weeks":
                        int weeks = UtilClass.dayConverter(Integer.parseInt(singleMaintenance.etServiceLife.getText().toString()), 0, 0);
                        maintenance.setMaintenanceLife(weeks);
                        maintenance.setNextMaintenanceDate(UtilClass.getDateAfterAddingDaysInGivenDate(new Date(), weeks));
                        break;

                    case "month":
                        int months = UtilClass.dayConverter(0, 0, Integer.parseInt(singleMaintenance.etServiceLife.getText().toString()));
                        maintenance.setMaintenanceLife(months);
                        maintenance.setNextMaintenanceDate(UtilClass.getDateAfterAddingDaysInGivenDate(new Date(), months));
                        break;

                    case "year":
                        int years = UtilClass.dayConverter(0, Integer.parseInt(singleMaintenance.etServiceLife.getText().toString()), 0);
                        maintenance.setMaintenanceLife(years);
                        maintenance.setNextMaintenanceDate(UtilClass.getDateAfterAddingDaysInGivenDate(new Date(), years));
                        break;
                }
                isEmpty = false;
            }


            if (TextUtils.isEmpty(singleMaintenance.etPrice.getText())) {
                isEmpty = true;
                singleMaintenance.etPrice.setError("Enter the Price");
            } else {
                isEmpty = false;
                maintenance.setMaintenanceCost(Integer.parseInt(singleMaintenance.etPrice.getText().toString()));
            }

            if (maintenanceService.equals(" ")) {
                isEmpty = true;
            } else {
                maintenance.setMaintenanceName(maintenanceService);
            }

            if (TextUtils.isEmpty(singleMaintenance.etOdometerReading.getText())) {
                singleMaintenance.etOdometerReading.setError("Enter your Odometer reading at that Service");
                isEmpty = true;
            } else {
                maintenance.setMaintenanceOdometerReading(Integer.parseInt(singleMaintenance.etOdometerReading.getText().toString()));
                isEmpty = false;
            }


            if (isEmpty) {
                Toast.makeText(getActivity(), "All fields are required", Toast.LENGTH_SHORT).show();
            } else {
                maintenance.setMaintenanceType(TimeLineItem.Type.MAINTENANCE);
                maintenance.setSaveDate(new Date());
                maintenance.setAlarmON(true);
                maintenances.add(maintenance);
                adapter.updateList(maintenances);
                singleMaintenanceDialog.dismiss();
            }
        });


    }

    private void showSingleAddPartsDialog(Maintenance maintenanceFromParameters) {
        if (maintenanceFromParameters == null) {
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
        } else {
            //TODO: UPDATE PART MAINTENANCE
        }

    }

    private void showSingleAddExpenseDialog(Maintenance maintenanceFromParameters) {
        if (maintenanceFromParameters == null) {
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

    }

    private void initializeAdapters() {
        adapter = new SingleMaintenanceAdapter(getActivity(), this, maintenances);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        servicesBinding.rvAddServices.setAdapter(adapter);
        servicesBinding.rvAddServices.setLayoutManager(layoutManager);

        carAdapter = new GeneralCarSpinnerAdapter(getActivity(), carList);
        servicesBinding.spinnerCarSelection.setAdapter(carAdapter);
    }

    @Override
    public void onEditClick(Maintenance maintenance) {
        Toast.makeText(getActivity(), "Edit Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClick(Maintenance maintenance) {
        Toast.makeText(getActivity(), "Delete Clicked", Toast.LENGTH_SHORT).show();
    }

    private boolean checkEmptyEntries() {
        if (TextUtils.isEmpty(servicesBinding.etLocation.getText())) {
            servicesBinding.etLocation.setError("Enter your Maintenance Location");
            return false;
        }
        return true;
    }

}
