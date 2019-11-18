package com.example.tourism.ui.activity;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.example.tourism.R;
import com.example.tourism.application.ServerApi;
import com.example.tourism.common.DefineView;
import com.example.tourism.entity.ScenicSpot;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.ui.fragment.PlaneTicketFragment;
import com.example.tourism.utils.AppUtils;
import com.example.tourism.widget.CustomToolbar;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 机票、车票预订页
 */
public class AirTicketActivity extends BaseActivity implements DefineView {
    @BindView(R.id.custom_toolbar)
    CustomToolbar customToolbar;
    @BindView(R.id.tl_air_ticket)
    TabLayout tlAirTicket;
    @BindView(R.id.fl_air_ticket)
    FrameLayout flAirTicket;
    //机票预订fragment
    private PlaneTicketFragment planeTicketFragment = new PlaneTicketFragment();

    //网络请求api
    private ServerApi api;
    //景区数据集
    private List<ScenicSpot> scenicSpotList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_ticket_layout);
        ButterKnife.bind(this);
        initView();
        initValidata();
        initListener();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initValidata() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fl_air_ticket, planeTicketFragment);
        transaction.commit();
        bindData();
    }

    @Override
    public void initListener() {
        customToolbar.setOnLeftButtonClickLister(() -> finish());

        //tablayout 点击监听
        tlAirTicket.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                AppUtils.getToast(tab.getPosition()+"");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void bindData() {
        String[] mTitles = {"飞机", "火车票", "汽车票", "船票"};
        for (int i = 0; i < mTitles.length; i++) {
            tlAirTicket.addTab(tlAirTicket.newTab().setText(mTitles[i]));
        }
    }
}
