package com.uos.upkodah.local.position;

import androidx.annotation.NonNull;

public class EstateInformation extends PositionInformation {
    public final int monPrice = 400;  // 월세
    public final int dpsPrice = 4000; // 보증금
    public final double rmSize = 400;   // 방 크기
    public final int mngFee = 40;  // 관리비
    public final String rmType= "원룸";  // 방 타입
    public final String trdType = "월세"; // 거래 타입

    private int gridId = 0;
    private String dongId;
    private String guId;


    // PositionInformation으로 만드는 임시
    public EstateInformation(PositionInformation positionInformation){
        this.longitude = positionInformation.longitude;
        this.latitude = positionInformation.latitude;
        this.postalAddress = positionInformation.postalAddress;
    }

    public void setRegion(String gu, String dong){
        this.guId = gu;
        this.dongId = dong;
    }
    public String getRegion(){
        return guId+" "+dongId;
    }
    public String getGu(){
        return guId;
    }

    @NonNull
    @Override
    public String toString() {
        return guId+" "+dongId+" "+postalAddress;
    }
}
