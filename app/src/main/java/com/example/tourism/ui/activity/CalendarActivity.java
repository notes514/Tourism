package com.example.tourism.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.tourism.R;
import com.example.tourism.common.DefineView;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.widget.CustomToolbar;
import com.necer.calendar.MonthCalendar;
import com.necer.view.WeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 日历选择
 * Name:laodai
 * Time:2019.10.28
 */
public class CalendarActivity extends BaseActivity implements DefineView {
    @BindView(R.id.custom_toolbar)
    CustomToolbar customToolbar;
    @BindView(R.id.week_bar)
    WeekBar weekBar;
    @BindView(R.id.monthCalendar)
    MonthCalendar monthCalendar;
    @BindView(R.id.tv_reduse)
    TextView tvReduse;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.btn_completion_order)
    Button btnCompletionOrder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_layout);
        ButterKnife.bind(this);

    }

    @Override
    public void initView() {

    }

    @Override
    public void initValidata() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void bindData() {

    }


}
