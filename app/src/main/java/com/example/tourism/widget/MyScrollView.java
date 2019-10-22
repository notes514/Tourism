package com.example.tourism.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {

    private OnScrollViewListener mOnScrollViewListener;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollViewListener != null) mOnScrollViewListener.onScrollView(t, oldt);
    }

    public interface OnScrollViewListener {
        /**
         * 下拉监听
         *
         * @param t
         * @param oldt
         */
        void onScrollView(int t, int oldt);
    }

    public void setOnScrollViewListener(OnScrollViewListener mOnScrollViewListener) {
        this.mOnScrollViewListener = mOnScrollViewListener;
    }
}
