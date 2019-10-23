package com.innovidio.androidbootstrap.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.innovidio.androidbootstrap.entity.Preferences;
import com.innovidio.androidbootstrap.entity.User;
import com.innovidio.androidbootstrap.repository.PreferencesRepository;
import com.innovidio.androidbootstrap.repository.UserRepository;

import javax.inject.Inject;

public class PreferencesViewModel extends ViewModel {
    @Inject
    PreferencesRepository preferencesRepository;
    @Inject
    public PreferencesViewModel(PreferencesRepository preferencesRepository) {
        this.preferencesRepository = preferencesRepository;
    }

    public void addPreferences(Preferences preferences) {
        this.preferencesRepository.addPreferences(preferences);
    }

    public void deletePreferences(Preferences preferences) {
        this.preferencesRepository.deletePreferences(preferences);
    }

    public void updatePreferences(Preferences preferences) {
        this.preferencesRepository.updatePreferences(preferences);
    }

    public LiveData<Preferences> getUserById(int id) {
        return this.preferencesRepository.getPreferencesById(id);
    }
}