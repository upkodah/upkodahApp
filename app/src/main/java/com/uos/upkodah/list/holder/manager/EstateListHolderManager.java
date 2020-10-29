package com.uos.upkodah.list.holder.manager;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.uos.upkodah.R;
import com.uos.upkodah.list.fragment.SelectionListFragment;
import com.uos.upkodah.list.holder.EstateListViewHolder;
import com.uos.upkodah.local.position.EstateInformation;

import java.util.List;

public class EstateListHolderManager implements SelectionListFragment.ViewHolderManager {
    private List<EstateInformation> estates;

    public EstateListHolderManager(List<EstateInformation> estates){
        this.estates = estates;
    }


    @Override
    public int getLayoutId() {
        return R.layout.list_item_estate;
    }

    @Override
    public RecyclerView.ViewHolder generate(View view) {
        return new EstateListViewHolder(view);
    }

    @Override
    public void setViewHolder(RecyclerView.ViewHolder viewHolder, int index) {
        // ViewHolder에서 뷰를 가져온다.
        ((TextView) viewHolder.itemView.findViewById(R.id.estate_addr)).setText(estates.get(index).getPostalAddress());
    }

    @Override
    public int getItemCount() {
        return estates.size();
    }
}
