package com.uos.upkodah.dialog.activity.viewmodel;

import android.view.View;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.uos.upkodah.R;
import com.uos.upkodah.fragment.list.LinearListAdapter;
import com.uos.upkodah.fragment.list.data.SelectionListData;
import com.uos.upkodah.fragment.list.holder.ListViewHolderManager;
import com.uos.upkodah.fragment.list.holder.LinearListViewHolder;
import com.uos.upkodah.fragment.map.data.GoogleMapData;
import com.uos.upkodah.data.local.position.PositionInformation;
import com.uos.upkodah.fragment.searchbar.SearchBarData;

import java.util.List;

public class SelectLocationViewModel extends ViewModel implements ListViewHolderManager {
    private PositionInformation position;

    public SelectLocationViewModel(){
        // 초기화
        searchBarData = new SearchBarData();
        selectionListData = new SelectionListData();
//        mapData = new KakaoMapData()
        mapData = new GoogleMapData();
        selectionListData.setAdapter(new LinearListAdapter(this));
    }

    // Fragment용 데이터
    public SearchBarData searchBarData;
    public SelectionListData selectionListData;
//    public KakaoMapData mapData;
    public GoogleMapData mapData;

    // 검색 결과 리스트
    private List<PositionInformation> positions;
    public void setPositionInformation(List<PositionInformation> positionList){
        this.positions = positionList;

        // 리스트 변경에 따라 리스트와 카카오맵을 초기화한다.
        // 리스트 초기화
        selectionListData.notifyUpdateListData();

        // 맵 초기화
        mapData.setMapMarkers(positionList);
    }

    @Override
    public int getLayoutId() {
        return R.layout.list_item_location;
    }
    @Override
    public RecyclerView.ViewHolder generate(View view) {
        return new LinearListViewHolder(view);
    }
    @Override
    public void setViewHolder(RecyclerView.ViewHolder viewHolder, int index) {
        LinearListViewHolder linearListViewHolder = (LinearListViewHolder) viewHolder;
        linearListViewHolder.setInfo(positions.get(index));
    }
    @Override
    public int getItemCount() {
        return positions.size();
    }
}
