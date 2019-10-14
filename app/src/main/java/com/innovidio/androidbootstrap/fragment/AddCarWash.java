package com.innovidio.androidbootstrap.fragment;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.Utils.UtilClass;
import com.innovidio.androidbootstrap.databinding.FragmentAddCarWashBinding;
import com.innovidio.androidbootstrap.di.viewmodel.ViewModelProviderFactory;
import com.innovidio.androidbootstrap.entity.Maintenance;
import com.innovidio.androidbootstrap.viewmodel.MaintenanceViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

import static com.innovidio.androidbootstrap.Utils.UtilClass.checkEmptyField;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCarWash extends DaggerFragment {

    @Inject
    ViewModelProviderFactory providerFactory;

    MaintenanceViewModel maintenanceViewModel;

    private FragmentAddCarWashBinding carWashBinding;
    private Maintenance maintenance = new Maintenance();
    private final Calendar todaysCalender = Calendar.getInstance();


    public AddCarWash() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        maintenanceViewModel = new ViewModelProvider(getActivity(), providerFactory).get(MaintenanceViewModel.class);
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

        DatePickerDialog.OnDateSetListener date = (datePicker, i, i1, i2) -> {
            todaysCalender.set(Calendar.YEAR, i);
            todaysCalender.set(Calendar.MONTH, i1);
            todaysCalender.set(Calendar.DAY_OF_MONTH, i2);
            updateDate();
        };

        carWashBinding.etCarwashDate.setOnClickListener(view1 -> {
            new DatePickerDialog(getContext(), date, todaysCalender.get(Calendar.YEAR), todaysCalender.get(Calendar.MONTH), todaysCalender.get(Calendar.DAY_OF_MONTH)).show();
        });

        carWashBinding.ivCarwashBack.setOnClickListener(view1 -> {
            getActivity().onBackPressed();
        });

        TimePickerDialog.OnTimeSetListener time = (timePicker, i, i1) -> {
            todaysCalender.set(Calendar.HOUR_OF_DAY,i);
            todaysCalender.set(Calendar.MINUTE, i1);
            updateTime();
        };

        carWashBinding.etCarwashTime.setOnClickListener(view1 ->{
            new TimePickerDialog(getActivity(), time, todaysCalender.get(Calendar.HOUR_OF_DAY),todaysCalender.get(Calendar.MINUTE), false).show();
        });

        if (checkEmptyField(carWashBinding.etOdometerReading)) {
            maintenance.setMaintenanceOdometerReading(carWashBinding.etOdometerReading.getText().toString());
        }

        if (checkEmptyField(carWashBinding.etCarwashLocation)) {
            maintenance.setMaintenanceLocation(carWashBinding.etCarwashLocation.getText().toString());
        }

        if (checkEmptyField(carWashBinding.etCarwashCost)) {
            maintenance.setMaintenanceCost(Integer.parseInt(carWashBinding.etCarwashCost.getText().toString()));
        }

        String dateInString = date + "T" + time + "Z";
        maintenance.setSaveDate(convertToDate(dateInString));

        carWashBinding.btnSaveCarWashData.setOnClickListener(view1 -> {
            maintenanceViewModel.addMaintenanceService(maintenance);
            Log.d("FORM", "Data Saved to ViewModel");
        });
    }

    private void updateTime() {
        String myFormat = "hh:mm"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        carWashBinding.etCarwashTime.setText(sdf.format(todaysCalender.getTime()));
    }

    private void updateDate() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        carWashBinding.etCarwashDate.setText(sdf.format(todaysCalender.getTime()));
    }

    private Date convertToDate(String dateInString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            Date date = format.parse(dateInString);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

}
