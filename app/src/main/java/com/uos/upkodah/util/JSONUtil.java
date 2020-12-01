package com.uos.upkodah.util;

import android.util.Log;

import androidx.annotation.Nullable;

import com.uos.upkodah.server.ukd.parser.EstateResultParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONUtil {
    public static String get(JSONObject obj, String str, String defaultValue){
        try{
            return obj.getString(str);
        }
        catch(ClassCastException e){
            Log.d("SERVER", "잘못된 형식 요청");
            return defaultValue;
        }
        catch(JSONException e){
            Log.d("SERVER", "잘못된 JSON 요청");
            return defaultValue;
        }
    }
    public static int get(JSONObject obj, String str, int defaultValue){
        try{
            return obj.getInt(str);
        }
        catch(ClassCastException e){
            Log.d("SERVER", "잘못된 형식 요청");
            return defaultValue;
        }
        catch(JSONException e){
            Log.d("SERVER", "잘못된 JSON 요청");
            return defaultValue;
        }
    }
    public static double get(JSONObject obj, String str, double defaultValue){
        try{
            return obj.getDouble(str);
        }
        catch(ClassCastException e){
            Log.d("SERVER", "잘못된 형식 요청");
            return defaultValue;
        }
        catch(JSONException e){
            Log.d("SERVER", "잘못된 JSON 요청");
            return defaultValue;
        }
    }
    public static boolean get(JSONObject obj, String str, boolean defaultValue){
        try{
            return obj.getBoolean(str);
        }
        catch(ClassCastException e){
            Log.d("SERVER", "잘못된 형식 요청");
            return defaultValue;
        }
        catch(JSONException e){
            Log.d("SERVER", "잘못된 JSON 요청");
            return defaultValue;
        }
    }

    public static List<JSONObject> getJSONObjectList(JSONObject obj, String str){
        List<JSONObject> result = new ArrayList<>();
        Object data;
        try {
            data = obj.get(str);

            if(data instanceof JSONObject){
                result.add((JSONObject) data);
            }
            else if(data instanceof JSONArray){
                JSONArray dataArr = (JSONArray) data;
                Log.d("SERVER", dataArr.toString());
                for(int i=0;i<dataArr.length();i++){
                    result.add(dataArr.getJSONObject(i));
                }
            }

            return result;
        } catch (JSONException e) {
            Log.d("SERVER", "JSON 오브젝트를 정상적으로 얻지 못했습니다.");
        }
        return result;
    }



    public static <T> boolean checkValue(JSONObject obj, String str, T value){
        try{
            T compare = (T) obj.get(str);
            return compare.equals(value);
        } catch (JSONException e) {
            return false;
        } catch (ClassCastException e){
            return false;
        }
    }

    @Nullable
    public static JSONObject getJSONObject(JSONObject obj, String str){
        try{
            return obj.getJSONObject(str);
        }
        catch (JSONException e){
            return null;
        }
    }
}
