package com.innovidio.androidbootstrap.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.innovidio.androidbootstrap.Constants;
import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.Utils.CustomDeleteDialog;
import com.innovidio.androidbootstrap.Utils.UtilClass;
import com.innovidio.androidbootstrap.activity.FilterResultActivity;
import com.innovidio.androidbootstrap.activity.FormActivity;
import com.innovidio.androidbootstrap.adapter.ServiceDialogAdapter;
import com.innovidio.androidbootstrap.adapter.TimelineAdapter;
import com.innovidio.androidbootstrap.databinding.DialogAddFuelCapacityBinding;
import com.innovidio.androidbootstrap.databinding.DialogCarwashDetailsBinding;
import com.innovidio.androidbootstrap.databinding.DialogFilterListBinding;
import com.innovidio.androidbootstrap.databinding.DialogFuelupDetailsBinding;
import com.innovidio.androidbootstrap.databinding.DialogMaintenanceDetailsBinding;
import com.innovidio.androidbootstrap.databinding.DialogTripDetailsBinding;
import com.innovidio.androidbootstrap.databinding.FragmentMainDashboardBinding;
import com.innovidio.androidbootstrap.entity.FuelUp;
import com.innovidio.androidbootstrap.entity.Maintenance;
import com.innovidio.androidbootstrap.entity.Trip;
import com.innovidio.androidbootstrap.interfaces.TimeLineItem;
import com.innovidio.androidbootstrap.interfaces.TimelineItemClickListener;
import com.innovidio.androidbootstrap.repository.PreferencesRepository;
import com.innovidio.androidbootstrap.viewmodel.FuelUpViewModel;
import com.innovidio.androidbootstrap.viewmodel.MaintenanceViewModel;
import com.innovidio.androidbootstrap.viewmodel.PreferencesViewModel;
import com.innovidio.androidbootstrap.viewmodel.TimeLineViewModel;
import com.innovidio.androidbootstrap.viewmodel.TripViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

import static com.innovidio.androidbootstrap.Constants.ACTIVITY;
import static com.innovidio.androidbootstrap.Constants.CAR_WASH_FORM;
import static com.innovidio.androidbootstrap.Constants.FUEL_UP_FORM;
import static com.innovidio.androidbootstrap.Constants.SERVICE_FORM;
import static com.innovidio.androidbootstrap.Constants.TRIP_FORM;

/**
 * A simple {@link Fragment} subclass.
 */

public class FragmentMainDashboard extends DaggerFragment implements TimelineItemClickListener, View.OnClickListener {

    private TimelineAdapter timelineAdapter;
    private FragmentMainDashboardBinding binding;
    @Inject
    TimeLineViewModel timeLineViewModel;
    @Inject
    FuelUpViewModel fuelUpViewModel;
    @Inject
    MaintenanceViewModel maintenanceViewModel;
    @Inject
    TripViewModel tripViewModel;

    @Inject
    PreferencesRepository prefRepo;

    private CustomDeleteDialog tripDeleteDialog, maintenanceDeleteDialog, fuelDeleteDialog, carWashDeleteDialog;


    private List<TimeLineItem> timeLineItemList = new ArrayList<>();

    public FragmentMainDashboard() {
        // Required empty public constructor
    }

