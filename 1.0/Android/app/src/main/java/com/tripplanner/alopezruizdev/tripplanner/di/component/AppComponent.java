package com.tripplanner.alopezruizdev.tripplanner.di.component;

import android.app.Application;

import com.tripplanner.alopezruizdev.tripplanner.App;
import com.tripplanner.alopezruizdev.tripplanner.di.module.ActivityModule;
import com.tripplanner.alopezruizdev.tripplanner.di.module.AppModule;
import com.tripplanner.alopezruizdev.tripplanner.di.module.FragmentModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {ActivityModule.class, FragmentModule.class, AppModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder{
        @BindsInstance
                Builder application(Application application);
                AppComponent build();
    }

    void inject(App app);
}
