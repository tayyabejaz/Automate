package com.innovidio.androidbootstrap.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.innovidio.androidbootstrap.AppPreferences;
import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.Utils.UtilClass;
import com.innovidio.androidbootstrap.databinding.FragmentDriveBinding;
import com.innovidio.androidbootstrap.entity.Trip;
import com.innovidio.androidbootstrap.repository.PreferencesRepository;
import com.innovidio.androidbootstrap.viewmodel.FuelUpViewModel;
import com.innovidio.androidbootstrap.viewmodel.TripViewModel;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDrive extends DaggerFragment {

    @Inject
    AppPreferences appPreferences;

    @Inject
    TripViewModel tripViewModel;

    @Inject
    FuelUpViewModel fuelUpViewModel;

    @Inject
    PreferencesRepository prefRepo;

    private FragmentDriveBinding binding;

    public FragmentDrive() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_drive, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_drive, container, false);
        View v = binding.getRoot();
        // binding.setFirstFragmentData(this);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.tvGoButton.setOnClickListener(v -> {
            UtilClass.showStartTripDialog(getActivity());
        });

        initilizeCardsData();
    }

    private void initilizeCardsData(){
        int carId = appPreferences.getInt(AppPreferences.Key.SELECTED_CAR_ID, 1);
        tripViewModel.getLastTrip(carId).observe(getActivity(), new Observer<Trip>() {
            @Override
            public void onChanged(Trip trip) {
                binding.setTripdata(trip);
            }
        });


        Date startDate = UtilClass.getCurrentMonthFirstDayDate();
        Date endDate =  UtilClass.getCurrentMonthLastDayDate();

        tripViewModel.getTripsCount(carId, startDate, endDate).observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer!=null)
                    binding.tvTotalTripValue.setText(integer.toString());
            }
        });

        tripViewModel.getTripsCoverDistanceBetweenDateRange(carId, startDate, endDate).observe(getActivity(), new Observer<Long>() {
            @Override
            public void onChanged(Long longValue) {
                if (longValue!=null)
                    binding.tvTotalDistanceValue.setText(longValue+" "+prefRepo.getDistanceUnit());
            }
        });

        fuelUpViewModel.getFuelUpCountBetweenDateRange(carId, startDate, endDate).observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer intValue) {
                if (intValue!=null)
                    binding.tvTotalFuelupsValue.setText(intValue.toString());
            }
        });
    }

}
