package com.uos.upkodah.list.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uos.upkodah.R;

public class KeywordLocationListViewHolder extends RecyclerView.ViewHolder {
    private TextView address;
    private TextView name;

    public KeywordLocationListViewHolder(@NonNull View itemView) {
        super(itemView);

        address = itemView.findViewById(R.id.location_address);
        name = itemView.findViewById(R.id.location_name);
    }
    public void setName(String name){
        this.name.setText(name);
    }
    public void setAddress(String address){
        this.address.setText(address);
    }
}
