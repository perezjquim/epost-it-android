package com.perezjquim.epost_it;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.perezjquim.PermissionChecker;

public class BluetoothHandler {

    private BluetoothAdapter adapter;
    private BroadcastReceiver mReceiver;
    private Activity activity;

    public BluetoothHandler(Activity activity)
    {
        this.activity = activity;
        this.adapter = BluetoothAdapter.getDefaultAdapter();
        this.mReceiver = initializeReceiver();
    }

    public void scanDevices()
    {
        IntentFilter filter = new IntentFilter();

        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        activity.registerReceiver(mReceiver, filter);
        adapter.startDiscovery();
    }

    private BroadcastReceiver initializeReceiver()
    {
        return mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                    //discovery starts, we can show progress dialog or perform other tasks
                } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                    //discovery finishes, dismis progress dialog
                } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    //bluetooth device found
                    BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    Toast.makeText(context, "Found device " + device.getName(),
                            Toast.LENGTH_LONG).show();
                }
            }
        };
    }
}
