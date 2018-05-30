package com.tripplanner.alopezruizdev.tripplanner.models;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.io.Serializable;

@Parcel
public class PlaceModel implements Serializable{
    private String name;
    private String geo_point;
    private String description;

    public PlaceModel() {
    }

    @ParcelConstructor
    public PlaceModel(String name, String geo_point, String description) {
        this.name = name;
        this.geo_point = geo_point;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGeo_point() {
        return geo_point;
    }

    public void setGeo_point(String geo_point) {
        this.geo_point = geo_point;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
