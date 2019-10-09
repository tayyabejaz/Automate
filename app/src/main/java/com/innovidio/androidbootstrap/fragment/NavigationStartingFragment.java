package com.innovidio.androidbootstrap.fragment;


import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.adapter.SpinnerAdapter;
import com.innovidio.androidbootstrap.databinding.FragmentNavigationStartingBinding;
import com.innovidio.androidbootstrap.entity.models.SpinnerDataModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationStartingFragment extends Fragment{

    private FragmentNavigationStartingBinding binding;
    private ArrayList<SpinnerDataModel> dataList;
    private SpinnerAdapter mAdapter;
    private NavController navigationController;
    public NavigationStartingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_navigation_starting, container,false);
        View view = binding.getRoot();
        binding.setMainSpinnerData(this);
        return view;
    }






}
