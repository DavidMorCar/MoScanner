package com.app.davidmoreno.example.dependencyinjection.modules;

import android.content.Context;
import android.content.SharedPreferences;

import com.app.davidmoreno.example.services.base.MainHttpService;
import com.app.davidmoreno.example.util.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ProvidesAppModule {

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Context application){
        return application.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    MainHttpService provideCloudFunctionService() {
        return new Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(MainHttpService.class);
    }

}
