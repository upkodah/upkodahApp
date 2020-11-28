package com.uos.upkodah.data.local.position.composite;

import androidx.annotation.NonNull;

import com.uos.upkodah.data.local.estate.EstateInformation;
import com.uos.upkodah.data.local.position.LocationInformation;
import com.uos.upkodah.data.local.position.PositionInformation;

import java.util.ArrayList;
import java.util.List;

public abstract class CompositePositionInformation<L extends LocationInformation> extends LocationInformation {
    public final static double SCALE = 4;
    protected List<L> subInfo;

    private String parentId;

    public CompositePositionInformation(Region region){
        super(region);
        this.subInfo = new ArrayList<>();
        calculateCoord();
    }


    public void addSubInformation(L sub){
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
                for(L c : subInfo){
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

            for(L p : subInfo){
                resultList.addAll(((CompositePositionInformation) p).getAllEstates());
            }

            return resultList;
        }
    }

    public List<L> getSubInfoList(){
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
}
