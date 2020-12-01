package com.uos.upkodah.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.uos.upkodah.R;
import com.uos.upkodah.data.local.estate.EstateClassifier;
import com.uos.upkodah.data.local.estate.Room;
import com.uos.upkodah.databinding.ListItemGridEstateBinding;
import com.uos.upkodah.fragment.list.GridListAdapter;
import com.uos.upkodah.fragment.list.SelectionListAdapter;
import com.uos.upkodah.fragment.list.data.SelectionListData;
import com.uos.upkodah.fragment.list.holder.GridListViewHolder;
import com.uos.upkodah.fragment.list.holder.ListViewHolderManager;
import com.uos.upkodah.fragment.map.data.GoogleMapData;
import com.uos.upkodah.data.local.estate.EstateInformation;
import com.uos.upkodah.data.local.position.composite.GridRegionInformation;
import com.uos.upkodah.data.local.position.PositionInformation;
import com.uos.upkodah.data.local.position.composite.GuRegionInformation;
import com.uos.upkodah.data.local.position.composite.DongRegionInformation;

import java.util.ArrayList;
import java.util.List;

/**
 * 이 클래스는 크게 두 가지 기능을 한다.
 * 1) 줌 수준에 따라 mapData의 Positions를 적절히 결정한다.
 * 2) 줌 내에 보이는 모든 positions를 저장한다.
 */
public class SelectEstateViewModel extends AndroidViewModel  {
    public GoogleMapData mapData = new GoogleMapData();
    public SelectionListData listData = new SelectionListData();

    public SelectEstateViewModel(@NonNull Application application){
        super(application);
        mapData.setZoomLevelWithDepth(1);

        listData.setAdapter(adapter);
    }

    private List<GuRegionInformation> estates;

    public void setEstates(List<GuRegionInformation> estates) {
        this.estates = estates;
        if(this.estates!=null){
            mapData.setMapMarkers(estates);
            mapData.setCenterUsingPositions();
        }
    }
    public void setRooms(List<Room> rooms){
        final EstateClassifier classifier = new EstateClassifier(
                this.getApplication().getApplicationContext(),
                new EstateClassifier.Listener() {
                    @Override
                    public void onEstateClassified(EstateClassifier classifier, EstateInformation estateInformation) {
                        setEstates(classifier.getResult());
                        Log.d("MYTEST", classifier.toString());
                    }
                });
        for(Room r : rooms){
            classifier.requestClassify(new EstateInformation(r));
        }
    }

    private int preDepth = 0;
    public boolean isDepthChanged(int depth) {
        return preDepth!=depth;
    }

    @Nullable
    public List<PositionInformation> getDisplayedEstateList(int depth){
        List<PositionInformation> result = new ArrayList<>();
        if(estates==null) return result;

        switch(depth){
            case 1:
                // 구 단위를 보여줘야할 때
                Log.d("MAP", "구 단위로 보여줍니다.");
                preDepth = 1;
                result.addAll(estates);
                break;

            case 2:
                // 동 단위를 보여줘야할 때
                Log.d("MAP", "동 단위로 보여줍니다.");
                preDepth = 2;
                for(GuRegionInformation p : estates){
                    result.addAll(p.getSubInfoList());
                }
                break;

            case 3:
                // Grid 단위를 보여줘야할 때
                Log.d("MAP", "Grid 단위로 보여줍니다.");
                preDepth = 3;
                for(GuRegionInformation p : estates){
                    for(DongRegionInformation p2 : p.getSubInfoList()){
                        result.addAll(p2.getSubInfoList());
                    }
                }
                break;

            case 4:
                // 매물 단위를 보여줘야할 때
                Log.d("MAP", "매물 단위로 보여줍니다.");
                preDepth = 4;
                for(GuRegionInformation p : estates){
                    for(DongRegionInformation p2 : p.getSubInfoList()){
                        for(GridRegionInformation p3 : p2.getSubInfoList()){
                            result.addAll(p3.getSubInfoList());
                        }
                    }
                }
                break;
            default:
                break;
        }
        return result;
    }

    private final GridListAdapter adapter = new GridListAdapter();
    public void setListEstateData(List<EstateInformation> data) {
        adapter.setItemList(data);
        listData.notifyUpdateListData();
    }
    public void setEstateListener(GridListViewHolder.OnClickListener estateListener) {
        adapter.setListener(estateListener);
    }
}
