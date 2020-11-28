package com.uos.upkodah.data;

import com.uos.upkodah.data.local.position.PositionInformation;

public interface InputData {
    public PositionInformation getPosition();
    public int getLimitTimeMin();
    public int getEstateType();
    public int getTradeType();
    public String[] getFacilities();
}
