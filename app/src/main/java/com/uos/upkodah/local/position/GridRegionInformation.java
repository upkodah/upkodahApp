package com.uos.upkodah.local.position;

import java.util.List;

public class GridRegionInformation extends CompositePositionInformation<EstateInformation>{
    public static final double MEASURE = 400;


    public GridRegionInformation(String name, List<EstateInformation> subInfo) {
        super(name, subInfo);
    }
    public GridRegionInformation(String name) {
        super(name);
    }
}
