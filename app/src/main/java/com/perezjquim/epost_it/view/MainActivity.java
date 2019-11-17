package com.perezjquim.epost_it.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.perezjquim.PermissionChecker;
import com.perezjquim.epost_it.R;
import com.perezjquim.epost_it.data.StorageHandler;
import com.perezjquim.epost_it.misc.GenericActivity;

public class MainActivity extends GenericActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StorageHandler.test();
        PermissionChecker.init(this);
    }

    public void onButhoothHandler(View view)
    {
        Intent intent = new Intent(this, FindDevicesActivity.class);
        startActivity(intent);
    }

}
