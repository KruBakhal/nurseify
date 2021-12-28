package com.weboconnect.nurseify.webService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private Retrofit retrofit;
    public static final String BASE_URL = "http://35.200.185.126/api/";
    private static RetrofitClient mInstance;
    private OkHttpClient client;
    private OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

    public OkHttpClient.Builder getOkHttpBuilder() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpBuilder.networkInterceptors().add(chain -> {
            Request.Builder requestBuilder = chain.request().newBuilder();
//            requestBuilder.addHeader("Client-Service", "pNvZIhreWf56RxGfw9ERwVFQlNilSEU7hfWOndQ7iJ1Y6y1svw");
//            requestBuilder.addHeader("Auth-Key", "oRo5qJC1fwrYyPHODqLRpIrYU0H7XWBaDN5");
            return chain.proceed(requestBuilder.build());
        });
        return okHttpBuilder.addInterceptor(interceptor);
    }

    public RetrofitClient() {
        client = getOkHttpBuilder().build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public RetrofitApi getNurseRetrofitApi() {
        return retrofit.create(RetrofitApi.class);
    }

    public FacilityAPI getFacilityApi() {
        return retrofit.create(FacilityAPI.class);
    }

}
