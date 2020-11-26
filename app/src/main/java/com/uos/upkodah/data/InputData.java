package com.uos.upkodah.data;

import com.uos.upkodah.data.local.position.PositionInformation;

public interface InputData {
    public PositionInformation getPosition();
    public int getLimitTimeMin();
    public String getEstateType();
    public String getTradeType();
    public int[] getFacilities();
}
