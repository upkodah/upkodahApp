package com.uos.upkodah.data.local.position.composite;

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

    @Override
    public String getIconBitmapKey() {
        int radius = (Math.min(getTotalSize(), 30))*5+300;

        return "__region_dong_"+radius+"_"+regionData.dong+"_"+60;
    }
}
