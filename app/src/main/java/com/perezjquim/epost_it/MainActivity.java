package com.perezjquim.epost_it;

<<<<<<< HEAD
=======
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

>>>>>>> 446b73413e4f7ea6914fcda5a306f5285d778a6a
import com.perezjquim.PermissionChecker;
import android.os.Bundle;

<<<<<<< HEAD
public class MainActivity extends GenericActivity
=======
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
>>>>>>> 446b73413e4f7ea6914fcda5a306f5285d778a6a
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
<<<<<<< HEAD
=======
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
>>>>>>> 446b73413e4f7ea6914fcda5a306f5285d778a6a
    }


    public void onButhoothHandler(View view)
    {
        Intent intent = new Intent(MainActivity.this, FindDevicesActivity.class);
        startActivity(intent);
    }

}
