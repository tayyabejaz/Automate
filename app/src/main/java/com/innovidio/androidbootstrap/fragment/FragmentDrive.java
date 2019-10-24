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
import com.innovidio.androidbootstrap.Utils.UtilClass;
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
            UtilClass.showStartTripDialog(getActivity());
        });
    }

}
