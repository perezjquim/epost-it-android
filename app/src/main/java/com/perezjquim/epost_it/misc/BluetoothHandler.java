package com.perezjquim.epost_it.misc;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.perezjquim.UIHelper;
import com.perezjquim.epost_it.data.StorageHandler;
import com.perezjquim.epost_it.view.FindDevicesActivity;

import java.lang.reflect.Method;

public class BluetoothHandler {

    private BluetoothAdapter adapter;
    private BroadcastReceiver mReceiver;
    private FindDevicesActivity activity;
    public final static int REQUEST_ENABLE_BT = 1;

    public BluetoothHandler(FindDevicesActivity activity)
    {
        this.activity = activity;
        this.adapter = BluetoothAdapter.getDefaultAdapter();
        this.mReceiver = initializeReceiver();
    }

    public void scanDevices()
    {
//        UIHelper.openProgressDialog(activity, "Scanning..");

        IntentFilter filter = new IntentFilter();

        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        activity.registerReceiver(mReceiver, filter);
        adapter.startDiscovery();
    }

    public int bluetoothState()
    {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            return -1;
        }
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            return 0;
        }else{
            return 1;
        }
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
//                    UIHelper.closeProgressDialog(activity);
                } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    //bluetooth device found
                    BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    activity.addDevice(device);
                }
                //pair/unpair device
                else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                    final int state        = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR);
                    final int prevState    = intent.getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE, BluetoothDevice.ERROR);

                    BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    if (state == BluetoothDevice.BOND_BONDED && prevState == BluetoothDevice.BOND_BONDING) {
                        UIHelper.toast(context, "Paired");
                        StorageHandler.insertEPostIt(device.getAddress());
                    } else if (state == BluetoothDevice.BOND_NONE && prevState == BluetoothDevice.BOND_BONDED){
                        UIHelper.toast(context, "Unpaired");
                        StorageHandler.deleteEPostIt(device.getAddress());
                    }

                }
            }
        };
    }

    public void pairDevice(BluetoothDevice device)
    {
        try {
            Method method = device.getClass().getMethod("createBond", (Class[]) null);
            method.invoke(device, (Object[]) null);
            IntentFilter intent = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
            activity.registerReceiver(mReceiver, intent);

//            StorageHandler.insertEPostIt(device.getAddress());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unpairDevice(BluetoothDevice device) {
        try {
            Method method = device.getClass().getMethod("removeBond", (Class[]) null);
            method.invoke(device, (Object[]) null);

//            StorageHandler.deleteEPostIt(device.getAddress());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BroadcastReceiver getMReceiver(){return this.mReceiver;}
}
