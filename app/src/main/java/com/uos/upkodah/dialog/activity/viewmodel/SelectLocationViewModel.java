package com.uos.upkodah.dialog.activity.viewmodel;

import androidx.lifecycle.ViewModel;

import com.uos.upkodah.local.position.PositionInformation;
import com.uos.upkodah.user.fragment.data.SearchBarData;

public class SelectLocationViewModel extends ViewModel {
    private PositionInformation position;

    // Fragment용 데이터
    public SearchBarData searchBarData = new SearchBarData();

}
