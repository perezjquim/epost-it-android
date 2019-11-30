package com.perezjquim.epost_it.misc;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.douglasjunior.bluetoothclassiclibrary.BluetoothStatus;
import com.perezjquim.epost_it.R;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable
{
    private Context context;
    private ArrayList<BluetoothDevice> devices = new ArrayList<>();
    private ArrayList<BluetoothDevice> deviceListFiltered;
    private View.OnClickListener  onClickInterface;

    public MyRecyclerViewAdapter(Context context, ArrayList<BluetoothDevice> devices, View.OnClickListener onClickInterface) {
        this.context = context;
        this.devices = devices;
        this.deviceListFiltered = devices;
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
        viewHolder.tvDeviceName.setText(getDeviceName(deviceListFiltered.get(i)));
        viewHolder.tvDeviceAddress.setText(deviceListFiltered.get(i).getAddress());
        viewHolder.rowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickInterface.onClick(v);
            }
        });
    }



    @Override
    public int getItemCount() {
        if(deviceListFiltered != null)
        {
            return deviceListFiltered.size();
        }
        else
        {
            return 0;
        }
    }

    public String getDeviceName(BluetoothDevice device)
    {
//        String deviceId = "";
        if(device.getName() == null || device.getName().equals("")){
            return context.getResources().getString(R.string.no_device_name);
//            deviceId = device.getAddress();
        }
//        else{
//            deviceId = device.getName();
//        }
        return device.getName();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    deviceListFiltered = devices;
                } else if(charString != null){
                    ArrayList<BluetoothDevice> filteredList = new ArrayList<>();
                    for (BluetoothDevice row : devices) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getName().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    deviceListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = deviceListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                deviceListFiltered = (ArrayList<BluetoothDevice>) filterResults.values;
//                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDeviceName;
        private TextView tvDeviceAddress;
        private LinearLayout rowLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDeviceName = itemView.findViewById(R.id.tvDeviceName);
            tvDeviceAddress = itemView.findViewById(R.id.tvDeviceAddress);
            rowLayout = itemView.findViewById(R.id.row_layout);
        }
    }
}
