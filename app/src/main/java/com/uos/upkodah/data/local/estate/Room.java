package com.uos.upkodah.data.local.estate;

import android.os.Parcelable;

import com.uos.upkodah.data.local.gps.GeoCoordinate;

public interface Room extends GeoCoordinate, Parcelable {
    public int getRoomID();
    public int getGridID();
    public int getEstateType();
    public int getTradeType();
    public String getTitle();
    public int getPrice();
    public int getDeposit();
    public String getFloor();
    public double getRealSize();
    public double getRoughSize();
    public String[] getFacilities();
    public String[] getImgURLs();
    public String getAddr();
    public String getRoadAddr();
    public String getDesc();
    public boolean isAnimal();
    public boolean isBalcony();
    public boolean isElevator();
    public int getBathNum();
    public int getBedNum();
    public String getDirection();
    public String getHeatType();
    public String getTotalCost();
    public String getPhoneNum();
}
