package com.uos.upkodah.local.position;

import androidx.annotation.IntDef;

import com.uos.upkodah.local.map.UkdMapMarker;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.List;

public class RegionInformation extends PositionInformation{
    // 전체 매물 리스트를 말한다.
    private List<PositionInformation> positions;

    /**
     * 해당 메소드는 Estate들의 수에 따라 반지름 크기를 결정한다.
     * @return
     */
    public float getMarkerRadius(){
        return positions.size() / 10;
    }
    public RegionInformation(List<String> regions){
        this.region = new ArrayList<>();
        this.region.addAll(regions);
    }
    public void addPosition(PositionInformation position){
        positions.add(position);
    }
    public void clearPositions(){
        positions = new ArrayList<>();
    }
    public List<PositionInformation> getPositions(){
        return positions;
    }


    @Override
    public void drawInto(MapView mapView){

    }

    // Region은 위치를 얻는 방법도 평균치 계산에 의존합니다.
    @Override
    public double getLongitude(){
        double result = 0;

        for(PositionInformation e : positions){
            result += e.getLongitude();
        }

        return result / positions.size();
    }
    @Override
    public double getLatitude(){
        double result = 0;

        for(PositionInformation e : positions){
            result += e.getLatitude();
        }

        return result / positions.size();
    }
}
