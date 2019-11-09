package com.example.tourism.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.tourism.R;
import com.example.tourism.common.DefineView;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.widget.CustomToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonalOpenmemberActivity extends BaseActivity implements DefineView {

    @BindView(R.id.btn_open)
    Button btnOpen;
    @BindView(R.id.custom_toolbar)
    CustomToolbar customToolbar;
    private TextView tvNums2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_open_member);
        ButterKnife.bind(this);
        initListener();
    }
    @Override
    public void initView() {
        Intent intent = new Intent(PersonalOpenmemberActivity.this,PersonalOnpenMemberExchangeActivity.class);
        startActivity(intent);
    }

    @Override
    public void initValidata() {

    }

    @Override
    public void initListener() {
        customToolbar.setOnRightTitleClickLister(() -> initView());
        customToolbar.setOnLeftButtonClickLister(() -> finish());

    }

    @Override
    public void bindData() {

    }

    @OnClick(R.id.btn_open)
    public void onViewClicked() {
        Intent intent = new Intent(PersonalOpenmemberActivity.this, PersonalOpenmembershipActivity.class);
        startActivity(intent);
    }
}
