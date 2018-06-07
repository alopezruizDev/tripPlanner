package com.tripplanner.alopezruizdev.tripplanner.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.io.Serializable;

@Parcel
@Entity
public class Place implements Serializable{

    @PrimaryKey
    @NonNull
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;

    @NonNull
    @SerializedName("geo_point")
    @Expose
    private String geo_point;

    @SerializedName("description")
    @Expose
    private String description;

    public Place() {
    }

    @ParcelConstructor
    public Place(@NonNull String id, String name, @NonNull String geo_point, String description) {
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

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }
}
