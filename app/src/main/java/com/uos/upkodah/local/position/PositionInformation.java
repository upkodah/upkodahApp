package com.uos.upkodah.local.position;


import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.uos.upkodah.local.gps.GPSPermissionManager;
import com.uos.upkodah.local.map.google.GoogleMapDrawable;
import com.uos.upkodah.local.map.kakao.KakaoMapDrawable;
import com.uos.upkodah.local.map.kakao.UkdMapMarker;
import com.uos.upkodah.server.extern.KakaoAPIRequest;
import com.uos.upkodah.server.extern.parser.CoordToAddrParser;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class PositionInformation implements Parcelable, KakaoMapDrawable, GoogleMapDrawable, Cloneable {
    public final static int REGION_DEPTH_1 = 0;
    public final static int REGION_DEPTH_2 = 1;
    public final static int REGION_DEPTH_3 = 2;



    @Retention(RetentionPolicy.SOURCE)
    @IntDef(value = { REGION_DEPTH_1,REGION_DEPTH_2,REGION_DEPTH_3 })
    @interface RegionDepth{}

    double longitude;
    double latitude;
    String postalAddress;
    String name;

    public double getLongitude() {
        return longitude;
    }
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
        this.postalAddress = address;
        this.name = postalAddress;
    }
    public PositionInformation(){
        this.longitude = 0;
        this.latitude = 0;
        this.postalAddress = "";
        this.name = "";
    }

    @Override
    public void drawInto(MapView mapView) {
        MapPOIItem marker = new MapPOIItem();
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude));
        marker.setItemName(postalAddress);
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
        marker.setDraggable(false);
        marker.setShowCalloutBalloonOnTouch(true);
        marker.setUserObject(this);

        mapView.addPOIItem(marker);
        mapView.selectPOIItem(marker, false);
    }
    @Override
    public void drawInto(GoogleMap map) {
        map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)))
                .setTag(this);
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
}