package com.innovidio.androidbootstrap.fragment;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.adapter.GeneralSpinnerAdapter;
import com.innovidio.androidbootstrap.databinding.FragmentAddNewCarBinding;
import com.innovidio.androidbootstrap.interfaces.ActivityBtnClickListener;
import com.innovidio.androidbootstrap.interfaces.FragmentClickListener;
import com.innovidio.androidbootstrap.viewmodel.CarQueryViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class FragmentAddNewCar extends DaggerFragment implements ActivityBtnClickListener {


    private FragmentClickListener fragmentClickListener;
    private FragmentAddNewCarBinding binding;
    @Inject
    CarQueryViewModel carQueryViewModel;
    private ArrayList<String> makerData = new ArrayList<>();
    private ArrayList<String> modelData = new ArrayList<>();
    private ArrayList<String> subModelData = new ArrayList<>();
    private GeneralSpinnerAdapter adapterMaker;
    private GeneralSpinnerAdapter adapterModel;
    private GeneralSpinnerAdapter adapterSubmodel;
    private String year, make, model, submodel;

    public FragmentAddNewCar(FragmentClickListener listener) {
        // Required empty public constructor
        this.fragmentClickListener = listener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_new_car, container, false);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeAdapters();

        binding.btnCustomCar.setOnClickListener(view1 -> {
            fragmentClickListener.onCustomFragmentClick();
        });


        binding.spinnerYearOfManufacture.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                year = adapterView.getItemAtPosition(i).toString();
                if (i != 0) {
                    getCarMakes(year);

                    if (year != null) {
                        binding.spinnerMakeOfCar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                make = adapterView.getItemAtPosition(i).toString();
                                if (make!=null) {
                                    getCarModelsByYearAndMake(year, make);
                                        binding.spinnerModelOfCar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                model = adapterView.getItemAtPosition(i).toString();
                                                if (model != null)
                                                    getCarTrimsByYearMakeModel(year, make, model);
                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> adapterView) {

                                            }
                                        });
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        if (model != null) {
            binding.spinnerSubModelOfCar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    submodel = adapterView.getItemAtPosition(i).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }

    private void initializeAdapters() {
        adapterMaker = new GeneralSpinnerAdapter(getActivity(), makerData);
        adapterModel = new GeneralSpinnerAdapter(getActivity(), modelData);
        adapterSubmodel = new GeneralSpinnerAdapter(getActivity(), subModelData);
    }


    private void getCarMakes(String year) {
        carQueryViewModel.getCarMakesByYear(year).observe(this, carMakesByYears -> {
            if (carMakesByYears != null && carMakesByYears.size() > 0) {
                Log.e("FromFragment", "CarMakesByYear: " + carMakesByYears.get(0).getMakeDisplay());
                makerData.clear();
                for (int i = 0; i < carMakesByYears.size(); i++) {
                    makerData.add(carMakesByYears.get(i).getMakeDisplay());
                }
                adapterMaker.notifyDataSetChanged();
                binding.spinnerMakeOfCar.setAdapter(adapterMaker);

            }
        });
    }

    private void getCarModelsByYearAndMake(String year, String make) {
        carQueryViewModel.getCarModelsByYearAndMake(year, make).observe(this, carModelNames -> {
            if (carModelNames != null) {
                modelData.clear();
                Log.e("FromFragment", "CarModelName: " + carModelNames.get(0).getModelName());
                for (int i = 0; i < carModelNames.size(); i++) {
                    modelData.add(carModelNames.get(i).getModelName());
                }
                adapterModel.notifyDataSetChanged();
                binding.spinnerModelOfCar.setAdapter(adapterModel);
            }
        });
    }

    private void getCarTrimsByYearMakeModel(String year, String make, String subModel) {
        carQueryViewModel.getCarTrimsByYearMakeModel(year, make, subModel).observe(this, carTrimsInfos -> {
            if (carTrimsInfos != null && carTrimsInfos.size() > 0) {
                Log.e("FromFragment", "CarTrimsInfo: " + carTrimsInfos.get(0).getModelEngineCc());
                subModelData.clear();
                for (int i = 0; i < carTrimsInfos.size(); i++) {
                    subModelData.add(carTrimsInfos.get(0).getModelTrim());
                }
                adapterSubmodel.notifyDataSetChanged();
                binding.spinnerSubModelOfCar.setAdapter(adapterSubmodel);
            }
        });
    }

    @Override
    public void onSubmitButtonClick(Context context) {
        Toast.makeText(context, "Add new Car fragment", Toast.LENGTH_SHORT).show();
    }
}
