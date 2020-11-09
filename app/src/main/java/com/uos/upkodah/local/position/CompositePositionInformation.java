package com.uos.upkodah.local.position;

import java.util.ArrayList;
import java.util.List;

public abstract class CompositePositionInformation<P extends PositionInformation> extends PositionInformation{
    public final static double SCALE = 4;
    protected List<P> subInfo;

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

        super.longitude = resultLon;
        super.latitude = resultLat;
    }

    public float getMarkerRadius(){
        return subInfo.size() / 10;
    }
    public void clearPositions(){
        subInfo = new ArrayList<>();
        calculateCoord();
    }

    public List<P> getSubInfoList(){
        return subInfo;
    }
}
