package com.perezjquim.epost_it.misc;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothConfiguration;
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothService;
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothStatus;
import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothWriter;
import com.perezjquim.UIHelper;
import com.perezjquim.epost_it.R;
import com.perezjquim.epost_it.data.StorageHandler;
import com.perezjquim.epost_it.view.FindDevicesActivity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class BluetoothHandler
{

    private FindDevicesActivity findDevicesActivity;
    private Activity activity;
    public final static int REQUEST_ENABLE_BT = 1;
    private static ArrayList<BluetoothDevice> devicesPaired = new ArrayList<BluetoothDevice>();
    private static BluetoothConfiguration config;
    private static BluetoothService mainService;
    private static ArrayList<BluetoothService> clientServices;
    private static HashMap<BluetoothService, BluetoothWriter> writers;
    private static final UUID UUID_DEVICE = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    private static final UUID UUID_SERVICE = UUID.fromString("e7810a71-73ae-499d-8c15-faa9aef0c3f2");
    private static final UUID UUID_CHARACTERISTIC = UUID.fromString("bef8d6c9-9c21-4c9e-b632-bd58c1009f9f");

    public BluetoothHandler(Context context)
    {
        this.activity = (Activity) context;
        this.findDevicesActivity = verifyFindDevicesActivity(this.activity);
        prepare();
    }

    public void prepare()
    {
        if (this.config == null)
        {
            this.config = new BluetoothConfiguration();
            this.config.bluetoothServiceClass = BluetoothServiceExt.class;
            this.config.context = this.activity.getApplicationContext();
            this.config.bufferSize = 1024;
            this.config.characterDelimiter = '\n';
            this.config.deviceName = "Bluetooth Sample";
            this.config.callListenersInMainThread = true;
            this.config.uuid = UUID_DEVICE; // For Classic
            this.config.uuidService = UUID_SERVICE; // For BLE
            this.config.uuidCharacteristic = UUID_CHARACTERISTIC; // For BLE
            this.config.transport = BluetoothDevice.TRANSPORT_LE; // Only for dual-mode devices

            BluetoothService.init(config);
        }

        if (mainService == null) mainService = BluetoothService.getDefaultInstance();

//        service = new BluetoothServiceExt(config);
        if (clientServices == null) clientServices = new ArrayList<>();
        if (writers == null) writers = new HashMap<>();

//        this.writer = new BluetoothWriter(service);

        prepareScan();
    }

    private void prepareScan()
    {
        int state = checkState();
        if (state == -1)
        {//Bluetooth active
            UIHelper.toast(activity, activity.getString(R.string.no_bluetooth));
            return;
        } else if (state == 1)
        {
            mainService.startScan();
        }

        mainService.setOnScanCallback(new BluetoothService.OnBluetoothScanCallback()
        {
            @Override
            public void onDeviceDiscovered(BluetoothDevice device, int rssi)
            {
                if (findDevicesActivity != null)
                {
                    findDevicesActivity.addDevice(device);
                }
            }

            @Override
            public void onStartScan()
            {
                UIHelper.toast(activity, "Scanning start...");
                System.out.println("-- BT -- SCAN START");
//                UIHelper.openProgressDialog(activity,"Scanning start...");
            }

            @Override
            public void onStopScan()
            {
                UIHelper.toast(activity, "Scanning stop...");
                System.out.println("-- BT -- SCAN STOP");
//                UIHelper.closeProgressDialog(activity);
            }
        });

    }

    public void startScan()
    {
        int state = checkState();
        if (state == -1)
        {//Bluetooth active
            UIHelper.toast(activity, activity.getString(R.string.no_bluetooth));
            return;
        }
        mainService.startScan();
    }

    private int checkState()
    {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null)
        {
            // Device does not support Bluetooth
            return -1;
        }
        if (!mBluetoothAdapter.isEnabled())
        {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            return 0;
        } else
        {
//            service.startScan();
            return 1;
        }
    }

    public void initConnection(BluetoothDevice device)
    {
        BluetoothServiceExt service = new BluetoothServiceExt(config);

        clientServices.add(service);

        BluetoothWriter writer = new BluetoothWriter(service);

        writers.put(service, writer);

        service.setOnEventCallback(new BluetoothService.OnBluetoothEventCallback()
        {
            @Override
            public void onDataRead(byte[] buffer, int length)
            {
                String s = new String(buffer, 0, length) + "\n";
                UIHelper.toast(activity, s);

                System.out.println("-- BT -- READ: " + s);
            }

            @Override
            public void onStatusChange(BluetoothStatus status)
            {
                if (status == BluetoothStatus.CONNECTED)
                {
                    String name = device.getName();
                    if (name == null || name.equals(""))
                    {
                        name = activity.getResources().getString(R.string.no_device_name);
                    }
                    StorageHandler.insertEPostIt(device.getAddress(), name);
                    //remover o device da lista de devices
                    if (findDevicesActivity != null)
                    {
                        findDevicesActivity.removeDevice(device);
                    }
                    devicesPaired.add(device);

//                    UIHelper.closeProgressDialog(activity);
                    UIHelper.toast(activity, "Paired");
                    System.out.println("-- BT -- PAIRED");

                } else if (status == BluetoothStatus.NONE)
                {
                    devicesPaired.remove(device);
                    clientServices.remove(service);
                    writers.remove(service);

//                    UIHelper.closeProgressDialog(activity);
                    UIHelper.toast(activity, "Unpaired");
                    System.out.println("-- BT -- CONNECTION LOST / UNPAIRED");
                } else if (status == BluetoothStatus.CONNECTING)
                {
//                    UIHelper.openProgressDialog(activity, "Connecting..");
                    System.out.println("-- BT -- CONNECTING");
                }
            }

            @Override
            public void onDeviceName(String deviceName)
            {
            }

            @Override
            public void onToast(String message)
            {
            }

            @Override
            public void onDataWrite(byte[] buffer)
            {
                String s = new String(buffer);
                System.out.println("-- BT -- WRITE:" + s);
            }
        });

        service.connect(device);
    }

    public void writeMessage(String aMsg)
    {
        String msg = createMessage("SEARCH", aMsg.replace(" ",","));

        for (Map.Entry<BluetoothService, BluetoothWriter> entry : writers.entrySet())
        {
            BluetoothWriter w = entry.getValue();
            w.writeln(msg);
        }
    }

    private FindDevicesActivity verifyFindDevicesActivity(Activity activity)
    {
        Log.i("CLASSNAME", activity.getClass().getSimpleName());
        if (activity.getClass().getSimpleName().equals("FindDevicesActivity"))
        {
            return (FindDevicesActivity) activity;
        }
        return null;
    }

    public String createMessage(String action, String msgCode)
    {
        return "#"+"EPOSTIT" + "#" + action + "#" + msgCode;
    }

    public void disconnect()
    {
//        service.disconnect();
    }

    public static void test()
    {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device : pairedDevices)
        {
            try
            {
                Method m = device.getClass()
                        .getMethod("removeBond", (Class[]) null);
                m.invoke(device, (Object[]) null);
            } catch (Exception e)
            {
                Log.e("fail", e.getMessage());
            }
        }
    }

}
