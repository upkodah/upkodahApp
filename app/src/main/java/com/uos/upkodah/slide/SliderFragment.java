package com.uos.upkodah.slide;

import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.Button;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.uos.upkodah.R;

/**
 * 해당 Fragment는 내부에 포함된 특정 Fragment를 감싸
 * 상, 하, 좌, 우 중 한 방향으로 슬라이드가 가능하도록 하는 Fragment입니다.
 */
public class SliderFragment extends Fragment {
//    public void s(){
//        final Button button = (Button) findViewById(R.id.slide_list_btn);
//        final View listContainer = findViewById(R.id.estate_list_container);
//        final ConstraintLayout map = findViewById(R.id.estate_map);
//        final View estateList = findViewById(R.id.estate_list);
//
//        slider = new ViewSlider(ViewSlider.DOWN, 1000);
//
//        button.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                if(is){
//                    System.out.println("UP");
//                    ObjectAnimator animator = ObjectAnimator.ofFloat(listContainer, "translationY", 0,1000);
//                    is = false;
//                    animator.setDuration(1000);
//                    animator.start();
//                    estateList.setVisibility(View.GONE);
//
//                    map.invalidate();
//                }
//                else{
//                    System.out.println("DOWN");
//                    ObjectAnimator animator2 = ObjectAnimator.ofFloat(listContainer, "translationY", 1000,0);
//                    is = true;
//                    animator2.setDuration(1000);
//                    animator2.start();
//                    estateList.setVisibility(View.VISIBLE);
//                    map.invalidate();
//                }
//
//            }
//        });
//    }

    /**
     * 외부에서 이 Fragment 안의 Fragment에 접근할 수 있도록 반환합니다.
     * @return
     */
    public Fragment getFragment(){
        return null;
    }
}
