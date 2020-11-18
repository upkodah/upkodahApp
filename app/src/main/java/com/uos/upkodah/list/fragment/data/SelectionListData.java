package com.uos.upkodah.list.fragment.data;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.uos.upkodah.BR;
import com.uos.upkodah.list.fragment.SelectionListFragment;
import com.uos.upkodah.list.holder.ListViewHolderManager;

public class SelectionListData extends BaseObservable {
    private RecyclerView.Adapter<RecyclerView.ViewHolder> adapter = null;
    public ListViewHolderManager manager;

    @Bindable
    public RecyclerView.Adapter<RecyclerView.ViewHolder> getAdapter() {
        return adapter;
    }
    public void setAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        this.adapter = adapter;
        notifyUpdateListData();
    }
    public void notifyUpdateListData(){
        this.notifyPropertyChanged(BR.adapter);
    }

    @BindingAdapter("android:recyclerAdapter")
    public static void setRecyclerViewAdapter(RecyclerView view, RecyclerView.Adapter adapter){
        if(adapter == null) return;
        if(view.getAdapter()==null){
            // 만약 어댑터가 null이면 새로 설정
            view.setAdapter(adapter);
        }
        else if(view.getAdapter().equals(adapter)){
            // 어댑터가 동일하면 어댑터 업데이트만 실시
            adapter.notifyDataSetChanged();
        }
        else{
            // 다르면 교체
            view.swapAdapter(adapter, false);
        }
    }
}
