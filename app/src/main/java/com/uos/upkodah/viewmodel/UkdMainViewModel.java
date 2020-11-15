package com.uos.upkodah.viewmodel;

import android.view.View;

import androidx.lifecycle.ViewModel;

import com.uos.upkodah.local.position.PositionInformation;
import com.uos.upkodah.local.position.UserPositionInformation;
import com.uos.upkodah.user.fragment.data.SearchBarData;
import com.uos.upkodah.user.fragment.data.SearchOptionData;
import com.uos.upkodah.user.input.InputData;
import com.uos.upkodah.user.input.LimitTimeStringConverter;
import com.uos.upkodah.server.ukd.UserDataToTransmit;

public class UkdMainViewModel extends ViewModel implements InputData{
    public UkdMainViewModel(){
        // 초기화
        this.setLimitTimeMin(10);
        this.setEstateType("원룸");
        this.setTradeType("월세");
    }


    public SearchBarData searchBarData = new SearchBarData();

    public UserDataToTransmit getDataToTransmit(){
        UserDataToTransmit result = new UserDataToTransmit(this);
        return result;
    }

    private UserPositionInformation positionInformation;
    public PositionInformation getPosition() {
        return positionInformation;
    }
    public void setPosition(PositionInformation position) {
        this.positionInformation = new UserPositionInformation(position);
        alertPositionChange();
    }
    public void alertPositionChange(){
        searchBarData.setSearchText(positionInformation.getPostalAddress());
    }

    public SearchOptionData limitTimeData = new SearchOptionData();
    public int getLimitTimeMin() {
        return LimitTimeStringConverter.toMinute(limitTimeData.getOptionText());
    }
    public void setLimitTimeMin(int minute) {
        limitTimeData.setOptionText(new LimitTimeStringConverter(minute).toString());
    }

    public SearchOptionData estateData = new SearchOptionData();;
    public String getEstateType() {
        return estateData.getOptionText();
    }
    public void setEstateType(String estateType) {
        estateData.setOptionText(estateType);
    }

    public SearchOptionData tradeTypeData = new SearchOptionData();
    public String getTradeType() {
        return tradeTypeData.getOptionText();
    }
    public void setTradeType(String tradeType) {
        tradeTypeData.setOptionText(tradeType);
    }

    public int[] getFacilities() {
        return new int[0];
    }
    public void setFacilities(int index, int data) {

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
