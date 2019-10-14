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

import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.Utils.UtilClass;
import com.innovidio.androidbootstrap.databinding.FragmentAddTripBinding;

import java.util.Calendar;

import dagger.android.support.DaggerFragment;

public class AddTrip extends DaggerFragment {

    private FragmentAddTripBinding tripBinding;
    private Calendar calendar = Calendar.getInstance();

    public AddTrip() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        tripBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_trip, container, false);
        View view = tripBinding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DatePickerDialog.OnDateSetListener date = (datePicker, i, i1, i2) -> {
            calendar.set(Calendar.YEAR, i);
            calendar.set(Calendar.MONTH, i1);
            calendar.set(Calendar.DAY_OF_MONTH, i2);
            UtilClass.updateDate(calendar, tripBinding.tvTripDate);
        };

        tripBinding.tvTripDate.setOnClickListener(view1 -> {
            UtilClass.showDatePicker(getActivity(),calendar,date);
        });

       tripBinding.ivCarwashBack.setOnClickListener(view1 -> {
            getActivity().onBackPressed();
        });

        TimePickerDialog.OnTimeSetListener time = (timePicker, i, i1) -> {
            calendar.set(Calendar.HOUR_OF_DAY, i);
            calendar.set(Calendar.MINUTE, i1);
            UtilClass.updateTime(calendar, tripBinding.tvTripTime);
        };

        tripBinding.tvTripTime.setOnClickListener(view1 -> {
            UtilClass.showTimePicker(getActivity(),calendar,time);
        });
    }
}
