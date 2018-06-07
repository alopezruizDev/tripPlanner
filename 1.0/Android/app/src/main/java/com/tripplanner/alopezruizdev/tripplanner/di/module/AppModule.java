package com.tripplanner.alopezruizdev.tripplanner.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tripplanner.alopezruizdev.tripplanner.api.PlacesAPI;
import com.tripplanner.alopezruizdev.tripplanner.database.TPDataBase;
import com.tripplanner.alopezruizdev.tripplanner.database.dao.PlaceDao;
import com.tripplanner.alopezruizdev.tripplanner.repositories.PlaceRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
public class AppModule {

    // --- DATABASE INJECTION ---

    @Provides
    @Singleton
    TPDataBase provideDatabase(Application application){
        return Room.databaseBuilder(application,TPDataBase.class,"TPDataBase.db").build();
    }

    @Provides
    @Singleton
    PlaceDao providePlaceDao(TPDataBase tpDataBase){return tpDataBase.placeDao();}


    @Provides
    Executor provideExecutor(){return Executors.newSingleThreadExecutor();}

    @Provides
    @Singleton
    PlaceRepository providePlaceRepository(PlacesAPI placesAPI, PlaceDao placeDao, Executor executor) {
        return new PlaceRepository(placesAPI, placeDao, executor);
    }

    // --- REPOSITORY INJECTION ---

    private static String BASE_URL = "https://raw.githubusercontent.com/";

    @Provides
    Gson provideGson(){
        return new GsonBuilder().create();
    }

    @Provides
    Retrofit provideRetrofit(Gson gson) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    PlacesAPI providePlacesAPI(Retrofit restAdapter){
        return restAdapter.create(PlacesAPI.class);
    }

}
