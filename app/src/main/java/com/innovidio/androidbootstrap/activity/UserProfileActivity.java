package com.innovidio.androidbootstrap.activity;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.databinding.ActivityUserProfileBinding;
import com.innovidio.androidbootstrap.entity.User;
import com.innovidio.androidbootstrap.viewmodel.UserViewModel;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class UserProfileActivity extends DaggerAppCompatActivity {

    @Inject
    UserViewModel userViewModel;
    private User userInstance;
    private ActivityUserProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_profile);
        userInstance = new User();
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.rgDrivingLicense.setOnCheckedChangeListener((radioGroup, i) -> {

            switch (i) {
                case R.id.rb_yes:
                    inflateViews(true);
                    break;

                case R.id.rb_no:
                    inflateViews(false);
                    break;
            }

        });

        binding.rgViolation.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i) {
                case R.id.rb_voilation_yes:
                    binding.etNoOfViolation.setVisibility(View.VISIBLE);
                    break;
                case R.id.rb_voilation_no:
                    binding.etNoOfViolation.setVisibility(View.GONE);
                    break;
            }
        });
    }

    private void inflateViews(boolean isVisible) {
        if (isVisible) {
            binding.etExpiryDate.setVisibility(View.VISIBLE);
            binding.cardViolation.setVisibility(View.VISIBLE);
        } else {
            binding.etExpiryDate.setVisibility(View.GONE);
            binding.cardViolation.setVisibility(View.GONE);

        }
    }
}
