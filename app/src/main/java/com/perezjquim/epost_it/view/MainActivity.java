package com.perezjquim.epost_it.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.perezjquim.PermissionChecker;
import com.perezjquim.UIHelper;
import com.perezjquim.epost_it.R;
import com.perezjquim.epost_it.data.StorageHandler;
import com.perezjquim.epost_it.data.model.*;
import com.perezjquim.epost_it.misc.GenericActivity;
import com.perezjquim.epost_it.misc.MyEPostItsViewAdapter;
import com.perezjquim.epost_it.misc.MyRecyclerViewAdapter;

import java.util.*;

public class MainActivity extends GenericActivity
{
    private ArrayList<ePostIt> epostit_list;
    private RecyclerView recyclerView;
    private MyEPostItsViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StorageHandler.test();
        epostit_list = new ArrayList<>(StorageHandler.getAllEPostIts());
        //System.out.println(epostit_list.size());
        this.initializeRecycleView();
    }

    public void onButhoothHandler(View view)
    {
        Intent intent = new Intent(this, FindDevicesActivity.class);
        startActivity(intent);
    }

    private void initializeRecycleView()
    {
        this.recyclerView = findViewById(R.id.rvMyEPostIts);
        this.adapter = new MyEPostItsViewAdapter(this, this.epostit_list,(selection) ->
        {
            ViewGroup parent = (ViewGroup) selection.getParent();
            int position = parent.indexOfChild(selection);
            UIHelper.toast(this, "Position " + position);
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
