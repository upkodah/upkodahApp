package com.uos.upkodah.data.local.estate;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.uos.upkodah.data.local.RegionData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 해당 클래스는 EstateInformation들의 GridID를 이용해 중심좌표를 뽑아내고
 * 해당 중심좌표로 행정구역 분류를 실시하여
 * 최종적으로 RegionInformation 객체를 반솬하는 클래스이다.
 */
public class EstateClassifier {
    private final List<EstateInformation> estateList;
    private final Context context;

    public EstateClassifier(Context context, EstateInformation...estates){
        this(context, Arrays.asList(estates));
    }
    public EstateClassifier(final Context context, List<EstateInformation> estateList){
        Log.d("MYTEST", "분류기생성");
        this.context = context;
        this.estateList = estateList;

        for(final EstateInformation e : estateList){
            RegionData.OnBuildListener listener = new RegionData.OnBuildListener() {
                @Override
                public void onBuild(RegionData data) {
                    e.setRegion(data);
                    Log.d("MYTEST", data.toString());
                    Toast.makeText(context, data.toString(), Toast.LENGTH_SHORT).show();
                }
            };
            Log.d("MYTEST", "요청 시작");
            RegionData.requestInstance(context, listener, e.getRoomInfo().getGridID());
        }
    }
}