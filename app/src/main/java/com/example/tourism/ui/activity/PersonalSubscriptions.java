package com.example.tourism.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import com.example.tourism.R;
import com.example.tourism.common.DefineView;
import com.example.tourism.widget.CustomToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonalSubscriptions extends Activity implements DefineView {


    @BindView(R.id.custom_toolbar)
    CustomToolbar customToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_subscriptions);
        ButterKnife.bind(this);
        initListener();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initValidata() {

    }

    @Override
    public void initListener() {
        customToolbar.setOnLeftButtonClickLister(() -> show());
    }

    @Override
    public void bindData() {

    }

    public void show() {
        finish();
    }
}
