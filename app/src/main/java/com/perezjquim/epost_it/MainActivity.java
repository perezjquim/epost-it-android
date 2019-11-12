package com.perezjquim.epost_it;

import com.perezjquim.PermissionChecker;
import com.perezjquim.epost_it.data.StorageHandler;

import android.os.Bundle;

public class MainActivity extends GenericActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StorageHandler.test();
    }
}
