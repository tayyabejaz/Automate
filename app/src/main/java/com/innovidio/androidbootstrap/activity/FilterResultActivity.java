package com.innovidio.androidbootstrap.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.innovidio.androidbootstrap.Constants;
import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.adapter.ServiceDialogAdapter;
import com.innovidio.androidbootstrap.adapter.TimelineAdapter;
import com.innovidio.androidbootstrap.databinding.ActivityFilterResultBinding;
import com.innovidio.androidbootstrap.databinding.DialogCarwashDetailsBinding;
import com.innovidio.androidbootstrap.databinding.DialogFuelupDetailsBinding;
import com.innovidio.androidbootstrap.databinding.DialogMaintenanceDetailsBinding;
import com.innovidio.androidbootstrap.databinding.DialogTripDetailsBinding;
import com.innovidio.androidbootstrap.entity.FuelUp;
import com.innovidio.androidbootstrap.entity.Maintenance;
import com.innovidio.androidbootstrap.entity.Trip;
import com.innovidio.androidbootstrap.interfaces.TimeLineItem;
import com.innovidio.androidbootstrap.interfaces.TimelineItemClickListener;
import com.innovidio.androidbootstrap.viewmodel.FuelUpViewModel;
import com.innovidio.androidbootstrap.viewmodel.MaintenanceViewModel;
import com.innovidio.androidbootstrap.viewmodel.TimeLineViewModel;
import com.innovidio.androidbootstrap.viewmodel.TripViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

import static com.innovidio.androidbootstrap.Constants.ACTIVITY;
import static com.innovidio.androidbootstrap.Constants.CAR_WASH_FORM;
import static com.innovidio.androidbootstrap.Constants.FUEL_UP_FORM;
import static com.innovidio.androidbootstrap.Constants.SERVICE_FORM;
import static com.innovidio.androidbootstrap.Constants.TRIP_FORM;

public class FilterResultActivity extends DaggerAppCompatActivity implements TimelineItemClickListener{

    @Inject
    TimeLineViewModel timeLineViewModel;

    @Inject
    FuelUpViewModel fuelUpViewModel;

    @Inject
    TripViewModel tripViewModel;
    @Inject
    MaintenanceViewModel maintenanceViewModel;

