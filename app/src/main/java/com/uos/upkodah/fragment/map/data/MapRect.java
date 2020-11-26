package com.uos.upkodah.fragment.map.data;

import androidx.annotation.NonNull;

import com.uos.upkodah.util.GeoToMeterConverter;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;

public class MapRect{
    public final double width;
    public final double height;

    public MapRect(MapPointBounds points){
        if(points == null){
            width = 0;
            height = 0;
        }
        else{
            MapPoint botlef = points.bottomLeft;
            MapPoint topRig = points.topRight;

            MapPoint.GeoCoordinate botlefCoord = botlef.getMapPointGeoCoord();
            MapPoint.GeoCoordinate topRigCoord = topRig.getMapPointGeoCoord();

            width = GeoToMeterConverter.gpsToMeter(
                    botlefCoord.longitude,topRigCoord.latitude,
                    topRigCoord.longitude, topRigCoord.latitude);
            height = GeoToMeterConverter.gpsToMeter(
                    botlefCoord.longitude, botlefCoord.latitude,
                    botlefCoord.longitude, topRigCoord.latitude);
        }

    }

    @NonNull
    @Override
    public String toString() {
        return "맵 크기 : 가로 "+width+"m, 세로 "+height+"m";
    }
}