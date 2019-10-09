package com.innovidio.androidbootstrap.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.innovidio.androidbootstrap.db.dao.FuelDao;
import com.innovidio.androidbootstrap.entity.FuelUp;
import com.innovidio.androidbootstrap.entity.Maintenance;
import com.innovidio.androidbootstrap.entity.Trip;
import com.innovidio.androidbootstrap.interfaces.TimeLineItem;
import com.innovidio.androidbootstrap.repository.FuelUpRepository;
import com.innovidio.androidbootstrap.repository.MaintenanceRepository;
import com.innovidio.androidbootstrap.repository.TripRepository;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class TimeLineViewModel extends ViewModel {
    @Inject
    TripRepository tripRepository;
    @Inject
    FuelUpRepository fuelUpRepository;
    @Inject
    MaintenanceRepository maintenanceRepository;

//    public static final String TRIP = "Trip";
//    public static final String FUEL_UP = "Fuel Up";
//    public static final String MAINTENANCE = "Maintenance";
//    public static final String NONE = "none";


    MutableLiveData<List<FuelUp>> fuelUpsLiveData = null;
    MutableLiveData<List<Maintenance>> maintenanceLiveData = null;
    MutableLiveData<List<Trip>> tripsLiveData = null;

    MediatorLiveData<List<TimeLineItem>> timeLineLiveDataMerger = new MediatorLiveData<>();

    @Inject
    public TimeLineViewModel(TripRepository tripRepository, FuelUpRepository fuelUpRepository, MaintenanceRepository maintenanceRepository) {
        this.tripRepository = tripRepository;
        this.fuelUpRepository = fuelUpRepository;
        this.maintenanceRepository =  maintenanceRepository;
    }

    public LiveData<List<Trip>> getTrips() {
        return this.tripRepository.getAllTrips();
    }

    public LiveData<List<FuelUp>> getAllFuelUps() {
        return this.fuelUpRepository.getAllFuelUps();
    }

    public LiveData<List<Maintenance>> getAllMaintenanceService() {
        return this.maintenanceRepository.getAllMaintenanceService();
    }

    public MediatorLiveData<List<TimeLineItem>> getAllTimelineMergerData(){
        this.tripsLiveData =   this.tripRepository.getAllTripsTimeline();
        this.maintenanceLiveData =   this.maintenanceRepository.getAllMaintenanceForTimeLine();
        this.fuelUpsLiveData =   this.fuelUpRepository.getAllFuelUpsForTimeLine();



        //MediatorLiveData liveDataMerger = new MediatorLiveData<>();
        timeLineLiveDataMerger.getValue().addAll(tripsLiveData.getValue());
        timeLineLiveDataMerger.getValue().addAll(maintenanceLiveData.getValue());
        timeLineLiveDataMerger.getValue().addAll(fuelUpsLiveData.getValue());

//        timeLineLiveDataMerger.addSource(tripsLiveData, value -> timeLineLiveDataMerger.setValue(value));
//        timeLineLiveDataMerger.addSource(maintenanceLiveData, value -> timeLineLiveDataMerger.setValue(value));
//        timeLineLiveDataMerger.addSource(fuelUpsLiveData, value -> timeLineLiveDataMerger.setValue(value));
        return timeLineLiveDataMerger;
    }

}