package com.innovidio.androidbootstrap.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.innovidio.androidbootstrap.entity.FuelUp;
import com.innovidio.androidbootstrap.entity.Maintenance;
import com.innovidio.androidbootstrap.entity.Trip;
import com.innovidio.androidbootstrap.repository.FuelUpRepository;
import com.innovidio.androidbootstrap.repository.MaintenanceRepository;
import com.innovidio.androidbootstrap.repository.TripRepository;

import java.util.List;

import javax.inject.Inject;

public class TimeLineViewModel extends ViewModel {
    @Inject
    TripRepository tripRepository;
    @Inject
    FuelUpRepository fuelUpRepository;
    @Inject
    MaintenanceRepository maintenanceRepository;

    public static final String TRIP = "Trip";
    public static final String FUEL_UP = "Fuel Up";
    public static final String MAINTENANCE = "Maintenance";
    public static final String NONE = "none";


    LiveData<List<FuelUp>> fuelUpsLiveData = null;
    LiveData<List<Maintenance>> maintenanceLiveData = null;
    LiveData<List<Trip>> tripsLiveData = null;

    MediatorLiveData liveDataMerger = new MediatorLiveData<>();

    @Inject
    public TimeLineViewModel(TripRepository tripRepository, FuelUpRepository fuelUpRepository, MaintenanceRepository maintenanceRepository) {
        this.tripRepository = tripRepository;
        this.fuelUpRepository = fuelUpRepository;
        this.maintenanceRepository =  maintenanceRepository;
    }

    public LiveData<List<Trip>> getTrips() {
        this.tripsLiveData =   this.tripRepository.getAllTrips();
        return this.tripsLiveData;
    }

    public LiveData<List<FuelUp>> getAllFuelUps() {
        return this.fuelUpRepository.getAllFuelUps();
    }

    public LiveData<List<Maintenance>> getAllMaintenanceService() {
        return this.maintenanceRepository.getAllMaintenanceService();
    }

    public void getAllTimeline(){
        this.tripsLiveData =   this.tripRepository.getAllTrips();
        this.maintenanceLiveData =   this.maintenanceRepository.getAllMaintenanceService();
        this.fuelUpsLiveData =   this.fuelUpRepository.getAllFuelUps();


       // MediatorLiveData liveDataMerger = new MediatorLiveData<>();
//        liveDataMerger.addSource(liveData1, value -> liveDataMerger.setValue(value));
//        liveDataMerger.addSource(liveData2, value -> liveDataMerger.setValue(value));
    }

}