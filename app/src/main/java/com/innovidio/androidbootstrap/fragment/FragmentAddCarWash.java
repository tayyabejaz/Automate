package com.innovidio.androidbootstrap.fragment;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.innovidio.androidbootstrap.AppPreferences;
import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.Utils.UtilClass;
import com.innovidio.androidbootstrap.activity.MainActivity;
import com.innovidio.androidbootstrap.adapter.GeneralSpinnerAdapter;
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
import java.util.List;

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


    private GeneralSpinnerAdapter carAdapter;
    private ArrayList<String> carDataList = new ArrayList<>();
    private FragmentAddCarWashBinding carWashBinding;
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

        carViewModel.getAllCars().observe(this, new Observer<List<Car>>() {
            @Override
            public void onChanged(List<Car> cars) {
                for (int i = 0; i < cars.size(); i++) {
                    String carYear = String.valueOf(cars.get(i).getMakeYear());
                    String carMake = cars.get(i).getManufacturer();
                    String carModel = cars.get(i).getModelName();
                    String carSubmodel = cars.get(i).getSubModel();


                    String finalName = carMake + "_" + carModel + "_" + carSubmodel + "_" + carYear;
                    carDataList.add(finalName);
                    carAdapter.notifyDataSetChanged();
                }
            }
        });
        //TIME PICKER
        TimePickerDialog.OnTimeSetListener time = (timePicker, i, i1) -> {
            todaysCalender.set(Calendar.HOUR_OF_DAY, i);
            todaysCalender.set(Calendar.MINUTE, i1);
            UtilClass.updateTime(todaysCalender, carWashBinding.etCarwashTime);
        };

        carWashBinding.etCarwashTime.setOnClickListener(view1 -> {
            UtilClass.showTimePicker(getActivity(), todaysCalender, time);
        });

        DatePickerDialog.OnDateSetListener date = (datePicker, i, i1, i2) -> {
            todaysCalender.set(Calendar.YEAR, i);
            todaysCalender.set(Calendar.MONTH, i1);
            todaysCalender.set(Calendar.DAY_OF_MONTH, i2);
            UtilClass.updateDate(todaysCalender, carWashBinding.etCarwashDate);
        };


        carWashBinding.etCarwashDate.setOnClickListener(view1 -> {
            UtilClass.showDatePicker(getActivity(), todaysCalender, date);
        });

        carWashBinding.ivCarwashBack.setOnClickListener(view1 -> {
            getActivity().onBackPressed();
        });


        carWashBinding.btnSaveCarWashData.setOnClickListener(view1 -> {

            if (TextUtils.isEmpty(carWashBinding.etCarwashDate.getText())) {
                carWashBinding.etCarwashDate.setError("Please enter the date");
                isEmpty = true;
            } else {
                isEmpty = false;
                dateForEntry = carWashBinding.etCarwashDate.getText().toString();
            }

            if (TextUtils.isEmpty(carWashBinding.etCarwashTime.getText())) {
                carWashBinding.etCarwashTime.setError("Please enter the time");
                isEmpty = true;
            } else {
                isEmpty = false;
                timeForEntry = carWashBinding.etCarwashTime.getText().toString();
            }

            String dateInString = dateForEntry + " " + timeForEntry;
//            Date date1 = UtilClass.convertToDate(dateInString);
//
//            Log.d("TAYYAB", "" + date1);

            maintenance.setSaveDate(new Date());

            if (TextUtils.isEmpty(carWashBinding.etOdometerReading.getText())) {
                isEmpty = true;
                carWashBinding.etOdometerReading.setError("Please enter your current odometer reading");
            } else {
                maintenance.setMaintenanceOdometerReading(Integer.parseInt(carWashBinding.etOdometerReading.getText().toString()));
            }

            if (!TextUtils.isEmpty(carWashBinding.etCarwashLocation.getText())) {
                isEmpty = false;
                maintenance.setMaintenanceLocation(carWashBinding.etCarwashLocation.getText().toString());
            } else {
                isEmpty = true;
                carWashBinding.etCarwashLocation.setError("Location is necessary");
            }

            if (!TextUtils.isEmpty(carWashBinding.etCarwashCost.getText())) {
                isEmpty = false;
                maintenance.setMaintenanceCost(Integer.parseInt(carWashBinding.etCarwashCost.getText().toString()));
            } else {
                isEmpty = true;
                carWashBinding.etCarwashCost.setError("Enter your Car Wash Cost");
            }
            Form form = createForm();

            formViewModel.addForm(createForm()).observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {
                    if (aBoolean) {
                        formViewModel.getLastForm().observe(getActivity(), new Observer<Form>() {
                            @Override
                            public void onChanged(Form form) {
                                addMaintenance(form.getId(), maintenance);
                                maintenance.setFormId(form.getId());
                            }
                        });
                    }
                }
            });
        });
    }

    private void initializeAdapter() {
        carAdapter = new GeneralSpinnerAdapter(getActivity(), carDataList);
        carWashBinding.spinnerSelectCar.setAdapter(carAdapter);
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

    private void addMaintenance(int formID, Maintenance maintenance) {
        if (!isEmpty) {
            maintenance.setFormId(formID);
            maintenance.setCarId(MainActivity.carID);
            maintenance.setMaintenanceName("Car Wash");
            maintenance.setMaintenanceLife(0);
            maintenance.setMaintenanceType(TimeLineItem.Type.CAR_WASH);
            maintenance.setNextMaintenanceDate(new Date());
            maintenance.setAlarmON(false);
            maintenanceViewModel.addMaintenanceService(maintenance);
            Toast.makeText(getActivity(), "Data Submitted Successfully", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        } else {
            Toast.makeText(getActivity(), "All fields are Necessary. Please fill all fields", Toast.LENGTH_SHORT).show();
        }
    }
}
