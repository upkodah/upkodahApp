package com.uos.upkodah.data.local.position;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.uos.upkodah.data.local.gps.GeoCoordinate;
import com.uos.upkodah.data.local.gps.GeoCoordinateUtil;
import com.uos.upkodah.data.local.position.composite.CompositePositionInformation;
import com.uos.upkodah.server.extern.KakaoAPIRequest;
import com.uos.upkodah.server.extern.parser.CoordToRegionParser;
import com.uos.upkodah.util.GridIDToCoord;

/**
 * 실제 사용될 위치 정보를 위해 준비해야하는 수단을 정의하는 클래스
 */
public abstract class LocationInformation extends PositionInformation{
    protected Region regionData = null;
    @Nullable
    public Region getRegionData() {
        return regionData;
    }

    private String gridId;
    public LocationInformation(String gridId){
        this.gridId = gridId;
    }
    public LocationInformation(Region regionData){
        this.regionData = regionData;
        this.gridId = regionData.grid;
    }

    public void buildRegion(Context context, @Nullable OnRegionBuildListener listener){
        requestInstance(context, listener);
    }

    public abstract String getClassifyingKey();

    public class Region{
        public final String city;
        public final String gu;
        public final String dong;
        public final String grid;
        public final GeoCoordinate gridCenter;

        private Region(String city, String gu, String dong, String grid){
            this.city = city;
            this.grid = grid;
            this.gu = gu;
            this.dong = dong;
            gridCenter = GridIDToCoord.convert(grid);
        }

        @NonNull
        @Override
        public String toString() {
            return gu+" "+dong+" "+grid+" "+ GeoCoordinateUtil.toString(gridCenter);
        }
    }
    private void requestInstance(Context context, final OnRegionBuildListener listener){
        // 위치정보로 요청
        KakaoAPIRequest.getCoordToRegionRequest(GridIDToCoord.convert(gridId), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("MYTEST", response);
                CoordToRegionParser parser = CoordToRegionParser.getInstance(response);

                if(parser!=null){
                    LocationInformation.this.regionData = new Region(parser.getRegion(0), parser.getRegion(1), parser.getRegion(2), gridId);

                    if(listener!=null) listener.onBuild(LocationInformation.this.regionData);
                }
            }
        }, null).request(context);
    }
    public interface OnRegionBuildListener {
        public void onBuild(Region data);
    }
}
