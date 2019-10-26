package com.perezjquim.epost_it;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.perezjquim.PermissionChecker;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.setTheme(R.style.AppTheme);
        PermissionChecker.init(this);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            // tratamento das permissões
            case PermissionChecker.REQUEST_CODE:
                PermissionChecker.restart();
                break;
        }
    }
}
