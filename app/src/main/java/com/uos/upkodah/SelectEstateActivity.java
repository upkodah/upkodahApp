package com.uos.upkodah;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.uos.upkodah.slide.ViewSlider;

public class SelectEstateActivity extends AppCompatActivity {
    private ViewSlider slider;
    private boolean is = false;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_estate);

        final Button button = (Button) findViewById(R.id.slide_list_btn);
        final View listContainer = findViewById(R.id.estate_list_container);
        final ConstraintLayout map = findViewById(R.id.estate_map);
        final View estateList = findViewById(R.id.estate_list);

        slider = new ViewSlider(ViewSlider.DOWN, 1000);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(is){
                    System.out.println("UP");
                    ObjectAnimator animator = ObjectAnimator.ofFloat(listContainer, "translationY", 0,1000);
                    is = false;
                    animator.setDuration(1000);
                    animator.start();
                    estateList.setVisibility(View.GONE);

                    map.invalidate();
                }
                else{
                    System.out.println("DOWN");
                    ObjectAnimator animator2 = ObjectAnimator.ofFloat(listContainer, "translationY", 1000,0);
                    is = true;
                    animator2.setDuration(1000);
                    animator2.start();
                    estateList.setVisibility(View.VISIBLE);
                    map.invalidate();
                }

            }
        });
    }
}
