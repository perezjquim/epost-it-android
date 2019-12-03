package com.perezjquim.epost_it.view;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.perezjquim.UIHelper;
import com.perezjquim.epost_it.R;
import com.perezjquim.epost_it.data.StorageHandler;
import com.perezjquim.epost_it.data.model.ePostIt;
import com.perezjquim.epost_it.misc.BluetoothHandler;
import com.perezjquim.epost_it.misc.GenericActivity;
import com.perezjquim.epost_it.misc.MyEPostItsAdapter;

import java.util.ArrayList;

public class MainActivity extends GenericActivity
{
    private ArrayList<ePostIt> epostit_list;
    private RecyclerView recyclerView;
    private MyEPostItsAdapter adapter;
    private BluetoothHandler _btHandler;
    private SearchView _searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BluetoothHandler.test();
        StorageHandler.test();
        epostit_list = new ArrayList<>(StorageHandler.getAllEPostIts());
        this.initializeRecycleView();

        _btHandler = new BluetoothHandler(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        this.epostit_list.clear();
        this.epostit_list.addAll(StorageHandler.getAllEPostIts());
        adapter.notifyDataSetChanged();
    }

    public void onFindDevices(View view)
    {
        Intent intent = new Intent(this, FindDevicesActivity.class);
        startActivity(intent);
    }

    private void initializeRecycleView()
    {
        this.recyclerView = findViewById(R.id.rvMyEPostIts);
        this.adapter = new MyEPostItsAdapter(this, this.epostit_list, (selection) ->
        {
            ViewGroup parent = (ViewGroup) selection.getParent();
            int position = parent.indexOfChild(selection);
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
                _btHandler.writeMessage(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query)
            {
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
