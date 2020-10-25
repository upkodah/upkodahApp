package com.uos.upkodah.user.input;

import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;

import com.uos.upkodah.BR;
import com.uos.upkodah.local.position.PositionInformation;

public class UserInputData implements Observable {
    // 각 필드는 위치, 제한시간, 매물타입, 편의시설태그를 나타낸다.
    private PositionInformation position = null;
    private int limitTimeMin = 30;
    private String estateType = null;

    /*
    편의시설 목록 : 편의점0, 마트1, 공원2, 병원3, 약국4, 경찰서5, 피트니스6, 세탁소7
    총 8개로, 상세 위치는
     */
    private int[] facilities = new int[8];


    // 프로퍼티 설정은 데이터바인딩 설정을 원활하게 하기 위함이다.
    @Bindable
    public PositionInformation getPosition() {
        return position;
    }
    public void setPosition(PositionInformation position) {
        this.position = position;
    }

    /**
     * 해당 메소드는 PositionInformation 객체가 변경될 때 사용된다.
     * 데이터바인딩 연동 시에만 작동한다.
     */
    public void alertPositionChange(){}

    @Bindable
    public int getLimitTimeMin() {
        return limitTimeMin;
    }
    public void setLimitTimeMin(int limitTimeMin) {
        this.limitTimeMin = limitTimeMin;
    }

    @Bindable
    public String getEstateType() {
        return estateType;
    }
    public void setEstateType(String estateType) {
        this.estateType = estateType;
    }

    @Bindable
    public int[] getFacilities() {
        return facilities.clone();
    }
    public void setFacilities(int index, int data) {
        this.facilities[index] = data;
    }


    // 해당 변수는 데이터바인딩을 위함이나, 필요할 때만 할당한다.
    protected PropertyChangeRegistry registry;
    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        registry.add(callback);

    }
    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        registry.remove(callback);
    }

    /**
     * 데이터바인딩을 위한 Wrapper 클래스 반환한다.
     */
    public static UserInputData getInstanceForDataBinding(){
        return new ObservableUserInputData();
    }
    public static UserInputData changeForDataBinding(UserInputData data){
        UserInputData result = new ObservableUserInputData();
        result.position = data.position;
        result.estateType = data.estateType;
        result.facilities = data.facilities;
        result.limitTimeMin = data.limitTimeMin;

        return result;
    }
}

class ObservableUserInputData extends UserInputData{
    ObservableUserInputData(){
        super.registry = new PropertyChangeRegistry();
    }

    @Override
    public void setPosition(PositionInformation position) {
        super.setPosition(position);
        super.registry.notifyChange(this, BR.position);
    }

    /**
     * 해당 메소드는 PositionInformation 객체가 자체적으로 변경될 때를 위해 사용한다.
     */
    @Override
    public void alertPositionChange(){
        super.registry.notifyChange(this, BR.position);
    }

    @Override
    public void setLimitTimeMin(int limitTimeMin) {
        super.setLimitTimeMin(limitTimeMin);
        super.registry.notifyChange(this, BR.limitTimeMin);
    }

    @Override
    public void setEstateType(String estateType) {
        super.setEstateType(estateType);
        super.registry.notifyChange(this, BR.estateType);
    }

    @Override
    public void setFacilities(int index, int data) {
        super.setFacilities(index, data);
        super.registry.notifyChange(this, BR.facilities);
    }
}
