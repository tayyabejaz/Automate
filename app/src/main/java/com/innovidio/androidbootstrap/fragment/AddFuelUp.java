package com.innovidio.androidbootstrap.fragment;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.Utils.UtilClass;
import com.innovidio.androidbootstrap.databinding.FragmentAddFuelUpBinding;

import java.util.Calendar;

import dagger.android.support.DaggerFragment;

public class AddFuelUp extends DaggerFragment {

    private FragmentAddFuelUpBinding fuelUpBinding;
    private final Calendar calenderInstance = Calendar.getInstance();

    public AddFuelUp() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fuelUpBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_fuel_up, container, false);
        View view = fuelUpBinding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DatePickerDialog.OnDateSetListener date = (datePicker, i, i1, i2) -> {
            calenderInstance.set(Calendar.YEAR, i);
            calenderInstance.set(Calendar.MONTH, i1);
            calenderInstance.set(Calendar.DAY_OF_MONTH, i2);
            UtilClass.updateDate(calenderInstance, fuelUpBinding.tvFuelupDate);
        };

        fuelUpBinding.tvFuelupDate.setOnClickListener(view1 -> {
            UtilClass.showDatePicker(getActivity(), calenderInstance, date);
        });

        fuelUpBinding.ivCarwashBack.setOnClickListener(view1 -> {
            getActivity().onBackPressed();
        });

        TimePickerDialog.OnTimeSetListener time = (timePicker, i, i1) -> {
            calenderInstance.set(Calendar.HOUR_OF_DAY, i);
            calenderInstance.set(Calendar.MINUTE, i1);
            UtilClass.updateTime(calenderInstance, fuelUpBinding.tvFuelupTime);
        };

        fuelUpBinding.tvFuelupTime.setOnClickListener(view1 -> {
            UtilClass.showTimePicker(getActivity(), calenderInstance, time);
        });

        fuelUpBinding.tvFuelType.setOnClickListener(view1 -> {
            showFuelTypeDialog();
        });

    }

    private void showFuelTypeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = (getActivity()).getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the
        // dialog layout

        builder.setCancelable(true);
        View view = inflater.inflate(R.layout.fuel_type_dialog, null);
        RadioGroup radioGroup = view.findViewById(R.id.rg_fuel_type);
        view.setBackgroundColor(getActivity().getResources().getColor(android.R.color.transparent));
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("OK", (dialog, id) -> {
                    dialog.dismiss();
                });

        radioGroup.setOnCheckedChangeListener((radioGroup1, i) -> {
            switch (i) {
                case R.id.rb_petrol:
                    Log.d("Radio", "showFuelTypeDialog: Petrol ");
                    break;

                case R.id.rb_diesel:
                    Log.d("Radio", "showFuelTypeDialog: Diesel ");
                    break;

                case R.id.rb_cng:
                    Log.d("Radio", "showFuelTypeDialog: CNG ");
                    break;

                case R.id.rb_electric:
                    Log.d("Radio", "showFuelTypeDialog: Electric ");
                    break;

                case R.id.rb_ethanol:
                    Log.d("Radio", "showFuelTypeDialog: Ethanol ");
                    break;

                case R.id.rb_gasoline:
                    Log.d("Radio", "showFuelTypeDialog: Gasoline ");
                    break;

                case R.id.rb_lpg:
                    Log.d("Radio", "showFuelTypeDialog: LPG ");
                    break;
            }
        });
        builder.create();
        builder.show();

    }
}
