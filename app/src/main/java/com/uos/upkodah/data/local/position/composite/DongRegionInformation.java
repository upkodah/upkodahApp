package com.uos.upkodah.data.local.position.composite;

import java.util.List;

public class DongRegionInformation extends CompositePositionInformation<GridRegionInformation>{
    public static final double MEASURE = GridRegionInformation.MEASURE*CompositePositionInformation.SCALE;

    public DongRegionInformation(Region region) {
        super(region);
        this.name = regionData.dong;
        this.postalAddress = regionData.city+" "+regionData.gu;
    }

    @Override
    public String getClassifyingKey() {
        return regionData.city+regionData.gu;
    }
}
