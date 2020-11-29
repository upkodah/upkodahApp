package com.uos.upkodah.data.local.estate.data;

import com.uos.upkodah.data.local.gps.GeoCoordinate;

public interface LocationPanelDisplayable extends GeoCoordinate {
    public String getEstateAddress();
    public String[] getSelectedFacilities();
}
