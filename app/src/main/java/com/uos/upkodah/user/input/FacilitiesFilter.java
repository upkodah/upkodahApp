package com.uos.upkodah.user.input;

import android.content.pm.PackageManager;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public class FacilitiesFilter {
    private int facilities = 0;

        /*
    편의시설 목록 : 편의점0, 마트1, 공원2, 병원3, 약국4, 경찰서5, 피트니스6, 세탁소7
    총 8개로, 상세 위치는
     */
    public final static int CONVENIENCE_STORE = 0;
    public final static int MART = 1;
    public final static int PARK = 2;
    public final static int HOSPITAL = 3;
    public final static int PHARMACY = 4;
    public final static int POLICE_STATION= 5;
    public final static int FITNESS = 6;
    public final static int LAUNDRY = 7;

    public final static int NUM_OF_FACILITIES = 8;

    @Retention(RetentionPolicy.RUNTIME)
    @IntDef(
            value = {CONVENIENCE_STORE, MART, PARK, HOSPITAL, PHARMACY, POLICE_STATION, FITNESS, LAUNDRY}
    )
    public @interface Facility{}

    public void setFacilities(@Facility int id, boolean isOn){
        if(isOn){
            int tmp = 0b11111111;
            tmp -= 1 << id;
            facilities &= tmp;
        }
        else{
            int tmp = 0b00000000;
            tmp += 1 << id;
            facilities |= tmp;
        }
    }

    /**
     * 선택된 편의시설의 ID를 가져온다.
     * @return
     */
    public List<Integer> toList(){
        ArrayList<Integer> result = new ArrayList<>();

        for(int i=0;i<NUM_OF_FACILITIES;i++){
            int tmp = 1 << i;
            if((facilities & tmp) == tmp)
                result.add(i);
        }
        return result;
    }
}
