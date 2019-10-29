package com.innovidio.androidbootstrap.fragment;


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

import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.databinding.FragmentAddCustomCarBinding;
import com.innovidio.androidbootstrap.entity.Car;
import com.innovidio.androidbootstrap.interfaces.ActivityBtnClickListener;
import com.innovidio.androidbootstrap.interfaces.FragmentClickListener;
import com.innovidio.androidbootstrap.viewmodel.CarViewModel;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAddCustomCar extends DaggerFragment implements ActivityBtnClickListener {

    @Inject
    CarViewModel carViewModel;
    private FragmentClickListener listener;
    private FragmentAddCustomCarBinding binding;
    private Car car = new Car();
    private boolean isEmpty = true;

    public FragmentAddCustomCar(FragmentClickListener clickListener) {
        // Required empty public constructor
        this.listener = clickListener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_custom_car, container, false);
        View view = binding.getRoot();
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnCustomCar.setOnClickListener(view1 -> {
            listener.onGoDefaultClick();
        });


    }

    private void checkEnteries() {
        if (!TextUtils.isEmpty(binding.etCarRegNo.getText())) {
            car.setRegistrationNo(binding.etCarRegNo.getText().toString());
            isEmpty = false;
        } else {
            binding.etCarRegNo.setError("Registration Number is required");
            isEmpty = true;
        }

        if (!TextUtils.isEmpty(binding.etYearOfManu.getText())) {
            isEmpty = false;
            car.setMakeYear(Integer.parseInt(binding.etYearOfManu.getText().toString()));
        } else {
            isEmpty = true;
            binding.etYearOfManu.setError("Car Year is required");
        }

        if (!TextUtils.isEmpty(binding.etMakeOfCar.getText())) {
            isEmpty = false;
            car.setManufacturer(binding.etMakeOfCar.getText().toString());
        } else {
            isEmpty = true;
            binding.etMakeOfCar.setError("Car Manufacturer is required");
        }

        if (!TextUtils.isEmpty(binding.etModelOfCar.getText())) {
            isEmpty = false;
            car.setModelName(binding.etModelOfCar.getText().toString());
        } else {
            isEmpty = true;
            binding.etModelOfCar.setError("Car Model Is necessary");
        }

        if (!TextUtils.isEmpty(binding.etSubModelOfCar.getText())) {
            car.setSubModel(binding.etSubModelOfCar.getText().toString());
        } else {
            binding.etSubModelOfCar.setError("Car Trim is necessary");
        }

        if (!TextUtils.isEmpty(binding.etFuelType.getText())) {
            isEmpty = false;
            car.setEngineFuel(binding.etFuelType.getText().toString());
        } else {
            isEmpty = true;
            binding.etFuelType.setError("Enter Car Fuel Type");
        }

        if (!TextUtils.isEmpty(binding.etFuelCapacity.getText())) {
            isEmpty = false;
            car.setFuelCapacityInLiters(Integer.parseInt(binding.etFuelCapacity.getText().toString()));
        } else {
            isEmpty = true;
            binding.etFuelCapacity.setError("Enter you car Fuel Capacity");
        }

        if (!TextUtils.isEmpty(binding.etEngineDisplacement.getText())) {
            isEmpty = false;
            car.setEnginecc(Integer.parseInt(binding.etEngineDisplacement.getText().toString()));
        } else {
            isEmpty = true;
            binding.etEngineDisplacement.setError("Enter your Engine Displacement");
        }

        if (!TextUtils.isEmpty(binding.etCurrentOdometer.getText())) {
            isEmpty = false;
            car.setCurrentOdomaterReading(Integer.parseInt(binding.etCurrentOdometer.getText().toString()));
        } else {
            isEmpty = true;
            binding.etCurrentOdometer.setError("Enter your current Odometer reading");
        }
    }

    @Override
    public void onSubmitButtonClick(Context context) {
        checkEnteries();
        if (isEmpty) {
            Toast.makeText(context, "Please Enter all the required fields", Toast.LENGTH_SHORT).show();
        } else {
            Log.d("FORM_SUBMISSION", "onSubmitButtonClick: Custom Car Added Successfully ");
            carViewModel.addCar(car);
            //TODO: Exit Activity after submission
        }

    }
}
