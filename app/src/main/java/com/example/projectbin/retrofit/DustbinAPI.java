package com.example.projectbin.retrofit;

import com.example.projectbin.model.Dustbin;
import com.example.projectbin.model.LatLongPOJO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DustbinAPI {
    @POST("/dustbins/add")
    Call<String> save(@Body Dustbin dustbin);
    @GET("/dustbins/get")
    Call<List<LatLongPOJO>> getLatLong();
}
