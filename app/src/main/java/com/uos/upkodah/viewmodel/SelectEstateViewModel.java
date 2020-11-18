package com.uos.upkodah.viewmodel;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.uos.upkodah.R;
import com.uos.upkodah.list.fragment.SelectionListAdapter;
import com.uos.upkodah.list.fragment.SelectionListFragment;
import com.uos.upkodah.list.fragment.data.SelectionListData;
import com.uos.upkodah.list.holder.EstateListViewHolder;
import com.uos.upkodah.list.holder.ListViewHolderManager;
import com.uos.upkodah.local.map.google.data.GoogleMapData;
import com.uos.upkodah.local.map.kakao.UkdMapMarker;
import com.uos.upkodah.local.map.kakao.fragment.KakaoMapFragment;
import com.uos.upkodah.local.map.kakao.fragment.data.KakaoMapData;
import com.uos.upkodah.local.position.EstateInformation;
import com.uos.upkodah.local.position.GridRegionInformation;
import com.uos.upkodah.local.position.PositionInformation;
import com.uos.upkodah.local.position.RegionInformation;
import com.uos.upkodah.local.position.SubRegionInformation;

import java.util.ArrayList;
import java.util.List;

/**
 * 이 클래스는 크게 두 가지 기능을 한다.
 * 1) 줌 수준에 따라 mapData의 Positions를 적절히 결정한다.
 * 2) 줌 내에 보이는 모든 positions를 저장한다.
 */
public class SelectEstateViewModel extends ViewModel  {
//    public KakaoMapData mapData = new KakaoMapData();
    public GoogleMapData mapData = new GoogleMapData();
    public SelectionListData listData = new SelectionListData();

    public int currentDepth;

    public SelectEstateViewModel(){
        currentDepth = 1;

        mapData.setZoomLevelWithDepth(currentDepth);

        this.manager = new EstateListManager(new ArrayList<EstateInformation>());
        listData.setAdapter(new SelectionListAdapter(this.manager));
    }

    private List<RegionInformation> estates;
    public void setEstates(List<RegionInformation> estates) {
        this.estates = estates;
        if(this.estates!=null){
            mapData.setMapMarkers(estates);
        }

    }

    public List<PositionInformation> getDisplayedEstateList(int depth){
        List<PositionInformation> result = new ArrayList<>();
        if(estates==null) return result;

        switch(depth){
            case 1:
                // 구 단위를 보여줘야할 때
                Log.d("MAP", "구 단위로 보여줍니다.");
                result.addAll(estates);
                break;

            case 2:
                // 동 단위를 보여줘야할 때
                Log.d("MAP", "동 단위로 보여줍니다.");
                for(RegionInformation p : estates){
                    result.addAll(p.getSubInfoList());
                }
                break;

            case 3:
                // Grid 단위를 보여줘야할 때
                Log.d("MAP", "Grid 단위로 보여줍니다.");
                for(RegionInformation p : estates){
                    for(SubRegionInformation p2 : p.getSubInfoList()){
                        result.addAll(p2.getSubInfoList());
                    }
                }
                break;

            case 4:
                // 매물 단위를 보여줘야할 때
                Log.d("MAP", "매물 단위로 보여줍니다.");
                for(RegionInformation p : estates){
                    for(SubRegionInformation p2 : p.getSubInfoList()){
                        for(GridRegionInformation p3 : p2.getSubInfoList()){
                            result.addAll(p3.getSubInfoList());
                        }
                    }
                }
                break;
            default:
                break;
        }
        return result;
    }

    private EstateListManager manager;
    public void setListEstateData(List<EstateInformation> data) {
        this.manager.estatesInCurrentSelectedGrid = data;
        listData.notifyUpdateListData();
    }

    class EstateListManager implements ListViewHolderManager {
        private List<EstateInformation> estatesInCurrentSelectedGrid;
        EstateListManager(List<EstateInformation> list){
            this.estatesInCurrentSelectedGrid = list;
        }

        @Override
        public int getLayoutId() {
            return R.layout.list_item_grid_estate;
        }

        @Override
        public RecyclerView.ViewHolder generate(View view) {
            return new EstateListViewHolder(view);
        }

        @Override
        public void setViewHolder(RecyclerView.ViewHolder viewHolder, int index) {
            Log.d("LIST", "리스트 표출");
            ((EstateListViewHolder) viewHolder).setEstateInfo(estatesInCurrentSelectedGrid.get(index));
        }

        @Override
        public int getItemCount() {
            return estatesInCurrentSelectedGrid==null ? 0 : estatesInCurrentSelectedGrid.size();
        }
    }
}
