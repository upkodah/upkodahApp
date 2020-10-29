package com.uos.upkodah.local.position;

import androidx.annotation.IntDef;

import com.uos.upkodah.local.map.UkdMapMarker;

import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.List;

public class RegionInformation extends EstateInformation{
    // 전체 매물 리스트를 말한다.
    private List<EstateInformation> estates;

    /**
     * 해당 메소드는 Estate들의 수에 따라 반지름 크기를 결정한다.
     * @return
     */
    public float getMarkerRadius(){
        return estates.size() / 10;
    }
    public RegionInformation(List<String> regions){
        this.region = new ArrayList<>();
        this.region.addAll(regions);
    }
    public void addEstate(EstateInformation estate){
        estates.add(estate);
    }
    public void clearEstates(){
        estates = new ArrayList<>();
    }
    public List<EstateInformation> getEstates(){
        return estates;
    }

    /**
     * 지역은 그리는 방법이 약간 달라 다시 그립니다.
     * @param mapView
     */
    @Override
    public void drawInto(MapView mapView){
        UkdMapMarker.getBuilder(this);
    }

    // Region은 위치를 얻는 방법도 평균치 계산에 의존합니다.
    @Override
    public double getLongitude(){
        double result = 0;

        for(EstateInformation e : estates){
            result += e.getLongitude();
        }

        return result / estates.size();
    }
    @Override
    public double getLatitude(){
        double result = 0;

        for(EstateInformation e : estates){
            result += e.getLatitude();
        }

        return result / estates.size();
    }
}
