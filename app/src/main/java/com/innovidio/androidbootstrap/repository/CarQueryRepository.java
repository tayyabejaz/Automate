package com.innovidio.androidbootstrap.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.innovidio.androidbootstrap.entity.Car;
import com.innovidio.androidbootstrap.network.CarQueryAPIService;
import com.innovidio.androidbootstrap.network.dto.CarModelName;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class CarQueryRepository {

    private static final String TAG = "CarQueryRepository";
    // define Live data

     CarQueryAPIService carQueryAPIService;
    MutableLiveData<List<CarModelName>> carModelNamesLiveData = new MutableLiveData<List<CarModelName>>();
//    List<CarModelName>CarModelName carModelNames;

//    public MutableLiveData<List<CarModelName>> getCarModelNamesLiveData() {
//        return this.carModelNamesLiveData;
//    }

    @Inject
    public CarQueryRepository(CarQueryAPIService carQueryAPIService){
         // init live data
        this.carQueryAPIService = carQueryAPIService;
    }

    public MutableLiveData<List<CarModelName>> getCarModels(String year, String make){
        Call<ResponseBody> getCarModels = this.carQueryAPIService.getCardDetails(year, make);
        getCarModels.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response != null) {
                    List<CarModelName> carModelNames  = parseResponseObject(response, CarModelName.class);
                       // Log.d(TAG, "onResponse: " + carsList.get(0).getMakeid() +" size "+carsList.size());
                        carModelNamesLiveData.setValue(carModelNames);
                } else {
                    Log.d(TAG, "onResponse: not found");
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Log.d(TAG, "onResponseE: "+t.getLocalizedMessage());
            }
        });

        return  carModelNamesLiveData;
    }

    private <T> List<T> parseResponseObject(Response<ResponseBody> response,Type modelType){
        try {
            String responseValue = response.body().string();
            String jsonstringformodel = responseValue.substring(2, responseValue.length() - 2);
            JSONObject jsonObjectformodel = new JSONObject(jsonstringformodel);
            JSONArray jsonArray = jsonObjectformodel.getJSONArray("Models");

            Moshi moshi = new Moshi.Builder().build();
            Type type = Types.newParameterizedType(List.class, modelType);
            JsonAdapter<List<T>> adapter = moshi.adapter(type);
            List<T> list = adapter.fromJson(jsonArray.toString());
            return list;

        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "onResponse: " + e.getLocalizedMessage());
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "onResponse: " + e.getLocalizedMessage());
            return null;
        }
    }



    private List<Car> parseResponseObject2(Response<ResponseBody> response){
        try {
            String responseValue = response.body().string();
            String jsonstringformodel = responseValue.substring(2, responseValue.length() - 2);
            JSONObject jsonObjectformodel = new JSONObject(jsonstringformodel);
            JSONArray jsonArray = jsonObjectformodel.getJSONArray("Models");

            Moshi moshi = new Moshi.Builder().build();
            Type type = Types.newParameterizedType(List.class, CarModelName.class);
            JsonAdapter<List<Car>> adapter = moshi.adapter(type);
            List<Car> cars = adapter.fromJson(jsonArray.toString());

            return cars;
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "onResponse: " + e.getLocalizedMessage());
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "onResponse: " + e.getLocalizedMessage());
            return null;
        }
    }


    public void getCarModels2(String year, String make){
        Call<ResponseBody> getCarModels = this.carQueryAPIService.getCardDetails(year, make);
        getCarModels.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    System.out.println(response.message());
                    if (response!=null)
                        Log.d(TAG, "onResponse: "+response.body().string());
                    else{
                        Log.d(TAG, "onResponse: none found");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Log.d(TAG, "onResponseE: "+t.getLocalizedMessage());
            }
        });
    }

}
