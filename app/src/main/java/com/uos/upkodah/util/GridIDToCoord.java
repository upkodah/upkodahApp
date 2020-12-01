package com.uos.upkodah.util;

import androidx.annotation.Nullable;

import com.uos.upkodah.data.local.gps.DegreeMinSec;
import com.uos.upkodah.data.local.gps.GeoCoordinate;
import com.uos.upkodah.data.local.gps.GeoCoordinateUtil;


public class GridIDToCoord {
    public final static int X_ID_LENGTH = 4;
    public final static double KOR_BND_UPP = new DegreeMinSec(43,0,36).value;
    public final static double KOR_BND_LOW = new DegreeMinSec(32,7,22).value;
    public final static double KOR_BND_RIGHT = new DegreeMinSec(131,52,22).value;
    public final static double KOR_BND_LEFT = new DegreeMinSec(124,10,47).value;

    public final static double LON_GRID_SIZE = 0.0036d;
    public final static double LAT_GRID_SIZE = 0.0036d;


    @Nullable
    public static GeoCoordinate convert(int gridID){
        // ID를 String으로 변환하여 넘긴다.
        return convert(Integer.toString(gridID));
    }
    @Nullable
    public static GeoCoordinate convert(String gridID){
        // X_ID_LENGTH만큼의 앞자리는 Longitude, 나머지는 Latitude다.
        try{
            String lonStr = gridID.substring(0,3);
            String latStr = gridID.substring(3);

            double longitude = Integer.parseInt(lonStr);
            double latitude = Integer.parseInt(latStr);

            longitude = KOR_BND_LEFT + LON_GRID_SIZE * (longitude + 0.5);
            latitude = KOR_BND_LOW + LAT_GRID_SIZE * (latitude + 0.5);

            return GeoCoordinateUtil.getInstance(longitude, latitude);
        }
        catch(NumberFormatException | StringIndexOutOfBoundsException e){
            return null;
        }
    }


}
