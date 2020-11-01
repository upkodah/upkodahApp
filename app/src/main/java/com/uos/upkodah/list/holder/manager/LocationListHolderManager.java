package com.uos.upkodah.list.holder.manager;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.uos.upkodah.list.fragment.SelectionListFragment;

public class LocationListHolderManager implements SelectionListFragment.ViewHolderManager {


    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder generate(View view) {
        return null;
    }

    @Override
    public void setViewHolder(RecyclerView.ViewHolder viewHolder, int index) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
