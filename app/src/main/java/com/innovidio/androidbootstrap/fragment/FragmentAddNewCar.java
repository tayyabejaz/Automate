package com.innovidio.androidbootstrap.fragment;


import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.innovidio.androidbootstrap.entity.Car;
import com.innovidio.androidbootstrap.interfaces.ActivityBtnClickListener;
import com.innovidio.androidbootstrap.interfaces.FragmentClickListener;
import com.innovidio.androidbootstrap.viewmodel.CarQueryViewModel;
import com.innovidio.androidbootstrap.viewmodel.CarViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class FragmentAddNewCar extends DaggerFragment implements ActivityBtnClickListener {

    @Inject
    CarViewModel carViewModel;
    @Inject
    CarQueryViewModel carQueryViewModel;

    private Car car = new Car();
    private FragmentClickListener fragmentClickListener;
    private FragmentAddNewCarBinding binding;
    private ArrayList<String> makerData = new ArrayList<>();
    private ArrayList<String> modelData = new ArrayList<>();
    private ArrayList<String> subModelData = new ArrayList<>();
    private GeneralSpinnerAdapter adapterMaker;
    private GeneralSpinnerAdapter adapterModel;
    private GeneralSpinnerAdapter adapterSubmodel;
    private String year, make, model, submodel;
    private boolean isEmpty;

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
                Log.e("TAYYAB", "YEAR: " + year);
                isEmpty = false;
                if (i != 0) {
                    getCarMakes(year);

                    if (year != null) {
                        binding.spinnerMakeOfCar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                make = adapterView.getItemAtPosition(i).toString();
                                Log.e("TAYYAB", "Make: " + make);
                                isEmpty = false;
                                if (make != null) {
                                    getCarModelsByYearAndMake(year, make);
                                    binding.spinnerModelOfCar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                            model = adapterView.getItemAtPosition(i).toString();
                                            Log.e("TAYYAB", "MODEL: " + model);
                                            isEmpty = false;
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
                    Log.e("TAYYAB", "SUBMODEL: " + submodel);
                    isEmpty = false;
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
                make = makerData.get(0);
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
                model = modelData.get(0);
            }
        });
    }

    private void getCarTrimsByYearMakeModel(String year, String make, String model) {
        carQueryViewModel.getCarTrimsByYearMakeModel(year, make, model).observe(this, carTrimsInfos -> {
            if (carTrimsInfos != null && carTrimsInfos.size() > 0) {
                Log.e("FromFragment", "CarTrimsInfo: " + carTrimsInfos.get(0).getModelEngineCc());
                subModelData.clear();
                for (int i = 0; i < carTrimsInfos.size(); i++) {
                    subModelData.add(carTrimsInfos.get(0).getModelTrim());
                }
                adapterSubmodel.notifyDataSetChanged();
                binding.spinnerSubModelOfCar.setAdapter(adapterSubmodel);
                submodel = subModelData.get(0);
            }
        });
    }

    private boolean checkEmptyEnteries() {

        if (TextUtils.isEmpty(binding.etCarRegNo.getText())) {
            binding.etCarRegNo.setError("Enter you car registration number");
            return false;
        } else if (TextUtils.isEmpty(binding.etCurrentOdometer.getText())) {
            binding.etCurrentOdometer.setError("Enter your current Odometer reading");
            return false;
        } else if (year == null && make == null && model == null && submodel == null) {
            return false;
        }
        car.setRegistrationNo(binding.etCarRegNo.getText().toString());
        car.setCurrentOdomaterReading(Integer.parseInt(binding.etCurrentOdometer.getText().toString()));
        car.setMakeYear(Integer.parseInt(year));
        car.setManufacturer(make);
        car.setModelName(model);
        car.setSubModel(submodel);
        return true;
    }


    @Override
    public void onSubmitButtonClick(Context context) {

        if (checkEmptyEnteries()) {
            Log.d("FORM_SUBMISSION", "onSubmitButtonClick: Car Added Successfully");
            carViewModel.addCar(car);
            //TODO: Make a succesful Dialog and then Finish Activity
            getActivity().finish();
        } else {
            Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show();
        }
    }
}
