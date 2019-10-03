package com.innovidio.androidbootstrap.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created on : Feb 25, 2019
 * Author     : AndroidWave
 */
public interface CarQueryAPIService {
    @GET("/api/0.3/?callback=?&cmd=getModels&year=2019&make=Acura")
    Call<ResponseBody> getCardDetails2();

    @GET("/api/0.3/?callback=?&cmd=getModels")
    Call<ResponseBody> getCardDetails(@Query("year") String year, @Query("make") String make);

}
