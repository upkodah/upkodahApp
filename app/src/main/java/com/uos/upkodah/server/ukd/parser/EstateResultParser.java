package com.uos.upkodah.server.ukd.parser;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.android.gms.common.util.JsonUtils;
import com.uos.upkodah.data.local.estate.Room;
import com.uos.upkodah.data.local.gps.GeoCoordinateUtil;
import com.uos.upkodah.util.JSONUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class EstateResultParser extends UkdServerParser {
    protected EstateResultParser(String response) throws JSONException, UkdResponseException{
        super(response);
        /* data라는 이름의 오브젝트를 긁어온다. */
//        for(JSONObject j : JSONUtil.getJSONObjectList(jobj, "data")){
//            resultRooms.add(new RoomInformation(j));
//        }

        JSONObject data = JSONUtil.getJSONObject(jobj, "data");
        JSONObject grids = null;
        if(data!=null) grids = data.getJSONObject("grids");

        String key = "";
        if(grids!=null){
            JSONArray arr = grids.names();
            for(int i = 0; i< (arr != null ? arr.length() : 0); i++) {
                key = arr.get(i).toString();
                Log.d("SERVER", "현재 grid : "+key+" ");
                for(JSONObject j : JSONUtil.getJSONObjectList(grids, key)){
                    //Log.d("SERVER", "매물 JSON : "+j.toString());
                    resultRooms.add(new RoomInformation(j));
                }
            }
        }
    }

    @Nullable
    public static EstateResultParser getInstance(String response){
        try {
            return new EstateResultParser(response);
        } catch (JSONException e) {
            Log.d("SERVER", "파싱 오류 발생");
            e.printStackTrace();
            return null;
        } catch (UkdResponseException e) {
            Log.d("SERVER", "서버 응답 오류 발생");
            return null;
        }
    }


    private final List<Room> resultRooms = new ArrayList<>();
    public List<Room> getResultRooms(){
        return resultRooms;
    }



    private static class RoomInformation implements Room {
        private RoomInformation(JSONObject estateJson) throws JSONException{
            // 복구용
            this.jsonString = estateJson.toString();

            this.roomID = JSONUtil.get(estateJson, "id", 0);
            Log.d("SERVER", "roomId 응답"+this.roomID);
            this.gridID = JSONUtil.get(estateJson, "gridId", 0);
            Log.d("SERVER", "gridId 응답"+this.gridID);
            this.latitude = JSONUtil.get(estateJson, "latitude", GeoCoordinateUtil.INIT_LATITUDE);
            Log.d("SERVER", "latitude 응답"+this.latitude);
            this.longitude = JSONUtil.get(estateJson, "longitude", GeoCoordinateUtil.INIT_LONGITUDE);
            Log.d("SERVER", "longitude 응답"+this.longitude);
            this.estateType = JSONUtil.get(estateJson, "estateType", 0);
            Log.d("SERVER", "estateType 응답"+this.estateType);
            this.tradeType = JSONUtil.get(estateJson, "tradeType", 0);
            Log.d("SERVER", "tradeType 응답"+this.tradeType);

            this.title = JSONUtil.get(estateJson, "title", "");
            Log.d("SERVER", "title 응답"+this.title);
            this.price = JSONUtil.get(estateJson, "price", 0);
            Log.d("SERVER", "price 응답"+this.price);
            this.deposit = JSONUtil.get(estateJson, "deposit", 0);
            Log.d("SERVER", "deposit 응답"+this.deposit);

            this.floorStr = JSONUtil.get(estateJson, "floorStr", "1층");
            this.realSize = JSONUtil.get(estateJson, "realSize", 0.1d);
            this.roughSize = (double) JSONUtil.get(estateJson, "roughSize", 0.1d);

            StringTokenizer facilityTokenizer = new StringTokenizer(JSONUtil.get(estateJson, "facilities", ""),", ");   //
            facilities = new String[facilityTokenizer.countTokens()];
            for(int i=0;i<facilities.length;i++){
                facilities[i] = facilityTokenizer.nextToken();
            }

            StringTokenizer imgsTokenizer = new StringTokenizer(JSONUtil.get(estateJson, "imgUrls", ""),", []'");  //
            imgUrls = new String[imgsTokenizer.countTokens()];
            for(int i=0;i<imgUrls.length;i++){
                imgUrls[i] = imgsTokenizer.nextToken();
            }
            this.address = JSONUtil.get(estateJson, "address", "");
            this.roadAddress = JSONUtil.get(estateJson, "roadAddress", "");

            this.describe = JSONUtil.get(estateJson, "describe", "");

            this.isAnimal = JSONUtil.get(estateJson, "isAnimal", false);
            this.isBalcony = JSONUtil.get(estateJson, "isBalcony", false);
            this.isElevator = JSONUtil.get(estateJson, "isElevator", false);
            this.bathNum = JSONUtil.get(estateJson, "bathNum", 0);
            this.bedNum = JSONUtil.get(estateJson, "bedNum", 0);
            this.direct = JSONUtil.get(estateJson, "direct", "");
            this.heatType = JSONUtil.get(estateJson, "heatType", "");
            this.tolalCost = JSONUtil.get(estateJson, "totalCost", "");

            this.phoneNum = JSONUtil.get(estateJson, "phoneNum", "");

        }
        private final String jsonString;

        private final int roomID;
        private final int gridID;
        private final double latitude;
        private final double longitude;
        private final int estateType;
        private final int tradeType;

        private final String title;
        private final int price;
        private final int deposit;

        private final String floorStr;
        private final double realSize;
        private final double roughSize;

        private final String[] facilities;

        private final String[] imgUrls;

        private final String address;
        private final String roadAddress;

        private final String describe;

        private final boolean isAnimal;
        private final boolean isBalcony;
        private final boolean isElevator;
        private final int bathNum;
        private final int bedNum;
        private final String direct;
        private final String heatType;
        private final String tolalCost;

        private final String phoneNum;

        @Override
        public int getRoomID() {
            return this.roomID;
        }

        @Override
        public int getGridID() {
            return this.gridID;
        }

        @Override
        public double getLongitude() {
            return this.longitude;
        }

        @Override
        public double getLatitude() {
            return this.latitude;
        }

        @Override
        public int getEstateType() {
            return this.estateType;
        }

        @Override
        public int getTradeType() {
            return this.tradeType;
        }

        @Override
        public String getTitle() {
            return this.title;
        }

        @Override
        public int getPrice() {
            return this.price;
        }

        @Override
        public int getDeposit() {
            return this.deposit;
        }

        @Override
        public String getFloor() {
            return this.floorStr;
        }

        @Override
        public double getRealSize() {
            return this.realSize;
        }

        @Override
        public double getRoughSize() {
            return this.roughSize;
        }

        @Override
        public String[] getFacilities() {
            return this.facilities;
        }

        @Override
        public String[] getImgURLs() {
            return this.imgUrls;
        }

        @Override
        public String getAddr() {
            return this.address;
        }

        @Override
        public String getRoadAddr() {
            return this.roadAddress;
        }

        @Override
        public String getDesc() {
            return this.describe;
        }

        @Override
        public boolean isAnimal() {
            return this.isAnimal;
        }

        @Override
        public boolean isBalcony() {
            return this.isBalcony;
        }

        @Override
        public boolean isElevator() {
            return this.isElevator;
        }

        @Override
        public int getBathNum() {
            return this.bathNum;
        }

        @Override
        public int getBedNum() {
            return this.bedNum;
        }

        @Override
        public String getDirection() {
            return this.direct;
        }

        @Override
        public String getHeatType() {
            return this.heatType;
        }

        @Override
        public String getTotalCost() {
            return this.tolalCost;
        }

        @Override
        public String getPhoneNum() {
            return this.phoneNum;
        }

        @Override
        public int describeContents() {
            return 0;
        }
        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(jsonString);
        }
        public final static Creator<Room> CREATOR = new RoomParcelableCreator();
    }
    private static class RoomParcelableCreator implements Parcelable.Creator<Room>{

        @Override
        public Room createFromParcel(Parcel parcel) {
            try {
                RoomInformation room = new RoomInformation(new JSONObject(parcel.readString()));
                return room;
            } catch (JSONException e) {
                return null;
            }

        }

        @Override
        public Room[] newArray(int i) {
            return new RoomInformation[i];
        }
    }
}
