package com.app.davidmoreno.example.dependencyinjection.modules;

import android.app.Application;
import android.content.Context;

import com.app.davidmoreno.example.appmodules.app.Example;
import com.app.davidmoreno.example.appmodules.device.view.DeviceActivity;
import com.app.davidmoreno.example.appmodules.principal.view.MainActivity;
import com.app.davidmoreno.example.dependencyinjection.PerActivity;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Module(includes = { AndroidSupportInjectionModule.class, ProvidesAppModule.class})
public abstract class AppModule {

    @Binds
    @Singleton
    abstract Application application(Example app);

    @Binds
    @Singleton
    abstract Context appContext(Example app);

    @PerActivity
    @ContributesAndroidInjector(modules = { MainActivityModule.class, ViewModelModule.class })
    abstract MainActivity mainActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = { DeviceActivityModule.class, ViewModelModule.class })
    abstract DeviceActivity deviceActivityInjector();
}