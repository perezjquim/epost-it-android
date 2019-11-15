package com.perezjquim.epost_it.misc;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.perezjquim.epost_it.R;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>
{
    private Context context;
    private ArrayList<BluetoothDevice> devices = new ArrayList<>();
    private View.OnClickListener  onClickInterface;

    public MyRecyclerViewAdapter(Context context, ArrayList<BluetoothDevice> devices, View.OnClickListener onClickInterface) {
        this.context = context;
        this.devices = devices;
        this.onClickInterface = onClickInterface;
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
        viewHolder.tvDeviceName.setText(getDeviceName(devices.get(i)));
        viewHolder.rowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickInterface.onClick(v);
            }
        });
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
        private LinearLayout rowLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDeviceName = itemView.findViewById(R.id.tvDeviceName);
            rowLayout = itemView.findViewById(R.id.row_layout);
        }
    }
}
