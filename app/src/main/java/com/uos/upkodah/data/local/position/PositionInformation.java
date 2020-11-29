package com.uos.upkodah.data.local.position;


import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.uos.upkodah.data.local.gps.GeoCoordinate;
import com.uos.upkodah.fragment.list.holder.ListDisplayable;
import com.uos.upkodah.fragment.map.GoogleMapDrawable;


public class PositionInformation implements Parcelable, GoogleMapDrawable, Cloneable, GeoCoordinate, ListDisplayable {
    protected double longitude;
    protected double latitude;
    protected String postalAddress;
    protected String name;

    @Override
    public double getLongitude() {
        return longitude;
    }
    @Override
    public double getLatitude() {
        return latitude;
    }
    public String getPostalAddress() {
        return this.postalAddress;
    }
    public String getName(){ return this.name; }
    public void setName(String name){ this.name = name; }

    public PositionInformation(double longitude, double latitude, @Nullable String address){
        this.longitude = longitude;
        this.latitude = latitude;
        this.postalAddress = address!=null ? address : "";
        this.name = postalAddress;
    }
    public PositionInformation(){
        this.longitude = 0;
        this.latitude = 0;
        this.postalAddress = "";
        this.name = "";
    }

    @Override
    public String getMarkerWindowTitle() {
        return this.name;
    }
    @Override
    public String getMarkerWindowSnippet() {
        return this.postalAddress;
    }
    @Override
    public Bitmap getIconBitmap() {
        return null;
    }


    // 여기부터는 Intent에 실어 보내기 위한 Parcelable 처리
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(longitude);
        parcel.writeDouble(latitude);
        parcel.writeString(postalAddress);
    }
    public static final Creator<PositionInformation> CREATOR = new Creator<PositionInformation>() {
        @Override
        public PositionInformation createFromParcel(Parcel parcel) {
            PositionInformation p = new PositionInformation();
            p.longitude = parcel.readDouble();
            p.latitude = parcel.readDouble();
            p.postalAddress = parcel.readString();
            p.name = parcel.readString();

            return p;
        }

        @Override
        public PositionInformation[] newArray(int i) {
            return new PositionInformation[i];
        }
    };

    @NonNull
    @Override
    public PositionInformation clone() {
        PositionInformation result = new PositionInformation();
        result.longitude = this.longitude;
        result.latitude = this.latitude;
        result.name = this.name;
        result.postalAddress = this.postalAddress;

        return result;
    }

    @NonNull
    @Override
    public String toString() {
        String result = "";

        if(!name.isEmpty()){
            result+=name;
            if(!postalAddress.isEmpty()){
                result += "("+postalAddress+")";
            }
        }
        else{
            result += postalAddress;
        }

        return result;
    }

    @Override
    public String getListDisplayedName() {
        return this.name;
    }

    @Override
    public String getListDisplayedAddr() {
        return this.postalAddress;
    }

    @Override
    public String getListDisplayedDesc() {
        return "";
    }
}