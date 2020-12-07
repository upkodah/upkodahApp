package com.uos.upkodah.viewmodel;

import android.view.View;

import androidx.lifecycle.ViewModel;

import com.uos.upkodah.data.local.position.PositionInformation;
import com.uos.upkodah.data.local.position.PositionPreparedListener;
import com.uos.upkodah.data.local.position.UserPositionInformation;
import com.uos.upkodah.data.mapping.InnerMapping;
import com.uos.upkodah.fragment.facilities.FacilitiesFragment;
import com.uos.upkodah.fragment.searchbar.SearchBarData;
import com.uos.upkodah.fragment.optionbar.SearchOptionData;
import com.uos.upkodah.data.InputData;

public class UkdMainViewModel extends ViewModel implements InputData, PositionPreparedListener<PositionInformation> {
    public UkdMainViewModel(){
        // 초기화
        this.setLimitTimeMin(10);
        this.setEstateType(0);
        this.setTradeType(0);
    }
    public SearchBarData searchBarData = new SearchBarData();

    private UserPositionInformation positionInformation;
    public PositionInformation getPosition() {
        return positionInformation;
    }
    public void setPosition(PositionInformation position) {
        this.positionInformation = new UserPositionInformation(position);
        this.positionInformation.setChangeListener(this);
        alertPositionChange();
    }
    @Override
    public void onPrepared(PositionInformation positionInformation) {
        // GPS 수신에 성공하면, 그냥 해당 객체가 가진 데이터바인딩에 신호를 보낸다.
        alertPositionChange();
    }
    public void alertPositionChange(){
        searchBarData.setSearchText(positionInformation.getPostalAddress());
    }

    public SearchOptionData limitTimeData = new SearchOptionData();
    private int limitTime = 0;
    public int getLimitTimeMin() {
        return this.limitTime;
    }
    public void setLimitTimeMin(int minute) {
        this.limitTime = minute;
        limitTimeData.setOptionText(InnerMapping.LIMIT_TIME.getString(minute));
    }

    public SearchOptionData estateData = new SearchOptionData();
    private int estateType = 0;
    public int getEstateType() {
        return estateType;
    }
    public void setEstateType(int estateType) {
        this.estateType = estateType;
        estateData.setOptionText(InnerMapping.ESTATE.getString(estateType));
    }

    public SearchOptionData tradeTypeData = new SearchOptionData();
    private int tradeType = 0;
    public int getTradeType() {
        return tradeType;
    }
    public void setTradeType(int tradeType) {
        this.tradeType = tradeType;
        tradeTypeData.setOptionText(InnerMapping.TRADE.getString(tradeType));
    }

    private FacilitiesFragment facilitiesFragment;
    public void setFacilitiesFragment(FacilitiesFragment facilitiesFragment) {
        this.facilitiesFragment = facilitiesFragment;
    }
    public String[] getFacilities() {
        return facilitiesFragment.getSelectedFacilities();
    }


    public void setGetMyLocationBtnListener(View.OnClickListener getMyLocationBtnListener) {
        this.getMyLocationBtnListener = getMyLocationBtnListener;
    }
    private View.OnClickListener getMyLocationBtnListener;

    public void setGetLocationBtnListener(View.OnClickListener getLocationBtnListener) {
        this.getLocationBtnListener = getLocationBtnListener;
    }
    private View.OnClickListener getLocationBtnListener;

    public void onClickGetMyLocationBtn(View vieW){
        getMyLocationBtnListener.onClick(vieW);
    }
    public void onClickGetLocatoinBtn(View view){
        getLocationBtnListener.onClick(view);
    }
}
