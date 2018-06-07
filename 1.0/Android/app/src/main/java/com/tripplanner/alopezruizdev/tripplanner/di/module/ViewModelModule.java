package com.tripplanner.alopezruizdev.tripplanner.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.tripplanner.alopezruizdev.tripplanner.di.key.ViewModelKey;
import com.tripplanner.alopezruizdev.tripplanner.view_models.FactoryViewModel;
import com.tripplanner.alopezruizdev.tripplanner.view_models.PlacesViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(PlacesViewModel.class)
    abstract ViewModel bindPlacesViewModel(PlacesViewModel repoViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(FactoryViewModel factory);
}
