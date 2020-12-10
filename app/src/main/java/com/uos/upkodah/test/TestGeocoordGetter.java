package com.uos.upkodah.test;

import com.uos.upkodah.data.local.gps.GeoCoordinate;
import com.uos.upkodah.data.local.gps.GeoCoordinateUtil;

import java.util.HashMap;

public class TestGeocoordGetter {
    public static HashMap<GeoCoordinate, String> searchDestinationMap;
    public static GeoCoordinate[] coordList = new GeoCoordinate[]{
            GeoCoordinateUtil.getInstance(126.97681d, 37.57604d),     // 13광화문
            GeoCoordinateUtil.getInstance(126.97681d, 37.57604d),     // 14광화문
            GeoCoordinateUtil.getInstance(127.05793d, 37.58993d),    // 15회기역
            GeoCoordinateUtil.getInstance(127.05793d, 37.58993d),    // 16회기역
            GeoCoordinateUtil.getInstance(127.05859d, 37.51317d),    // 17코엑스  (사용금지)
            GeoCoordinateUtil.getInstance(127.05859d, 37.51317d),    // 18코엑스  (사용금지)
            GeoCoordinateUtil.getInstance(127.02685d, 37.49777d),    // 19강남역
            GeoCoordinateUtil.getInstance(127.02685d, 37.49777d),    // 20강남역
            GeoCoordinateUtil.getInstance(127.05493d, 37.58348d),    // 21시립대 정문
            GeoCoordinateUtil.getInstance(127.05493d, 37.58348d),    // 22시립대 정문
            GeoCoordinateUtil.getInstance(126.97649d, 37.57077d),    // 23광화문역
            GeoCoordinateUtil.getInstance(126.97649d, 37.57077d)     // 24광화문역
    };
    public static String[] locationList = new String[]{
            "광화문",
            "광화문",
            "회기역",
            "회기역",
            "코엑스",
            "코엑스",
            "강남역",
            "강남역",
            "시립대 정문",
            "시립대 정문",
            "광화문역",
            "광화문역"
    };

    public static int findNear(GeoCoordinate geoCoordinate){
        double distance = -1;
        int result = 0;
        int count = 0;

        for(GeoCoordinate c : coordList){
            double tmpDist = getDistance(geoCoordinate, c);

            // 초기화
            if(distance<0) {
                distance = tmpDist;
            }

            // 짧은 거리 삽입
            if(distance > tmpDist) {
                result = count;
                distance = tmpDist;
            }

            count++;
        }

        return result;
    }
    private static double getDistance(GeoCoordinate g1, GeoCoordinate g2){
        double xDiff = g1.getLongitude() - g2.getLongitude();
        double yDiff = g1.getLatitude() - g2.getLatitude();

        return xDiff*xDiff +  yDiff*yDiff;
    }
}
