package com.tripplanner.alopezruizdev.tripplanner.di.module;

import com.tripplanner.alopezruizdev.tripplanner.activities.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector(modules = FragmentModule.class)
    abstract MainActivity contributeMainActivity();
}
