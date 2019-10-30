package com.innovidio.androidbootstrap.activity;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

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
import com.innovidio.androidbootstrap.repository.PreferencesRepository;
import com.innovidio.androidbootstrap.viewmodel.PreferencesViewModel;

import java.util.ArrayList;

import javax.inject.Inject;
import dagger.android.support.DaggerAppCompatActivity;

public class UserPreferencesActivity extends DaggerAppCompatActivity {

    @Inject
    PreferencesViewModel preferencesViewModel;

    @Inject
    PreferencesRepository prefRepo;

    private GeneralSpinnerAdapter countriesAdapter;
    private GeneralSpinnerAdapter currenciesAdapter;
    ArrayList<String> countriesList;
    ArrayList<String> currenciesList;
    private ActivityUserPreferencesBinding binding;

    private Preferences preferences = new Preferences();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_user_preferences);

        initializeAdapters();
        spinnerItemSelection();

        // pass one bcz default value already saved against 1
        preferencesViewModel.getPrefById(1).observe(this, new Observer<Preferences>() {
            @Override
            public void onChanged(Preferences pref) {
                if (pref!=null){
                    preferences = pref;
                    initializeViewWithValues();
                }
            }
        });

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

    private void initializeViewWithValues(){
       for (int i=0; i<countriesList.size(); i++){
           if (countriesList.get(i).equalsIgnoreCase(preferences.getCountry())){
               binding.countriesSpinner.setSelection(i);
           }
       }

        for (int i=0; i<currenciesList.size(); i++){
            if (currenciesList.get(i).equalsIgnoreCase(preferences.getCurrency())){
                binding.currencySpinner.setSelection(i);
            }
        }

        if (preferences.isAutoDetect()){
            binding.radioYes.setChecked(true);
            binding.radioNo.setChecked(false);
        }else{
            binding.radioYes.setChecked(false);
            binding.radioNo.setChecked(true);
        }


        if (preferences.getFuelUnit().equals(Constants.LITTERS)){
            binding.radioLitters.setChecked(true);
            binding.radioGallons.setChecked(false);
        }else{
            binding.radioLitters.setChecked(false);
            binding.radioGallons.setChecked(true);
        }

        if (preferences.getSpeedUnit().equals(Constants.KM_HR)){
            binding.radioKmsPerHr.setChecked(true);
            binding.radioMilesPerHr.setChecked(false);
        }else{
            binding.radioKmsPerHr.setChecked(false);
            binding.radioMilesPerHr.setChecked(true);
        }

        if (preferences.getDistanceUnit().equals(Constants.KM)){
            binding.radioKm.setChecked(true);
            binding.radioMiles.setChecked(false);
        }else{
            binding.radioKm.setChecked(false);
            binding.radioMiles.setChecked(true);
        }

        binding.etFuelPricePerUnit.setText(preferences.getFuelUnitPrice()+"");
    }

    private void spinnerItemSelection(){
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
                    preferencesViewModel.updatePreferences(preferences);
                    Toast.makeText(this, "Preferences Updated.", Toast.LENGTH_SHORT).show();
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

        // already set on selection and by default also
//        preferences.setCountry(binding.currencySpinner.getSelectedItem().toString());
//        preferences.setCurrency(binding.currencySpinner.getSelectedItem().toString());

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

        if (binding.rgDistanceUnit.getCheckedRadioButtonId()==binding.radioKm.getId()){
            preferences.setDistanceUnit(Constants.KM);
        }else{
            preferences.setDistanceUnit(Constants.MILES);
        }

        if (binding.rgSpeedUnit.getCheckedRadioButtonId()==binding.radioKmsPerHr.getId()){
            preferences.setSpeedUnit(Constants.KM_HR);
        }else{
            preferences.setSpeedUnit(Constants.M_HR);
        }

        preferences.setFuelUnitPrice(Double.parseDouble(binding.etFuelPricePerUnit.getText().toString()));

        return true;
    }

}
