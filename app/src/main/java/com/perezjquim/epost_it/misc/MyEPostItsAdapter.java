package com.perezjquim.epost_it.misc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.bluetooth.BluetoothDevice;

import com.perezjquim.epost_it.R;
import com.perezjquim.epost_it.data.model.ePostIt;

import java.util.ArrayList;

public class MyEPostItsAdapter extends RecyclerView.Adapter<MyEPostItsAdapter.ViewHolder>
{
    private Context context;
    private ArrayList<ePostIt> ePostIts = new ArrayList<>();
    private View.OnClickListener onClickInterface;
    private View.OnLongClickListener onLongClickListener;

    public MyEPostItsAdapter(Context context, ArrayList<ePostIt> ePostIts, View.OnClickListener onClickInterface, View.OnLongClickListener onLongClickListener)
    {
        this.context = context;
        this.ePostIts = ePostIts;
        this.onClickInterface = onClickInterface;
        this.onLongClickListener = onLongClickListener;
    }

    @NonNull
    @Override
    public MyEPostItsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_my_epostits, viewGroup, false);
        MyEPostItsAdapter.ViewHolder holder = new MyEPostItsAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyEPostItsAdapter.ViewHolder viewHolder, final int i)
    {
        ePostIt e = this.ePostIts.get(i);

        String btAddress = e.getBTAddress();
        viewHolder._addr = btAddress;
        viewHolder.ePostItName.setText(e.getName());
        viewHolder.ePostItBTAddress.setText(btAddress);

        LinearLayout l = viewHolder.row_my_epostits;
        l.setOnClickListener((v) -> onClickInterface.onClick(v));
        l.setOnLongClickListener((v) -> onLongClickListener.onLongClick(v));
    }

    @Override
    public int getItemCount()
    {
        return ePostIts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private String _addr;
        private TextView ePostItName;
        private TextView ePostItBTAddress;
        private LinearLayout row_my_epostits;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            ePostItName = itemView.findViewById(R.id.tvEPostItName);
            ePostItBTAddress = itemView.findViewById(R.id.tvEPostIrAddress);
            row_my_epostits = itemView.findViewById(R.id.row_my_epostits);
        }

        public String getAddress()
        {
            return _addr;
        }
    }
}
