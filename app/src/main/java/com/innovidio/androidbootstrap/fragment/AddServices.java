package com.innovidio.androidbootstrap.fragment;


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
import com.innovidio.androidbootstrap.databinding.FragmentAddServicesBinding;

import java.util.Calendar;

import dagger.android.support.DaggerFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddServices extends DaggerFragment {

    private FragmentAddServicesBinding servicesBinding;
    private Calendar calendar = Calendar.getInstance();

    public AddServices() {
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
            UtilClass.showDatePicker(getActivity(),calendar,date);
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
           UtilClass.showTimePicker(getActivity(),calendar,time);
        });
    }
}
