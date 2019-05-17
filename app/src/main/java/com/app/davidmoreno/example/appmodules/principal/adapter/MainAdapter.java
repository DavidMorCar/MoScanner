package com.app.davidmoreno.example.appmodules.principal.adapter;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.app.davidmoreno.example.R;
import com.app.davidmoreno.example.appmodules.base.BaseOnActionListener;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<BluetoothDevice> devices;
    private int size;
    private Context context;
    private BaseOnActionListener baseOnActionListener;

    public MainAdapter(Context applicationContext, List<BluetoothDevice> devices,
                       BaseOnActionListener baseOnActionListener) {
        this.context = applicationContext;
        this.devices = devices;
        this.size = devices.size();
        this.baseOnActionListener = baseOnActionListener;
    }

    public void setValues(List<BluetoothDevice> values) {
        devices.clear();
        devices.addAll(values);
        size = values.size();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_device, parent, false);
        return new MainAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final MainAdapter.ViewHolder holder, int position) {

        holder.nameTV.setText(devices.get(position).getName());
        holder.connectivityTV.setText("Connectable");
        holder.connectButton.setClickable(false);
        holder.mView.setOnClickListener(v -> {
            baseOnActionListener.onAction(devices.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView connectivityTV;
        final TextView nameTV;
        final Button connectButton;

        ViewHolder(View view) {
            super(view);
            mView = view;
            connectivityTV = view.findViewById(R.id.deviceConnectedTV);
            nameTV = view.findViewById(R.id.deviceNameTV);
            connectButton = view.findViewById(R.id.deviceConnectButton);
        }
    }
}
