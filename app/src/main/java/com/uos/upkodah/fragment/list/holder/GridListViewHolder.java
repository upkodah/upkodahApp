package com.uos.upkodah.fragment.list.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uos.upkodah.R;
import com.uos.upkodah.databinding.ListItemGridEstateBinding;

public class GridListViewHolder extends RecyclerView.ViewHolder {
    private final ListItemGridEstateBinding binding;
    private OnClickListener listener = null;

    public GridListViewHolder(@NonNull ListItemGridEstateBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    // 리스트 아이템의 세부 내용을 결정하는 메소드
    public void setInfo(final ListDisplayable info){
        binding.setData(info);
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
