package com.uos.upkodah.local.map;

import android.view.View;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapPOIItem;

public class RegionBalloonAdapter implements CalloutBalloonAdapter {
    private View balloonView;

    @Override
    public View getCalloutBalloon(MapPOIItem mapPOIItem) {
        return null;
    }

    @Override
    public View getPressedCalloutBalloon(MapPOIItem mapPOIItem) {
        return null;
    }
}
