package com.uos.upkodah.util;

import android.content.Context;

/**
 * 픽셀 밀도(dp) 계산을 위해 사용되는 클래스
 */
public class DisplayDensityCalculator {
    public static int toPx(Context context, int dp){
        final float scale = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * scale);
    }
}
