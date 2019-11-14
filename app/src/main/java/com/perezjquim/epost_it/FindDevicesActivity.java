package com.perezjquim.epost_it;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class FindDevicesActivity extends GenericActivity{
    private BluetoothHandler bluetoothHandler;
    private ArrayList<BluetoothDevice> devices = new ArrayList<BluetoothDevice>();
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private onClickInterface onClickInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_devices);
        bluetoothHandler = new BluetoothHandler(FindDevicesActivity.this);
        onClickInterface = initializeClickInterface();
        int bluetoothState = bluetoothHandler.bluetoothState();
        if(bluetoothState == 1){//Bluetooth active
            bluetoothHandler.scanDevices();
        }else if(bluetoothState == -1){//No support bluetooth
            Toast.makeText(this, getString(R.string.no_bluetooth), Toast.LENGTH_SHORT).show();
        }
        initializeRecycleView();
    }

    private onClickInterface initializeClickInterface()
    {
        return onClickInterface = new onClickInterface() {
            @Override
            public void setClick(int position) {
                Toast.makeText(FindDevicesActivity.this, "Position " + position, Toast.LENGTH_SHORT).show();
                bluetoothHandler.pairDevice(devices.get(position));
            }
        };
    }

    private void initializeRecycleView()
    {
        this.recyclerView = findViewById(R.id.rvDevices);
        this.adapter = new MyRecyclerViewAdapter(this, this.devices,onClickInterface);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == bluetoothHandler.getREQUEST_ENABLE_BT())
        {
            bluetoothHandler.scanDevices();
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(bluetoothHandler.getMReceiver());
        super.onDestroy();
    }

    public void addDevice(BluetoothDevice device)
    {
        if(!this.devices.contains(device)) {
            this.devices.add(device);
            adapter.notifyItemInserted(this.devices.size() - 1);
        }
    }
}
