package com.uos.upkodah.user.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.uos.upkodah.R;
import com.uos.upkodah.util.DisplayDensityCalculator;

import java.util.ArrayList;
import java.util.List;

/**
 * 해당 클래스는 편의시설 아이콘 목록을 표출시키는 Fragment
 */
public class FacilitiesFragment extends Fragment {
    private ArrayList<ImageButton> buttonList = new ArrayList<>();

    private int btnWidth;
    private int btnHeight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        LinearLayout filterList = (LinearLayout) inflater.inflate(R.layout.fragment_facilities, container, false);

        // 버튼 사이즈 계산
        btnWidth = DisplayDensityCalculator.toPx(getContext(), 50);
        btnHeight = DisplayDensityCalculator.toPx(getContext(), 50);

        // 리스트에 추가
        makeButton(android.R.drawable.star_on);
        makeButton(android.R.drawable.star_on);


        // 버튼 총 개수 세어서 4열에 나열한다.
        int count = buttonList.size();
        int columnCount = 4;
        int rowCount = (count-1) / 4 + 1;

        LinearLayout[] rows = new LinearLayout[rowCount];

        int counter = 0;


        for(int i=0; i<rowCount; i++){
            rows[i] = new LinearLayout(getContext());
            rows[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            rows[i].setOrientation(LinearLayout.HORIZONTAL);
            rows[i].setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);

            Drawable divider = getContext().getDrawable(R.drawable.layout_vertical_divider);
            rows[i].setDividerDrawable(divider);

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

        button.setLayoutParams(new ViewGroup.LayoutParams(btnWidth,btnHeight));
        button.setImageResource(drawableId);
        button.setScaleType(ImageView.ScaleType.FIT_XY);

        buttonList.add(button);
    }

    public List<ImageButton> getButtonList(){
        return buttonList;
    }
}
