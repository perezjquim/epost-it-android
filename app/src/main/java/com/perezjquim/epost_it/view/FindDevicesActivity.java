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
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

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
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_devices);

        if(savedInstanceState == null)
        {
            bluetoothHandler = new BluetoothHandler(this);
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
//        int bluetoothState = bluetoothHandler.checkState();
//        if(bluetoothState == 1){//Bluetooth active
//            bluetoothHandler = new BluetoothHandler(this);
//        }else if(bluetoothState == -1){//No support bluetooth
//            UIHelper.toast(this, getString(R.string.no_bluetooth));
//        }

            devices = new ArrayList<BluetoothDevice>();
            initializeRecycleView();
        }
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
//        super.onDestroy();
//                bluetoothHandler.disconnect();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                Toast.makeText(FindDevicesActivity.this, query, Toast.LENGTH_SHORT).show();
                adapter.getFilter().filter(query);

                bluetoothHandler.writeMessage(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                adapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed()
    {
        if( !searchView.isIconified())
        {
            searchView.setIconified(true);
            return;
        }

        super.onBackPressed();
    }
}
