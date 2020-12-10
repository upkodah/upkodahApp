package com.uos.upkodah.data.local.estate;

import android.os.Parcel;
import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
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
import java.util.Arrays;
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
        if(room.getTradeType()== InnerMapping.MONTHLY_RENTAL){
            return InnerMapping.TRADE.getString(room.getTradeType())+" "+room.getPrice()+"/"+room.getDeposit()+"만 ";
        }
        else{
            return InnerMapping.TRADE.getString(room.getTradeType())+" "+room.getDeposit()+"만 ";
        }
    }
    @Override
    public String getListDisplayedAddr(){
        return InnerMapping.ESTATE.getString(room.getEstateType())+" | "+room.getFloor()+" | "+room.getRealSize()+"㎡";
    }
    @Override
    public String getListDisplayedDesc(){
        return room.getTitle();
    }
    @Override
    public String getImgUrl() {
        return room.getImgURLs()[0];
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

    @Override
    public String[] getImageUrls() {
        return room.getImgURLs();
    }


    // TablePanel
    @Override
    public String getRequiredTime() {
        return "지하철 20분 이내";
    }
    @Override
    public String getTradeType() {
        if(room.getTradeType()== InnerMapping.MONTHLY_RENTAL){
            return InnerMapping.TRADE.getString(room.getTradeType())+" "+room.getPrice()+"/"+room.getDeposit()+"만 "+InnerMapping.ESTATE.getString(room.getEstateType());
        }
        else{
            return InnerMapping.TRADE.getString(room.getTradeType())+" "+room.getDeposit()+"만 "+InnerMapping.ESTATE.getString(room.getEstateType());
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
        System.out.println(Arrays.toString(room.getFacilities()));
        return room.getFacilities();
    }
    @Override
    public String getClassifyingKey() {
        return regionData.grid;
    }


    @BindingAdapter("android:ukdGlideImgUrl")
    public static void setImg(final ImageView imageView, final String url){
        Log.d("ESTATE", "이미지 출력"+url);
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }
}
