package com.tripplanner.alopezruizdev.tripplanner.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.tripplanner.alopezruizdev.tripplanner.database.entity.Place;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface PlaceDao {
    @Insert(onConflict = REPLACE)
    void save(Place place);

    @Query("SELECT * FROM place")
    LiveData<List<Place>> load();

    @Query("SELECT * FROM place WHERE id = :placeId LIMIT 1")
    Place hasPlace(String placeId);
}
