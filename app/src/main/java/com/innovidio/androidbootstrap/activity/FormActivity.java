package com.innovidio.androidbootstrap.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.databinding.ActivityFormBinding;
import com.innovidio.androidbootstrap.fragment.AddCarWash;
import com.innovidio.androidbootstrap.fragment.AddFuelUp;
import com.innovidio.androidbootstrap.fragment.AddServices;
import com.innovidio.androidbootstrap.fragment.AddTrip;

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
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_form_activity, new AddCarWash()).commit();
                break;

            case FUEL_UP_FORM:
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_form_activity, new AddFuelUp()).commit();
                break;

            case SERVICE_FORM:
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_form_activity, new AddServices()).commit();
                break;

            case TRIP_FORM:
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_form_activity, new AddTrip()).commit();
                break;
        }
    }
}
