package com.uos.upkodah.server.ukd;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.uos.upkodah.test.TestGeocoordGetter;
import com.uos.upkodah.util.JSONUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EstateSearchRequest extends StringRequest {
    protected EstateSearchRequest(String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
        Log.d("SERVER", "요청 URL :"+url);
    }
    protected static String getSearchRequestURL(UserDataToTransmit data) throws JSONException {
//        String result = ServerInfo.SERVER_ADDR +"/v1/rooms/1?"
//                +"longitude="+data.longitude+"&"
//                +"latitude="+data.latitude+"&"
//                +"limit_time="+data.limit_time+"&"
//                +"estate_type="+data.estate_type+"&"
//                +"trade_type="+data.trade_type;

        // TEST
        // 먼저, lonlat 목록에서 data와 가장 가까운곳의 번호를 찾는다.
        int num = TestGeocoordGetter.findNear(data);

        // 이 숫자를 짝수로 만든다.
        num -= num%2;

        // 해당 숫자에서 1을 더해야 실제 id와 매핑이 된다.
        num++;

        Log.d("SERVERT", "검색위치 : "+TestGeocoordGetter.locationList[num]);
        Log.d("SERVERT", "위도경도 : "+data.longitude+" "+data.latitude);
        Log.d("SERVERT", "제한시간 : "+data.limit_time);

        num+=12;

        // 이제 해당 숫자를 제한시간에 따라 나눠 요청한다.
        String result = ServerInfo.SERVER_ADDR +"/v1/rooms/";
        if(data.limit_time==20){
            Log.d("SERVERT", "제한시간 20분으로 요청합니다."+num);
            result+=(num)+"";
        }else{
            Log.d("SERVERT", "제한시간 30분으로 요청합니다."+(num+1));
            result+=(1+num)+"";
        }
//        String result = ServerInfo.SERVER_ADDR +":80/v1/rooms/?"
//                +"longitude="+ TestGeocoordGetter.coordList[7].getLongitude() +"&"
//                +"latitude="+TestGeocoordGetter.coordList[7].getLatitude()+"&"
//                +"limit_time="+data.limit_time+"&"
//                +"estate_type="+0+"&"
//                +"trade_type="+0+"&"
//                +"deposit=10000";
//
//        if(data.facilities.length > 0){
//            result += "&";
//
//            for(int i=0;i<data.facilities.length;i++){
//                result += "facilities["+i+"]="+data.facilities[i];
//                if(i<data.facilities.length-1) result+="&";
//            }
//        }
        Log.d("SERVER","요청 URL : "+result);

//        return ServerInfo.SERVER_ADDR +"/v1/rooms/7";
        return result;
    }
    protected static String getIDRequestURL(int id){
        return ServerInfo.SERVER_ADDR+"/v1/room/"+id+"/info";
    }
    public static RequestQueue requestQueue = null;

    private HashMap<String, String> params = new HashMap<>();
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }

    public static EstateSearchRequest getInstanceIDRequest(int id, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener){
        return new EstateSearchRequest(getIDRequestURL(id),listener, errorListener);
    }

    @Nullable
    public static EstateSearchRequest getInstanceSearchRequest(UserDataToTransmit data, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener){
        try {
            return new EstateSearchRequest(getSearchRequestURL(data),listener, errorListener);
        } catch (JSONException e) {
            return null;
        }
    }


    public synchronized void request(Context context){
        if(requestQueue==null) requestQueue = Volley.newRequestQueue(context);

        requestQueue.add(this);
    }
}
