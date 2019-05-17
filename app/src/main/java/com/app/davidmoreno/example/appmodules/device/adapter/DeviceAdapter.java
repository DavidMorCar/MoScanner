package com.app.davidmoreno.example.appmodules.device.adapter;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.davidmoreno.example.R;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {

    private BluetoothDevice device;
    private int size;
    private Context context;

    public DeviceAdapter(Context applicationContext, BluetoothDevice device) {
        this.context = applicationContext;
        this.device = device;
        this.size = device.getUuids().length;
    }

    public void setValues(BluetoothDevice value) {
        device = value;
        size = device.getUuids().length;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DeviceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_device, parent, false);
        return new DeviceAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final DeviceAdapter.ViewHolder holder, int position) {
        //todo uuid characteristics functionality
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView uuidTV;
        final TextView accessTV;
        final TextView valueTV;

        ViewHolder(View view) {
            super(view);
            mView = view;
            uuidTV = view.findViewById(R.id.itemCharacteristicUUIDTV);
            accessTV = view.findViewById(R.id.itemCharacteristicAccessTV);
            valueTV = view.findViewById(R.id.itemCharacteristicValueTV);
        }
    }
}
