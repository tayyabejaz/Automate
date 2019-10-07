package com.innovidio.androidbootstrap.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created on : Oct 03, 2019
 * Author     : Adnan Naeem
 */
public interface CarQueryAPIService {
//      demo check
//    @GET("/api/0.3/?callback=?&cmd=getModels&year=2019&make=Acura")
//    Call<ResponseBody> getCardDetails2();


    @GET("/api/0.3/?callback=?&cmd=getMakes")
    Call<ResponseBody> getCarMakesByYear(@Query("year") String year);

    @GET("/api/0.3/?callback=?&cmd=getModels")
    Call<ResponseBody> getCarModelsByYearAndMake(@Query("year") String year, @Query("make") String make);

    @GET("/api/0.3/?callback=?&cmd=getTrims")
    Call<ResponseBody> getCarTrimsByYearMakeModel(@Query("year") String year, @Query("make") String make, @Query("model") String model);

}
