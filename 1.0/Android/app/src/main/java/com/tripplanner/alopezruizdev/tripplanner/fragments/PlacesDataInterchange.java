package com.tripplanner.alopezruizdev.tripplanner.fragments;

import com.tripplanner.alopezruizdev.tripplanner.database.entity.Place;

import java.util.List;

public interface PlacesDataInterchange {

    public List<Place> getPlacesList();
    public void setPlacesList(List<Place> placeList);

}
