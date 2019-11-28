package com.perezjquim.epost_it.view;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.perezjquim.UIHelper;
import com.perezjquim.epost_it.R;
import com.perezjquim.epost_it.data.StorageHandler;
import com.perezjquim.epost_it.misc.BluetoothHandler;
import com.perezjquim.epost_it.misc.GenericActivity;
import com.perezjquim.epost_it.misc.MyRecyclerViewAdapter;

import java.util.ArrayList;

public class FindDevicesActivity extends GenericActivity
{
    private BluetoothHandler bluetoothHandler;
    private ArrayList<BluetoothDevice> devices;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_devices);
        bluetoothHandler = new BluetoothHandler(this);
        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bluetoothHandler.startScan(); // your code
                pullToRefresh.setRefreshing(false);
            }
        });
//        int bluetoothState = bluetoothHandler.checkState();
//        if(bluetoothState == 1){//Bluetooth active
//            bluetoothHandler = new BluetoothHandler(this);
//        }else if(bluetoothState == -1){//No support bluetooth
//            UIHelper.toast(this, getString(R.string.no_bluetooth));
//        }

        devices = new ArrayList<BluetoothDevice>();
        initializeRecycleView();
    }

    private void initializeRecycleView()
    {
        this.recyclerView = findViewById(R.id.rvDevices);
        this.adapter = new MyRecyclerViewAdapter(this, this.devices,(selection) ->
                {
                        ViewGroup parent = (ViewGroup) selection.getParent();
                        int position = parent.indexOfChild(selection);
                        UIHelper.toast(this, "Position " + position);
                        bluetoothHandler.initConnection(this.devices.get(position));
                });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == BluetoothHandler.REQUEST_ENABLE_BT)
        {
            bluetoothHandler.startScan();
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
                bluetoothHandler.disconnect();
    }

    public void addDevice(BluetoothDevice device)
    {
        boolean alreadyExists = StorageHandler.doesPostItExist(device.getAddress());

//        if(!this.devices.contains(device)) {
//        if(!alreadyExists && !this.devices.contains(device))
        if(!this.devices.contains(device))
        {
            this.devices.add(device);
            adapter.notifyItemInserted(this.devices.size() - 1);
        }
    }

    public void removeDevice (BluetoothDevice device)
    {
        if(this.devices.contains(device))
        {
            int position = this.devices.indexOf(device);
            this.devices.remove(device);
            adapter.notifyItemRemoved(position);
        }
    }
}
