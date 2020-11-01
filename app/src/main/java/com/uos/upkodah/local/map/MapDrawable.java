package com.uos.upkodah.local.map;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapView;

// 맵에 그릴 수 있는 객체를 나타내는 인터페이스
public interface MapDrawable {
    public void drawInto(MapView mapView);
}
