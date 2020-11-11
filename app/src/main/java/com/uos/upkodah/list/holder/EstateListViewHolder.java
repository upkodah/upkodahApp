package com.uos.upkodah.list.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uos.upkodah.R;
import com.uos.upkodah.local.position.EstateInformation;

public class EstateListViewHolder extends RecyclerView.ViewHolder {
    private TextView displayedName;
    private TextView displayedAddr;
    private TextView displayedTradeInfo;

    public EstateListViewHolder(@NonNull View itemView) {

        super(itemView);
        this.displayedName = itemView.findViewById(R.id.txt_estate_name);
        this.displayedAddr = itemView.findViewById(R.id.txt_estate_addr);
        this.displayedTradeInfo = itemView.findViewById(R.id.txt_estate_trade_info);
    }
    public void setDisplayedName(String name){
        displayedName.setText(name);
    }
    public void setDisplayedAddr(String addr){
        displayedAddr.setText(addr);
    }
    public void setDisplayedTradeInfo(String info){
        displayedTradeInfo.setText(info);
    }
    public void setEstateInfo(EstateInformation info){
        displayedName.setText(info.getDisplayedName());
        displayedAddr.setText(info.getDisplayedAddr());
        displayedTradeInfo.setText(info.getDisplayedTradeInfo());
    }
}
