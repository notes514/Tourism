package com.example.tourism.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;


import androidx.viewpager.widget.ViewPager;

public class ViewPagerFixed extends ViewPager {

    public ViewPagerFixed(Context context){
        super(context);
    }

    public ViewPagerFixed(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (IllegalArgumentException ex){
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
