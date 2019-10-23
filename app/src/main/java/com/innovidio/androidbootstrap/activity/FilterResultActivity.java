package com.innovidio.androidbootstrap.activity;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.innovidio.androidbootstrap.Constants;
import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.adapter.TimelineAdapter;
import com.innovidio.androidbootstrap.databinding.ActivityFilterResultBinding;
import com.innovidio.androidbootstrap.interfaces.TimeLineItem;
import com.innovidio.androidbootstrap.viewmodel.TimeLineViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class FilterResultActivity extends DaggerAppCompatActivity {

    @Inject
    TimeLineViewModel timeLineViewModel;

    private ActivityFilterResultBinding binding;
    private List<TimeLineItem> timeLineItemList = new ArrayList<>();
    private TimelineAdapter timelineAdapter;
    private boolean trips, fuelups, maintenance, carwash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter_result);
        initializeAdapters();
        getIntentData();
        timeLineFilteredData();
    }

    @Override
    protected void onStart() {
        super.onStart();

        binding.ivFilterBackButton.setOnClickListener(view -> {
            finish();
        });
    }

    private void initializeAdapters() {
        timelineAdapter = new TimelineAdapter(this, null, timeLineItemList, Constants.FILTERED_ADAPTER);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        binding.rvFilterResults.setLayoutManager(layoutManager);
    }

    private void getIntentData() {
        fuelups = getIntent().getBooleanExtra(Constants.FILTER_FUEL_UPS, false);
        trips = getIntent().getBooleanExtra(Constants.FILTER_TRIPS, false);
        maintenance = getIntent().getBooleanExtra(Constants.FILTER_MAINTENANCE, false);
        carwash = getIntent().getBooleanExtra(Constants.FILTER_CARWASH, false);
    }

    private void timeLineFilteredData() {
        timeLineViewModel.getFilteredTimelineMergerData(1, trips, fuelups, maintenance, carwash).observe(this, timeLineItems -> {
            if (timeLineItems != null && timeLineItems.size() > 0) {
                timeLineItemList.addAll(timeLineItems);
                timelineAdapter.updateData(timeLineItemList);
                binding.rvFilterResults.setAdapter(timelineAdapter);

            }
        });
    }
}
