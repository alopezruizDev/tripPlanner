package com.tripplanner.alopezruizdev.tripplanner.fragments;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tripplanner.alopezruizdev.tripplanner.R;
import com.tripplanner.alopezruizdev.tripplanner.database.entity.Place;
import com.tripplanner.alopezruizdev.tripplanner.view_models.PlacesViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

import static android.R.layout.simple_list_item_1;
import static android.content.ContentValues.TAG;

public class ListFragment extends Fragment {
    // FOR DATA
    public static final String UID_KEY = "uid";

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private PlacesViewModel viewModel;

    //FOR DESIGN
    @BindView(R.id.list_view) ListView mListView;
    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout mSwipeView;

    private PlacesDataInterchange dataInterchange;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static ListFragment newInstance(){
        return new ListFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            dataInterchange = (PlacesDataInterchange) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement PlacesDataInterchange");
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);
        mSwipeView.setOnRefreshListener(new SwipeRefreshListener());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.configureDagger();
        this.configureViewModel();
        if(dataInterchange.getPlacesList()==null || dataInterchange.getPlacesList().size() == 0){
            loadList();
        }else {
            updateUI(dataInterchange.getPlacesList());
        }
    }
    // -----------------
    // CONFIGURATION
    //
    private void configureDagger(){
        AndroidSupportInjection.inject(this);
    }

    private void configureViewModel(){
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PlacesViewModel.class);
        viewModel.init();
    }

    // -----------------
    // UPDATE UI
    // -----------------
    private void updateUI(@Nullable List<Place> places){
        mSwipeView.setRefreshing(false);
        if (places != null){
            String[] listItems = new String[places.size()];
                for(int i = 0; i < places.size(); i++){
                    Place place = places.get(i);
                    listItems[i] = place.getName();
                }

                ArrayAdapter adapter = new ArrayAdapter(getContext(), simple_list_item_1, listItems);
                mListView.setAdapter(adapter);
        }else {
            showError("Error");
        }
        dataInterchange.setPlacesList(places);
//        addToDB(places);
    }

//    private void addToDB(List<Place> places){
//        for (Place place : places) {
//
//            db.collection("places")
//                    .add(place)
//                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                        @Override
//                        public void onSuccess(DocumentReference documentReference) {
//                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.w(TAG, "Error adding document", e);
//                        }
//                    });
//        }
//    }

    protected void showError(String error){
        Toast.makeText(getContext(),error,Toast.LENGTH_SHORT).show();
    }

    protected void loadList(){
        viewModel.getPlacesList().observe(this, places -> updateUI(places));
    }

    //Internal classes
    private class SwipeRefreshListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            loadList();
        }
    }
}
