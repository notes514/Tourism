package com.example.tourism.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * 自定义ScrollView-对外提供滚动监听
 * Name：laodai
 * Time：2019.10.19
 */
public class MyScrollView extends ScrollView {

    private OnScrollListener onScrollListener;

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
        if (onScrollListener != null) {
            onScrollListener.onScrollView(l, t, oldl, oldt);
        }
    }

    /**
     * 由垂直方向滚动条代表的所有垂直范围，缺省的范围是当前视图的画图高度。
     * @return
     */
    @Override
    protected int computeVerticalScrollRange() {
        return super.computeVerticalScrollRange();
    }

    /**
     * 定义滚动监听接口
     */
    public interface OnScrollListener {
        /**
         * 滚动监听方法
         *
         * @param l
         * @param t
         * @param oldl
         * @param oldt
         */
        void onScrollView(int l, int t, int oldl, int oldt);
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

}
