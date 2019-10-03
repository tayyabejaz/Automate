package com.innovidio.androidbootstrap.di;


import android.util.Log;

import com.innovidio.androidbootstrap.Constants;
import com.innovidio.androidbootstrap.network.CarQueryAPIService;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    @Inject
    public CarQueryAPIService provideCarQueryAPIService(Retrofit retrofit){
        return retrofit.create(CarQueryAPIService.class);
    }

    @Singleton
    @Provides
    static Retrofit provideRetrofitInstance(OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .baseUrl(Constants.URL_BASE_MUSIC)
                .addConverterFactory(MoshiConverterFactory.create())
            //    .addConverterFactory(ScalarsConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Inject
    @Singleton
    OkHttpClient getHTTPOkClient(LoggingInterceptor httpLoggingInterceptor) {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor);
        // .addInterceptor(authenticationIntercentor)
        // .authenticator(authenticator);


//        if (TDEBUG_MODE) {
//            builder.addNetworkInterceptor(new StethoInterceptor());
//        }
        return builder.build();
    }


    @Provides
    @Singleton
    LoggingInterceptor getLoggingInterceptor() {
        LoggingInterceptor interceptor = new LoggingInterceptor();
        return interceptor;
    }

    class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            Log.d("RETROFIT", String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            Log.d("RETROFIT", String.format("Received response for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, response.headers()));


            final String responseString = new String(response.body().bytes());
            Log.d("RETROFIT", "Response: " + responseString);
            if(response.headers().values("Auth-Token").size()>0) {
                String authToken = response.headers().values("Auth-Token").get(0);
                //preferenceManager.setStringValue("header", "Bearer " + authToken);
            }


//            int code = response.code();
//            if (code == 401) {
//                preferenceManager.deleteValue("header");
//            }
            return response.newBuilder()
                    .body(ResponseBody.create(response.body().contentType(), responseString))
                    .build();
        }
    }
}
