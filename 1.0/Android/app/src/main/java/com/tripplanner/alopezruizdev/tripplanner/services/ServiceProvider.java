package com.tripplanner.alopezruizdev.tripplanner.services;

import com.tripplanner.alopezruizdev.tripplanner.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceProvider {
    private static Retrofit retrofit;
    public static PlacesService getPlacesService(){
        return new PlacesService(getRetrofit());
    }
    protected static Retrofit getRetrofit(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://raw.githubusercontent.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
