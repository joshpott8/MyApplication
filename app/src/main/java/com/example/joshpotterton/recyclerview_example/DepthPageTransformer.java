package com.example.joshpotterton.recyclerview_example;

import android.support.v4.view.ViewPager;
import android.view.View;

public class DepthPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.75f;

    @Override
    public void transformPage(View view, float v) {
        int pageWitdh = view.getWidth();

        if(v < -1){
            view.setAlpha(0);
        }
        else if(v <= 0){
            view.setAlpha(1);
            view.setTranslationX(0);
            view.setScaleX(1);
            view.setScaleY(1);
        }
        else if(v <= 1){
            view.setAlpha(1 - v);

            view.setTranslationX(pageWitdh * -v);

            float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(v));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
        }
        else{
            view.setAlpha(0);
        }
    }
}
