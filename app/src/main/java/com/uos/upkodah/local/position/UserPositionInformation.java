package com.uos.upkodah.local.position;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.android.volley.Response;
import com.uos.upkodah.local.gps.GPSPermissionManager;
import com.uos.upkodah.server.extern.KakaoAPIRequest;
import com.uos.upkodah.server.extern.parser.CoordToAddrParser;

public class UserPositionInformation extends PositionInformation{
    public UserPositionInformation(PositionInformation posInfo){
        this.latitude = posInfo.latitude;
        this.longitude = posInfo.longitude;
        this.postalAddress = posInfo.postalAddress;
        this.name = posInfo.name;
    }
    public UserPositionInformation(){
        super();
    }

    /*
     UserPositionInformation은 GPS요청, API 요청에 의해 외부 쓰레드에 의해 임의의 시점에 상태가 변경된다.
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
        request.request(context);
    }
}

class SetCoordListener implements Response.Listener<String>{
    private UserPositionInformation info;

    SetCoordListener(UserPositionInformation info){
        this.info = info;
    }

    @Override
    public void onResponse(String response) {
        // 이 부분에서 응답을 보고 주소를 설정한다.
        CoordToAddrParser parser = null;
        parser = CoordToAddrParser.getInstance(response);

        // parser가 null을 반환하면 유효하지 않은 것이므로 생략해야 함
        if(parser != null){
            this.info.postalAddress = parser.getPostalAddress();

            // 리스너를 호출하여 변경을 알린다.
            if(info !=null) info.changeListener.onChange(info);
        }
    }
}

/**
 * GPS 정보를 수신하면, 해당 정보를 이용해 주소로 변환해 출력한다.
 * 그러니까, 이 요청이 완료되면 자동으로 주소 변환 요청까지 들어간다.
 */
class SetGPSInformationListener implements LocationListener{
    private Context context;
    private UserPositionInformation info;

    SetGPSInformationListener(Context context, UserPositionInformation info){
        this.context = context;
        this.info = info;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        // 위치를 잘 받았으니, 수신 취소
        GPSPermissionManager.getInstance(context).removeGPSRequest(context, this);

        // 이 부분에서 응답을 보고 위도 및 경도를 설정한다.
        this.info.setCoord(context, location.getLongitude(), location.getLatitude());
        System.out.println("lon="+this.info.longitude+", lat="+this.info.latitude);

        // 리스너를 호출하여 변경을 알린다.
        if(info !=null) info
                .changeListener
                .onChange(info);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }
    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}