package com.uos.upkodah.local.map.google;

import com.google.android.gms.maps.GoogleMap;
import com.uos.upkodah.local.map.MapDrawable;

public interface GoogleMapDrawable extends MapDrawable {
    public void drawInto(GoogleMap map);
}
