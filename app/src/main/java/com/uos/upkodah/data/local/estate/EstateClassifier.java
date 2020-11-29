package com.uos.upkodah.data.local.estate;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.uos.upkodah.data.local.position.LocationInformation;
import com.uos.upkodah.data.local.position.composite.CompositePositionInformation;
import com.uos.upkodah.data.local.position.composite.DongRegionInformation;
import com.uos.upkodah.data.local.position.composite.GridRegionInformation;
import com.uos.upkodah.data.local.position.composite.GuRegionInformation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 해당 클래스는 EstateInformation들의 GridID를 이용해 중심좌표를 뽑아내고
 * 해당 중심좌표로 행정구역 분류를 실시하여
 * 최종적으로 RegionInformation 객체를 반솬하는 클래스이다.
 */
public class EstateClassifier {
    private final Context context;

    public EstateClassifier(final Context context, @Nullable Listener classifyListener){
        Log.d("MYTEST", "분류기생성");
        this.classifyListener = classifyListener;
        this.context = context;
    }

    private final HashMap<String, GridRegionInformation> gridMap = new HashMap<>();
    private final HashMap<String, DongRegionInformation> dongMap = new HashMap<>();
    private final HashMap<String, GuRegionInformation> guMap = new HashMap<>();

    public void requestClassify(final EstateInformation e){
        final LocationInformation.OnRegionBuildListener listener = new LocationInformation.OnRegionBuildListener() {
            @Override
            public void onBuild(LocationInformation.Region data) {
                // 지역정보가 완성되었으면 지역 분류를 시작한다.
                Log.d("MYTEST", data.toString());

                putEstate(e);
            }
        };
        Log.d("MYTEST", "요청 시작");
        e.buildRegion(context, listener);
    }

    public void putEstate(EstateInformation e){
        String key = e.getClassifyingKey();

        // 이제 해당 키에 맞는 region이 있는지 확인
        if(gridMap.containsKey(key)){
            // 있으면 넣는다.
            gridMap.get(key).addSubInformation(e);
            Log.d("MYTEST", "현재개수 : "+gridMap.get(key).getSubInfoList().size());
        }
        else{
            // 없으면 만들어서 넣는다.
            gridMap.put(key, makeGrid(e));
        }

        if(classifyListener!=null) classifyListener.onEstateClassified(EstateClassifier.this, e);
    }
    private GridRegionInformation makeGrid(EstateInformation e){
        GridRegionInformation result = new GridRegionInformation(e.getRegionData());
        result.addSubInformation(e);

        String key = result.getClassifyingKey();

        if(dongMap.containsKey(key)){
            // 있으면 넣는다.
            dongMap.get(key).addSubInformation(result);
        }
        else{
            // 없으면 만들어서 넣는다.
            dongMap.put(key, makeDong(result));
        }

        return result;
    }
    private DongRegionInformation makeDong(GridRegionInformation g){
        DongRegionInformation result = new DongRegionInformation(g.getRegionData());
        result.addSubInformation(g);

        String key = result.getClassifyingKey();

        if(guMap.containsKey(key)){
            // 있으면 넣는다.
            guMap.get(key).addSubInformation(result);
        }
        else{
            // 없으면 만들어서 넣는다.
            guMap.put(key, makeGu(result));
        }

        return result;
    }
    private GuRegionInformation makeGu(DongRegionInformation d){
        GuRegionInformation result = new GuRegionInformation(d.getRegionData());
        result.addSubInformation(d);

        return result;
    }

    public List<GuRegionInformation> getResult() {
        return new ArrayList<>(guMap.values());
    }

    public void setListener(Listener listener) {
        this.classifyListener = listener;
    }
    public interface Listener{
        public void onEstateClassified(EstateClassifier classifier, EstateInformation estateInformation);
    }
    private Listener classifyListener = null;

    @NonNull
    @Override
    public String toString() {
        return gridMap.size()+" "+dongMap.size()+" "+guMap.size();
    }
}