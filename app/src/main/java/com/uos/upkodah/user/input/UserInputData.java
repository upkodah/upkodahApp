package com.uos.upkodah.user.input;

import com.uos.upkodah.local.position.PositionInformation;

public class UserInputData implements InputData {
    // 각 필드는 위치, 제한시간, 매물타입, 편의시설태그를 나타낸다.
    private PositionInformation position;
    private int limitTimeMin;
    private String estateType;
    private String tradeType;

    /*
    편의시설 목록 : 편의점0, 마트1, 공원2, 병원3, 약국4, 경찰서5, 피트니스6, 세탁소7
    총 8개로, 상세 위치는
     */
    private int[] facilities = new int[8];


    public PositionInformation getPosition() {
        return position;
    }
    public void setPosition(PositionInformation position) {
        this.position = position;
    }

    public int getLimitTimeMin() {
        return limitTimeMin;
    }
    public void setLimitTimeMin(int limitTimeMin) {
        this.limitTimeMin = limitTimeMin;
    }

    public String getEstateType() {
        return estateType;
    }
    public void setEstateType(String estateType) {
        this.estateType = estateType;
    }

    public String getTradeType() {
        return tradeType;
    }
    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public int[] getFacilities() {
        return facilities.clone();
    }
    public void setFacilities(int index, int data) {
        this.facilities[index] = data;
    }

}
