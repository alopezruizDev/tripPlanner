package com.tripplanner.alopezruizdev.tripplanner.api;

import com.tripplanner.alopezruizdev.tripplanner.models.PlaceModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PlacesAPI {
    @GET("alopezruizDev/tripPlanner/master/1.0/Android/app/src/main/jsonFiles/places.json")
    Call<List<PlaceModel>> getList();
}
