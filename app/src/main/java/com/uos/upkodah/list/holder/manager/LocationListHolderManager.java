package com.uos.upkodah.list.holder.manager;

import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;

import com.uos.upkodah.R;
import com.uos.upkodah.list.fragment.SelectionListFragment;
import com.uos.upkodah.list.holder.LocationListViewHolder;
import com.uos.upkodah.local.position.PositionInformation;

import java.util.List;


/**
 * 해당 홀더 매니저는 Position 리스트를 받아 저장하고,
 * 리스트 레이아웃을 가져온다.
 */
public class LocationListHolderManager implements SelectionListFragment.ViewHolderManager {
    private List<PositionInformation> positions;

    @LayoutRes
    @Override
    public int getLayoutId() {
        return R.layout.list_item_location;
    }

    @Override
    public RecyclerView.ViewHolder generate(View view) {
        return new LocationListViewHolder(view);
    }

    @Override
    public void setViewHolder(RecyclerView.ViewHolder viewHolder, int index) {
        LocationListViewHolder locationListViewHolder = (LocationListViewHolder) viewHolder;
        locationListViewHolder.setName(positions.get(index).getName());
        locationListViewHolder.setAddress(positions.get(index).getPostalAddress());
    }

    @Override
    public int getItemCount() {
        return positions != null ? positions.size() : 0;
    }
}
