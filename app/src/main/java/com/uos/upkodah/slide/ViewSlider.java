package com.uos.upkodah.slide;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.TranslateAnimation;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ViewSlider {
    private boolean isDisplaying;

    public final static int UP = 0;
    public final static int DOWN = 1;
    public final static int LEFT = 2;
    public final static int RIGHT = 3;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef(value={UP, DOWN, LEFT, RIGHT})
    public @interface Direction{}

    public ViewSlider(@Direction int direction, int distance){
        int fromXDelta = 0;
        int toXDelta = 0;
        int fromYDelta = 0;
        int toYDelta = 0;

        switch(direction){
            case UP:
                toYDelta -= distance;
                break;
            case DOWN:
                toYDelta += distance;
                break;
            case LEFT:
                toXDelta -= distance;
                break;
            case RIGHT:
                toXDelta += distance;
                break;
        }
    }

    public void slideIn(View view){
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", 0,1000);
        animator.start();
        view.setVisibility(View.VISIBLE);
        isDisplaying = true;
    }
    public void slideOut(View view){
        view.setVisibility(View.GONE);
        isDisplaying = false;
    }
    public void slide(View view){
        if(isDisplaying){
            slideOut(view);
        }
        else{
            slideIn(view);
        }
    }
}
