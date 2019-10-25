package com.innovidio.androidbootstrap.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.databinding.ActivityPrivacyPolicyBinding;

public class PrivacyPolicyActivity extends AppCompatActivity {

    private ActivityPrivacyPolicyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_privacy_policy);
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.btnPrivacyPolicyBack.setOnClickListener(view -> {
            finish();
        });

        //TODO: Update with the Privacy policy text
        binding.tvPrivacypolicy.setText(this.getResources().getString(R.string.app_name));

    }
}
