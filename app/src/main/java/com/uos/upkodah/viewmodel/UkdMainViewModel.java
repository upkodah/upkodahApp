package com.uos.upkodah.viewmodel;

import android.content.DialogInterface;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;

import com.uos.upkodah.dialog.SelectEstateTypeDialog;
import com.uos.upkodah.dialog.SelectLimitTimeDialog;
import com.uos.upkodah.dialog.permission.PermissionRequiringOnClickListener;
import com.uos.upkodah.local.gps.GPSPermissionManager;
import com.uos.upkodah.local.position.PositionInformation;
import com.uos.upkodah.user.fragment.data.SearchBarData;
import com.uos.upkodah.user.fragment.data.SearchOptionData;
import com.uos.upkodah.user.input.InputData;
import com.uos.upkodah.user.input.LimitTimeStringConverter;
import com.uos.upkodah.user.input.UserInputData;

public class UkdMainViewModel extends ViewModel implements InputData {
    public SearchBarData searchBarData = new SearchBarData();

    public InputData getUserInputData(){
        UserInputData result = new UserInputData();
        result.setPosition(positionInformation);
        result.setLimitTimeMin(getLimitTimeMin());
        result.setEstateType(getEstateType());
        result.setTradeType(getTradeType());

        return result;
    }

    private PositionInformation positionInformation;
    @Override
    public PositionInformation getPosition() {
        return positionInformation;
    }
    @Override
    public void setPosition(PositionInformation position) {
        this.positionInformation = position;
        alertPositionChange();
    }
    public void alertPositionChange(){
        searchBarData.setSearchText(positionInformation.getPostalAddress());
    }

    public SearchOptionData limitTimeData = new SearchOptionData();
    @Override
    public int getLimitTimeMin() {
        return LimitTimeStringConverter.toMinute(limitTimeData.getOptionText());
    }
    @Override
    public void setLimitTimeMin(int minute) {
        limitTimeData.setOptionText(new LimitTimeStringConverter(minute).toString());
    }

    public SearchOptionData estateData = new SearchOptionData();;
    @Override
    public String getEstateType() {
        return estateData.getOptionText();
    }
    @Override
    public void setEstateType(String estateType) {
        estateData.setOptionText(estateType);
    }

    public SearchOptionData tradeTypeData = new SearchOptionData();
    @Override
    public String getTradeType() {
        return tradeTypeData.getOptionText();
    }
    @Override
    public void setTradeType(String tradeType) {
        tradeTypeData.setOptionText(tradeType);
    }

    @Override
    public int[] getFacilities() {
        return new int[0];
    }
    @Override
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