    public void init() {
        timeLineData();

        timelineAdapter = new TimelineAdapter(getContext(), this, timeLineItemList, 0);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.rvMainFragment.setLayoutManager(layoutManager);
        binding.rvMainFragment.setAdapter(timelineAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        initializeListeners();
        initializeDialogs();
    }

    private void initializeDialogs() {
        createCarWashDeleteDialog(null);
        createMaintenanceDeleteDialog(null);
        createTripDeleteDialog(null);
        createFuelUpDeleteDialog(null);
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
            }
        });
    }


    private void initializeListeners() {
        binding.topFragmentedLayout.firstAddFuel.setOnClickListener(this);
        binding.llFilterLayout.setOnClickListener(this);
    }

    private void showFuelTypeDialog(FuelUp fuelUp) {

        createFuelUpDeleteDialog(fuelUp);
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

        fuelupDetailsBinding.btnDelete.setOnClickListener(view -> {
            fuelDeleteDialog.showDialog();
            exitDialog.dismiss();
        });

        fuelupDetailsBinding.btnEdit.setOnClickListener(view -> {
            UtilClass.startFormActivity(getContext(), FUEL_UP_FORM);
            exitDialog.dismiss();
        });

        exitDialog.show();

    }

    private void showCarWashDialog(Maintenance maintenance) {
        createCarWashDeleteDialog(maintenance);
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

        carwashDetailsBinding.btnDelete.setOnClickListener(view -> {
            maintenanceDeleteDialog.showDialog();
            exitDialog.dismiss();
        });

        carwashDetailsBinding.btnEdit.setOnClickListener(view -> {
            UtilClass.startFormActivity(getContext(),CAR_WASH_FORM);
            exitDialog.dismiss();
        });

        exitDialog.show();

    }

    private void showMaintenanceDialog(Maintenance maintenance) {
        createMaintenanceDeleteDialog(maintenance);
        DialogMaintenanceDetailsBinding maintenanceDetailsBinding;
        maintenanceDetailsBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.dialog_maintenance_details, null, false);
        maintenanceDetailsBinding.setMaintenancedata(maintenance);

        maintenanceDetailsBinding.setPrefdata(prefRepo.getPreferences());
        View dialogView = maintenanceDetailsBinding.getRoot();

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final AlertDialog exitDialog = dialogBuilder.create();
        exitDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        exitDialog.setView(dialogView);

        maintenanceDetailsBinding.btnClose.setOnClickListener(view -> {
            exitDialog.dismiss();
        });

        maintenanceDetailsBinding.btnDelete.setOnClickListener(view -> {
            maintenanceDeleteDialog.showDialog();
            exitDialog.dismiss();
        });
        maintenanceDetailsBinding.btnEdit.setOnClickListener(view -> {
            UtilClass.startFormActivity(getContext(),SERVICE_FORM);
            exitDialog.dismiss();
        });

        exitDialog.show();

    }

    private void showTripDialog(Trip trip) {
        createTripDeleteDialog(trip);
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

        dialogTripDetailsBinding.btnDelete.setOnClickListener(view -> {
            tripDeleteDialog.showDialog();
            exitDialog.dismiss();

        });

        dialogTripDetailsBinding.btnEdit.setOnClickListener(view -> {
            UtilClass.startFormActivity(getContext(), TRIP_FORM);
            exitDialog.dismiss();

        });

        exitDialog.show();


    }

    private void showAddFuelUpDialog() {
        DialogAddFuelCapacityBinding binding;
        binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.dialog_add_fuel_capacity, null, false);
        View dialogView = binding.getRoot();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final AlertDialog exitDialog = dialogBuilder.create();
        exitDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        exitDialog.setView(dialogView);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.first_add_fuel:
                showAddFuelUpDialog();
                break;

            case R.id.ll_filter_layout:
                showFilterDialog();
                break;
        }
    }

    private void showFilterDialog() {
        DialogFilterListBinding binding;
        binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.dialog_filter_list, null, false);
        View dialogView = binding.getRoot();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final AlertDialog exitDialog = dialogBuilder.create();
        exitDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        exitDialog.setView(dialogView);
        exitDialog.show();

        Intent filterIntent = new Intent(getActivity(), FilterResultActivity.class);

        binding.checkboxCarwash.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                filterIntent.putExtra(Constants.FILTER_CARWASH, true);
            } else {
                filterIntent.removeExtra(Constants.FILTER_CARWASH);
            }
        });

        binding.checkboxFuelups.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                filterIntent.putExtra(Constants.FILTER_FUEL_UPS, true);
            } else {
                filterIntent.removeExtra(Constants.FILTER_FUEL_UPS);
            }
        });

        binding.checkboxMaintenance.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                filterIntent.putExtra(Constants.FILTER_MAINTENANCE, true);
            } else {
                filterIntent.removeExtra(Constants.FILTER_MAINTENANCE);
            }
        });

        binding.checkboxTrips.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                filterIntent.putExtra(Constants.FILTER_TRIPS, true);
            } else {
                filterIntent.removeExtra(Constants.FILTER_TRIPS);
            }
        });

        binding.btnFilteredResult.setOnClickListener(view -> {
            startActivity(filterIntent);
        });
    }

    private void createFuelUpDeleteDialog(FuelUp fuelUp) {

        fuelDeleteDialog = new CustomDeleteDialog(getActivity(), "Confirm Delete", "Are you sure you want to delete this 'Fuel up' entry?", "No", "Yes", R.drawable.automate_delete_fuelup_dialog_icon) {
            @Override
            public void onNegativeBtnClick(Dialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void onPositiveBtnClick(Dialog dialog) {
                fuelUpViewModel.deleteFuelUp(fuelUp);
                dialog.dismiss();
            }
        };
        fuelDeleteDialog.createDialog();

    }

    private void createMaintenanceDeleteDialog(Maintenance maintenance) {

        maintenanceDeleteDialog = new CustomDeleteDialog(getActivity(), "Confirm Delete", "Do you want to delete this 'Maintenance' entry?", "No", "Yes", R.drawable.automate_delete_maintenance_dialog_icon) {
            @Override
            public void onNegativeBtnClick(Dialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void onPositiveBtnClick(Dialog dialog) {
                maintenanceViewModel.deleteMaintenanceService(maintenance);
                dialog.dismiss();
            }
        };
        maintenanceDeleteDialog.createDialog();

    }

    private void createTripDeleteDialog(Trip trip) {
        tripDeleteDialog = new CustomDeleteDialog(getActivity(), "Confirm Delete", "Are you sure you want to delete this 'Trip' entry?", "No", "Yes", R.drawable.automate_delete_drive_dialog_icon) {
            @Override
            public void onNegativeBtnClick(Dialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void onPositiveBtnClick(Dialog dialog) {
                tripViewModel.deleteTrip(trip);
                dialog.dismiss();
            }
        };
        tripDeleteDialog.createDialog();

    }

    private void createCarWashDeleteDialog(Maintenance carwash) {

        carWashDeleteDialog = new CustomDeleteDialog(getActivity(), "Confirm Delete", "Are you sure you want to delete this 'Car Wash' Entry", "No", "Yes", R.drawable.automate_delete_carwash_dialog_icon) {
            @Override
            public void onNegativeBtnClick(Dialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void onPositiveBtnClick(Dialog dialog) {
                maintenanceViewModel.deleteMaintenanceService(carwash);
                dialog.dismiss();
            }
        };
        carWashDeleteDialog.createDialog();
    }

}
