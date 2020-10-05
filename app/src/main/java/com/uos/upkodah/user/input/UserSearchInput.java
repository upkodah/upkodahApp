package com.uos.upkodah.user.input;

import com.uos.upkodah.local.gps.PositionInformation;

public interface UserSearchInput {
    public PositionInformation getPositionInformation();
    public int getLimitTimeMin();
    public String getKeyword();
}
