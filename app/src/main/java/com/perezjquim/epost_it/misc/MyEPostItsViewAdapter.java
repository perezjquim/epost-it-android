package com.perezjquim.epost_it.misc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.perezjquim.epost_it.R;
import com.perezjquim.epost_it.data.model.ePostIt;

import java.util.ArrayList;

public class MyEPostItsViewAdapter extends RecyclerView.Adapter<MyEPostItsViewAdapter.ViewHolder>
{
    private Context context;
    private ArrayList<ePostIt> ePostIts = new ArrayList<ePostIt>();
    private View.OnClickListener  onClickInterface;

    public MyEPostItsViewAdapter(Context context, ArrayList<ePostIt> ePostIts, View.OnClickListener onClickInterface) {
        this.context = context;
        this.ePostIts = ePostIts;
        this.onClickInterface = onClickInterface;
    }

    @NonNull
    @Override
    public MyEPostItsViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_my_epostits,viewGroup,false);
        MyEPostItsViewAdapter.ViewHolder holder = new MyEPostItsViewAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyEPostItsViewAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.ePostItName.setText(this.ePostIts.get(i).getName());
        viewHolder.ePostItBTAddress.setText(this.ePostIts.get(i).getBTAddress());
        viewHolder.row_my_epostits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickInterface.onClick(v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ePostIts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView ePostItName;
        private TextView ePostItBTAddress;
        private LinearLayout row_my_epostits;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ePostItName = itemView.findViewById(R.id.tvEPostItName);
            ePostItBTAddress = itemView.findViewById(R.id.tvEPostIrAddress);
            row_my_epostits = itemView.findViewById(R.id.row_my_epostits);
        }
    }
}
