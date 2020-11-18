package com.uos.upkodah.local.map.kakao;

import com.uos.upkodah.local.map.MapDrawable;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapView;

// 맵에 그릴 수 있는 객체를 나타내는 인터페이스
public interface KakaoMapDrawable extends MapDrawable {
    public void drawInto(MapView mapView);
}
