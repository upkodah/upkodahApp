package com.uos.upkodah.local.position;

import androidx.annotation.IntDef;

import com.uos.upkodah.local.map.UkdMapMarker;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.List;

public class RegionInformation extends CompositePositionInformation<SubRegionInformation>{
    public static final double MEASURE = GridRegionInformation.MEASURE*CompositePositionInformation.SCALE;

    public RegionInformation(String name, List<SubRegionInformation> subInfo) {
        super(name, subInfo);
    }
    public RegionInformation(String name){
        super(name);
    }


}
