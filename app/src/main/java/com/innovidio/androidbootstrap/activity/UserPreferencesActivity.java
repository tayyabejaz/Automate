package com.innovidio.androidbootstrap.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.innovidio.androidbootstrap.Constants;
import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.Utils.UtilClass;
import com.innovidio.androidbootstrap.adapter.GeneralSpinnerAdapter;
import com.innovidio.androidbootstrap.databinding.ActivityUserPreferencesBinding;
import com.innovidio.androidbootstrap.entity.Preferences;
import com.innovidio.androidbootstrap.viewmodel.PreferencesViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.DaggerActivity;

public class UserPreferencesActivity extends DaggerActivity {

    @Inject
    PreferencesViewModel preferencesViewModel;

    private GeneralSpinnerAdapter countriesAdapter;
    private GeneralSpinnerAdapter currenciesAdapter;
    ArrayList<String> countriesList;
    ArrayList<String> currenciesList;
    private ActivityUserPreferencesBinding binding;

    Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_user_preferences);


        initializeAdapters();
        spinnerItemSelection();

        preferences =  new Preferences();
        preferences.setAutoDetect(false);
        preferences.setFuelUnit(Constants.LITTERS);
        preferences.setDistanceUnit(Constants.KM);
        preferences.setSpeedUnit(Constants.KM_HR);

    }

    private void initializeAdapters(){
        countriesList = UtilClass.getCountriesListFromLocale();
        countriesAdapter = new GeneralSpinnerAdapter(this,countriesList);
        binding.countriesSpinner.setAdapter(countriesAdapter);
        countriesAdapter.notifyDataSetChanged();


        currenciesList = UtilClass.getCurrenciesList();
        currenciesAdapter = new GeneralSpinnerAdapter(this,currenciesList);
        binding.currencySpinner.setAdapter(currenciesAdapter);
        currenciesAdapter.notifyDataSetChanged();

    }

    private void spinnerItemSelection(){
        String country = UtilClass.getMyCountry();
        for(int i=0; i<countriesList.size(); i++){
            if (countriesList.get(i).equals(country)) {
                binding.countriesSpinner.setSelection(i);
            }
        }
        countriesAdapter.notifyDataSetChanged();

        binding.countriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               preferences.setCountry(countriesList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(UserPreferencesActivity.this, "Please Choose a Country", Toast.LENGTH_SHORT).show();
            }
        });



        binding.currencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                preferences.setCurrency(currenciesList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(UserPreferencesActivity.this, "Please Choose a Currency", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        binding.preferenceBackButton.setOnClickListener(view -> {
            finish();
        });

        binding.userpreferenceDoneButton.setOnClickListener(view -> {
                if (getFormValidation()){
                    preferencesViewModel.addPreferences(preferences);
                    Toast.makeText(this, "Preferences Saved", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(this, "Please Select All Values", Toast.LENGTH_SHORT).show();
                }
        });
    }

    private boolean getFormValidation(){
        if (TextUtils.isEmpty(binding.etFuelPricePerUnit.getText())) {
            binding.etFuelPricePerUnit.setError("Fuel price required");
            return false;
        }

        preferences.setCountry(binding.currencySpinner.getSelectedItem().toString());
        preferences.setCurrency(binding.currencySpinner.getSelectedItem().toString());

        if (binding.rgDriveDetect.getCheckedRadioButtonId()==binding.radioYes.getId()){
            preferences.setAutoDetect(true);
        }else{
            preferences.setAutoDetect(false);
        }

        if (binding.rgFuelUnit.getCheckedRadioButtonId()==binding.radioLitters.getId()){
            preferences.setFuelUnit(Constants.LITTERS);
        }else{
            preferences.setFuelUnit(Constants.GALLONS);
        }

        if (binding.rgSpeedUnit.getCheckedRadioButtonId()==binding.radioKmsPerHr.getId()){
            preferences.setSpeedUnit(Constants.KM_HR);
        }else{
            preferences.setSpeedUnit(Constants.M_HR);
        }


        if (binding.rgDistanceUnit.getCheckedRadioButtonId()==binding.radioKm.getId()){
            preferences.setDistanceUnit(Constants.KM);
        }else{
            preferences.setDistanceUnit(Constants.MILES);
        }

        preferences.setFuelUnitPrice(Double.parseDouble(binding.etFuelPricePerUnit.getText().toString()));

        return true;
    }

}
