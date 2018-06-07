package com.tripplanner.alopezruizdev.tripplanner.view_models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.tripplanner.alopezruizdev.tripplanner.database.entity.Place;
import com.tripplanner.alopezruizdev.tripplanner.repositories.PlaceRepository;

import java.util.List;

import javax.inject.Inject;

public class PlacesViewModel extends ViewModel {

    private LiveData<List<Place>> placesList;
    private PlaceRepository placeRepo;

    @Inject
    public PlacesViewModel(PlaceRepository placeRepo) {
        this.placeRepo = placeRepo;
    }

    public void init(){
        if (this.placesList != null){
            return;
        }
        placesList = placeRepo.getPlaces();
    }

    public LiveData<List<Place>> getPlacesList() {
        return placesList;
    }
}
