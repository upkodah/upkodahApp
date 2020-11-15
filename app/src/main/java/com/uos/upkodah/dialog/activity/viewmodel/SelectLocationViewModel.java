package com.uos.upkodah.dialog.activity.viewmodel;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.uos.upkodah.R;
import com.uos.upkodah.list.fragment.SelectionListAdapter;
import com.uos.upkodah.list.fragment.SelectionListFragment;
import com.uos.upkodah.list.fragment.data.SelectionListData;
import com.uos.upkodah.list.holder.LocationListViewHolder;
import com.uos.upkodah.local.map.kakao.fragment.data.KakaoMapData;
import com.uos.upkodah.local.position.PositionInformation;
import com.uos.upkodah.user.fragment.data.SearchBarData;

import net.daum.mf.map.api.MapPoint;

import java.util.List;

public class SelectLocationViewModel extends ViewModel implements SelectionListFragment.ViewHolderManager {
    private PositionInformation position;

    public SelectLocationViewModel(){
        // 초기화
        searchBarData = new SearchBarData();
        selectionListData = new SelectionListData();
        kakaoMapData = new KakaoMapData();
        selectionListData.setAdapter(new SelectionListAdapter(this));
    }

    // Fragment용 데이터
    public SearchBarData searchBarData;
    public SelectionListData selectionListData;
    public KakaoMapData kakaoMapData;

    // 검색 결과 리스트
    private List<PositionInformation> positions;
    public void setPositionInformation(List<PositionInformation> positionList){
        this.positions = positionList;

        // 리스트 변경에 따라 리스트와 카카오맵을 초기화한다.
        // 리스트 초기화
        selectionListData.notifyUpdateListData();

        // 맵 초기화
        kakaoMapData.setMapMarkers(positionList);
    }

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
        return positions.size();
    }
}
