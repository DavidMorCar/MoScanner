package com.app.davidmoreno.example.dependencyinjection.modules;

import android.support.v7.app.AppCompatActivity;

import com.app.davidmoreno.example.appmodules.device.view.DeviceActivity;
import com.app.davidmoreno.example.dependencyinjection.PerActivity;

import dagger.Binds;
import dagger.Module;

@Module(includes = BaseActivityModule.class)
public abstract class DeviceActivityModule {
    @Binds
    @PerActivity
    abstract AppCompatActivity activity(DeviceActivity deviceActivity);
}