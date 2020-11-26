package com.uos.upkodah.fragment.list.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uos.upkodah.R;

public class LinearListViewHolder extends RecyclerView.ViewHolder {
    private TextView address;
    private TextView name;

    public LinearListViewHolder(@NonNull View itemView) {
        super(itemView);

        address = itemView.findViewById(R.id.location_address);
        name = itemView.findViewById(R.id.location_name);
    }
    public void setInfo(final ListDisplayable info){
        this.name.setText(info.getListDisplayedName());
        this.address.setText(info.getListDisplayedAddr());
    }
}
