package com.example.tourism.ui.activity;

import android.os.Bundle;
import android.app.Activity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;
import android.widget.ListView;

import com.example.tourism.R;
import com.example.tourism.adapter.RedEnvelopsItemAdapter;
import com.example.tourism.entity.RedEnvelops;
import com.example.tourism.ui.activity.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class ServiceExplainActivity extends BaseActivity {

    private Toolbar toolBar;
    private TextView toolbarTitle;
    private ListView serviceExplain;
    List<RedEnvelops> object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_explain);

        toolBar = (Toolbar) findViewById(R.id.toolBar);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        serviceExplain = (ListView) findViewById(R.id.serviceExplain);
        initToolBar();
        initData();
        initServiceExplain();
    }

    public void initToolBar(){
        toolBar.setTitle("");
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initData(){
        object = new ArrayList<>();
        object.add(new RedEnvelops("豪华游","享受豪华型住宿标准"));
        object.add(new RedEnvelops("无自费","一旦网上预订并付款后，无自费项目，包括但不限于无附加费，无强迫自费项目，不收取任何特殊人群附加费。"));
        object.add(new RedEnvelops("无购物","一旦网上预订并付款后，行程中无购物点（特殊路线除外），绝无强迫"));
        object.add(new RedEnvelops("特定成团","一旦网上预订并付款后，不管该团期参团人数多少，肯定会发团，保障消费者可以按时出游。"));
    }

    public void initServiceExplain(){
        RedEnvelopsItemAdapter adapter = new RedEnvelopsItemAdapter(ServiceExplainActivity.this,object);
        serviceExplain.setAdapter(adapter);
    }
}
