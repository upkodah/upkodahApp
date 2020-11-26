package com.uos.upkodah.data.local.position.estate;

import androidx.annotation.NonNull;

import com.uos.upkodah.data.local.position.PositionInformation;
import com.uos.upkodah.data.local.position.estate.data.LocationPanelDisplayable;
import com.uos.upkodah.data.local.position.estate.data.RoomInfoTableDisplayable;
import com.uos.upkodah.data.local.position.estate.data.TitlePanelDisplayable;
import com.uos.upkodah.fragment.list.holder.ListDisplayable;

public class EstateInformation extends PositionInformation implements LocationPanelDisplayable, TitlePanelDisplayable, RoomInfoTableDisplayable {
    public final int monPrice = 40;  // 월세
    public final int dpsPrice = 500; // 보증금
    public final double rmSize = 33;   // 방 크기
    public final int mngFee = 40;  // 관리비
    public final String rmType= "원룸";  // 방 타입
    public final String trdType = "월세"; // 거래 타입

    private int gridId = 0;
    private String dongId;
    private String guId;


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

    public void setRegion(String gu, String dong){
        this.guId = gu;
        this.dongId = dong;
    }
    public String getRegion(){
        return guId+" "+dongId;
    }
    public String getGu(){
        return guId;
    }

    @NonNull
    @Override
    public String toString() {
        return guId+" "+dongId+" "+postalAddress;
    }

    @Override
    public String getListDisplayedName(){
        String result = "["+rmType+"] "+rmSize+"㎡\n"+name;

        return result;
    }
    @Override
    public String getListDisplayedAddr(){
        return postalAddress;
    }
    @Override
    public String getListDisplayedDesc(){
        return trdType+" "+dpsPrice+"/"+monPrice;
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
