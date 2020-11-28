package com.uos.upkodah.viewmodel;

import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.uos.upkodah.R;
import com.uos.upkodah.fragment.list.SelectionListAdapter;
import com.uos.upkodah.fragment.list.data.SelectionListData;
import com.uos.upkodah.fragment.list.holder.GridListViewHolder;
import com.uos.upkodah.fragment.list.holder.ListViewHolderManager;
import com.uos.upkodah.fragment.map.data.GoogleMapData;
import com.uos.upkodah.data.local.estate.EstateInformation;
import com.uos.upkodah.data.local.position.composite.GridRegionInformation;
import com.uos.upkodah.data.local.position.PositionInformation;
import com.uos.upkodah.data.local.position.composite.GuRegionInformation;
import com.uos.upkodah.data.local.position.composite.DongRegionInformation;

import java.util.ArrayList;
import java.util.List;

/**
 * 이 클래스는 크게 두 가지 기능을 한다.
 * 1) 줌 수준에 따라 mapData의 Positions를 적절히 결정한다.
 * 2) 줌 내에 보이는 모든 positions를 저장한다.
 */
public class SelectEstateViewModel extends ViewModel  {
    public GoogleMapData mapData = new GoogleMapData();
    public SelectionListData listData = new SelectionListData();

    public SelectEstateViewModel(){
        mapData.setZoomLevelWithDepth(1);

        this.manager = new EstateListManager(new ArrayList<EstateInformation>());
        listData.setAdapter(new SelectionListAdapter(this.manager));
    }

    private List<GuRegionInformation> estates;
    public void setEstates(List<GuRegionInformation> estates) {
        this.estates = estates;
        if(this.estates!=null){
            mapData.setMapMarkers(estates);
        }
    }

    private int preDepth = 0;
    public boolean isDepthChanged(int depth) {
        return preDepth!=depth;
    }

    @Nullable
    public List<PositionInformation> getDisplayedEstateList(int depth){
        List<PositionInformation> result = new ArrayList<>();
        if(estates==null) return result;

        switch(depth){
            case 1:
                // 구 단위를 보여줘야할 때
                Log.d("MAP", "구 단위로 보여줍니다.");
                preDepth = 1;
                result.addAll(estates);
                break;

            case 2:
                // 동 단위를 보여줘야할 때
                Log.d("MAP", "동 단위로 보여줍니다.");
                preDepth = 2;
                for(GuRegionInformation p : estates){
                    result.addAll(p.getSubInfoList());
                }
                break;

            case 3:
                // Grid 단위를 보여줘야할 때
                Log.d("MAP", "Grid 단위로 보여줍니다.");
                preDepth = 3;
                for(GuRegionInformation p : estates){
                    for(DongRegionInformation p2 : p.getSubInfoList()){
                        result.addAll(p2.getSubInfoList());
                    }
                }
                break;

            case 4:
                // 매물 단위를 보여줘야할 때
                Log.d("MAP", "매물 단위로 보여줍니다.");
                preDepth = 4;
                for(GuRegionInformation p : estates){
                    for(DongRegionInformation p2 : p.getSubInfoList()){
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
    private GridListViewHolder.OnClickListener estateListener = null;
    public void setListEstateData(List<EstateInformation> data) {
        this.manager.estatesInCurrentSelectedGrid = data;
        listData.notifyUpdateListData();
    }
    public void setEstateListener(GridListViewHolder.OnClickListener estateListener) {
        this.estateListener = estateListener;
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
            GridListViewHolder holder = new GridListViewHolder(view);
            holder.setListener(estateListener);
            return holder;
        }

        @Override
        public void setViewHolder(RecyclerView.ViewHolder viewHolder, int index) {
            Log.d("LIST", "리스트 표출");
            ((GridListViewHolder) viewHolder).setInfo(estatesInCurrentSelectedGrid.get(index));
        }

        @Override
        public int getItemCount() {
            return estatesInCurrentSelectedGrid==null ? 0 : estatesInCurrentSelectedGrid.size();
        }
    }
}
