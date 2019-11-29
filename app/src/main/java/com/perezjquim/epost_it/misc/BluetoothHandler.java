package com.perezjquim.epost_it.misc;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.ParcelUuid;
import android.util.Log;
import android.widget.Toast;

import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothClassicService;
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothConfiguration;
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothService;
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothStatus;
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothWriter;
import com.perezjquim.UIHelper;
import com.perezjquim.epost_it.R;
import com.perezjquim.epost_it.data.StorageHandler;
import com.perezjquim.epost_it.view.FindDevicesActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.UUID;

public class BluetoothHandler {

    private FindDevicesActivity findDevicesActivityctivity;
    private Activity activity;
    public final static int REQUEST_ENABLE_BT = 1;
    private static ArrayList<BluetoothDevice> devicesPaired = new ArrayList<BluetoothDevice>();
    private BluetoothConfiguration config;
    private BluetoothService service;
    private BluetoothWriter writer;
    private static final UUID UUID_DEVICE = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    private static final UUID UUID_SERVICE = UUID.fromString("e7810a71-73ae-499d-8c15-faa9aef0c3f2");
    private static final UUID UUID_CHARACTERISTIC = UUID.fromString("bef8d6c9-9c21-4c9e-b632-bd58c1009f9f");

    public BluetoothHandler(Context context)
    {
        this.activity =  (Activity) context;
        this.findDevicesActivityctivity = verifyFindDevicesActivity(this.activity);
        prepare();
    }

    public void prepare()
    {
        this.config = new BluetoothConfiguration();
        this.config.bluetoothServiceClass = BluetoothClassicService.class;
        this.config.context = this.activity.getApplicationContext();
        this.config.bufferSize = 2048;
        this.config.characterDelimiter = '\n';
        this.config.deviceName = "Bluetooth Sample";
        this.config.callListenersInMainThread = true;

        this.config.uuid = UUID_DEVICE; // For Classic


        this.config.uuidService = UUID_SERVICE; // For BLE
        this.config.uuidCharacteristic = UUID_CHARACTERISTIC; // For BLE
        this.config.transport = BluetoothDevice.TRANSPORT_LE; // Only for dual-mode devices

        BluetoothService.init(config);

        service = BluetoothService.getDefaultInstance();

        this.writer = new BluetoothWriter(service);

        prepareScan();
    }

    private void prepareScan()
    {
        int state = checkState();
        if(state == -1){//Bluetooth active
            UIHelper.toast(activity, activity.getString(R.string.no_bluetooth));
            return;
        } else if( state == 1)
        {
            service.startScan();
        }

        service.setOnScanCallback(new BluetoothService.OnBluetoothScanCallback() {
            @Override
            public void onDeviceDiscovered(BluetoothDevice device, int rssi) {
                if(findDevicesActivityctivity != null) {
                    findDevicesActivityctivity.addDevice(device);
                }
            }
            @Override
            public void onStartScan() {
                Toast.makeText(activity, "Scanning start...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopScan() {
                Toast.makeText(activity, "Scanning stop...", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void startScan()
    {
        int state = checkState();
        if(state == -1){//Bluetooth active
            UIHelper.toast(activity, activity.getString(R.string.no_bluetooth));
            return;
        }
        service.startScan();
    }

    private int checkState()
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
//            service.startScan();
            return 1;
        }
    }

    public void initConnection(BluetoothDevice device)
    {
        service.setOnEventCallback(new BluetoothService.OnBluetoothEventCallback() {
            @Override
            public void onDataRead(byte[] buffer, int length) {
               String s = new String(buffer, 0, length) + "\n";
                Toast.makeText(activity, s , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStatusChange(BluetoothStatus status) {
                if (status == BluetoothStatus.CONNECTED) {
                    String name = device.getName();
                    if(name == null || name.equals("")){
                        name = activity.getResources().getString(R.string.no_device_name);
                    }
                    StorageHandler.insertEPostIt(device.getAddress(),name);
                    //remover o device da lista de devices
                    if(findDevicesActivityctivity != null) {
                        findDevicesActivityctivity.removeDevice(device);
                    }
                    devicesPaired.add(device);
                    UIHelper.toast(activity, "Paired");

                    writeMessage("test");
                }
            }

            @Override
            public void onDeviceName(String deviceName) {
            }

            @Override
            public void onToast(String message) {
            }

            @Override
            public void onDataWrite(byte[] buffer) {
            }
        });
        service.connect(device);
    }

    public void writeMessage(String aMsg)
    {
        final String msg = createMessage("SEARCH",aMsg);

        UIHelper.runOnUiThread(() -> writer.writeln(msg));
    }

    private FindDevicesActivity verifyFindDevicesActivity(Activity activity)
    {
        Log.i("CLASSNAME",activity.getClass().getSimpleName());
        if(activity.getClass().getSimpleName().equals("FindDevicesActivity"))
        {
            return (FindDevicesActivity) activity;
        }
        return null;
    }

    public String createMessage(String action, String msgCode)
    {
        return "EPOSTIT"+"#"+action+"#"+msgCode;
    }

    public void disconnect()
    {
        service.disconnect();
    }

}
