package com.innovidio.androidbootstrap.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.innovidio.androidbootstrap.entity.Trip;
import com.innovidio.androidbootstrap.repository.TripRepository;

import java.util.List;

import javax.inject.Inject;

public class TripViewModel extends ViewModel {
    @Inject
    TripRepository tripRepository;
    @Inject
    public TripViewModel(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public LiveData<List<Trip>> getTrips() {
        return this.tripRepository.getAllTrips();
    }
}