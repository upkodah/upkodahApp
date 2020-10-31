package com.uos.upkodah.local.position;

public class EstateInformation extends PositionInformation {
    // PositionInformation으로 만드는 임시
    public EstateInformation(PositionInformation positionInformation){
        this.longitude = positionInformation.longitude;
        this.latitude = positionInformation.latitude;
        this.postalAddress = positionInformation.postalAddress;
        this.region.addAll(positionInformation.region);
    }
}
