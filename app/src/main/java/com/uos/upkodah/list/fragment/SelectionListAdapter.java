package com.uos.upkodah.list.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SelectionListAdapter extends RecyclerView.Adapter{
    private SelectionListFragment.ViewHolderManager manager;

    public SelectionListAdapter(SelectionListFragment.ViewHolderManager manager){
        this.manager = manager;
    }

    /**
     * 해당 메소드는 RecyclerView의 요청에 따라 삽입될 뷰를 준비하는 메소드다.
     * 여기에서 Inflater를 이용해 ViewHolder를 생성한다.
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(manager.getLayoutId(), parent,false);

        RecyclerView.ViewHolder holder = manager.generate(v);

        return holder;
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