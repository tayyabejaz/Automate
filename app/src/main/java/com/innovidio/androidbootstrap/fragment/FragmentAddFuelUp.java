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
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.innovidio.androidbootstrap.AppPreferences;
import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.Utils.UtilClass;
import com.innovidio.androidbootstrap.databinding.FragmentAddFuelUpBinding;
import com.innovidio.androidbootstrap.entity.FuelUp;
import com.innovidio.androidbootstrap.viewmodel.FuelUpViewModel;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

import static com.innovidio.androidbootstrap.AppPreferences.Key.SELECTED_CAR_ID;

public class FragmentAddFuelUp extends DaggerFragment {

    @Inject
    FuelUpViewModel fuelUpViewModel;

    @Inject
    AppPreferences appPreferences;

    private FuelUp fuelUp = new FuelUp();

    private String sDate, sTime;
    private boolean isEmpty;
    private FragmentAddFuelUpBinding binding;
    private final Calendar calenderInstance = Calendar.getInstance();

    public FragmentAddFuelUp() {
        // Required empty public constructor
    }

    private void init() {
        binding.tvFuelupDate.setText(new Date().toString());
        binding.tvFuelupTime.setText(new Date().toString());
        binding.etOdometerReading.setText("32500");
        binding.tvFuelType.setText("Petrol");
        binding.etPricePerUnit.setText("113");
        binding.etNoOfLitre.setText("10");
        binding.etFuelupCost.setText("1130");
        binding.tvFuelupLocation.setText("Lahore");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_fuel_up, container, false);
        View view = binding.getRoot();

        init();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DatePickerDialog.OnDateSetListener date = (datePicker, i, i1, i2) -> {
            calenderInstance.set(Calendar.YEAR, i);
            calenderInstance.set(Calendar.MONTH, i1);
            calenderInstance.set(Calendar.DAY_OF_MONTH, i2);
            sDate = UtilClass.updateDate(calenderInstance, binding.tvFuelupDate);
        };

        binding.tvFuelupDate.setOnClickListener(view1 -> {
            UtilClass.showDatePicker(getActivity(), calenderInstance, date);
        });

        binding.ivCarwashBack.setOnClickListener(view1 -> {
            getActivity().onBackPressed();
        });

        TimePickerDialog.OnTimeSetListener time = (timePicker, i, i1) -> {
            calenderInstance.set(Calendar.HOUR_OF_DAY, i);
            calenderInstance.set(Calendar.MINUTE, i1);
            sTime = UtilClass.updateTime(calenderInstance, binding.tvFuelupTime);
        };

        binding.tvFuelupTime.setOnClickListener(view1 -> {
            UtilClass.showTimePicker(getActivity(), calenderInstance, time);
        });

        binding.tvFuelType.setOnClickListener(view1 -> {
            showFuelTypeDialog();
        });


        binding.btnSaveFuelupData.setOnClickListener(view1 -> {
            checkEnteries();
            if (isEmpty) {
                Toast.makeText(getContext(), "All Fields are Required", Toast.LENGTH_SHORT).show();
            } else {
                fuelUpViewModel.addFuelUp(fuelUp);
                Log.d("FORM_SUBMISSION", "showFuelTypeDialog: FuelUp Added Successfully");
                Toast.makeText(getActivity(), "Fuel Up added Successfully", Toast.LENGTH_SHORT).show();
                //TODO: Make a DIALOG FOR SUCCESFULL ADDITION
                getActivity().finish();
            }


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
                    binding.tvFuelType.setText("Petrol");
                    Log.d("Radio", "showFuelTypeDialog: Petrol ");
                    break;

                case R.id.rb_diesel:
                    binding.tvFuelType.setText("Diesel");
                    Log.d("Radio", "showFuelTypeDialog: Diesel ");
                    break;

                case R.id.rb_cng:
                    binding.tvFuelType.setText("CNG");
                    Log.d("Radio", "showFuelTypeDialog: CNG ");
                    break;

                case R.id.rb_electric:
                    binding.tvFuelType.setText("Electric");
                    Log.d("Radio", "showFuelTypeDialog: Electric ");
                    break;

                case R.id.rb_ethanol:
                    binding.tvFuelType.setText("Ethanol");
                    Log.d("Radio", "showFuelTypeDialog: Ethanol ");
                    break;

                case R.id.rb_gasoline:
                    binding.tvFuelType.setText("Gasoline");
                    Log.d("Radio", "showFuelTypeDialog: Gasoline ");
                    break;

                case R.id.rb_lpg:
                    binding.tvFuelType.setText("LPG");
                    Log.d("Radio", "showFuelTypeDialog: LPG ");
                    break;
            }
        });
        builder.create();
        builder.show();


    }

    private void checkEnteries() {

        fuelUp.setCarId(appPreferences.getInt(SELECTED_CAR_ID, 1));

        Date convertedDate = UtilClass.convertToDate(sDate, sTime);
        if (convertedDate == null) {
            convertedDate = new Date();
        }
        fuelUp.setSaveDate(convertedDate);

        if (!TextUtils.isEmpty(binding.etOdometerReading.getText())) {
            fuelUp.setOdometerreading(Integer.parseInt(binding.etOdometerReading.getText().toString()));
            isEmpty = false;
        } else {
            isEmpty = true;
            binding.etOdometerReading.setError("Enter your Odometer Reading");
        }

        if (!TextUtils.isEmpty(binding.tvFuelType.getText())) {
            isEmpty = false;
            fuelUp.setFuelType(binding.tvFuelType.getText().toString());
        } else {
            isEmpty = true;
            binding.tvFuelType.setError("Select the Fuel Type");
        }

        if (!TextUtils.isEmpty(binding.etPricePerUnit.getText())) {
            isEmpty = false;
            fuelUp.setPerunitfuelprice(Integer.parseInt(binding.etPricePerUnit.getText().toString()));
        } else {
            isEmpty = true;
            binding.etPricePerUnit.setError("Enter Price Per Unit");
        }

        if (!TextUtils.isEmpty(binding.etNoOfLitre.getText())) {
            isEmpty = false;
            fuelUp.setLiters(Integer.parseInt(binding.etNoOfLitre.getText().toString()));
        } else {
            isEmpty = true;
            binding.etNoOfLitre.setError("How much Litres you have fueled up");
        }

        if (!TextUtils.isEmpty(binding.tvFuelupLocation.getText())) {
            isEmpty = false;
            fuelUp.setLocation(binding.tvFuelupLocation.getText().toString());
        } else {
            isEmpty = true;
            binding.tvFuelupLocation.setError("Enter the Fuel Up Location");
        }

    }
}
