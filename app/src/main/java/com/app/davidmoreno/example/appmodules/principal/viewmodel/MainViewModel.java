package com.app.davidmoreno.example.appmodules.principal.viewmodel;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainViewModel extends ViewModel implements LifecycleObserver {
    private MutableLiveData<List<BluetoothDevice>> postValue;
    private boolean enable;

    @Inject
    public MainViewModel() {
        postValue = new MutableLiveData<>();
        enable = false;
    }

    public MutableLiveData<List<BluetoothDevice>> getPosts() {
        return postValue;
    }

    public void enableBluetooth(Context context){
        Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        context.startActivity(enableIntent);
        enable = true;
        IntentFilter bluetoothFilter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        context.registerReceiver(enableBroadcastReceiver, bluetoothFilter);
    }

    public void scanBluetooth(Context context, BroadcastReceiver broadcastReceiver){
        Intent scanIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        scanIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        context.startActivity(scanIntent);
        IntentFilter bluetoothFilter = new IntentFilter(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        context.registerReceiver(broadcastReceiver, bluetoothFilter);
    }

    public void scanBluetoothDevices(Context context){
        IntentFilter bluetoothFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        context.registerReceiver(devicesBroadcastReceiver, bluetoothFilter);
    }

    public void unregister(Context context){
        context.unregisterReceiver(devicesBroadcastReceiver);
        if(enable)
            context.unregisterReceiver(enableBroadcastReceiver);
    }

    private final BroadcastReceiver enableBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null && action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                if (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR) ==
                        BluetoothAdapter.STATE_ON)
                    Toast.makeText(context, "Bluetooth on 2", Toast.LENGTH_LONG).show();
            }
        }
    };

    private final BroadcastReceiver devicesBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null && action.equals(BluetoothDevice.ACTION_FOUND)) {
                List<BluetoothDevice> bluetoothDevices = postValue.getValue();
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if(device != null){
                    if(bluetoothDevices == null) bluetoothDevices = new ArrayList<>();
                    bluetoothDevices.add(device);
                    postValue.postValue(bluetoothDevices);
                }
            }
        }
    };
}