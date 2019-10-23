package com.innovidio.androidbootstrap.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.databinding.ActivityFormBinding;
import com.innovidio.androidbootstrap.fragment.FragmentAddCarWash;
import com.innovidio.androidbootstrap.fragment.FragmentAddFuelUp;
import com.innovidio.androidbootstrap.fragment.FragmentAddServices;
import com.innovidio.androidbootstrap.fragment.FragmentAddTrip;

import static com.innovidio.androidbootstrap.Constants.ACTIVITY;
import static com.innovidio.androidbootstrap.Constants.CAR_WASH_FORM;
import static com.innovidio.androidbootstrap.Constants.FUEL_UP_FORM;
import static com.innovidio.androidbootstrap.Constants.SERVICE_FORM;
import static com.innovidio.androidbootstrap.Constants.TRIP_FORM;

public class FormActivity extends AppCompatActivity {

    ActivityFormBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_form);

        initializeFragment(getIntent().getStringExtra(ACTIVITY));

    }

    private void initializeFragment(String fragmentName) {
        switch (fragmentName) {
            case CAR_WASH_FORM:
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_form_activity, new FragmentAddCarWash()).commit();
                break;

            case FUEL_UP_FORM:
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_form_activity, new FragmentAddFuelUp()).commit();
                break;

            case SERVICE_FORM:
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_form_activity, new FragmentAddServices()).commit();
                break;

            case TRIP_FORM:
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_form_activity, new FragmentAddTrip()).commit();
                break;
        }
    }
}
