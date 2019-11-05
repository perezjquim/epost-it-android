package com.perezjquim.epost_it;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class FindDevicesActivity extends AppCompatActivity {
    private BluetoothHandler bluetoothHandler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bluetoothHandler = new BluetoothHandler(FindDevicesActivity.this);
        bluetoothHandler.scanDevices();
    }

}
