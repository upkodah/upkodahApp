package com.uos.upkodah.util;

public class GeoToMeterConverter {
    public static double gpsToMeter(double lon1,  double lat1, double lon2, double lat2){
        double radius = 6378.1370d;
        double dLat = (lat2-lat1) *  Math.PI / 180;
        double dLon = (lon2-lon1) * Math.PI / 180;

        double tmpA = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double tmpC = 2 * Math.atan2(Math.sqrt(tmpA), Math.sqrt(1-tmpA));
        double tmpD = radius * tmpC;

        return tmpD * 1000;
    }
    public static double meterToGPSDif(double meter){
        double result = 0;
        double tmpD = meter;
        double radius = 6378.1370d;
        double tmpC = tmpD / radius;
        double tmpA = Math.tan(tmpC/2);


        return result;
    }
}
