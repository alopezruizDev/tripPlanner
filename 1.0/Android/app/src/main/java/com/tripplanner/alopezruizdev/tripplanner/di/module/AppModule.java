package com.tripplanner.alopezruizdev.tripplanner.di.module;

import android.app.Application;
import android.arch.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tripplanner.alopezruizdev.tripplanner.api.PlacesAPI;
import com.tripplanner.alopezruizdev.tripplanner.repositories.PlaceRepository;
import com.tripplanner.alopezruizdev.tripplanner.view_models.FactoryViewModel;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = ViewModelModule.class)
public class AppModule {

    // --- DATABASE INJECTION ---

//    @Provides
//    @Singleton
//    TPDataBase provideDatabase(Application application){
//        return Room.databaseBuilder(application,TPDataBase.class,"TPDataBase.db").build();
//    }
//
//    @Provides
//    @Singleton
//    PlaceDao providePlaceDao(TPDataBase tpDataBase){return tpDataBase.placeDao();}


    @Provides
    Executor provideExecutor(){return Executors.newSingleThreadExecutor();}


//    @Provides
//    FactoryViewModel providesFactoryViewModel(Map<Class<? extends ViewModel>, Provider<ViewModel>> creators) {
//        return new FactoryViewModel(creators);
//    }
//    @Provides
//    @Singleton
//    PlaceRepository providePlaceRepository(PlacesAPI placesAPI, PlaceDao placeDao, Executor executor) {
//        return new PlaceRepository(placesAPI, placeDao, executor);
//    }
    @Provides
    @Singleton
    PlaceRepository providePlaceRepository() {
        return new PlaceRepository();
    }
//
//    // --- REPOSITORY INJECTION ---
//
//    private static String BASE_URL = "https://raw.githubusercontent.com/";
//
//    @Provides
//    Gson provideGson(){
//        return new GsonBuilder().create();
//    }

}
