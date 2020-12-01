package com.uos.upkodah.fragment.list;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.uos.upkodah.R;
import com.uos.upkodah.databinding.ListItemGridEstateBinding;
import com.uos.upkodah.fragment.list.holder.GridListViewHolder;
import com.uos.upkodah.fragment.list.holder.ListDisplayable;
import com.uos.upkodah.fragment.list.holder.ListViewHolderManager;

import java.util.ArrayList;
import java.util.List;

public class GridListAdapter extends RecyclerView.Adapter<GridListViewHolder> {
    private List<ListDisplayable> itemList = new ArrayList<>();
    private GridListViewHolder.OnClickListener listener;

    @NonNull
    @Override
    public GridListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItemGridEstateBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_item_grid_estate, parent, false);

        return new GridListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GridListViewHolder holder, int position) {
        GridListViewHolder gHolder = (GridListViewHolder) holder;
        gHolder.setInfo(itemList.get(position));
        gHolder.setListener(listener);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setItemList(List<? extends ListDisplayable> itemList) {
        this.itemList = new ArrayList<>(itemList);
    }

    public void setListener(GridListViewHolder.OnClickListener listener) {
        this.listener = listener;
    }
}
