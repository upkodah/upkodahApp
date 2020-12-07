package com.uos.upkodah.data.local.position.composite;

import com.uos.upkodah.data.local.estate.EstateInformation;

import java.util.List;

public class GridRegionInformation extends CompositePositionInformation<EstateInformation>{
    public static final double MEASURE = 400;

    public GridRegionInformation(Region region) {
        super(region);
        this.name = "";
        this.postalAddress = regionData.city+" "+regionData.gu+" "+regionData.dong;
    }
    @Override
    public String getClassifyingKey() {
        return regionData.city+regionData.gu+regionData.dong;
    }

    @Override
    public void addSubInformation(EstateInformation sub) {
        super.addSubInformation(sub);
    }
    @Override
    public String getIconBitmapKey() {
        return "";
    }
}
