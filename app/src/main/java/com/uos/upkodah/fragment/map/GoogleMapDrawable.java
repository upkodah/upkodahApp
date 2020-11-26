package com.uos.upkodah.fragment.map;

import com.google.android.gms.maps.GoogleMap;
import com.uos.upkodah.data.local.gps.GeoCoordinate;

public interface GoogleMapDrawable extends GeoCoordinate {
    public String getMarkerWindowTitle();
    public String getMarkerWindowSnippet();
}