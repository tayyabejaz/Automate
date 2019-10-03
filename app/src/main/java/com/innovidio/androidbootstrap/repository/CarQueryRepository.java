package com.innovidio.androidbootstrap.repository;

import android.util.Log;

import com.innovidio.androidbootstrap.entity.CarModel;
import com.innovidio.androidbootstrap.network.CarQueryAPIService;
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

    @Inject
    public CarQueryRepository(CarQueryAPIService carQueryAPIService){
         // init live data
        this.carQueryAPIService = carQueryAPIService;
    }

    public void getCarModels(String year, String make){
        Call<ResponseBody> getCarModels = this.carQueryAPIService.getCardDetails(year, make);
        getCarModels.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response != null) {
                        List<CarModel> carsList = parseResponseObject(response);
                       // Log.d(TAG, "onResponse: " + carsList.get(0).getMakeid() +" size "+carsList.size());
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
    }

    private List<CarModel> parseResponseObject(Response<ResponseBody> response){
        try {
            String responseValue = response.body().string();
            String jsonstringformodel = responseValue.substring(2, responseValue.length() - 2);
            JSONObject jsonObjectformodel = new JSONObject(jsonstringformodel);
            JSONArray jsonArray = jsonObjectformodel.getJSONArray("Models");

            Moshi moshi = new Moshi.Builder().build();
            Type type = Types.newParameterizedType(List.class, CarModel.class);
            JsonAdapter<List<CarModel>> adapter = moshi.adapter(type);
            return adapter.fromJson(jsonArray.toString());
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
