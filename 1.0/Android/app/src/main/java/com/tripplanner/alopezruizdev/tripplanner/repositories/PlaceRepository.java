package com.tripplanner.alopezruizdev.tripplanner.repositories;

import android.arch.lifecycle.LiveData;

import com.tripplanner.alopezruizdev.tripplanner.api.PlacesAPI;
import com.tripplanner.alopezruizdev.tripplanner.database.dao.PlaceDao;
import com.tripplanner.alopezruizdev.tripplanner.database.entity.Place;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class PlaceRepository {

    private static int FRESH_TIMEOUT_IN_MINUTES = 1;

    private final PlacesAPI placesAPI;
    private final PlaceDao placeDao;
    private final Executor executor;

    @Inject
    public PlaceRepository(PlacesAPI placesAPI, PlaceDao placeDao, Executor executor) {
        this.placesAPI = placesAPI;
        this.placeDao = placeDao;
        this.executor = executor;
    }

    public LiveData<List<Place>> getPlaces(){
        refreshPlaces();
        return placeDao.load();
    }

    private void refreshPlaces(){
        executor.execute(() -> {
            placesAPI.getList().enqueue(new Callback<List<Place>>() {
                @Override
                public void onResponse(Call<List<Place>> call, final Response<List<Place>> response) {
                    executor.execute(() -> {
                        List<Place> placeList = response.body();
                        for (Place place: placeList) {
                            placeDao.save(place);
                        }
                    });
                }

                @Override
                public void onFailure(Call<List<Place>> call, Throwable t) {

                }
            });
        });
    }
    private Date getMaxRefreshTime(Date currentDate){
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.MINUTE, - FRESH_TIMEOUT_IN_MINUTES);
        return cal.getTime();
    }
}
