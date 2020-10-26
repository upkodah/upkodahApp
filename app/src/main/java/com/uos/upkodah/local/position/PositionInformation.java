package com.uos.upkodah.local.position;


import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.uos.upkodah.local.gps.GPSPermissionManager;
import com.uos.upkodah.local.map.MapDrawable;
import com.uos.upkodah.server.KakaoAPIRequest;
import com.uos.upkodah.server.parser.AddrToCoordParser;
import com.uos.upkodah.server.parser.CoordToAddrParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PositionInformation implements Parcelable, MapDrawable {
    public final static String EXTRA = "PositionInformation";

    double longitude;
    double latitude;
    String postalAddress;
    List<String> region;

    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude){
        this.longitude = longitude;
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude){
        this.latitude = latitude;
    }
    public String getPostalAddress() {
        return this.postalAddress;
    }
    public void setPostalAddress(String address){ this.postalAddress = address; }

    public PositionInformation(double longitude, double latitude, String address, List<String> region){
        this.longitude = longitude;
        this.latitude = latitude;
        this.postalAddress = address;
        this.region = region;
    }
    public PositionInformation(Context context, @Nullable ChangeListener listener){
        // 디폴트 생성자를 호출하면 GPS를 자동 호출한다.
        this();
        this.changeListener = listener;
        requestGPS(context);
    }

    /**
     * 디폴트 생성자는 내부 연산을 위해서만 사용
     */
    protected PositionInformation(){
        this.longitude = 0;
        this.latitude = 0;
        this.postalAddress = "";
        this.region = new ArrayList<>();
    }

    /*
     PositionInformation은 GPS요청, API 요청에 의해 외부 쓰레드에 의해 임의의 시점에 상태가 변경된다.
     만약 해당 객체가 변경될 때 실행되어야 할 동작이 있다면, 리스너를 설정해야 한다.
     */
    public interface ChangeListener{
        public void onChange(PositionInformation position);
    }
    ChangeListener changeListener;
    public void setChangeListener(ChangeListener listener){
        this.changeListener = listener;
    }

    /**
     * GPS로 위치 정보를 받아 수정을 요청한다.
     * @param context
     */
    public void requestGPS(Context context){
        LocationListener listener = new SetGPSInformationListener(context, this);
        GPSPermissionManager.getInstance(context).requestCurrentPosition(context, listener);
    }


    /*
     PositionInformation은 좌표와 주소의 일관성 유지를 위해
     좌표를 변경하면 주소 변경을 요청하고 (SearchCoordToAddrRequest)
     주소를 변경하면 좌표 변경을 요청한다. (SearchAddrRequest)

     만약 리스너가 null이면 실행되지 않는다.
    */

    /**
     * 위도와 경도를 입력하면, 주소까지 자동 완성해준다. API 요청이 들어간다.
     * @param context
     * @param longitude 위도
     * @param latitude 경도
     */
    public void setCoord(Context context, double longitude, double latitude){
        this.longitude = longitude;
        this.latitude = latitude;

        Response.Listener<String> listener = new SetCoordListener(this);
        KakaoAPIRequest request = KakaoAPIRequest.getCoordToAddrRequest(longitude,latitude,listener,null);
        KakaoAPIRequest.request(context, request);
    }

    /**
     * 주소를 입력하면, 위도와 경도까지 자동 완성해준다. API 요청이 들어간다.
     * @param context
     * @param address
     */
    public void setPostalAddress(Context context, String address){
        this.postalAddress = address;

        Response.Listener<String> listener = new SetPostalAddressListener(this);
        KakaoAPIRequest request = KakaoAPIRequest.getSearchAddrRequest(address,listener,null);
        KakaoAPIRequest.request(context, request);
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
        parcel.writeStringList(region);
    }
    public static final Creator<PositionInformation> CREATOR = new Creator<PositionInformation>() {
        @Override
        public PositionInformation createFromParcel(Parcel parcel) {
            PositionInformation p = new PositionInformation();
            p.longitude = parcel.readDouble();
            p.latitude = parcel.readDouble();
            p.postalAddress = parcel.readString();
            parcel.readStringList(p.region);

            return p;
        }

        @Override
        public PositionInformation[] newArray(int i) {
            return new PositionInformation[i];
        }
    };
}

class SetCoordListener implements Response.Listener<String>{
    private PositionInformation positionInformation;

    SetCoordListener(PositionInformation positionInformation){
        this.positionInformation = positionInformation;
    }

    @Override
    public void onResponse(String response) {
        // 이 부분에서 응답을 보고 주소를 설정한다.
        CoordToAddrParser parser = new CoordToAddrParser(response);
        this.positionInformation.postalAddress = parser.getPostalAddress();
        this.positionInformation.region = parser.getRegions();

        // 리스너를 호출하여 변경을 알린다.
        if(positionInformation!=null) positionInformation.changeListener.onChange(positionInformation);
    }
}
class SetPostalAddressListener implements Response.Listener<String>{
    private PositionInformation positionInformation;

    SetPostalAddressListener(PositionInformation positionInformation){
        this.positionInformation = positionInformation;
    }
    @Override
    public void onResponse(String response) {
        // 이 부분에서 응답을 보고 위도 및 경도를 설정한다.
        AddrToCoordParser parser = new AddrToCoordParser(response);

        this.positionInformation.longitude = parser.getLongitude();
        this.positionInformation.latitude = parser.getLatitude();

        // 리스너를 호출하여 변경을 알린다.
        if(positionInformation!=null) positionInformation.changeListener.onChange(positionInformation);
    }
}

/**
 * GPS 정보를 수신하면, 해당 정보를 이용해 주소로 변환해 출력한다.
 * 그러니까, 이 요청이 완료되면 자동으로 주소 변환 요청까지 들어간다.
 */
class SetGPSInformationListener implements LocationListener{
    private Context context;
    private PositionInformation positionInformation;

    SetGPSInformationListener(Context context, PositionInformation positionInformation){
        this.context = context;
        this.positionInformation = positionInformation;
    }
    @Override
    public void onLocationChanged(@NonNull Location location) {
        // 이 부분에서 응답을 보고 위도 및 경도를 설정한다.
        this.positionInformation.setCoord(context, location.getLongitude(), location.getLatitude());
        System.out.println("lon="+this.positionInformation.longitude+", lat="+this.positionInformation.latitude);

        // 위치를 잘 받았으니, 수신 취소
        GPSPermissionManager.getInstance(context).removeGPSRequest(context, this);

        // 리스너를 호출하여 변경을 알린다.
        if(positionInformation!=null) positionInformation
                .changeListener
                .onChange(positionInformation);
    }
}