package com.uos.upkodah.user.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.uos.upkodah.R;

import java.util.ArrayList;

/**
 * 해당 클래스는 편의시설 아이콘 목록을 표출시키는 Fragment
 */
public class FacilitiesFragment extends Fragment {
    private ArrayList<ImageButton> buttonList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        LinearLayout filterList = (LinearLayout) inflater.inflate(R.layout.facilities_frag, container, false);

        // 리스트에 추가
        makeButton(R.drawable.ic_launcher_foreground);
        makeButton(R.drawable.ic_launcher_foreground);
        makeButton(R.drawable.ic_launcher_foreground);
        makeButton(R.drawable.ic_launcher_foreground);
        makeButton(R.drawable.ic_launcher_foreground);




        // 버튼 총 개수 세어서 4열에 나열한다.
        int count = buttonList.size();
        int columnCount = 4;
        int rowCount = (count-1) / 4 + 1;

        LinearLayout[] rows = new LinearLayout[rowCount];

        int counter = 0;


        for(int i=0; i<rowCount; i++){
            rows[i] = new LinearLayout(getContext());
            rows[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            rows[i].setOrientation(LinearLayout.HORIZONTAL);
//            rows[i].setDividerPadding(20);
            for(int j=0;j<columnCount;j++){
                System.out.println(i);
                System.out.println(j);
                rows[i].addView(buttonList.remove(0));
                if(buttonList.size() <= 0) break;
            }
            filterList.addView(rows[i]);
        }


        return filterList;
    }

    public void makeButton(int drawableId){
        ImageButton button = new ImageButton(getContext());
        button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        button.setImageResource(drawableId);
        button.setScaleX(0.3f);
        button.setScaleY(0.3f);

        buttonList.add(button);
    }
}
