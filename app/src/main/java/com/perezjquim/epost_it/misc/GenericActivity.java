package com.perezjquim.epost_it.misc;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.perezjquim.PermissionChecker;
import com.perezjquim.epost_it.R;

public class GenericActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        super.setTheme(R.style.AppTheme);
//        PermissionChecker.init(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            // tratamento das permissões
            case PermissionChecker.REQUEST_CODE:
//                PermissionChecker.restart();
                break;
        }
    }
}
