package com.uos.upkodah.data.local.estate;

import androidx.annotation.NonNull;

import com.uos.upkodah.data.local.RegionData;
import com.uos.upkodah.data.local.position.PositionInformation;
import com.uos.upkodah.data.local.estate.data.LocationPanelDisplayable;
import com.uos.upkodah.data.local.estate.data.RoomInfoTableDisplayable;
import com.uos.upkodah.data.local.estate.data.TitlePanelDisplayable;

public class EstateInformation extends PositionInformation implements LocationPanelDisplayable, TitlePanelDisplayable, RoomInfoTableDisplayable {
    // PositionInformation으로 만드는 임시
    public EstateInformation(PositionInformation positionInformation){
        this.longitude = positionInformation.getLongitude();
        this.latitude = positionInformation.getLatitude();
        this.postalAddress = positionInformation.getPostalAddress();
    }
    public EstateInformation(Room room){
        this.room = room;
    }
    private Room room;
    public Room getRoomInfo(){
        return room;
    }

    private RegionData regionData;
    public void setRegion(RegionData regionData){
        this.regionData = regionData;
    }
    public String getRegion(){
        return regionData.getGu()+" "+regionData.getDong();
    }
    public String getGu(){
        return regionData.getGu();
    }

    @Override
    public String getListDisplayedName(){
        String result = "["+room.getTradeType()+"] "+room.getRealSize()+"㎡\n"+room.getTitle();

        return result;
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
        return "전세"+room.getPrice()+"만 / "+room.getDeposit()+"만";
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

}
