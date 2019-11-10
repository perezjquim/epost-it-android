package com.perezjquim.epost_it;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>
{
    private Context context;
//    private ArrayList<String> devicesNames = new ArrayList<String>();
    private ArrayList<BluetoothDevice> devices = new ArrayList<BluetoothDevice>();

    public MyRecyclerViewAdapter(Context context, ArrayList<BluetoothDevice> devices) {
        this.context = context;
        //this.devicesNames = devicesNames;
        this.devices = devices;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_devices_row,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        //viewHolder.tvDeviceName.setText(devicesNames.get(i));
        viewHolder.tvDeviceName.setText(getDeviceName(devices.get(i)));
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    public String getDeviceName(BluetoothDevice device)
    {
        String deviceId = "";
        if(device.getName() == null){
            deviceId = device.getAddress();
        }else{
            deviceId = device.getName();
        }
        return deviceId;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDeviceName;
//        private Button bPairUnPair;
        private LinearLayout rowLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDeviceName = itemView.findViewById(R.id.tvDeviceName);
//            bPairUnPair = itemView.findViewById(R.id.bPairUnPair);
            rowLayout = itemView.findViewById(R.id.row_layout);
        }
    }
}
