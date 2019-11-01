package com.innovidio.androidbootstrap.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.Utils.UtilClass;
import com.innovidio.androidbootstrap.databinding.ActivityUserProfileBinding;
import com.innovidio.androidbootstrap.entity.User;
import com.innovidio.androidbootstrap.viewmodel.UserViewModel;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class UserProfileActivity extends DaggerAppCompatActivity {

    @Inject
    UserViewModel userViewModel;

    private User user;
    private ActivityUserProfileBinding binding;
    private Calendar calenderInstance = Calendar.getInstance();
    private String sDate;
    private boolean isFirstTime;
    private boolean haveDrivingLicense, anyVoliation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_profile);
        user = new User();
    }

    @Override
    protected void onStart() {
        super.onStart();

        userViewModel.getUserById(1).observe(this, user -> {
            if (user != null) {
                isFirstTime = false;
            } else {
                isFirstTime = true;
            }
        });

        binding.rgDrivingLicense.setOnCheckedChangeListener((radioGroup, i) -> {

            switch (i) {
                case R.id.rb_yes:
                    haveDrivingLicense = true;
                    inflateViews(true);
                    break;

                case R.id.rb_no:
                    haveDrivingLicense = false;
                    inflateViews(false);
                    break;
            }

        });

        binding.rgViolation.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i) {
                case R.id.rb_voilation_yes:
                    anyVoliation = true;
                    binding.etNoOfViolation.setVisibility(View.VISIBLE);
                    break;
                case R.id.rb_voilation_no:
                    anyVoliation = false;
                    binding.etNoOfViolation.setVisibility(View.GONE);
                    break;
            }
        });

        DatePickerDialog.OnDateSetListener date = (datePicker, i, i1, i2) -> {

            calenderInstance.set(Calendar.YEAR, i);
            calenderInstance.set(Calendar.MONTH, i1);
            calenderInstance.set(Calendar.DAY_OF_MONTH, i2);
            sDate = UtilClass.updateDate(calenderInstance, binding.etExpiryDate);
        };

        binding.etExpiryDate.setOnClickListener(view -> {
            UtilClass.showDatePicker(this, calenderInstance, date);
        });

        binding.btnUserDone.setOnClickListener(view -> {
            if (checkEmptyEntries()) {
                if (isFirstTime) {
                    userViewModel.addUser(user);
                    Log.d("TAYYAB", "User Added Successfully");
                } else {
                    userViewModel.updateUser(user);
                }
            } else {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkEmptyEntries() {
        if (TextUtils.isEmpty(binding.etUserName.getText())) {
            binding.etUserName.setError("User name is Required");
            return false;
        } else if (TextUtils.isEmpty(binding.etUserCountry.getText())) {
            binding.etUserCountry.setError("Country is required");
            return false;
        } else if (TextUtils.isEmpty(binding.etUserEmail.getText())) {
            binding.etUserEmail.setError("Email is required");
            return false;
        }

        user.setId(1);
        user.setUserImageUrl("");
        user.setName(binding.etUserName.getText().toString());
        user.setCountry(binding.etUserCountry.getText().toString());
        user.setCity("");
        user.setEmail(binding.etUserEmail.getText().toString());
        if (haveDrivingLicense) {
            if (TextUtils.isEmpty(binding.etExpiryDate.getText())) {
                binding.etExpiryDate.setError(" Enter the License Expiry Date ");
                return false;
            }
            user.setDrivingLicense(true);
            Date convertedDate = UtilClass.convertToDate(sDate);
            if (convertedDate == null) {
                convertedDate = new Date();
            }
            user.setLicenseExpiryDate(convertedDate);
        }

        if (anyVoliation) {
            user.setAnyViolation(true);
            if (TextUtils.isEmpty(binding.etNoOfViolation.getText())) {
                binding.etNoOfViolation.setError("Enter No. of Violation");
                return false;
            }
            user.setNoOfViolations(Integer.parseInt(binding.etNoOfViolation.getText().toString()));
        }

        return true;
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
