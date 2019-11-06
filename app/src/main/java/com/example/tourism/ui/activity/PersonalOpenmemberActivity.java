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
    //    @BindView(R.id.tv_exchange)
//    TextView tvExchangeMember;
//    @BindView(R.id.btn_member_arrow)
//    ImageView btnMemberReturnArrow;
    private TextView tvNums2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_open_member);
        ButterKnife.bind(this);
        initListener();
    }
    private void show1(){
        Intent intent = new Intent(PersonalOpenmemberActivity.this,PersonalOnpenMemberExchangeActivity.class);
        startActivity(intent);
    }
    private void show2(){
        finish();
    }
    @Override
    public void initView() {

    }

    @Override
    public void initValidata() {

    }

    @Override
    public void initListener() {
        customToolbar.setOnRightTitleClickLister(() -> show1());
        customToolbar.setOnLeftButtonClickLister(() -> show2());

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
