package com.tripplanner.alopezruizdev.tripplanner.di.module;

import com.tripplanner.alopezruizdev.tripplanner.fragments.ListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract ListFragment contributesListFragment();
}
