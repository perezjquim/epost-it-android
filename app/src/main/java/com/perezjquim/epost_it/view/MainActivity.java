package com.perezjquim.epost_it.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.perezjquim.PermissionChecker;
import com.perezjquim.epost_it.R;
import com.perezjquim.epost_it.data.StorageHandler;
import com.perezjquim.epost_it.data.model.*;
import com.perezjquim.epost_it.misc.GenericActivity;

import java.util.*;

public class MainActivity extends GenericActivity
{
    private ArrayList<ePostIt> epostit_list;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionChecker.init(this);

        epostit_list = new ArrayList<>(StorageHandler.getAllEPostIts());
        System.out.println(epostit_list.size());
    }

    public void onButhoothHandler(View view)
    {
        Intent intent = new Intent(this, FindDevicesActivity.class);
        startActivity(intent);
    }

}
