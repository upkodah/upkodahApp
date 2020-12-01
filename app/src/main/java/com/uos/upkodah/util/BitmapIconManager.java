package com.uos.upkodah.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.uos.upkodah.R;

import java.lang.reflect.Field;
import java.util.HashMap;

public class BitmapIconManager {
    private static BitmapIconManager manager = null;
    private final HashMap<String, Bitmap> bitmapCache = new HashMap<>();

    public synchronized Bitmap get(String key){
        // Key값으로 비트맵 객체를 얻습니다. 캐싱이 되지 않았다면 null
        // 넣기 전에 아이콘 크기 변형
        Bitmap result = bitmapCache.get(key);
        Log.d("MAP", key+"의 마커 이미지"+result);
        return bitmapCache.get(key);
    }
    public synchronized void put(String key, Bitmap iconBitmap){
        // 넣기 전에 아이콘 크기 변경
        int width = 100;
        int height = 100;
        int radius = width/2;

        Bitmap resizedBitmap = Bitmap.createScaledBitmap(iconBitmap, width,height,false);
        Bitmap background = Bitmap.createBitmap(width, height, resizedBitmap.getConfig());
        Canvas canvas = new Canvas(background);

        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#d6e2f4"));
        canvas.drawCircle(radius,radius,radius,paint);
        canvas.drawBitmap(resizedBitmap, 0,0,null);

        bitmapCache.put(key, background);
    }

    /**
     * 해당 메소드는 'onCreate' 메소드에서 반드시 실행해야 합니다.
     * 그렇지 않으면 NullPointerException 에러를 발생시킵니다.
     */
    public synchronized static BitmapIconManager getInstance(){
        if(manager==null){
            manager = new BitmapIconManager();
        }
        return manager;
    }

}
