package com.uos.upkodah.data.local;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.Response;
import com.uos.upkodah.data.local.gps.GeoCoordinate;
import com.uos.upkodah.data.local.gps.GeoCoordinateUtil;
import com.uos.upkodah.server.extern.KakaoAPIRequest;
import com.uos.upkodah.server.extern.parser.CoordToRegionParser;
import com.uos.upkodah.util.GridIDToCoord;

public class RegionData{
    private String gu;
    private String dong;
    private String grid;
    private GeoCoordinate gridCenter;
    private OnBuildListener listener;

    protected RegionData(String grid){
        this.grid = grid;
        gridCenter = GridIDToCoord.convert(grid);
    }

    public static void requestInstance(Context context, OnBuildListener listener, int gridId){
        requestInstance(context, listener, Integer.toString(gridId));
    }
    public static void requestInstance(Context context, OnBuildListener listener, String gridId){
        final RegionData result = new RegionData(gridId);
        result.listener = listener;

        // 위치정보로 요청
        KakaoAPIRequest.getCoordToRegionRequest(result.gridCenter, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("MYTEST", response);
                CoordToRegionParser parser = CoordToRegionParser.getInstance(response);

                if(parser!=null){
                    result.gu = parser.getRegion(1);
                    result.dong = parser.getRegion(2);
                    result.listener.onBuild(result);
                }
            }
        }, null).request(context);
        KakaoAPIRequest.getSearchKeywordRequest("식당", result.gridCenter, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        },null);
    }

    public String getGu() {
        return gu;
    }
    public String getDong() {
        return dong;
    }
    public String getGrid() {
        return grid;
    }

    @NonNull
    @Override
    public String toString() {
        return gu+" "+dong+" "+grid+" "+ GeoCoordinateUtil.toString(gridCenter);
    }

    public interface OnBuildListener{
        public void onBuild(RegionData data);
    }
}
