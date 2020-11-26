package com.uos.upkodah.viewmodel;

import androidx.lifecycle.ViewModel;

import com.uos.upkodah.data.local.position.estate.EstateInformation;
import com.uos.upkodah.data.local.position.estate.data.LocationPanelDisplayable;
import com.uos.upkodah.data.local.position.estate.data.RoomInfoTableDisplayable;
import com.uos.upkodah.data.local.position.estate.data.TitlePanelDisplayable;

public class ShowEstateViewModel extends ViewModel {
    private EstateInformation estateInformation;

    public void setEstate(EstateInformation e){
        this.estateInformation = e;
    }

    public TitlePanelDisplayable getTitlePanelDisplayable() {
        return estateInformation;
    }

    public RoomInfoTableDisplayable getRoomInfoTableDisplayable() {
        return estateInformation;
    }

    public LocationPanelDisplayable getLocationPanelDisplayable() {
        return estateInformation;
    }
}
