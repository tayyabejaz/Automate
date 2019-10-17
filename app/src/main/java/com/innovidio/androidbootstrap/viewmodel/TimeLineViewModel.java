package com.innovidio.androidbootstrap.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.innovidio.androidbootstrap.Utils.Sorting;
import com.innovidio.androidbootstrap.db.dao.FuelDao;
import com.innovidio.androidbootstrap.entity.FuelUp;
import com.innovidio.androidbootstrap.entity.Maintenance;
import com.innovidio.androidbootstrap.entity.Trip;
import com.innovidio.androidbootstrap.interfaces.TimeLineItem;
import com.innovidio.androidbootstrap.repository.FuelUpRepository;
import com.innovidio.androidbootstrap.repository.MaintenanceRepository;
import com.innovidio.androidbootstrap.repository.TripRepository;

import java.util.ArrayList;
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

    // ? extends for generic data type with same parent


    LiveData<List<FuelUp>> fuelUpsLiveData = null;
    LiveData<List<Maintenance>> maintenanceLiveData = null;
    LiveData<List<Trip>> tripsLiveData = null;

   // MutableLiveData<List<? extends TimeLineItem>> timeLineList = new MutableLiveData<>();

    MediatorLiveData<List<TimeLineItem>> timeLineLiveDataMerger = new MediatorLiveData<>();

    @Inject
    public TimeLineViewModel(TripRepository tripRepository, FuelUpRepository fuelUpRepository, MaintenanceRepository maintenanceRepository) {
        this.tripRepository = tripRepository;
        this.fuelUpRepository = fuelUpRepository;
        this.maintenanceRepository = maintenanceRepository;

        this.tripsLiveData = this.tripRepository.getAllTripsLiveDataTimeline();
        this.maintenanceLiveData = this.maintenanceRepository.getAllMaintenanceForTimeLine();
        this.fuelUpsLiveData = this.fuelUpRepository.getAllFuelUpsForTimeLine();

//        timeLineList.addAll(tripsLiveData);
//        timeLineList.addAll(tripsLiveData);
//        timeLineList.addAll(tripsLiveData);

//        MediatorLiveData liveDataMerger = new MediatorLiveData<>();
//        timeLineLiveDataMerger.getValue().addAll(tripsLiveData.getValue());
//        timeLineLiveDataMerger.getValue().addAll(maintenanceLiveData.getValue());
//        timeLineLiveDataMerger.getValue().addAll(fuelUpsLiveData.getValue());


     //   timeLineLiveDataMerger.addSource(maintenanceLiveData, value -> timeLineLiveDataMerger.setValue(value));
     //   timeLineLiveDataMerger.addSource(fuelUpsLiveData, value -> timeLineLiveDataMerger.setValue(value));
      //  timeLineLiveDataMerger.addSource(tripsLiveData, value -> timeLineLiveDataMerger.setValue(value));
        getAllTimelineData(1);
    }

    public String getName(){
        return "hello";
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

    public MediatorLiveData<List<TimeLineItem>> getAllTimelineMergerData() {

        return timeLineLiveDataMerger;
    }

    public void getAllTimelineData(int carId ) {
        List<Trip> trips = tripRepository.getAllTripsTimeline(carId);
        List<Maintenance> maintenances = maintenanceRepository.getAllMaintenanceTimeLine(carId);
        List<FuelUp> fuelUps = fuelUpRepository.getAllFuelUpsTimeLine(carId);
        List<TimeLineItem> timeLineItems =  new ArrayList<>();
        timeLineItems.addAll(trips);
        timeLineItems.addAll(maintenances);
        timeLineItems.addAll(fuelUps);
        timeLineItems = Sorting.sortList(timeLineItems);
        List<TimeLineItem> finalTimeLineItems = timeLineItems;
        timeLineLiveDataMerger.addSource(maintenanceLiveData, value -> timeLineLiveDataMerger.setValue(finalTimeLineItems));
    }


}