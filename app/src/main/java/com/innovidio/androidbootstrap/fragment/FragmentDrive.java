package com.innovidio.androidbootstrap.fragment;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.innovidio.androidbootstrap.AppPreferences;
import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.dashboard.SpeedDashboardActivity;
import com.innovidio.androidbootstrap.databinding.DialogDriveSelectionBinding;
import com.innovidio.androidbootstrap.databinding.FragmentDriveBinding;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDrive extends Fragment {

    @Inject
    AppPreferences appPreferences;

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
            showStartTripDialog();
        });
    }


    private void startDrive() {
        Intent i = new Intent(getContext(), SpeedDashboardActivity.class);
        startActivity(i);
    }

    private void showStartTripDialog() {
        DialogDriveSelectionBinding dialogBinding;
        dialogBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.dialog_drive_selection, null, false);
        View dialogView = dialogBinding.getRoot();


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final AlertDialog exitDialog = dialogBuilder.create();
        exitDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        exitDialog.setView(dialogView);
        exitDialog.show();

        dialogBinding.numberpickerSpeedLimit.setMinValue(60);
        dialogBinding.numberpickerSpeedLimit.setMaxValue(240);
        dialogBinding.numberpickerSpeedLimit.computeScroll();
        dialogBinding.numberpickerSpeedLimit.setFormatter(i -> String.format("%03d kmph", i));

        dialogBinding.numberpickerSpeedLimit.setOnValueChangedListener((numberPicker, i, i1) -> {
            AppPreferences.SPEED_LIMIT = "" + i1;
        });

        dialogBinding.numberpickerSpeedLimit.setOnValueChangedListener((numberPicker, i, i1) -> {
            dialogBinding.numberpickerSpeedLimit.setValue((i1 < i) ? i - 10 : i + 10);
        });

        dialogBinding.btnCommercial.setOnClickListener(view -> {
            AppPreferences.TRIP_TYPE = "Commercial";
            dialogBinding.btnCommercial.setSelected(true);
            dialogBinding.btnCommercial.setTextColor(getActivity().getResources().getColor(R.color.whiteColor));

            dialogBinding.btnPersonal.setSelected(false);
            dialogBinding.btnPersonal.setTextColor(getActivity().getResources().getColor(R.color.blackColor));

            dialogBinding.btnOfficial.setSelected(false);
            dialogBinding.btnOfficial.setTextColor(getActivity().getResources().getColor(R.color.blackColor));

            dialogBinding.btnCustom.setSelected(false);
            dialogBinding.btnCustom.setTextColor(getActivity().getResources().getColor(R.color.blackColor));
        });

        dialogBinding.btnPersonal.setOnClickListener(view -> {
            AppPreferences.TRIP_TYPE = "Personal";
            dialogBinding.btnCommercial.setSelected(false);
            dialogBinding.btnCommercial.setTextColor(getActivity().getResources().getColor(R.color.blackColor));

            dialogBinding.btnPersonal.setSelected(true);
            dialogBinding.btnPersonal.setTextColor(getActivity().getResources().getColor(R.color.whiteColor));

            dialogBinding.btnOfficial.setSelected(false);
            dialogBinding.btnOfficial.setTextColor(getActivity().getResources().getColor(R.color.blackColor));

            dialogBinding.btnCustom.setSelected(false);
            dialogBinding.btnCustom.setTextColor(getActivity().getResources().getColor(R.color.blackColor));
        });

        dialogBinding.btnOfficial.setOnClickListener(view -> {
            AppPreferences.TRIP_TYPE = "Official";
            dialogBinding.btnCommercial.setSelected(false);
            dialogBinding.btnCommercial.setTextColor(getActivity().getResources().getColor(R.color.blackColor));

            dialogBinding.btnPersonal.setSelected(false);
            dialogBinding.btnPersonal.setTextColor(getActivity().getResources().getColor(R.color.blackColor));

            dialogBinding.btnOfficial.setSelected(true);
            dialogBinding.btnOfficial.setTextColor(getActivity().getResources().getColor(R.color.whiteColor));

            dialogBinding.btnCustom.setSelected(false);
            dialogBinding.btnCustom.setTextColor(getActivity().getResources().getColor(R.color.blackColor));
        });

        dialogBinding.btnCustom.setOnClickListener(view -> {
            AppPreferences.TRIP_TYPE = "Custom";
            dialogBinding.btnCommercial.setSelected(false);
            dialogBinding.btnCommercial.setTextColor(getActivity().getResources().getColor(R.color.blackColor));

            dialogBinding.btnPersonal.setSelected(false);
            dialogBinding.btnPersonal.setTextColor(getActivity().getResources().getColor(R.color.blackColor));

            dialogBinding.btnOfficial.setSelected(false);
            dialogBinding.btnOfficial.setTextColor(getActivity().getResources().getColor(R.color.blackColor));

            dialogBinding.btnCustom.setSelected(true);
            dialogBinding.btnCustom.setTextColor(getActivity().getResources().getColor(R.color.whiteColor));
        });

        dialogBinding.btnStartDrive.setOnClickListener(view -> {
            startDrive();
        });

        dialogBinding.switchDriveDetect.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                AppPreferences.AUTO_DRIVE_DETECT = true;
            } else {
                AppPreferences.AUTO_DRIVE_DETECT = false;
            }
        });
    }


}
