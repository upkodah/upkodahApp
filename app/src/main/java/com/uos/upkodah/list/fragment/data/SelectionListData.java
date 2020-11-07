package com.uos.upkodah.list.fragment.data;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.uos.upkodah.BR;
import com.uos.upkodah.list.fragment.SelectionListFragment;

public class SelectionListData extends BaseObservable {
    private RecyclerView.Adapter<RecyclerView.ViewHolder> adapter = null;
    public SelectionListFragment.ViewHolderManager manager;

    @Bindable
    public RecyclerView.Adapter<RecyclerView.ViewHolder> getAdapter() {
        return adapter;
    }
    public void setAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        this.adapter = adapter;
    }

    public void notifyUpdateListData(){
        this.notifyPropertyChanged(BR.adapter);
    }

    @BindingAdapter("android:recyclerAdapter")
    public static void setRecyclerViewAdapter(RecyclerView view, RecyclerView.Adapter adapter){
        if(adapter == null) return;
        if(view.getAdapter().equals(adapter)){
            // 어댑터가 동일하면 어댑터 업데이트만 실시
            adapter.notifyDataSetChanged();
        }
        else{
            // 다르면 교체
            view.swapAdapter(adapter, false);
        }
    }
}
