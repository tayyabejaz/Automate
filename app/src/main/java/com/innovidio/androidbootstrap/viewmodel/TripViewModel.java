package com.innovidio.androidbootstrap.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.innovidio.androidbootstrap.entity.Trip;
import com.innovidio.androidbootstrap.repository.TripRepository;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class TripViewModel extends ViewModel {
    @Inject
    TripRepository tripRepository;
    @Inject
    public TripViewModel(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public void addTrip(Trip trip) {
        this.tripRepository.addTrip(trip);
    }

    public void deleteTrip(Trip trip) {
        this.tripRepository.deleteTrip(trip);
    }

    public void updateTrip(Trip trip) {
        this.tripRepository.updateTrip(trip);
    }

    public LiveData<List<Trip>> getTrips() {
        return this.tripRepository.getAllTrips();
    }

    public LiveData<List<Trip>> getTripByType(String type) {
        return this.tripRepository.getTripByType(type);
    }

    public LiveData<Trip> getTripById(int id) {
        return this.tripRepository.getTripById(id);
    }

    public LiveData<Trip> getLastTrip(int cardId) {
        return this.tripRepository.getLastTrip(cardId);
    }

    public LiveData<Integer> getTripsCount(int carId, Date starDate, Date endDate) {
        return this.tripRepository.getTripsCountBetweenDateRange(carId, starDate, endDate);
    }

    public LiveData<Long> getTripsCoverDistanceBetweenDateRange(int cardId, Date starDate, Date endDate) {
        return this.tripRepository.getDistanceCoveredBetweenDateRange(cardId, starDate, endDate);
    }
}