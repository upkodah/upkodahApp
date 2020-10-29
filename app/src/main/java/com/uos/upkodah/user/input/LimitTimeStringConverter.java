package com.uos.upkodah.user.input;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class LimitTimeStringConverter {
    private final static String[] minuteStringList = new String[]{"제한 없음","10분","20분","30분","40분","50분","60분"};
    private int index;

    public final static int NO_LIMIT = 0;

    @Retention(RetentionPolicy.RUNTIME)
    @IntDef(value={NO_LIMIT,10,20,30,40,50,60})
    public @interface LimitMinute{};

    public LimitTimeStringConverter(@LimitMinute int time){
        index = time/10;
    }
    public static int toMinute(String s){
        int index;

        for(index=0;index<minuteStringList.length;index++){
            if(minuteStringList[index].equals(s)){
                return index*10;
            }
        }

        return NO_LIMIT;
    }

    @Override
    public String toString(){
        return minuteStringList[index];
    }

}
