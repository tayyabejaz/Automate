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

public class FormActivity extends AppCompatActivity {

    ActivityFormBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_form);

        initializeFragment(getIntent().getStringExtra("activity"));

    }

    private void initializeFragment(String fragmentName) {
        switch (fragmentName) {
            case "carwash":
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_form_activity, new AddCarWash()).commit();
                break;

            case "fuelup":
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_form_activity, new AddFuelUp()).commit();
                break;

            case "service":
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_form_activity, new AddServices()).commit();
                break;

            case "trip":
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_form_activity, new AddTrip()).commit();
                break;
        }
    }
}
