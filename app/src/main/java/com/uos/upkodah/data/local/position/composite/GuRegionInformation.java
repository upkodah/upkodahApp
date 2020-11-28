package com.uos.upkodah.data.local.position.composite;

import java.util.List;

public class GuRegionInformation extends CompositePositionInformation<DongRegionInformation>{
    public static final double MEASURE = DongRegionInformation.MEASURE*CompositePositionInformation.SCALE;

    public GuRegionInformation(Region region){
        super(region);
        this.name = regionData.gu;
        this.postalAddress = regionData.city;
    }

    @Override
    public String getClassifyingKey() {
        return regionData.city;
    }
}
