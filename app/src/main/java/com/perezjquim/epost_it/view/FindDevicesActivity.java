package com.perezjquim.epost_it.view;

import android.app.SearchManager;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.ViewGroup;

import com.perezjquim.UIHelper;
import com.perezjquim.epost_it.R;
import com.perezjquim.epost_it.data.StorageHandler;
import com.perezjquim.epost_it.misc.BluetoothHandler;
import com.perezjquim.epost_it.misc.FindDevicesAdapter;
import com.perezjquim.epost_it.misc.GenericActivity;

import java.util.ArrayList;

public class FindDevicesActivity extends GenericActivity
{
    private BluetoothHandler bluetoothHandler;
    private ArrayList<BluetoothDevice> devices;
    private RecyclerView recyclerView;
    private FindDevicesAdapter adapter;
    private SearchView _searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_devices);

        if (bluetoothHandler == null) bluetoothHandler = new BluetoothHandler(this);
        if (devices == null) devices = new ArrayList<BluetoothDevice>();

        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                bluetoothHandler.startScan(); // your code
                pullToRefresh.setRefreshing(false);
            }
        });

        initializeRecycleView();
    }

    private void initializeRecycleView()
    {
        this.recyclerView = findViewById(R.id.rvDevices);
        this.adapter = new FindDevicesAdapter(this, this.devices, (selection) ->
        {
            ViewGroup parent = (ViewGroup) selection.getParent();
            int position = parent.indexOfChild(selection);
//            UIHelper.toast(this, "Position " + position);
            bluetoothHandler.initConnection(this.devices.get(position));
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BluetoothHandler.REQUEST_ENABLE_BT)
        {
            bluetoothHandler.startScan();
        }
    }

    public void addDevice(BluetoothDevice device)
    {
        boolean alreadyExists = StorageHandler.doesPostItExist(device.getAddress());

//        if(!this.devices.contains(device)) {
        if (!alreadyExists && !this.devices.contains(device))
//        if (!this.devices.contains(device))
        {
            this.devices.add(device);
            adapter.notifyItemInserted(this.devices.size() - 1);
        }
    }

    public void removeDevice(BluetoothDevice device)
    {
        if (this.devices.contains(device))
        {
            int position = this.devices.indexOf(device);
            this.devices.remove(device);
            adapter.notifyItemRemoved(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        _searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();

        _searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));

        _searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change

        Context c = this;

        _searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                UIHelper.toast(c, query);
                adapter.getFilter().filter(query);
//                bluetoothHandler.writeMessage(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query)
            {
                // filter recycler view when text is changed
                adapter.getFilter().filter(query);
                return true;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed()
    {
        // close search view on back button pressed
        if (!_searchView.isIconified())
        {
            _searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
}
