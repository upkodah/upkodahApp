package com.uos.upkodah.local.position;


import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.uos.upkodah.local.gps.GPSPermissionManager;
import com.uos.upkodah.local.gps.GPSToAddressRequest;
import com.uos.upkodah.local.map.MapDrawable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PositionInformation implements Parcelable, MapDrawable {
    public final static String EXTRA = "PositionInformation";

    protected double longitude;
    protected double latitude;
    protected String postalAddress;
    protected List<String> region;

    public PositionInformation(double longitude, double latitude, String postalAddress){
        this.longitude = longitude;
        this.latitude = latitude;

        this.postalAddress = postalAddress;

        region = new ArrayList<>();
    }
    public PositionInformation(double longitude, double latitude){
        this(longitude,latitude,"");
    }

    public void requestGPS(Context context, Listener gpsListener){
        final PositionInformationProxy proxy = new PositionInformationProxy(context, this, gpsListener);
        new Thread(new Runnable() {
            @Override
            public void run() {
                proxy.setGPSLocation();
            }
        }).start();
    }
    public void requestAddress(Context context, Listener addressListener){
        final PositionInformationProxy proxy = new PositionInformationProxy(context, this, addressListener);
        new Thread(new Runnable() {
            @Override
            public void run() {
                proxy.setAddress();
            }
        }).start();
    }


    public double getLongitude() {
        return longitude;
    }
    public double getLatitude() {
        return latitude;
    }
    public String getPostalAddress() {
        return postalAddress;
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
            PositionInformation p = new PositionInformation(0,0);
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

    @FunctionalInterface
    public interface Listener{
        public void onResponse();
    }
}

/**
 * GPS 수신이나 로컬 API를 이용해 원본 PositionInformation을 완성하는 프록시 객체입니다.
 */
class PositionInformationProxy extends PositionInformation{
    private Context context;

    private PositionInformation origin;
    private PositionInformation.Listener listener;

    public PositionInformationProxy(Context context, PositionInformation origin, PositionInformation.Listener listener) {
        super(0,0,"");
        this.origin = origin;
        this.context = context;
        this.listener = listener;
    }

    /**
     * GPS 요청을 통해 원본의 위도와 경도값을 설정합니다.
     */
    public void setGPSLocation(){
        PositionInformation p = GPSPermissionManager.getInstance(context).getCurrentPosition(context);
        origin.longitude = p.longitude;
        origin.latitude = p.latitude;

        // 지정된 리스너를 실행시킵니다.
        listener.onResponse();
    }
    public void setAddress(){
        // 원본의 위도와 경도를 이용해 주소를 완성합니다.
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // 결과는 JSON이므로, 적절히 변환한다.
                try {
                    JSONObject jobj = new JSONObject(response).getJSONArray("documents").getJSONObject(0).getJSONObject("address");

                    origin.postalAddress = jobj.getString("address_name");

                    for(int i=1;i<=3;i++){
                        String region = jobj.getString("region_"+i+"depth_name");
                        if(region.isEmpty()) break;

                        origin.region.add(region);
                    }
                } catch (JSONException e) {
                }
                finally {
                    // 지정된 리스너를 실행시킵니다.
                    PositionInformationProxy.this.listener.onResponse();
                }
            }
        };
        StringRequest request = new GPSToAddressRequest(origin.longitude,origin.latitude,listener,null);

        requestQueue.add(request);
    }
}