package com.innovidio.androidbootstrap.fragment;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import com.innovidio.androidbootstrap.AppPreferences;
import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.Utils.UtilClass;
import com.innovidio.androidbootstrap.adapter.GeneralCarSpinnerAdapter;
import com.innovidio.androidbootstrap.databinding.FragmentAddCarWashBinding;
import com.innovidio.androidbootstrap.entity.Car;
import com.innovidio.androidbootstrap.entity.Form;
import com.innovidio.androidbootstrap.entity.Maintenance;
import com.innovidio.androidbootstrap.interfaces.TimeLineItem;
import com.innovidio.androidbootstrap.viewmodel.CarViewModel;
import com.innovidio.androidbootstrap.viewmodel.FormViewModel;
import com.innovidio.androidbootstrap.viewmodel.MaintenanceViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

import static com.innovidio.androidbootstrap.AppPreferences.Key.SELECTED_CAR_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAddCarWash extends DaggerFragment {

    @Inject
    MaintenanceViewModel maintenanceViewModel;

    @Inject
    CarViewModel carViewModel;

    @Inject
    FormViewModel formViewModel;

    @Inject
    AppPreferences appPreferences;


    private GeneralCarSpinnerAdapter carAdapter;
    private ArrayList<Car> carDataList = new ArrayList<>();
    private FragmentAddCarWashBinding carWashBinding;
    private int carID;
    private Maintenance maintenance = new Maintenance();
    private final Calendar todaysCalender = Calendar.getInstance();
    private String dateForEntry, timeForEntry;
    private boolean isEmpty = true;


    public FragmentAddCarWash() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        carWashBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_car_wash, container, false);
        View view = carWashBinding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeAdapter();

        carViewModel.getAllCars().observe(this, cars -> {
            carDataList.addAll(cars);
            carAdapter.notifyDataSetChanged();
            carID = cars.get(0).getId();
        });
        //TIME PICKER
        TimePickerDialog.OnTimeSetListener time = (timePicker, i, i1) -> {
            todaysCalender.set(Calendar.HOUR_OF_DAY, i);
            todaysCalender.set(Calendar.MINUTE, i1);
            timeForEntry = UtilClass.updateTime(todaysCalender, carWashBinding.etCarwashTime);
        };

        carWashBinding.etCarwashTime.setOnClickListener(view1 -> {
            UtilClass.showTimePicker(getActivity(), todaysCalender, time);
        });

        DatePickerDialog.OnDateSetListener date = (datePicker, i, i1, i2) -> {
            todaysCalender.set(Calendar.YEAR, i);
            todaysCalender.set(Calendar.MONTH, i1);
            todaysCalender.set(Calendar.DAY_OF_MONTH, i2);
            dateForEntry = UtilClass.updateDate(todaysCalender, carWashBinding.etCarwashDate);
        };


        carWashBinding.etCarwashDate.setOnClickListener(view1 -> {
            UtilClass.showDatePicker(getActivity(), todaysCalender, date);
        });

        carWashBinding.ivCarwashBack.setOnClickListener(view1 -> {
            getActivity().onBackPressed();
        });


        carWashBinding.btnSaveCarWashData.setOnClickListener(view1 -> {
            if (checkEmptyEntries()) {
                LiveData<Boolean> booleanLiveData = formViewModel.addForm(createForm());
                booleanLiveData.observe(this, formSaved -> {
                    if (formSaved) {
                        LiveData<Form> lastForm = formViewModel.getLastForm();
                        lastForm.observe(getActivity(), form -> {
                            addMaintenance(form.getId());
                        });
                    }
                });
            } else {
                Toast.makeText(getActivity(), "All Fields are required", Toast.LENGTH_SHORT).show();
            }


        });
    }

    private void initializeAdapter() {
        carAdapter = new GeneralCarSpinnerAdapter(getActivity(), carDataList);
        carWashBinding.spinnerSelectCar.setAdapter(carAdapter);
    }

    private boolean checkEmptyEntries() {
        if (TextUtils.isEmpty(carWashBinding.etCarwashDate.getText())) {
            carWashBinding.etCarwashDate.setError("Please enter the date");
            return false;
        } else if (TextUtils.isEmpty(carWashBinding.etCarwashTime.getText())) {
            carWashBinding.etCarwashTime.setError("Please enter the time");
            return false;
        } else if (TextUtils.isEmpty(carWashBinding.etOdometerReading.getText())) {
            carWashBinding.etOdometerReading.setError("Please enter your current odometer reading");
            return false;
        } else if (TextUtils.isEmpty(carWashBinding.etCarwashLocation.getText())) {
            carWashBinding.etCarwashLocation.setError("Location is necessary");
            return false;
        } else if (TextUtils.isEmpty(carWashBinding.etCarwashCost.getText())) {
            carWashBinding.etCarwashCost.setError("Enter your Car Wash Cost");
            return false;
        }

        dateForEntry = carWashBinding.etCarwashDate.getText().toString();
        timeForEntry = carWashBinding.etCarwashTime.getText().toString();
        maintenance.setMaintenanceOdometerReading(Integer.parseInt(carWashBinding.etOdometerReading.getText().toString()));
        maintenance.setMaintenanceLocation(carWashBinding.etCarwashLocation.getText().toString());
        maintenance.setMaintenanceCost(Integer.parseInt(carWashBinding.etCarwashCost.getText().toString()));

        maintenance.setCarId(carID);
        maintenance.setMaintenanceName("Car Wash");
        maintenance.setMaintenanceLife(0);
        maintenance.setMaintenanceType(TimeLineItem.Type.CAR_WASH);
        maintenance.setNextMaintenanceDate(new Date());
        maintenance.setAlarmON(false);

        Date convertedDate = UtilClass.convertToDate(dateForEntry, timeForEntry);
        if (convertedDate == null) {
            convertedDate = new Date();
        }
        maintenance.setSaveDate(convertedDate);

        return true;
    }

    private Form createForm() {
        Form form = new Form();
        form.setCarId(appPreferences.getInt(SELECTED_CAR_ID));
        form.setStartDate(new Date());
        form.setEndDate(new Date());
        form.setSaveDate(new Date());
        form.setLocation(carWashBinding.etCarwashLocation.getText().toString());
        form.setTitle("Car Wash");
        return form;
    }

    private void addMaintenance(int formID) {
        if (checkEmptyEntries()) {
            maintenance.setFormId(formID);
            maintenanceViewModel.addMaintenanceService(maintenance);
            Toast.makeText(getActivity(), "Data Submitted Successfully", Toast.LENGTH_SHORT).show();

            //TODO: Show Successful dialog
            getActivity().finish();
        }
    }


}
