package com.tripplanner.alopezruizdev.tripplanner.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.tripplanner.alopezruizdev.tripplanner.R;
import com.tripplanner.alopezruizdev.tripplanner.models.PlaceModel;
import com.tripplanner.alopezruizdev.tripplanner.services.PlacesService;
import com.tripplanner.alopezruizdev.tripplanner.services.ServiceProvider;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.layout.simple_list_item_1;

public class ListFragment extends Fragment {
    @BindView(R.id.list_view) ListView mListView;
    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout mSwipeView;

    List<PlaceModel> places = null;
    PlacesService service;

    public static ListFragment newInstance(){
        return new ListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);

        service = ServiceProvider.getPlacesService();
        mSwipeView.setOnRefreshListener(new SwipeRefreshListener());
        getList();
        return view;
    }

    private void getList(){
        mSwipeView.setRefreshing(true);
        service.getPlacesList(new PlaceListCallback());
    }

    protected void showError(String error){
        Toast.makeText(getContext(),error,Toast.LENGTH_SHORT).show();
    }

    private class PlaceListCallback implements Callback<List<PlaceModel>> {
        @Override
        public void onResponse(Call<List<PlaceModel>> call, Response<List<PlaceModel>> response) {
            mSwipeView.setRefreshing(false);
            if(response.body()!=null){
                places = response.body();

                String[] listItems = new String[places.size()];

                for(int i = 0; i < places.size(); i++){
                    PlaceModel recipe = places.get(i);
                    listItems[i] = recipe.getName();
                }

                ArrayAdapter adapter = new ArrayAdapter(getContext(), simple_list_item_1, listItems);
                mListView.setAdapter(adapter);
            }else {
//                showError(getString(R.string.error_recipe_list));
                showError("Error");

            }

        }

        @Override
        public void onFailure(Call<List<PlaceModel>> call, Throwable t) {
            mSwipeView.setRefreshing(false);
//            showError(getString(R.string.error_recipe_list));
            showError("Error");
        }
    }
    private class SwipeRefreshListener implements SwipeRefreshLayout.OnRefreshListener{
        @Override
        public void onRefresh() {
            getList();
        }
    }
}
