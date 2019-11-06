package com.example.tourism.ui.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * ScrollView中嵌套ListView，重写ListView计算高度
 */
public class PersonalScrollviewListViewActivity extends ListView {
    public PersonalScrollviewListViewActivity(Context context) {
        super(context);
    }

    public PersonalScrollviewListViewActivity(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PersonalScrollviewListViewActivity(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,

                MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);

    }
}
