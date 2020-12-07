package com.uos.upkodah.data.local.position.composite;

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

    @Override
    public String getIconBitmapKey() {
        int num = getTotalSize();
        int radius = (Math.min(num, 50))*5+400;

        return "__region_gu_"+radius+"_"+regionData.gu+"_"+80;
    }
}
