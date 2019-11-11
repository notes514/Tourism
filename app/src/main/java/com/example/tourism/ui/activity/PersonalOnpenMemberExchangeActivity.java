package com.example.tourism.ui.activity;

import android.os.Bundle;

import com.example.tourism.R;
import com.example.tourism.common.DefineView;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.widget.CustomToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonalOnpenMemberExchangeActivity extends BaseActivity implements DefineView {


    @BindView(R.id.custom_toolbar)
    CustomToolbar customToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_open_member_exchange);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {
        customToolbar.setOnLeftButtonClickLister(() ->finish());
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
