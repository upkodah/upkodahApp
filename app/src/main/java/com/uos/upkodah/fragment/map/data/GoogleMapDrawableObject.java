package com.uos.upkodah.fragment.map.data;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import com.uos.upkodah.fragment.map.GoogleMapDrawable;

public class GoogleMapDrawableObject implements GoogleMapDrawable, Parcelable {
    public GoogleMapDrawableObject(GoogleMapDrawable googleMapDrawable){
        this.title = googleMapDrawable.getMarkerWindowTitle();
        this.snippet = googleMapDrawable.getMarkerWindowSnippet();
        this.iconKey = googleMapDrawable.getIconBitmapKey();
        this.longitude = googleMapDrawable.getLongitude();
        this.latitude = googleMapDrawable.getLatitude();
    }

    private final String title;
    private final String snippet;
    private final String iconKey;
    private final double longitude;
    private final double latitude;

    protected GoogleMapDrawableObject(Parcel in) {
        title = in.readString();
        snippet = in.readString();
        iconKey = in.readString();
        longitude = in.readDouble();
        latitude = in.readDouble();
    }

    @Override
    public boolean isSelectedMarkerInit() {
        try{
            return iconKey==null || iconKey.isEmpty();
        }
        catch (NullPointerException e){
            return true;
        }
    }

    @Override
    public String getMarkerWindowTitle() {
        return this.title;
    }

    @Override
    public String getMarkerWindowSnippet() {
        return this.snippet;
    }

    @Override
    public String getIconBitmapKey() {
        return this.iconKey;
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
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(snippet);
        parcel.writeString(iconKey);
        parcel.writeDouble(longitude);
        parcel.writeDouble(latitude);
    }
    public static final Creator<GoogleMapDrawableObject> CREATOR = new Creator<GoogleMapDrawableObject>() {
        @Override
        public GoogleMapDrawableObject createFromParcel(Parcel in) {
            return new GoogleMapDrawableObject(in);
        }
        @Override
        public GoogleMapDrawableObject[] newArray(int size) {
            return new GoogleMapDrawableObject[size];
        }
    };
}
