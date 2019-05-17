package com.app.davidmoreno.example.dependencyinjection.modules;

import android.support.v7.app.AppCompatActivity;


import com.app.davidmoreno.example.dependencyinjection.PerActivity;
import com.app.davidmoreno.example.appmodules.principal.view.MainActivity;

import dagger.Binds;
import dagger.Module;

@Module(includes = BaseActivityModule.class)
public abstract class MainActivityModule {
    @Binds
    @PerActivity
    abstract AppCompatActivity activity(MainActivity mainActivity);
}