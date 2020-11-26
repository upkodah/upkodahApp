package com.uos.upkodah.util;

public class CoordConverter {
    /**
     * 도, 분, 초로 표현된 좌표를 위도와 경도값 실수로 반환합니다.
     * @param degree
     * @param min
     * @param sec
     * @return
     */
    public static double dmsToDegree(double degree, int min, int sec){
        return degree+((double)min)/60+((double)sec)/3600;
    }
}
