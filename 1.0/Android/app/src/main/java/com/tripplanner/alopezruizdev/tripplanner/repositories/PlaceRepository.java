package com.tripplanner.alopezruizdev.tripplanner.repositories;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tripplanner.alopezruizdev.tripplanner.api.PlacesAPI;
import com.tripplanner.alopezruizdev.tripplanner.database.entity.Place;
import com.tripplanner.alopezruizdev.tripplanner.database.liveData.FirebaseQueryLiveData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PlaceRepository {

    private static int FRESH_TIMEOUT_IN_MINUTES = 1;

//    private final PlacesAPI placesAPI;
//    private final PlaceDao placeDao;
    private static final CollectionReference placesRef =
        FirebaseFirestore.getInstance().collection("places");

//    private final Executor executor;

//    @Inject
//    public PlaceRepository(PlacesAPI placesAPI, PlaceDao placeDao, Executor executor) {
//        this.placesAPI = placesAPI;
//        this.placeDao = placeDao;
//        this.executor = executor;
//    }

//    @Inject
//    public PlaceRepository(Executor executor) {
//        this.executor = executor;
//    }

    @Inject
    public PlaceRepository() {
    }

    public LiveData<List<Place>> getPlaces(){
        FirebaseQueryLiveData mLiveData = new FirebaseQueryLiveData(placesRef);

        LiveData<List<Place>> mPlacesLiveData =
                Transformations.map(mLiveData, new Deserializer());

        return mPlacesLiveData;
    }

    private class Deserializer implements Function<QuerySnapshot, List<Place>> {

        @Override
        public List<Place> apply(QuerySnapshot querySnapshot) {
//            mList.clear();
            List<Place> mList = new ArrayList<Place>();
            for(QueryDocumentSnapshot snap : querySnapshot){
                Place place = snap.toObject(Place.class);
                mList.add(place);
            }
            return mList;
        }
    }

    private void refreshPlaces(){
//        executor.execute(() -> {
//            placesAPI.getList().enqueue(new Callback<List<Place>>() {
//                @Override
//                public void onResponse(Call<List<Place>> call, final Response<List<Place>> response) {
//                    executor.execute(() -> {
//                        List<Place> placeList = response.body();
//                        for (Place place: placeList) {
//                            placeDao.save(place);
//                        }
//                    });
//                }
//
//                @Override
//                public void onFailure(Call<List<Place>> call, Throwable t) {
//
//                }
//            });
//        });
    }
    private Date getMaxRefreshTime(Date currentDate){
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.MINUTE, - FRESH_TIMEOUT_IN_MINUTES);
        return cal.getTime();
    }
}
