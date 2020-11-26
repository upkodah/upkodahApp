package com.uos.upkodah.server.ukd.parser;

import android.util.Log;

import androidx.annotation.Nullable;

import com.uos.upkodah.data.local.position.estate.Room;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class EstateResultParser extends UkdServerParser {
    protected Object data;

    protected EstateResultParser(String response) throws JSONException, UkdResponseException{
        super(response);
        /* data라는 이름의 오브젝트를 긁어온다. */
//        dataArr = jobj.getJSONArray("data");
        data = jobj.get("data");

        if(data instanceof JSONObject){
            JSONObject dataObj = (JSONObject) data;
            resultRooms.add(new RoomInformation(dataObj));
        }
        else if(data instanceof JSONArray){
            JSONArray dataArr = (JSONArray) data;
            for(int i=0;i>dataArr.length();i++){
                resultRooms.add(new RoomInformation(dataArr.getJSONObject(i)));
            }
        }
        else{
            throw(new JSONException("파싱 오류 발생"));
        }
    }

    @Nullable
    public static EstateResultParser getInstance(String response){
        try {
            return new EstateResultParser(response);
        } catch (JSONException e) {
            Log.d("UKDSERVER", "파싱 오류 발생");
            e.printStackTrace();
            return null;
        } catch (UkdResponseException e) {
            Log.d("UKDSERVER", "서버 응답 오류 발생");
            return null;
        }
    }


    private final List<Room> resultRooms = new ArrayList<>();
    public List<Room> getResultRooms(){
        return resultRooms;
    }



    private class RoomInformation implements Room {
        private RoomInformation(JSONObject estateJson) throws JSONException{
            this.roomID = estateJson.getInt("roomId");
            this.gridID = estateJson.getInt("gridId");
            this.latitude = estateJson.getDouble("latitude");
            this.longitude = estateJson.getDouble("longitude");
            this.estateType = estateJson.getInt("estateType");
            this.tradeType = estateJson.getInt("tradeType");

            this.title = estateJson.getString("title");
            this.price = estateJson.getInt("price");
            this.deposit = estateJson.getInt("deposit");

            this.floorStr = estateJson.getString("floorStr");
            this.realSize = estateJson.getDouble("realSize");
            this.roughSize = estateJson.getDouble("roughSize");

            StringTokenizer facilityTokenizer = new StringTokenizer(estateJson.getString("facilities"),", ");
            facilities = new String[facilityTokenizer.countTokens()];
            for(int i=0;i<facilities.length;i++){
                facilities[i] = facilityTokenizer.nextToken();
            }

            StringTokenizer imgsTokenizer = new StringTokenizer(estateJson.getString("imgUrls"),", ");
            imgUrls = new String[imgsTokenizer.countTokens()];
            for(int i=0;i<imgUrls.length;i++){
                imgUrls[i] = imgsTokenizer.nextToken();
            }

            this.address = estateJson.getString("address");
            this.roadAddress = estateJson.getString("roadAddress");

            this.describe = estateJson.getString("describe");

            this.isAnimal = estateJson.getBoolean("isAnimal");
            this.isBalcony = estateJson.getBoolean("isBalcony");
            this.isElevator = estateJson.getBoolean("isElevator");
            this.bathNum = estateJson.getInt("bathNum");
            this.bedNum = estateJson.getInt("bedNum");
            this.direct = estateJson.getString("direct");
            this.heatType = estateJson.getString("heatType");
            this.tolalCost = estateJson.getString("totalCost");

            this.phoneNum = estateJson.getString("phoneNum");
        }
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
    }

}
