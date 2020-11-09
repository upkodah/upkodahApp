package com.uos.upkodah.server.ukd;

import com.google.gson.Gson;
import com.uos.upkodah.local.position.PositionInformation;
import com.uos.upkodah.user.input.InputData;

public class UserDataToTransmit {
    // 각 필드는 위치, 제한시간, 매물타입, 편의시설태그를 나타낸다.
    public final double longitude;
    public final double latitude;
    public final int limit_time;
    public final String estate_type;
    public final String trade_type;

    /*
    편의시설 목록 : 편의점0, 마트1, 공원2, 병원3, 약국4, 경찰서5, 피트니스6, 세탁소7
    총 8개로, 상세 위치는
     */
    public final int[] facilities;

    public UserDataToTransmit(InputData inputData){
        PositionInformation p = inputData.getPosition();
        longitude = p.getLongitude();
        latitude = p.getLatitude();
        limit_time = inputData.getLimitTimeMin();
        estate_type = inputData.getEstateType();
        trade_type = inputData.getTradeType();
        facilities = inputData.getFacilities();
    }

    public String toJSON(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
