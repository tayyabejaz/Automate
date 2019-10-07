package com.innovidio.androidbootstrap.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.innovidio.androidbootstrap.network.CarQueryAPIService;
import com.innovidio.androidbootstrap.network.dto.CarMakesByYear;
import com.innovidio.androidbootstrap.network.dto.CarTrimsInfo;
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
    private static final String MAKES = "Makes";
    private static final String MODELS = "Models";
    private static final String TRIMS = "Trims";
    // define Live data

    CarQueryAPIService carQueryAPIService;
    MutableLiveData<List<CarModelName>> carModelNamesLiveData = new MutableLiveData<>();
    MutableLiveData<List<CarMakesByYear>> carMakesListByYearLiveData = new MutableLiveData<>();
    MutableLiveData<List<CarTrimsInfo>> carTrimsInfoLiveData = new MutableLiveData<>();

    @Inject
    public CarQueryRepository(CarQueryAPIService carQueryAPIService) {
        // init live data
        this.carQueryAPIService = carQueryAPIService;
    }

    public MutableLiveData<List<CarMakesByYear>> getCarMakesByYear(String year) {
        Call<ResponseBody> getCarModels = this.carQueryAPIService.getCarMakesByYear(year);
        getCarModels.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response != null) {
                    List<CarMakesByYear> carModelNames = parseResponseObject(response, CarMakesByYear.class, MAKES);
                    // Log.d(TAG, "onResponse: " + carsList.get(0).getMakeid() +" size "+carsList.size());
                    carMakesListByYearLiveData.setValue(carModelNames);
                } else {
                    Log.d(TAG, "onResponse: not found");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Log.d(TAG, "onResponseE: " + t.getLocalizedMessage());
            }
        });
        return carMakesListByYearLiveData;
    }

    public MutableLiveData<List<CarModelName>> getCarModelsByYearAndMake(String year, String make) {
        Call<ResponseBody> getCarModels = this.carQueryAPIService.getCarModelsByYearAndMake(year, make);
        getCarModels.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response != null) {
                    List<CarModelName> carModelNames = parseResponseObject(response, CarModelName.class, MODELS);
                    // Log.d(TAG, "onResponse: " + carsList.get(0).getMakeid() +" size "+carsList.size());
                    carModelNamesLiveData.setValue(carModelNames);
                } else {
                    Log.d(TAG, "onResponse: not found");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Log.d(TAG, "onResponseE: " + t.getLocalizedMessage());
            }
        });

        return carModelNamesLiveData;
    }

    public MutableLiveData<List<CarTrimsInfo>> getCarTrimsByYearMakeModel(String year, String make, String model) {
        Call<ResponseBody> carTrimsByYearMakeModel = this.carQueryAPIService.getCarTrimsByYearMakeModel(year, make, model);
        carTrimsByYearMakeModel.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response != null) {
                    List<CarTrimsInfo> carTrimsInfoList = parseResponseObject(response, CarTrimsInfo.class, TRIMS);
                    // Log.d(TAG, "onResponse: " + carsList.get(0).getMakeid() +" size "+carsList.size());
                    carTrimsInfoLiveData.setValue(carTrimsInfoList);
                } else {
                    Log.d(TAG, "onResponse: not found");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Log.d(TAG, "onResponseE: " + t.getLocalizedMessage());
            }
        });

        return carTrimsInfoLiveData;
    }

    private <T> List<T> parseResponseObject(Response<ResponseBody> response, Type modelType, String ObjName) {
        try {
            String jsonArrayString =  parseToJSSONArrayString(response, ObjName);

            Moshi moshi = new Moshi.Builder().build();
            Type type = Types.newParameterizedType(List.class, modelType);
            JsonAdapter<List<T>> adapter = moshi.adapter(type);
            List<T> list = adapter.fromJson(jsonArrayString);
            return list;

        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "onResponse: " + e.getLocalizedMessage());
            return null;
        }
    }

    private String parseToJSSONArrayString(Response<ResponseBody> response, String ObjName){
        try {
            String responseValue = response.body().string();
            String jsonStringForModel = responseValue.substring(2, responseValue.length() - 2);
            JSONObject jsonObjectForModel = new JSONObject(jsonStringForModel);
            JSONArray jsonArray = jsonObjectForModel.getJSONArray(ObjName);
            return jsonArray.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


//    private List<Car> parseResponseObject2(Response<ResponseBody> response) {
//        try {
//            String responseValue = response.body().string();
//            String jsonstringformodel = responseValue.substring(2, responseValue.length() - 2);
//            JSONObject jsonObjectformodel = new JSONObject(jsonstringformodel);
//            JSONArray jsonArray = jsonObjectformodel.getJSONArray("Models");
//
//            Moshi moshi = new Moshi.Builder().build();
//            Type type = Types.newParameterizedType(List.class, CarModelName.class);
//            JsonAdapter<List<Car>> adapter = moshi.adapter(type);
//            List<Car> cars = adapter.fromJson(jsonArray.toString());
//
//            return cars;
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.d(TAG, "onResponse: " + e.getLocalizedMessage());
//            return null;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            Log.d(TAG, "onResponse: " + e.getLocalizedMessage());
//            return null;
//        }
//    }
//
//    public void getCarModels2(String year, String make) {
//        Call<ResponseBody> getCarModels = this.carQueryAPIService.getCardDetails(year, make);
//        getCarModels.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    System.out.println(response.message());
//                    if (response != null)
//                        Log.d(TAG, "onResponse: " + response.body().string());
//                    else {
//                        Log.d(TAG, "onResponse: none found");
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                t.printStackTrace();
//                Log.d(TAG, "onResponseE: " + t.getLocalizedMessage());
//            }
//        });
//    }

}
