package com.app.davidmoreno.example.appmodules.app;

import android.app.Activity;
import android.app.Application;
import android.app.Service;

import com.app.davidmoreno.example.appmodules.base.RxBus;
import com.app.davidmoreno.example.dependencyinjection.components.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasServiceInjector;

public class Example extends Application implements HasActivityInjector, HasServiceInjector {
    @Inject
    DispatchingAndroidInjector<Activity> activityInjector;
    @Inject
    DispatchingAndroidInjector<Service> dispatchingServiceInjector;
    private RxBus bus;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerAppComponent.builder().create(this).inject(this);
        bus = new RxBus();
    }

    public RxBus bus() {
        return bus;
    }


    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityInjector;
    }

    @Override
    public AndroidInjector<Service> serviceInjector() {
        return dispatchingServiceInjector;
    }
}
