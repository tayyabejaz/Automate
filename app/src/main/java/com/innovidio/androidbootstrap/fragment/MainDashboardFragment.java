package com.innovidio.androidbootstrap.fragment;


import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.adapter.TimelineAdapter;
import com.innovidio.androidbootstrap.databinding.DialogCarwashDetailsBinding;
import com.innovidio.androidbootstrap.databinding.DialogFuelupDetailsBinding;
import com.innovidio.androidbootstrap.databinding.DialogMaintenanceDetailsBinding;
import com.innovidio.androidbootstrap.databinding.DialogTripDetailsBinding;
import com.innovidio.androidbootstrap.databinding.FragmentMainDashboardBinding;
import com.innovidio.androidbootstrap.di.viewmodel.ViewModelProviderFactory;
import com.innovidio.androidbootstrap.entity.FuelUp;
import com.innovidio.androidbootstrap.entity.Maintenance;
import com.innovidio.androidbootstrap.entity.Trip;
import com.innovidio.androidbootstrap.interfaces.TimeLineItem;
import com.innovidio.androidbootstrap.interfaces.TimelineItemClickListener;
import com.innovidio.androidbootstrap.viewmodel.TimeLineViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainDashboardFragment extends Fragment implements TimelineItemClickListener {

    @Inject
    ViewModelProviderFactory providerFactory;
    private TimelineAdapter timelineAdapter;
    private FragmentMainDashboardBinding binding;
    private List<TimeLineItem> dataList = new ArrayList<>();

    TimeLineViewModel timeLineViewModel = null;

    private List<TimeLineItem> timeLineItemList = new ArrayList<>();

    public MainDashboardFragment() {
        // Required empty public constructor
    }

    private void init() {
        timeLineViewModel = new ViewModelProvider(getActivity(), providerFactory).get(TimeLineViewModel.class);
        timeLineData();

        timelineAdapter = new TimelineAdapter(getContext(), this, dataList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.rvMainFragment.setLayoutManager(layoutManager);
        binding.rvMainFragment.setAdapter(timelineAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_dashboard, container, false);
        View v = binding.getRoot();
        binding.setFirstFragmentData(this);
        return v;
    }

    private void timeLineData() {
        timeLineViewModel.getAllTimelineMergerData().observe(this, timeLineItems -> {
            if (timeLineItems != null && timeLineItems.size() > 0) {

                timeLineItemList.addAll(timeLineItems);
                //   timeLineItemList = Sorting.sortList(timeLineItemList);
                timelineAdapter.updateData(timeLineItemList);

                switch (timeLineItems.get(0).getType()) {
                    case FUEL:
                        FuelUp fuelUp = (FuelUp) timeLineItems.get(0);
                        Log.d("MainDashboardFragment", "FuelUp: " + fuelUp.getCarname());
                        break;

                    case MAINTENANCE:
                        Maintenance maintenance = (Maintenance) timeLineItems.get(0);
                        Log.d("MainDashboardFragment", "Maintenance: " + maintenance.getMaintenanceName());
                        break;

                    case TRIP:
                        Trip trip = (Trip) timeLineItems.get(0);
                        Log.d("MainDashboardFragment", "Trip: " + trip.getTripTitle());
                        break;
                }
            }
        });
    }

    private void showFuelTypeDialog(FuelUp fuelUp) {
        DialogFuelupDetailsBinding fuelupDetailsBinding;
        fuelupDetailsBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.dialog_fuelup_details, null, false);
        fuelupDetailsBinding.setFuelupdata(fuelUp);
        View dialogView = fuelupDetailsBinding.getRoot();


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final AlertDialog exitDialog = dialogBuilder.create();
        exitDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        exitDialog.setView(dialogView);

        fuelupDetailsBinding.btnClose.setOnClickListener(view -> {
            exitDialog.dismiss();
        });

        exitDialog.show();


    }

    private void showCarWashDialog(Maintenance maintenance) {
        DialogCarwashDetailsBinding carwashDetailsBinding;
        carwashDetailsBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.dialog_carwash_details, null, false);
        carwashDetailsBinding.setCarwashdata(maintenance);
        View dialogView = carwashDetailsBinding.getRoot();

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final AlertDialog exitDialog = dialogBuilder.create();
        exitDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        exitDialog.setView(dialogView);

        carwashDetailsBinding.btnClose.setOnClickListener(view -> {
            exitDialog.dismiss();
        });

        exitDialog.show();


    }

    private void showMaintenanceDialog(Maintenance maintenance) {
        DialogMaintenanceDetailsBinding maintenanceDetailsBinding;
        maintenanceDetailsBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.dialog_maintenance_details, null, false);
        maintenanceDetailsBinding.setMaintenancedata(maintenance);
        View dialogView = maintenanceDetailsBinding.getRoot();

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final AlertDialog exitDialog = dialogBuilder.create();
        exitDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        exitDialog.setView(dialogView);

        maintenanceDetailsBinding.btnClose.setOnClickListener(view -> {
            exitDialog.dismiss();
        });

        exitDialog.show();


    }

    private void showTripDialog(Trip trip) {
        DialogTripDetailsBinding dialogTripDetailsBinding;
        dialogTripDetailsBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.dialog_trip_details, null, false);
        dialogTripDetailsBinding.setTripdata(trip);
        View dialogView = dialogTripDetailsBinding.getRoot();


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final AlertDialog exitDialog = dialogBuilder.create();
        exitDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        exitDialog.setView(dialogView);

        dialogTripDetailsBinding.btnClose.setOnClickListener(view -> {
            exitDialog.dismiss();
        });

        exitDialog.show();


    }

    @Override
    public void onFuelUpClick(FuelUp fuelUp) {
        showFuelTypeDialog(fuelUp);
    }

    @Override
    public void onCarWashClick(Maintenance maintenance) {
        showCarWashDialog(maintenance);
    }

    @Override
    public void onMaintenanceClick(Maintenance maintenance) {
        showMaintenanceDialog(maintenance);
    }

    @Override
    public void onTripsClick(Trip trip) {
        showTripDialog(trip);
    }
}
