package com.innovidio.androidbootstrap.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.databinding.ActivityUserPreferencesBinding;

public class UserPreferencesActivity extends AppCompatActivity {

    private ActivityUserPreferencesBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_user_preferences);
    }

    @Override
    protected void onStart() {
        super.onStart();

        binding.preferenceBackButton.setOnClickListener(view -> {
            finish();
        });
    }
}
