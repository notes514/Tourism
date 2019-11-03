package com.example.tourism.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.adapter.MonthAdapter;
import com.example.tourism.common.DefineView;
import com.example.tourism.entity.DateBean;
import com.example.tourism.entity.MonthBean;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.utils.AppUtils;
import com.example.tourism.utils.Lunar;
import com.example.tourism.widget.CustomToolbar;
import com.github.mikephil.charting.formatter.IFillFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 日历选择
 * Name:laodai
 * Time:2019.10.28
 */
public class CalendarActivity extends BaseActivity implements DefineView, MonthAdapter.OnMonthChildClickListener {
    @BindView(R.id.custom_toolbar)
    CustomToolbar customToolbar;
    @BindView(R.id.rv_calender)
    RecyclerView rvCalender;
    @BindView(R.id.tv_removes)
    ImageView tvRemoves;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.btn_completion_order)
    Button btnCompletionOrder;
    @BindView(R.id.tv_date_days)
    TextView tvDateDays;
    @BindView(R.id.ll_date_days)
    LinearLayout llDateDays;
    @BindView(R.id.ll_buttoms)
    LinearLayout llButtoms;

    private int type;
    private int scenicSpotId;
    private final int CALENDAR_TODAY = 77; //今天的日历
    private MonthAdapter adapter;
    private List<MonthBean> monthList = new ArrayList<>();
    private int year; //年份
    private int month; //月份
    private int day; //日
    private int nowDay; //当天
    private int lastDateSelect = -1; //上次日期选择
    private int lastMonthSelect = -1; //上个月的选择
    private int number = 1;
    private boolean flag = false;
    private String dateStr = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_layout);
        ButterKnife.bind(this);
        initValidata();
        initListener();
        bindData();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initValidata() {
        //获取指定类型
        type = this.getIntent().getIntExtra("type", 0);
        if (type == 0) {
            //获取商品详情编号
            scenicSpotId = this.getIntent().getIntExtra("scenicSpotId", 0);
            btnCompletionOrder.setText(R.string.tourism_order_calendar_completion_order);
        } else {
            btnCompletionOrder.setText(R.string.tourism_order_calendar_determine);
            tvDateDays.setVisibility(View.GONE);
            llDateDays.setVisibility(View.GONE);
        }

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        nowDay = day;
        calendar.set(year, month, 1);

        for (int i = 0; i < 2; i++) {
            List<DateBean> deList = new ArrayList<>();
            MonthBean monthEntity = new MonthBean();
            int maxDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            int empty = calendar.get(Calendar.DAY_OF_WEEK);
            empty = empty == 1 ? 6 : empty - 2;
            for (int j = 0; j < empty; j++) {
                DateBean de = new DateBean();
                de.setType(1);
                deList.add(de);
            }
            for (int j = 1; j <= maxDayOfMonth; j++) {
                DateBean de = new DateBean();
                if (i == 0) {
                    de.setType(j < nowDay + 1 ? 4 : 0);
                } else {
                    de.setType(0);
                }
//                if (i == 0 && nowDay == j) {
//                    de.setDate(CALENDAR_TODAY);
//                } else {
//                    de.setDate(j);
//                }
                de.setDate(j);
                de.setParentPos(i);
                de.setDesc(Lunar.getLunarDate(year, month + 1, j));
                deList.add(de);
            }

            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH) + 1;
            monthEntity.setTitle(year + "年" + month + "月");
            monthEntity.setYear(year);
            monthEntity.setList(deList);
            monthList.add(monthEntity);

            calendar.add(Calendar.MONTH, 1);
        }
    }

    @Override
    public void initListener() {
        customToolbar.setOnLeftButtonClickLister(() -> finish());
    }

    @Override
    public void bindData() {
        //设置适配器布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvCalender.setLayoutManager(layoutManager);
        adapter = new MonthAdapter(this, monthList);
        adapter.setChildClickListener(this);
        rvCalender.setAdapter(adapter);
        tvNumber.setText(number + "");
    }

    @OnClick({R.id.tv_removes, R.id.iv_add, R.id.btn_completion_order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_removes:
                if (number == 1) return;
                number -= 1;
                tvNumber.setText(number + "");
                break;
            case R.id.iv_add:
                number += 1;
                tvNumber.setText(number + "");
                break;
            case R.id.btn_completion_order:
                if (type == 0) {
                    if (flag) {
                        Intent intent = new Intent(CalendarActivity.this, OrderCompletionActivity.class);
                        intent.putExtra("scenicSpotId", scenicSpotId);
                        intent.putExtra("number", tvNumber.getText().toString());
                        intent.putExtra("date", dateStr);
                        this.startActivity(intent);
                        break;
                    } else {
                        AppUtils.getToast("请先选择出行日期");
                    }
                } else {
                    finish();
                }
                break;
        }
    }

    /**
     * 重写适配器触摸监听
     *
     * @param parentPos
     * @param pos
     */
    @Override
    public void onMonthClick(int parentPos, int pos, Object object) {
        if (parentPos == lastMonthSelect && pos == lastDateSelect) {
            return;
        }
        monthList.get(parentPos).getList().get(pos).setType(8);
        adapter.notifyItemChanged(parentPos);
        if (lastDateSelect != -1) {
            monthList.get(lastMonthSelect).getList().get(lastDateSelect).setType(0);
            adapter.notifyItemChanged(lastMonthSelect);
        }
        lastMonthSelect = parentPos;
        lastDateSelect = pos;

        dateStr = (String) object;
        if (flag) {
            flag = false;
        } else {
            flag = true;
        }
    }

}
