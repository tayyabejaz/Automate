package com.innovidio.androidbootstrap.activity;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.innovidio.androidbootstrap.AppPreferences;
import com.innovidio.androidbootstrap.R;
import com.innovidio.androidbootstrap.network.dto.CarModelName;
import com.innovidio.androidbootstrap.viewmodel.MyViewModel;
import com.innovidio.androidbootstrap.db.dao.FeedDao;
import com.innovidio.androidbootstrap.di.viewmodel.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;
import dagger.android.support.DaggerAppCompatActivity;


public class MainActivity extends DaggerAppCompatActivity {
    private static final String TAG = "MainActivityLog";

    @Inject
    ViewModelProviderFactory providerFactory;
    @Inject
    FeedDao feedDao;
    @Inject
    AppPreferences appPreferences;

    @Inject
    String firstInjection;

    MyViewModel myViewModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myViewModel =  new ViewModelProvider(this, providerFactory).get(MyViewModel.class);
        Log.d(TAG, "onCreate: "+firstInjection);
    //    appPreferences.put(AppPreferences.Key.SAMPLE_INT,100);

     //   myViewModel.getCarModels("2019", "Acura");

        myViewModel.getResponseObserver(this,"2019", "Acura" ).observe(this, new Observer<List<CarModelName>>() {
            @Override
            public void onChanged(List<CarModelName> carModelNames) {
                if (carModelNames!=null){
                    Log.e("reponse",""+carModelNames.get(0).getModelName());
                }else{

                }
            }
        });

    }
}
