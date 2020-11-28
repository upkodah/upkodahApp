package com.uos.upkodah.data.local.estate;

import android.os.Parcel;

import com.uos.upkodah.data.Facility;
import com.uos.upkodah.data.local.position.LocationInformation;
import com.uos.upkodah.data.local.estate.data.LocationPanelDisplayable;
import com.uos.upkodah.data.local.estate.data.RoomInfoTableDisplayable;
import com.uos.upkodah.data.local.estate.data.TitlePanelDisplayable;
import com.uos.upkodah.data.local.position.PositionInformation;
import com.uos.upkodah.data.local.position.composite.CompositePositionInformation;
import com.uos.upkodah.data.local.position.composite.GridRegionInformation;
import com.uos.upkodah.data.mapping.InnerMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EstateInformation extends LocationInformation implements LocationPanelDisplayable, TitlePanelDisplayable, RoomInfoTableDisplayable {
    public EstateInformation(Room room){
        super(Integer.toString(room.getGridID()));
        this.room = room;
        this.longitude = room.getLongitude();
        this.latitude = room.getLatitude();
        this.name = room.getTitle();
        this.postalAddress = room.getRoadAddr().isEmpty() ? room.getAddr() : room.getRoadAddr();
    }
    private final Room room;
    public Room getRoomInfo(){
        return room;
    }

    @Override
    public String getListDisplayedName(){
        return "["+InnerMapping.TRADE.getString(room.getTradeType())+"] "+room.getRealSize()+"㎡\n"+this.name;
    }
    @Override
    public String getListDisplayedAddr(){
        return postalAddress;
    }
    @Override
    public String getListDisplayedDesc(){
        return room.getTradeType()+" "+room.getDeposit()+"/"+room.getPrice();
    }

    @Override
    public double getLongitude() {
        return room.getLongitude();
    }
    @Override
    public double getLatitude() {
        return room.getLatitude();
    }

    // TitlePanel
    @Override
    public String getTradeTitle() {
        return this.getTradeType();
    }
    @Override
    public String getEstateNameTitle() {
        return room.getTitle();
    }


    // TablePanel
    @Override
    public String getRequiredTime() {
        return "구파발역에서 지하철로 30분";
    }
    @Override
    public String getTradeType() {
        if(room.getTradeType()== InnerMapping.CHARTER_RENTAL){
            return "전세 "+room.getDeposit()+"만 "+InnerMapping.ESTATE.getString(room.getEstateType());
        }
        else{
            return "월세 "+room.getPrice()+"만 / "+room.getDeposit()+"만 "+InnerMapping.ESTATE.getString(room.getEstateType());
        }
    }
    @Override
    public String getRoomType() {
        return "방 "+room.getBedNum()+", 화장실 "+room.getBathNum()+(room.isBalcony()?"발코니 O":"");
    }
    @Override
    public String getRoomSize() {
        return room.getRealSize()+"㎡ ("+room.getRoughSize()+"평)";
    }
    @Override
    public String getEtc() {
        return (room.isAnimal()?"애완동물 가능 ":"")+(room.isElevator()?"엘리베이터 ":"")+room.getHeatType();
    }
    @Override
    public String getDescription() {
        return room.getDesc();
    }

    // LocationPanel
    @Override
    public String getEstateAddress() {
        return room.getAddr();
    }
    @Override
    public String[] getSelectedFacilities() {
        return room.getFacilities();
    }

    @Override
    public String getClassifyingKey() {
        return regionData.grid;
    }
}
