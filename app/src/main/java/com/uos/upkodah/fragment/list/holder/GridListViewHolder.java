package com.uos.upkodah.fragment.list.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uos.upkodah.R;

public class GridListViewHolder extends RecyclerView.ViewHolder {
    private final TextView displayedName;
    private final TextView displayedAddr;
    private final TextView displayedTradeInfo;
    private OnClickListener listener = null;

    public GridListViewHolder(@NonNull View itemView) {
        super(itemView);
        this.displayedName = itemView.findViewById(R.id.txt_estate_name);
        this.displayedAddr = itemView.findViewById(R.id.txt_estate_addr);
        this.displayedTradeInfo = itemView.findViewById(R.id.txt_estate_trade_info);
    }

    // 리스트 아이템의 세부 내용을 결정하는 메소드
    public void setInfo(final ListDisplayable info){
        displayedName.setText(info.getListDisplayedName());
        displayedAddr.setText(info.getListDisplayedAddr());
        displayedTradeInfo.setText(info.getListDisplayedDesc());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null) listener.onEstateClick(view, info);
            }
        });
    }
    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    public interface OnClickListener{
        public void onEstateClick(View view, Object o);
    }
}
