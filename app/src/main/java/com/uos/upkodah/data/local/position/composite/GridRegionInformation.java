package com.uos.upkodah.data.local.position.composite;

import com.uos.upkodah.data.local.position.estate.EstateInformation;

import java.util.List;

public class GridRegionInformation extends CompositePositionInformation<EstateInformation>{
    public static final double MEASURE = 400;

    private int gridId;


    public GridRegionInformation(int gridId, List<EstateInformation> subInfo) {
        super(""+gridId, subInfo);
        this.gridId = gridId;
    }
    public GridRegionInformation(String name) {
        super(name);
    }
}
