package com.app.davidmoreno.example.dependencyinjection.modules;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.app.davidmoreno.example.appmodules.base.ViewModelFactory;
import com.app.davidmoreno.example.appmodules.device.viewmodel.DeviceViewModel;
import com.app.davidmoreno.example.appmodules.principal.viewmodel.MainViewModel;
import com.app.davidmoreno.example.dependencyinjection.PerActivity;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel mainViewModel(MainViewModel mainViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DeviceViewModel.class)
    abstract ViewModel deviceViewModel(DeviceViewModel deviceViewModel);

    @Binds
    @PerActivity
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

}
