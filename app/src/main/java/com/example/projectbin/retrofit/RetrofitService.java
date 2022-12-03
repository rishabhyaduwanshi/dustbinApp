package com.example.projectbin.retrofit;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private Retrofit retrofit;
    public RetrofitService(){
        initializeRetrofit();
    }
    private void initializeRetrofit() {
        retrofit = new Retrofit.Builder().baseUrl("https://d53f-2409-4053-2c80-93e8-65e5-4c6f-38d1-15db.ngrok.io").addConverterFactory(GsonConverterFactory.create(new Gson())).build();
    }
    public Retrofit getRetrofit() {
        return retrofit;
    }
}
