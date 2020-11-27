package com.uos.upkodah.data.local.gps;

import com.google.android.gms.maps.model.LatLng;

public class GeoCoordinateUtil{
    public final static double INIT_LONGITUDE = 127.0317674d;
    public final static double INIT_LATITUDE = 37.6658609d;

    public static LatLng toLatLng(GeoCoordinate coord){
        return new LatLng(coord.getLatitude(), coord.getLongitude());
    }
    public static GeoCoordinate average(GeoCoordinate...coordinates){
        double avgLongitude = 0;
        double avgLatitude = 0;

        if(coordinates.length==0) return getInstance(INIT_LONGITUDE, INIT_LATITUDE);

        for(GeoCoordinate c : coordinates){
            avgLongitude += c.getLongitude();
            avgLatitude += c.getLatitude();
        }
        final double resultLon = avgLongitude / coordinates.length;
        final double resultLat = avgLatitude / coordinates.length;

        return getInstance(resultLon, resultLat);
    }
    public static GeoCoordinate getInstance(final double longitude, final double latitude){
        return new GeoCoordinate() {
            @Override
            public double getLongitude() {
                return longitude;
            }
            @Override
            public double getLatitude() {
                return latitude;
            }
        };
    }
    public static GeoCoordinate getInstance(final DegreeMinSec longitude, final DegreeMinSec latitude){
        return new GeoCoordinate() {
            @Override
            public double getLongitude() {
                return longitude.value;
            }
            @Override
            public double getLatitude() {
                return latitude.value;
            }
        };
    }
    public static GeoCoordinate getInit(){
        return new GeoCoordinate() {
            @Override
            public double getLongitude() {
                return INIT_LONGITUDE;
            }
            @Override
            public double getLatitude() {
                return INIT_LATITUDE;
            }
        };
    }
    public static String toString(GeoCoordinate geoCoordinate){
        return "Longitude="+geoCoordinate.getLongitude()+", Latitude="+geoCoordinate.getLatitude();
    }
}
