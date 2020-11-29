package com.uos.upkodah.server.ukd.parser;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.Nullable;

import com.uos.upkodah.data.local.estate.Room;

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
            Log.d("HTTP", "단일 오브젝트 처리");
            JSONObject dataObj = (JSONObject) data;
            Log.d("HTTP", "매물 생성");
            resultRooms.add(new RoomInformation(dataObj));
        }
        else if(data instanceof JSONArray){
            Log.d("HTTP", "다수의 오브젝트 처리");
            JSONArray dataArr = (JSONArray) data;
            Log.d("HTTP", dataArr.toString());
            for(int i=0;i<dataArr.length();i++){
                Log.d("HTTP", "매물 생성("+i+")");
                resultRooms.add(new RoomInformation(dataArr.getJSONObject(i)));
            }
        }
        else{
            Log.d("HTTP", "ERROR : 매물 파싱 오류 발생");
            throw(new JSONException("파싱 오류 발생"));
        }
    }

    @Nullable
    public static EstateResultParser getInstance(String response){
        try {
            return new EstateResultParser(response);
        } catch (JSONException e) {
            Log.d("HTTP", "파싱 오류 발생");
            e.printStackTrace();
            return null;
        } catch (UkdResponseException e) {
            Log.d("HTTP", "서버 응답 오류 발생");
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
            Log.d("HTTP", "매물 생성 시작"+jsonString);

            this.roomID = estateJson.getInt("id");   //
            Log.d("HTTP", "roomId 응답"+this.roomID);
            this.gridID = estateJson.getInt("gridId");   //
            Log.d("HTTP", "gridId 응답"+this.gridID);
            this.latitude = estateJson.getDouble("latitude");   //
            Log.d("HTTP", "latitude 응답"+this.latitude);
            this.longitude = estateJson.getDouble("longitude");   //
            Log.d("HTTP", "longitude 응답"+this.longitude);
            this.estateType = estateJson.getInt("estateType");   //
            Log.d("HTTP", "estateType 응답"+this.estateType);
            this.tradeType = estateJson.getInt("tradeType");   //
            Log.d("HTTP", "tradeType 응답"+this.tradeType);

            this.title = estateJson.getString("title");   //
            Log.d("HTTP", "title 응답"+this.roomID);
            this.price = estateJson.getInt("price");   //
            Log.d("HTTP", "price 응답"+this.roomID);
            this.deposit = estateJson.getInt("deposit");   //
            Log.d("HTTP", "deposit 응답"+this.roomID);

            this.floorStr = estateJson.getString("floorStr");   //
            this.realSize = estateJson.getDouble("realSize");   //
            this.roughSize = estateJson.getDouble("roughSize");   //

            StringTokenizer facilityTokenizer = new StringTokenizer(estateJson.getString("facilities"),", ");   //
            facilities = new String[facilityTokenizer.countTokens()];
            for(int i=0;i<facilities.length;i++){
                facilities[i] = facilityTokenizer.nextToken();
            }

            StringTokenizer imgsTokenizer = new StringTokenizer(estateJson.getString("imgUrls"),", ");  //
            imgUrls = new String[imgsTokenizer.countTokens()];
            for(int i=0;i<imgUrls.length;i++){
                imgUrls[i] = imgsTokenizer.nextToken();
            }


            // 아래부분은 shortcut이 아닌 경우우
           try{
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
            catch (JSONException e){
                this.address = "";
                this.roadAddress = "";

                this.describe = "";

                this.isAnimal = false;
                this.isBalcony = false;
                this.isElevator = false;
                this.bathNum = 0;
                this.bedNum = 0;
                this.direct = "";
                this.heatType = "";
                this.tolalCost = "";

                this.phoneNum = "";
            }

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

        private String address;
        private String roadAddress;

        private String describe;

        private boolean isAnimal;
        private boolean isBalcony;
        private boolean isElevator;
        private int bathNum;
        private int bedNum;
        private String direct;
        private String heatType;
        private String tolalCost;

        private String phoneNum;

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
