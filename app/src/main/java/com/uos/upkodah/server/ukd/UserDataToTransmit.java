package com.uos.upkodah.server.ukd;

import com.google.gson.Gson;
import com.uos.upkodah.data.local.position.PositionInformation;
import com.uos.upkodah.data.InputData;

import java.util.StringTokenizer;

public class UserDataToTransmit {
    // 각 필드는 위치, 제한시간, 매물타입, 편의시설태그를 나타낸다.
    public final double longitude;
    public final double latitude;
    public final int limit_time;
    public final int estate_type;
    public final int trade_type;
    public final String[] facilities;

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
