package com.innovidio.androidbootstrap.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.innovidio.androidbootstrap.Constants;
import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.activity.FormActivity;
import com.innovidio.androidbootstrap.adapter.TimelineAdapter;
import com.innovidio.androidbootstrap.databinding.FragmentMaintainBinding;
import com.innovidio.androidbootstrap.interfaces.TimeLineItem;
import com.innovidio.androidbootstrap.viewmodel.TimeLineViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class FragmentMaintain extends DaggerFragment {

    @Inject
    TimeLineViewModel timeLineViewModel;

    private FragmentMaintainBinding binding;
    private List<TimeLineItem> timeLineItemList = new ArrayList<>();
    private TimelineAdapter timelineAdapter;

    public FragmentMaintain() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_maintain, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeAdapters();
        timeLineFilteredData();

        binding.tvAddMaintenance.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), FormActivity.class);
            intent.putExtra(Constants.ACTIVITY, Constants.SERVICE_FORM);
            startActivity(intent);
        });
    }

    private void initializeAdapters() {
        timelineAdapter = new TimelineAdapter(getActivity(), null, timeLineItemList, Constants.FILTERED_ADAPTER);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        binding.rvAddMaintenance.setLayoutManager(layoutManager);
    }

    private void timeLineFilteredData() {
        timeLineViewModel.getFilteredTimelineMergerData(1, false, false, true, true).observe(this, timeLineItems -> {
            if (timeLineItems != null && timeLineItems.size() > 0) {
                timeLineItemList.addAll(timeLineItems);
                timelineAdapter.updateData(timeLineItemList);
                binding.rvAddMaintenance.setAdapter(timelineAdapter);

            }
        });
    }

}
