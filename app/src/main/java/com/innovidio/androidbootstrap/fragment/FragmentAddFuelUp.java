package com.innovidio.androidbootstrap.fragment;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.innovidio.androidbootstrap.AppPreferences;
import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.Utils.UtilClass;
import com.innovidio.androidbootstrap.adapter.GeneralCarSpinnerAdapter;
import com.innovidio.androidbootstrap.databinding.DialogFuelTypeBinding;
import com.innovidio.androidbootstrap.databinding.FragmentAddFuelUpBinding;
import com.innovidio.androidbootstrap.entity.Car;
import com.innovidio.androidbootstrap.entity.FuelUp;
import com.innovidio.androidbootstrap.viewmodel.CarViewModel;
import com.innovidio.androidbootstrap.viewmodel.FuelUpViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class FragmentAddFuelUp extends DaggerFragment {

    @Inject
    FuelUpViewModel fuelUpViewModel;

    @Inject
    AppPreferences appPreferences;

    @Inject
    CarViewModel carViewModel;

    private FuelUp fuelUp = new FuelUp();

    private GeneralCarSpinnerAdapter carAdapter;
    private ArrayList<Car> carDataList = new ArrayList<>();
    private String sDate, sTime;
    private int carID;
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
        binding.etFuelupCost.setText(calculateTotal(Float.parseFloat(binding.etPricePerUnit.getText().toString()), Float.parseFloat(binding.etNoOfLitre.getText().toString())));
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

        carViewModel.getAllCars().observe(this, cars -> {
            carDataList.addAll(cars);
            carAdapter.notifyDataSetChanged();
            carID = cars.get(0).getId();
        });

        Log.d("TAYYAB", "CAR ID: " + carID);

        initializeAdapter();


        binding.spinnerCarSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                carID = carDataList.get(i).getId();
                Log.d("TAYYAB", "CAR ID: " + carID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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
            if (checkEntries()) {
                fuelUpViewModel.addFuelUp(fuelUp);
                Log.d("FORM_SUBMISSION", "showFuelTypeDialog: FuelUp Added Successfully");
                Toast.makeText(getActivity(), "Fuel Up added Successfully", Toast.LENGTH_SHORT).show();
                //TODO: Make a DIALOG FOR SUCCESSFUL ADDITION
                getActivity().finish();
            } else {
                Toast.makeText(getContext(), "All Fields are Required", Toast.LENGTH_SHORT).show();

            }
        });

        //Checking the Text Change and Manipulate values
        binding.etPricePerUnit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("TAYYAB", "i = " + i + "  i1 = " + i1 + "  i2 = " + i2);
                if (charSequence.toString().trim().length() != 0) {
                    if (binding.etPricePerUnit.getText().length() != 0 && binding.etPricePerUnit.getText().length() != 0) {
                        binding.etFuelupCost.setText(calculateTotal(Float.parseFloat(binding.etPricePerUnit.getText().toString()), Float.parseFloat(binding.etNoOfLitre.getText().toString())));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.etNoOfLitre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() != 0) {
                    if (binding.etPricePerUnit.getText().length() != 0 && binding.etPricePerUnit.getText().length() != 0) {
                        binding.etFuelupCost.setText(calculateTotal(Float.parseFloat(binding.etPricePerUnit.getText().toString()), Float.parseFloat(binding.etNoOfLitre.getText().toString())));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private boolean checkEntries() {

        fuelUp.setCarId(carID);

        Date convertedDate = UtilClass.convertToDate(sDate, sTime);
        if (convertedDate == null) {
            convertedDate = new Date();
        }
        fuelUp.setSaveDate(convertedDate);

        if (TextUtils.isEmpty(binding.etOdometerReading.getText())) {
            binding.etOdometerReading.setError("Enter your Odometer Reading");
            return false;
        } else if (TextUtils.isEmpty(binding.tvFuelType.getText())) {
            binding.tvFuelType.setError("Select the Fuel Type");
            return false;
        } else if (TextUtils.isEmpty(binding.etPricePerUnit.getText())) {
            binding.etPricePerUnit.setError("Enter Price Per Unit");
            return false;
        } else if (TextUtils.isEmpty(binding.etNoOfLitre.getText())) {
            binding.etNoOfLitre.setError("How much Litres you have fueled up");
            return false;
        } else if (TextUtils.isEmpty(binding.tvFuelupLocation.getText())) {
            binding.tvFuelupLocation.setError("Enter the Fuel Up Location");
            return false;
        }

        fuelUp.setOdometerreading(Integer.parseInt(binding.etOdometerReading.getText().toString()));
        fuelUp.setFuelType(binding.tvFuelType.getText().toString());
        fuelUp.setPerunitfuelprice(Integer.parseInt(binding.etPricePerUnit.getText().toString()));
        fuelUp.setLiters(Integer.parseInt(binding.etNoOfLitre.getText().toString()));
        fuelUp.setLocation(binding.tvFuelupLocation.getText().toString());
        return true;
    }

    private void showFuelTypeDialog() {
        DialogFuelTypeBinding dialogBinding;
        dialogBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.dialog_fuel_type, null, false);
        View dialogView = dialogBinding.getRoot();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final AlertDialog fuelTypeDialog = dialogBuilder.create();
        fuelTypeDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        fuelTypeDialog.setView(dialogView);
        fuelTypeDialog.show();

        dialogBinding.btnDone.setOnClickListener(view -> {
            fuelTypeDialog.dismiss();
        });
        dialogBinding.rgFuelType.setOnCheckedChangeListener((radioGroup1, i) -> {
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
    }

    private void initializeAdapter() {
        carAdapter = new GeneralCarSpinnerAdapter(getActivity(), carDataList);
        binding.spinnerCarSelection.setAdapter(carAdapter);
    }

    private String calculateTotal(float pricePerUnit, float totalLiters) {
        float total = pricePerUnit * totalLiters;
        return String.valueOf(total);
    }
}
