package com.uos.upkodah.user.input;

import com.uos.upkodah.local.position.PositionInformation;

public interface InputData {
    public PositionInformation getPosition();
    public void setPosition(PositionInformation position);

    public int getLimitTimeMin();
    public void setLimitTimeMin(int minute);

    public String getEstateType();
    public void setEstateType(String estateType);

    public String getTradeType();
    public void setTradeType(String tradeType);

    public int[] getFacilities();
    public void setFacilities(int index, int data);
}
