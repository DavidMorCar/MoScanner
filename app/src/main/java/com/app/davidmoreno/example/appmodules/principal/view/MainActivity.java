package com.app.davidmoreno.example.appmodules.principal.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.app.davidmoreno.example.R;
import com.app.davidmoreno.example.appmodules.app.Example;
import com.app.davidmoreno.example.appmodules.base.BaseActivity;
import com.app.davidmoreno.example.appmodules.base.BaseOnActionListener;
import com.app.davidmoreno.example.appmodules.base.RxBus;
import com.app.davidmoreno.example.appmodules.device.view.DeviceActivity;
import com.app.davidmoreno.example.appmodules.principal.adapter.MainAdapter;
import com.app.davidmoreno.example.appmodules.principal.viewmodel.MainViewModel;
import com.app.davidmoreno.example.domain.model.Identifier;

import java.util.ArrayList;
import java.util.logging.Logger;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity implements BaseOnActionListener {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private MainViewModel mainViewModel;
    BluetoothAdapter bluetoothAdapter;
    MainAdapter mainAdapter;
    Button searchButton;
    RecyclerView listView;
    ArrayList<BluetoothDevice> devices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        searchButton = (Button) findViewById(R.id.mainSearchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chechPermissions();
                if (bluetoothAdapter.isDiscovering()) {
                    bluetoothAdapter.cancelDiscovery();
                    showLoadingDialog();
                    mDelayHandler.postDelayed(() -> {
                        hideLoadingDialog();
                    }, DELAY);
                    ((Example) getApplication())
                            .bus()
                            .send(new Identifier(1));
                } else {
                    bluetoothAdapter.startDiscovery();
                    mainViewModel.scanBluetoothDevices(view.getContext());
                    ((Example) getApplication())
                            .bus()
                            .send(new Identifier(0));
                }
            }
        });
        listView = (RecyclerView) findViewById(R.id.mainRecyclerView);
        this.listView.setLayoutManager(new LinearLayoutManager(this));
        this.mainAdapter = new MainAdapter(this, new ArrayList<>(), this);
        this.listView.setAdapter(this.mainAdapter);
        listView.addItemDecoration(new DividerItemDecoration(listView.getContext(), DividerItemDecoration.VERTICAL));
        devices = new ArrayList<>();
        initViewModel();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setBus();
    }

    @SuppressLint("CheckResult")
    private void setBus() {
        ((Example) getApplication()).bus()
                .toObservable()
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object object) throws Exception {
                        if (object instanceof Identifier) {
                            if (((Identifier) object).getIdentifier() == 1) {
                                searchButton.setText("Scan");
                            } else {
                                searchButton.setText("Stop");
                            }
                        }
                    }
                });
    }

    private void initViewModel() {
        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);
        enableBlueTooth();
        mainViewModel.getPosts().observe(this, posts -> {
            if (posts == null) {
                Toast.makeText(this, "Error loading data...",
                        Toast.LENGTH_LONG).show();
            } else {
                this.mainAdapter.setValues(posts);
            }
        });
    }

    public void enableBlueTooth() {
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth not supported...",
                    Toast.LENGTH_LONG).show();
        } else if (!bluetoothAdapter.enable()) {
            mainViewModel.enableBluetooth(this);
        } else {
            Toast.makeText(this, "Bluetooth on", Toast.LENGTH_LONG).show();
        }
    }

    private void chechPermissions() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            int check = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
            check += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
            if (check != 0)
                this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, 1001);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainViewModel.unregister(this);
    }

    @Override
    public void onAction(BluetoothDevice device) {
        Intent intent = new Intent(this, DeviceActivity.class);
        intent.putExtra("device", device);
        startActivity(intent);
    }
}
