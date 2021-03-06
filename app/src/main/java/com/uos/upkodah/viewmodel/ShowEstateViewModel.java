package com.uos.upkodah.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.uos.upkodah.data.local.estate.EstateInformation;
import com.uos.upkodah.data.local.estate.FacilityInformation;
import com.uos.upkodah.data.local.estate.data.LocationPanelDisplayable;
import com.uos.upkodah.data.local.estate.data.RoomInfoTableDisplayable;
import com.uos.upkodah.data.local.estate.data.TitlePanelDisplayable;
import com.uos.upkodah.data.local.position.PositionPreparedListener;
import com.uos.upkodah.fragment.map.data.GoogleMapData;

public class ShowEstateViewModel extends AndroidViewModel {
    private EstateInformation estateInformation;

    public ShowEstateViewModel(@NonNull Application application) {
        super(application);
        mapData.setZoomLevelWithDepth(3);


    }

    public void setEstate(EstateInformation e){
        this.estateInformation = e;
        e.setSelectedMarkerInit(true);
        mapData.setMapMarker(e);
        FacilityInformation.getFacilityInformations(
                getApplication().getApplicationContext(),
                estateInformation,
                new FacilityPreparedListener(),
                getLocationPanelDisplayable().getSelectedFacilities());
        mapData.setCenter(e);
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

    public GoogleMapData mapData = new GoogleMapData();

    private class FacilityPreparedListener implements PositionPreparedListener<FacilityInformation>{
        @Override
        public void onPrepared(FacilityInformation facilityInformation) {
            mapData.addMapMarker(facilityInformation);
        }
    }
}
