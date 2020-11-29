package com.uos.upkodah.data.local.estate;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.Response;
import com.uos.upkodah.data.Facility;
import com.uos.upkodah.data.local.gps.GeoCoordinate;
import com.uos.upkodah.data.local.position.PositionInformation;
import com.uos.upkodah.data.local.position.PositionPreparedListener;
import com.uos.upkodah.fragment.map.GoogleMapDrawable;
import com.uos.upkodah.server.extern.KakaoAPIRequest;
import com.uos.upkodah.server.extern.parser.SearchKeyworkParser;

import java.util.ArrayList;
import java.util.List;

public class FacilityInformation implements GoogleMapDrawable {
    /**
     * 중심좌표를 기준으로 하는 지정 코드의 모든 편의시설을 긁어 지정 맵에 표시하도록 합니다.
     * @param center 중심 좌표
     * @param codes 표시할 푠의시설 코드
     */
    public static void getFacilityInformations(Context context, GeoCoordinate center, final PositionPreparedListener<FacilityInformation> listener, String...codes){
        // 먼저, 코드에 해당하는 편의시설 정보를 모두 가져옵니다.
        final List<Facility> facilityList = Facility.getGlobalList(codes);
        final List<FacilityInformation> resultList = new ArrayList<>();

        // 중심점 기준으로 모든 편의시설을 긁어옵니다.
        for(final Facility f : facilityList){
            f.requestNearFacilities(context, center, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    SearchKeyworkParser parser = SearchKeyworkParser.getInstance(response);

                    if(parser!=null){
                        for(PositionInformation p : parser.getPositionList()){
                            FacilityInformation fac = new FacilityInformation(f.name, p, f.getIconBitmap());
                            resultList.add(fac);
                            listener.onPrepared(fac);
                        }
                    }
                }
            });
        }
    }

    private final String name;
    private final String category;
    private final double longitude;
    private final double latitude;
    private final Bitmap icon;
    public FacilityInformation(String category, PositionInformation p, Bitmap icon){
        this.name = p.getName();
        this.longitude = p.getLongitude();
        this.latitude = p.getLatitude();
        this.icon = icon;
        if(icon==null) Log.d("MAP", "오류 : 아이콘이 NULL입니다.");
        this.category = category;
    }

    @Override
    public String getMarkerWindowTitle() {
        return name;
    }

    @Override
    public String getMarkerWindowSnippet() {
        return category;
    }

    @Override
    public Bitmap getIconBitmap() {
        return icon;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }

    @Override
    public double getLatitude() {
        return latitude;
    }
}
