package com.uos.upkodah.data;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.android.volley.Response;
import com.uos.upkodah.data.local.gps.GeoCoordinate;
import com.uos.upkodah.data.local.position.PositionInformation;
import com.uos.upkodah.server.extern.KakaoAPIRequest;

public class Facility implements Parcelable {
    public final String code;
    public final int type;
    public final String name;
    public final String imgUrl;

    // type이 1이면 code로, type이 0이면 name으로 필터링
    public Facility(String code, int type, String name, String imgUrl){
        this.code = code;
        this.type = type;
        this.name = name;
        this.imgUrl = imgUrl;

        System.out.println(imgUrl);
    }
    public Facility(Parcel parcel){
        this.code = parcel.readString();
        this.name = parcel.readString();
        this.imgUrl = parcel.readString();
        this.type = parcel.readInt();
    }

    public void requestNearFacilities(Context context, GeoCoordinate coordinate, Response.Listener<String> listener){
        switch(type){
            case 0:
                // name으로 검색한다.
                KakaoAPIRequest.getSearchKeywordRequest(code, coordinate, listener, null)
                        .request(context);
            case 1:
                // code로 검색한다.
                KakaoAPIRequest.getSearchCategoryRequest(code, coordinate, listener, null)
                        .request(context);
        }
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }


    // 여기부터는 Intent에 실어 보내기 위한 Parcelable 처리
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(code);
        parcel.writeString(name);
        parcel.writeString(imgUrl);
        parcel.writeInt(type);
    }
    public static final Creator<Facility> CREATOR = new Creator<Facility>() {
        @Override
        public Facility createFromParcel(Parcel parcel) {
            Facility f = new Facility(parcel);
            System.out.println("복구 결과 : "+f.type+" "+f.code+" "+f.name+" "+f.imgUrl);
            return f;
        }

        @Override
        public Facility[] newArray(int i) {
            return new Facility[i];
        }
    };
}