    private ActivityFilterResultBinding binding;
    private List<TimeLineItem> timeLineItemList = new ArrayList<>();
    private TimelineAdapter timelineAdapter;
    private boolean trips, fuelups, maintenance, carwash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter_result);
        initializeAdapters();
        getIntentData();
        timeLineFilteredData();
    }

    @Override
    protected void onStart() {
        super.onStart();

        binding.ivFilterBackButton.setOnClickListener(view -> {
            finish();
        });
    }

    private void initializeAdapters() {
        timelineAdapter = new TimelineAdapter(this, this, timeLineItemList, Constants.FILTERED_ADAPTER);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        binding.rvFilterResults.setLayoutManager(layoutManager);
    }

    private void getIntentData() {
        fuelups = getIntent().getBooleanExtra(Constants.FILTER_FUEL_UPS, false);
        trips = getIntent().getBooleanExtra(Constants.FILTER_TRIPS, false);
        maintenance = getIntent().getBooleanExtra(Constants.FILTER_MAINTENANCE, false);
        carwash = getIntent().getBooleanExtra(Constants.FILTER_CARWASH, false);
    }

    private void timeLineFilteredData() {
        timeLineViewModel.getFilteredTimelineMergerData(1, trips, fuelups, maintenance, carwash).observe(this, timeLineItems -> {
            if (timeLineItems != null && timeLineItems.size() > 0) {
                timeLineItemList.addAll(timeLineItems);
                timelineAdapter.updateData(timeLineItemList);
                binding.rvFilterResults.setAdapter(timelineAdapter);
            }
        });
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

    private void showCarWashDialog(Maintenance maintenance) {
        DialogCarwashDetailsBinding carwashDetailsBinding;
        carwashDetailsBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_carwash_details, null, false);
        carwashDetailsBinding.setCarwashdata(maintenance);
        View dialogView = carwashDetailsBinding.getRoot();

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final AlertDialog exitDialog = dialogBuilder.create();
        exitDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        exitDialog.setView(dialogView);

        carwashDetailsBinding.btnClose.setOnClickListener(view -> {
            exitDialog.dismiss();
        });

        carwashDetailsBinding.btnDelete.setOnClickListener(view -> {
            maintenanceViewModel.deleteMaintenanceService(maintenance);
            exitDialog.dismiss();
        });


        carwashDetailsBinding.btnEdit.setOnClickListener(view -> {
            startFormActivity(CAR_WASH_FORM);
            exitDialog.dismiss();
        });

        exitDialog.show();

    }

    private void showMaintenanceDialog(Maintenance maintenance) {
        DialogMaintenanceDetailsBinding maintenanceDetailsBinding;
        maintenanceDetailsBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_maintenance_details, null, false);
        maintenanceDetailsBinding.setMaintenancedata(maintenance);
        View dialogView = maintenanceDetailsBinding.getRoot();

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final AlertDialog exitDialog = dialogBuilder.create();
        exitDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        exitDialog.setView(dialogView);

        maintenanceDetailsBinding.btnClose.setOnClickListener(view -> {
            exitDialog.dismiss();
        });

        maintenanceDetailsBinding.btnDelete.setOnClickListener(view -> {
            maintenanceViewModel.deleteMaintenanceService(maintenance);
            exitDialog.dismiss();
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        ServiceDialogAdapter adapter = new ServiceDialogAdapter();
        maintenanceDetailsBinding.rvServiceDialog.setAdapter(adapter);
        maintenanceDetailsBinding.rvServiceDialog.setLayoutManager(layoutManager);

        maintenanceDetailsBinding.btnEdit.setOnClickListener(view -> {
            startFormActivity(SERVICE_FORM);
            exitDialog.dismiss();
        });

        exitDialog.show();

    }

    private void showTripDialog(Trip trip) {
        DialogTripDetailsBinding dialogTripDetailsBinding;
        dialogTripDetailsBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_trip_details, null, false);
        dialogTripDetailsBinding.setTripdata(trip);
        View dialogView = dialogTripDetailsBinding.getRoot();


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final AlertDialog exitDialog = dialogBuilder.create();
        exitDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        exitDialog.setView(dialogView);

        dialogTripDetailsBinding.btnClose.setOnClickListener(view -> {
            exitDialog.dismiss();
        });

        dialogTripDetailsBinding.btnDelete.setOnClickListener(view -> {
            tripViewModel.deleteTrip(trip);
            exitDialog.dismiss();

        });


        dialogTripDetailsBinding.btnEdit.setOnClickListener(view -> {
            startFormActivity(TRIP_FORM);
            exitDialog.dismiss();

        });

        exitDialog.show();


    }

    private void showFuelTypeDialog(FuelUp fuelUp) {
        DialogFuelupDetailsBinding fuelupDetailsBinding;
        fuelupDetailsBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_fuelup_details, null, false);
        fuelupDetailsBinding.setFuelupdata(fuelUp);
        View dialogView = fuelupDetailsBinding.getRoot();

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final AlertDialog exitDialog = dialogBuilder.create();
        exitDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        exitDialog.setView(dialogView);

        fuelupDetailsBinding.btnClose.setOnClickListener(view -> {
            exitDialog.dismiss();
        });

        fuelupDetailsBinding.btnDelete.setOnClickListener(view -> {
            fuelUpViewModel.deleteFuelUp(fuelUp);

            exitDialog.dismiss();
        });

        fuelupDetailsBinding.btnEdit.setOnClickListener(view -> {
            startFormActivity(FUEL_UP_FORM);
            exitDialog.dismiss();
        });

        exitDialog.show();

    }

    public void startFormActivity(String formType){
        Intent intent = new Intent(this, FormActivity.class);
        intent.putExtra(ACTIVITY, formType);
        startActivity(intent);
    }
}
