package com.perezjquim.epost_it;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.perezjquim.PermissionChecker;
import android.os.Bundle;
import java.util.ArrayList;

public class MainActivity extends GenericActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionChecker.init(this);
    }

    public void onButhoothHandler(View view)
    {
        Intent intent = new Intent(MainActivity.this, FindDevicesActivity.class);
        startActivity(intent);
    }

}
