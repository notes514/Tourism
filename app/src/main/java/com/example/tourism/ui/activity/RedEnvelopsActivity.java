package com.example.tourism.ui.activity;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ListView;

import com.example.tourism.R;
import com.example.tourism.adapter.RedEnvelopsItemAdapter;
import com.example.tourism.entity.RedEnvelops;
import com.example.tourism.ui.activity.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class RedEnvelopsActivity extends BaseActivity {

    private Toolbar toolBar;
    private ListView redEnvelops;
    List<RedEnvelops> object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_envelops);

        toolBar = (Toolbar) findViewById(R.id.toolBar);
        redEnvelops = (ListView) findViewById(R.id.redEnvelops);
        initToolBar();
        initData();
        initRedEnvelops();
    }

    public void initToolBar(){
        toolBar.setTitle("");
        toolBar.setTitleTextAppearance(RedEnvelopsActivity.this,R.style.MyBar);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initData(){
        object = new ArrayList<>();
        object.add(new RedEnvelops("现金红包","使用现金红包，可享受立减优惠（不可与本网其他优惠活动同时使用，保险订单金额不参与优惠活动"));
        object.add(new RedEnvelops("专享红包","使用专享红包，可享受立减优惠（不可与本网其他优惠活动同时使用，保险订单金额不参与优惠活动"));
        object.add(new RedEnvelops("代金券","使用代金券，可享受立减优惠（不可与本网其他优惠活动同时使用，保险订单金额不参与优惠活动"));
    }

    public void initRedEnvelops(){
        RedEnvelopsItemAdapter adapter = new RedEnvelopsItemAdapter(RedEnvelopsActivity.this,object);
        redEnvelops.setAdapter(adapter);
    }
}
