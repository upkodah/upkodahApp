package com.uos.upkodah.fragment.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uos.upkodah.fragment.list.holder.ListViewHolderManager;

public class SelectionListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final ListViewHolderManager manager;

    public SelectionListAdapter(ListViewHolderManager manager){
        this.manager = manager;
    }

    /**
     * 해당 메소드는 RecyclerView의 요청에 따라 삽입될 뷰를 준비하는 메소드다.
     * 여기에서 Inflater를 이용해 ViewHolder를 생성한다.
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = LayoutInflater.from(parent.getContext()).inflate(manager.getLayoutId(), parent,false);

        return manager.generate(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        manager.setViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return manager.getItemCount();
    }
}