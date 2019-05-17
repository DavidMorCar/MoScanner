package com.app.davidmoreno.example.appmodules.device.viewmodel;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.bluetooth.BluetoothDevice;

import javax.inject.Inject;

public class DeviceViewModel extends ViewModel implements LifecycleObserver {
    private MutableLiveData<BluetoothDevice> postValue;
    private boolean enable;

    @Inject
    public DeviceViewModel() {
        postValue = new MutableLiveData<>();
        enable = false;
    }

    public MutableLiveData<BluetoothDevice> getPost() {
        return postValue;
    }

    public void setDevice(BluetoothDevice bluetoothDevice){
        postValue.postValue(bluetoothDevice);
    }
}
