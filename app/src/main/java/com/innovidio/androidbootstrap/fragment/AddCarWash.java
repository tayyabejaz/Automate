package com.innovidio.androidbootstrap.fragment;


import android.content.Context;
import android.net.Uri;
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
import com.innovidio.androidbootstrap.databinding.FragmentAddCarWashBinding;
import com.innovidio.androidbootstrap.di.viewmodel.ViewModelProviderFactory;
import com.innovidio.androidbootstrap.entity.Maintenance;
import com.innovidio.androidbootstrap.viewmodel.MaintenanceViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCarWash extends DaggerFragment {

    @Inject
    ViewModelProviderFactory providerFactory;

    MaintenanceViewModel maintenanceViewModel;

    private FragmentAddCarWashBinding carWashBinding;
    private Maintenance maintenance = new Maintenance();
    private String date, time;


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


//        if (checkEntry(carWashBinding.etCarSelection)) {
//            maintenance.setCarId(12);
//        }
        if (checkEntry(carWashBinding.etCarwashDate)) {
            date = carWashBinding.etCarwashDate.getText().toString();
        }
//        else {
//            carWashBinding.etCarwashDate.setError("Please Provide Date");
//        }

        if (checkEntry(carWashBinding.etCarwashTime)) {
            time = carWashBinding.etCarwashTime.getText().toString();
        }
//        else {
//            carWashBinding.etCarwashTime.setError("Provide Time");
//        }

        if (checkEntry(carWashBinding.etOdometerReading)) {
            maintenance.setMaintenanceOdometerReading(carWashBinding.etOdometerReading.getText().toString());
        }
//        else {
//            carWashBinding.etOdometerReading.setError("Provide Reading");
//        }

        if (checkEntry(carWashBinding.etCarwashLocation)) {
            maintenance.setMaintenanceLocation(carWashBinding.etCarwashLocation.getText().toString());
        }
//        else {
//            carWashBinding.etCarwashLocation.setError("Provide Location");
//        }

        if (checkEntry(carWashBinding.etCarwashCost)) {
            maintenance.setMaintenanceCost(Integer.parseInt(carWashBinding.etCarwashCost.getText().toString()));
        }
//        else {
//            carWashBinding.etCarwashCost.setError("Enter the Maintenance Cost");
//        }

        if (date != null && time != null) {
            String dateInString = date + "T" + time + "Z";
            maintenance.setSaveDate(convertTODate(dateInString));
        }

        carWashBinding.btnSaveCarWashData.setOnClickListener(view1 -> {
            maintenanceViewModel.addMaintenanceService(maintenance);
            Log.d("FORM", "Data Saved to ViewModel");
        });
    }

    private boolean checkEntry(EditText field) {
        if (field.getText().length() > 0) {
            return true;
        }
        return false;
    }

    private Date convertTODate(String dateInString) {
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
