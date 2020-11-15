package com.uos.upkodah.local.position;

import java.util.List;

public class RegionInformation extends CompositePositionInformation<SubRegionInformation>{
    public static final double MEASURE = SubRegionInformation.MEASURE*CompositePositionInformation.SCALE;

    public RegionInformation(String name, List<SubRegionInformation> subInfo) {
        super(name, subInfo);
    }
    public RegionInformation(String name){
        super(name);
    }


}
