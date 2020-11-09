package com.uos.upkodah.local.position;

import androidx.annotation.NonNull;

import net.daum.mf.map.api.MapView;

import java.util.List;

public class SubRegionInformation extends CompositePositionInformation<GridRegionInformation>{
    public static final double MEASURE = GridRegionInformation.MEASURE*CompositePositionInformation.SCALE;

    public SubRegionInformation(String name, List<GridRegionInformation> subInfo) {
        super(name, subInfo);
    }
    public SubRegionInformation(String name) {
        super(name);
    }

}
