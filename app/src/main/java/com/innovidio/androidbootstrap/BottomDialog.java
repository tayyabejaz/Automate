package com.innovidio.androidbootstrap;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.innovidio.androidbootstrap.activity.MainActivity;
import com.innovidio.androidbootstrap.databinding.DialogBottomSheetBinding;

public class BottomDialog extends BottomSheetDialog implements View.OnClickListener {
    public Activity context;

    DialogBottomSheetBinding binding;
    public BottomDialog(@NonNull Activity context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_bottom_sheet, null, false);
        setContentView(binding.getRoot());


        binding.fuelUpCardD.setOnClickListener(this);
        binding.addCarCardD.setOnClickListener(this);
        binding.addMaintenanceCardD.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.fuel_up_card_d:
//                ((MainActivity)context).fuel_up_dialog();
//                ((MainActivity)context).seticon();
                dismiss();
                break;

            case R.id.add_car_card_d:
//                ((MainActivity)context).addCarMethod();
                dismiss();
                break;

            case R.id.add_maintenance_card_d:
//                ((MainActivity)context).addMaintenance();
                dismiss();
                break;
        }
    }
}

