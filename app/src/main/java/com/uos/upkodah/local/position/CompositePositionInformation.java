package com.uos.upkodah.local.position;

import android.graphics.Color;

import androidx.annotation.NonNull;

import com.uos.upkodah.R;
import com.uos.upkodah.local.map.UkdMapMarker;

import net.daum.mf.map.api.MapCircle;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.List;

public abstract class CompositePositionInformation<P extends PositionInformation> extends PositionInformation{
    public final static double SCALE = 4;
    protected List<P> subInfo;

    private String parentId;

    public CompositePositionInformation(String name, List<P> subInfo){
        super(0,0,"");
        this.subInfo = subInfo;
        super.setName(name);
        calculateCoord();
    }
    public CompositePositionInformation(String name){
        this(name, new ArrayList<P>());
    }
    public void addSubInformation(P sub){
        subInfo.add(sub);
        calculateCoord();
    }
    protected void calculateCoord(){
        if(subInfo.size() <= 0) {
            super.longitude = 0;
            super.latitude = 0;
            return;
        }
        double resultLon = 0, resultLat = 0;

        for(PositionInformation e : subInfo){
            resultLon += e.getLongitude();
            resultLat += e.getLatitude();
        }

        super.longitude = resultLon / subInfo.size();
        super.latitude = resultLat / subInfo.size();
    }

    public int getMarkerRadius(){
        if(subInfo.size()>0){
            int result = 0;

            if(subInfo.get(0) instanceof CompositePositionInformation){
                for(P c : subInfo){
                    result += ((CompositePositionInformation) c).getMarkerRadius() * 3;
                }
            }
            else{
                result = subInfo.size() * 10;
            }
            return result;
        }
        else{
            return 0;
        }
    }
    public void clearPositions(){
        subInfo = new ArrayList<>();
        calculateCoord();
    }
    public List<EstateInformation> getAllEstates(){
        if(this instanceof GridRegionInformation){
            return ((GridRegionInformation) this).getSubInfoList();
        }
        else{
            List<EstateInformation> resultList = new ArrayList<>();

            for(P p : subInfo){
                resultList.addAll(((CompositePositionInformation) p).getAllEstates());
            }

            return resultList;
        }
    }

    public List<P> getSubInfoList(){
        return subInfo;
    }

    public String getParentId() {
        return parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }

    @Override
    public void drawInto(MapView mapView) {
        MapPOIItem marker = UkdMapMarker.getBuilder(this)
                .setMarkerImage(R.drawable.marker)
                .setMarkerPosition(0.5f,0.5f)
                .build();
        marker.setItemName(name);
        mapView.addPOIItem(marker);

        MapCircle circle = new MapCircle(MapPoint.mapPointWithGeoCoord(getLatitude(), getLongitude()), // center
                getMarkerRadius(), // radius
                Color.argb(0, 0, 0, 0), // strokeColor
                Color.argb(128, 0, 0, 255)); // fillColor
        mapView.addCircle(circle);
    }
}
