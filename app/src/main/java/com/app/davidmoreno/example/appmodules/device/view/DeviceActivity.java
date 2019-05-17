package com.app.davidmoreno.example.appmodules.device.view;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.bluetooth.BluetoothDevice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.davidmoreno.example.R;
import com.app.davidmoreno.example.appmodules.base.BaseActivity;
import com.app.davidmoreno.example.appmodules.device.viewmodel.DeviceViewModel;
import com.app.davidmoreno.example.appmodules.principal.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;

public class DeviceActivity extends BaseActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private DeviceViewModel deviceViewModel;
    TextView nameTV;
    TextView dataTV;
    TextView uuidTV;
    RecyclerView listView;
    BluetoothDevice device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        nameTV = (TextView) findViewById(R.id.deviceNameTV);
        dataTV = (TextView) findViewById(R.id.deviceDataTV);
        uuidTV = (TextView) findViewById(R.id.deviceUUIDTV);
        listView = (RecyclerView) findViewById(R.id.deviceRecyclerView);
        this.listView.setLayoutManager(new LinearLayoutManager(this));
        listView.addItemDecoration(new DividerItemDecoration(listView.getContext(), DividerItemDecoration.VERTICAL));
        device = Objects.requireNonNull(getIntent().getExtras()).getParcelable("device");
        setTitle(Objects.requireNonNull(device).getName());
        initViewModel();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupToolbar();
    }

    private void setupToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @SuppressLint("SetTextI18n")
    private void initViewModel() {
        deviceViewModel = ViewModelProviders.of(this, viewModelFactory).get(DeviceViewModel.class);
        deviceViewModel.getPost().observe(this, post -> {
            if (post == null) {
                Toast.makeText(this, "Error loading data...",
                        Toast.LENGTH_LONG).show();
            } else {
                nameTV.setText(nameTV.getText().toString() + " " + post.getName());
                dataTV.setText(dataTV.getText().toString() + " " + post.getAddress());
                //todo uuid characteristics functionality
            }
        });
        deviceViewModel.setDevice(device);
    }
}
