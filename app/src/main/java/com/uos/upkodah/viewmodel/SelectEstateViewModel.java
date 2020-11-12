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
import com.uos.upkodah.local.map.UkdMapMarker;
import com.uos.upkodah.local.map.fragment.KakaoMapFragment;
import com.uos.upkodah.local.map.fragment.data.KakaoMapData;
import com.uos.upkodah.local.position.CompositePositionInformation;
import com.uos.upkodah.local.position.EstateInformation;
import com.uos.upkodah.local.position.GridRegionInformation;
import com.uos.upkodah.local.position.PositionInformation;
import com.uos.upkodah.local.position.RegionInformation;
import com.uos.upkodah.local.position.SubRegionInformation;

import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.List;

/**
 * 이 클래스는 크게 두 가지 기능을 한다.
 * 1) 줌 수준에 따라 mapData의 Positions를 적절히 결정한다.
 * 2) 줌 내에 보이는 모든 positions를 저장한다.
 */
public class SelectEstateViewModel extends ViewModel implements SelectionListFragment.ViewHolderManager {
    public KakaoMapData mapData = new KakaoMapData();
    public SelectionListData listData = new SelectionListData();
    public final KakaoMapFragment.ZoomListener zoomListener;
    public final UkdMapMarker.Listener markerListener;

    private int currentDepth;
    private final static float LEVEL1 = 4.5f;
    private final static float LEVEL2 = 3.0f;
    private final static float LEVEL3 = 1.3f;

    public SelectEstateViewModel(){
        currentDepth = 1;

        zoomListener = new KakaoMapFragment.ZoomListener() {
            @Override
            public void onZoomChanged(MapView mapView, float zoomLevel) {
                // 줌이 변경되면, 줌에 따라 포함될 수 있는 Grid 크기를 구한다.
                int depth;

                if(zoomLevel > LEVEL1){
                    // 구 단위로 보여준다.
                    Log.d("MAP", "REGIONMEASURE : "+RegionInformation.MEASURE+",  ZOOM : "+zoomLevel);
                    depth = 1;
                }
                else if(zoomLevel > LEVEL2){
                    // 동 단위로 보여준다.
                    Log.d("MAP", "SUBMEASURE : "+SubRegionInformation.MEASURE+", ZOOM : "+zoomLevel);
                    depth = 2;
                }
                else{
                    // 그리드 단위로 보여준다
                    Log.d("MAP", "GRIDMEASURE : "+GridRegionInformation.MEASURE+", ZOOM : "+zoomLevel);
                    depth = 3;
                }

                // 마커를 변경한다.
                if(currentDepth != depth){
                    mapData.setMapMarkers(getDisplayedEstateList(depth));
                    currentDepth = depth;
                }
            }
        };

        markerListener = new UkdMapMarker.Listener() {
            @Override
            public void onMarkerSelected(MapView mapView, UkdMapMarker marker, PositionInformation positionInformation) {
                Log.d("LIST", "리스트 표출 준비");
                CompositePositionInformation compositePositionInformation = (CompositePositionInformation) positionInformation;
                estatesInCurrentSelectedGrid = compositePositionInformation.getAllEstates();
                listData.setAdapter(new SelectionListAdapter(SelectEstateViewModel.this));
            }
            @Override
            public void onMarkerBalloonSelected(MapView mapView, UkdMapMarker marker, PositionInformation positionInformation) {
                // 마커가 선택되면 마커의 타입을 먼저 확인한다.
                if(positionInformation instanceof GridRegionInformation){
                }
                else{
                    //만약 선택된 마커가 Grid가 아닐 경우, 줌과 중심점을 변경시킨다.
                    mapData.setCenterLongitude(positionInformation.getLongitude());
                    mapData.setCenterLatitude(positionInformation.getLatitude());
                    currentDepth++;
                    mapData.setZoomLevel(getZoomUsingDepth(currentDepth));
                    zoomListener.onZoomChanged(mapView, mapView.getZoomLevelFloat());
                }
            }
        };

        mapData.setZoomLevel(getZoomUsingDepth(currentDepth));
    }

    private List<EstateInformation> estatesInCurrentSelectedGrid;
    private List<RegionInformation> estates;
    public void setEstates(List<RegionInformation> estates) {
        this.estates = estates;
        if(this.estates!=null){
            mapData.setMapMarkers(estates);
        }

    }

    public float getZoomUsingDepth(int depth){
        switch(depth) {
            case 1:
                // 구 단위를 보여줘야할 때
                return LEVEL1+0.3f;

            case 2:
                // 동 단위를 보여줘야할 때
                return LEVEL2+0.3f;

            case 3:
                // Grid 단위를 보여줘야할 때
                return LEVEL3+0.3f;

            default:
                return LEVEL1+0.3f;
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
        Log.d("LIST", "리스트 표출");
        ((EstateListViewHolder) viewHolder).setEstateInfo(estatesInCurrentSelectedGrid.get(index));
    }

    @Override
    public int getItemCount() {
        return estatesInCurrentSelectedGrid==null ? 0 : estatesInCurrentSelectedGrid.size();
    }
}
