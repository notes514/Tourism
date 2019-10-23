package com.example.tourism.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 获取子view的高度
 */
public class ChildAutoViewPager extends ViewPager {
    //表示当前显示的第几个页面
    private int current;
    //保存子控件的测量值
    private int height;
    //保存position与对应的View
    private HashMap<Integer, View> mChildrenViews = new LinkedHashMap<>();
    //标记是否能够移动
    private boolean scrollble = true;

    public ChildAutoViewPager(@NonNull Context context) {
        super(context);
    }

    public ChildAutoViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 测量当前控件高度
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mChildrenViews.size() > current) {
            View child = mChildrenViews.get(current);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            height = child.getMeasuredHeight();
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 重置当前控件的高度
     *
     * @param current 当前页面
     */
    public void resetHeight(int current) {
        this.current = current;
        if (mChildrenViews.size() > current) {
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
            } else {
                layoutParams.height = height;
            }
            setLayoutParams(layoutParams);
        }
    }

    /**
     * 保存position与对应的View
     * @param position
     * @param view
     */
    public void setObjectForPosition(int position, View view) {
        mChildrenViews.put(position, view);
    }

    /**
     * onTouchEvent方法是重载的Activity方法
     * 重写了Acitivity的onTouchEvent方法后，当屏幕有Touch(触摸)事件时，此方法就会被调用。
     *（当把手放到Activity上时，onTouchEvent方法会一遍一遍的被调用）
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return !scrollble || super.onTouchEvent(ev);
    }

    public boolean isScrollble() {
        return scrollble;
    }

    public void setScrollble(boolean scrollble) {
        this.scrollble = scrollble;
    }
}
