package com.tripplanner.alopezruizdev.tripplanner.services;

import com.tripplanner.alopezruizdev.tripplanner.api.PlacesAPI;
import com.tripplanner.alopezruizdev.tripplanner.models.PlaceModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class PlacesService {
    PlacesAPI API;

    public PlacesService(Retrofit retrofit){
        API = retrofit.create(PlacesAPI.class);
    }

    public void getPlacesList(Callback<List<PlaceModel>> listCallback){
        Call<List<PlaceModel>> call = API.getList();
        call.enqueue(listCallback);
    }
}
