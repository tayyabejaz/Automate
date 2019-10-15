package com.innovidio.androidbootstrap.fragment;


import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.adapter.TimelineAdapter;
import com.innovidio.androidbootstrap.databinding.FragmentMainDashboardBinding;
import com.innovidio.androidbootstrap.di.viewmodel.ViewModelProviderFactory;
import com.innovidio.androidbootstrap.entity.models.TimeLine;
import com.innovidio.androidbootstrap.interfaces.TimeLineItem;
import com.innovidio.androidbootstrap.viewmodel.TimeLineViewModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainDashboardFragment extends Fragment {

    @Inject
    ViewModelProviderFactory providerFactory;
    private TimelineAdapter timelineAdapter;
    private FragmentMainDashboardBinding binding;
    private List<TimeLineItem> dataList;

    TimeLineViewModel timeLineViewModel = null;

    public MainDashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        timeLineViewModel = new ViewModelProvider(this, providerFactory).get(TimeLineViewModel.class);
        initData();
        timelineAdapter = new TimelineAdapter(getContext(), dataList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.rvMainFragment.setLayoutManager(layoutManager);
        binding.rvMainFragment.setAdapter(timelineAdapter);

    }

    private void initData() {
        Date currentTime = Calendar.getInstance().getTime();

//        TimeLineItem tdata = new TimeLineItem(0, currentTime, "Lahore", 20000, "Fuel", 35200, 35220, "35250", "Washed the car") {
//            @Override
//            public Date getInsertDateTime() {
//                return null;
//            }
//
//            @Override
//            public Type getType() {
//                return null;
//            }
//        };
//        dataList = new ArrayList<>();
//        dataList.add(tdata);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_dashboard, container, false);
        View v = binding.getRoot();
        binding.setFirstFragmentData(this);
        // Inflate the layout for this fragment
        return v;
    }

}
