package com.upcodot.uos.user.input;

public class PositionInformation {
    private double longitude;
    private double latitude;
    private String postalAddress;

    public PositionInformation(double longitude, double latitude){
        this.longitude = longitude;
        this.latitude = latitude;

        this.postalAddress = "";
    }
    public PositionInformation(String postalAddress){
        // 문자열 주소를 이용해 위도, 경도값 입력
    }


    public double getLongitude() {
        return longitude;
    }
    public double getLatitude() {
        return latitude;
    }
    public String getPostalAddress() {
        return postalAddress;
    }
}
