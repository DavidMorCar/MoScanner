package com.app.davidmoreno.example.dependencyinjection.components;

import com.app.davidmoreno.example.appmodules.app.Example;
import com.app.davidmoreno.example.dependencyinjection.modules.AppModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules =  AppModule.class)
interface AppComponent extends AndroidInjector<Example> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<Example> {
    }
}
