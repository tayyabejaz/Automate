package com.innovidio.androidbootstrap.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.innovidio.androidbootstrap.AppPreferences;
import com.innovidio.androidbootstrap.Constants;
import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.Utils.CustomDeleteDialog;
import com.innovidio.androidbootstrap.Utils.UtilClass;
import com.innovidio.androidbootstrap.activity.FormActivity;
import com.innovidio.androidbootstrap.adapter.MaintenanceAdapter;
import com.innovidio.androidbootstrap.adapter.ServiceDialogAdapter;
import com.innovidio.androidbootstrap.databinding.DialogFormMaintenanceDetailsBinding;
import com.innovidio.androidbootstrap.databinding.DialogMaintenanceDetailsBinding;
import com.innovidio.androidbootstrap.databinding.FragmentMaintainBinding;
import com.innovidio.androidbootstrap.entity.Form;
import com.innovidio.androidbootstrap.entity.FormWithMaintenance;
import com.innovidio.androidbootstrap.entity.Maintenance;
import com.innovidio.androidbootstrap.interfaces.MaintenanceItemClickListener;
import com.innovidio.androidbootstrap.repository.PreferencesRepository;
import com.innovidio.androidbootstrap.viewmodel.FormViewModel;
import com.innovidio.androidbootstrap.viewmodel.MaintenanceViewModel;
import com.innovidio.androidbootstrap.viewmodel.TimeLineViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

import static com.innovidio.androidbootstrap.Constants.SERVICE_FORM;

public class FragmentMaintain extends DaggerFragment implements MaintenanceItemClickListener {

    @Inject
    AppPreferences appPreferences;

    @Inject
    TimeLineViewModel timeLineViewModel;

    @Inject
    MaintenanceViewModel maintenanceViewModel;

    @Inject
    FormViewModel formViewModel;

    @Inject
    PreferencesRepository prefRepo;

    private FragmentMaintainBinding binding;
    private List<FormWithMaintenance> timeLineItemList = new ArrayList<>();
    private MaintenanceAdapter maintenanceAdapter;

    private CustomDeleteDialog  maintenanceDeleteDialog;

    public FragmentMaintain() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_maintain, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeAdapters();
        initializeCardsData();
        formWithMaintenance();

        binding.tvAddMaintenance.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), FormActivity.class);
            intent.putExtra(Constants.ACTIVITY, Constants.SERVICE_FORM);
            startActivity(intent);
        });
    }

    private void initializeAdapters() {
        maintenanceAdapter = new MaintenanceAdapter(getActivity(), this::onMaintenanceClick, timeLineItemList, Constants.FILTERED_ADAPTER);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        binding.rvAddMaintenance.setLayoutManager(layoutManager);
        binding.rvAddMaintenance.setAdapter(maintenanceAdapter);
    }


    private void initializeCardsData(){
        int carId = appPreferences.getInt(AppPreferences.Key.SELECTED_CAR_ID, 1);

        Date curDate =  new Date();
        Date todayDate =  UtilClass.getCurrentDayFrom0AM();
        Date startDate = UtilClass.getCurrentMonthFirstDayDate();
        Date endDate =  UtilClass.getCurrentMonthLastDayDate();


        binding.setPrefData(prefRepo.getPreferences());

        maintenanceViewModel.getLastMaintenance(carId).observe(getActivity(), new Observer<Maintenance>() {
            @Override
            public void onChanged(Maintenance maintenance) {
                if(maintenance!=null) {
                    binding.setMaintenanceData(maintenance);
                }
            }
        });

        maintenanceViewModel.getNextComingMaintenance(carId,todayDate).observe(getActivity(), new Observer<Maintenance>() {
            @Override
            public void onChanged(Maintenance maintenance) {
                if(maintenance!=null) {
                    binding.tvNextMaintenanceValue.setText(maintenance.getMaintenanceName());
                    long diff = maintenance.getNextMaintenanceDate().getTime() - todayDate.getTime();
                    long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

                    binding.tvDaysValue.setText(days + "");
                }
            }
        });


        maintenanceViewModel.getMaintenanceCountBetweenDateRange(carId, todayDate, curDate).observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer!=null)
                    binding.tvTotalMaintenanceValue.setText(integer.toString());
            }
        });

        maintenanceViewModel.getMaintenanceCostBetweenDateRange(carId, todayDate, curDate).observe(getActivity(), new Observer<Long>() {
            @Override
            public void onChanged(Long integer) {
                if(integer!=null)
                    binding.tvTotalCostValue.setText(integer.toString() + " "+ prefRepo.getCurrency());
            }
        });

    }

    private void formWithMaintenance(){
        formViewModel.getAllFormWithMaintenance().observe(this, new Observer<List<FormWithMaintenance>>() {
            @Override
            public void onChanged(List<FormWithMaintenance> formWithMaintenances) {
                if (formWithMaintenances!=null){
                        timeLineItemList.addAll(formWithMaintenances);
                        maintenanceAdapter.updateData(timeLineItemList);
                }
            }
        });
    }

    @Override
    public void onMaintenanceClick(FormWithMaintenance formWithMaintenance) {
        showMaintenanceDialog(formWithMaintenance);
    }

    private void showMaintenanceDialog(FormWithMaintenance formWithMaintenance) {
        Maintenance maintenance = formWithMaintenance.maintenanceList.get(0);
        List<Maintenance> maintenanceList = formWithMaintenance.maintenanceList;
        createMaintenanceDeleteDialog(formWithMaintenance.form);

        DialogFormMaintenanceDetailsBinding maintenanceDetailsBinding;
        maintenanceDetailsBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.dialog_form_maintenance_details, null, false);
        maintenanceDetailsBinding.setFormdata(formWithMaintenance.form);
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

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        ServiceDialogAdapter adapter = new ServiceDialogAdapter(getContext(), maintenanceList);
        maintenanceDetailsBinding.rvServiceDialog.setAdapter(adapter);
        maintenanceDetailsBinding.rvServiceDialog.setLayoutManager(layoutManager);

        maintenanceDetailsBinding.btnEdit.setOnClickListener(view -> {
            UtilClass.startFormActivity(getContext(),SERVICE_FORM);
            exitDialog.dismiss();
        });

        exitDialog.show();
    }

    private void createMaintenanceDeleteDialog(Form form) {

        maintenanceDeleteDialog = new CustomDeleteDialog(getActivity(), "Confirm Delete", "Do you want to delete this 'Maintenance' entry?", "No", "Yes", R.drawable.automate_delete_maintenance_dialog_icon) {
            @Override
            public void onNegativeBtnClick(Dialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void onPositiveBtnClick(Dialog dialog) {
                formViewModel.deleteMaintenanceService(form);
                // todo delete maintenace relvent to form also delete
                dialog.dismiss();
            }
        };
        maintenanceDeleteDialog.createDialog();

    }
}
