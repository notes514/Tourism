package com.example.tourism.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class ButtonGridView extends GridView {

    public ButtonGridView(Context context) {
        super(context);
    }

    public ButtonGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
