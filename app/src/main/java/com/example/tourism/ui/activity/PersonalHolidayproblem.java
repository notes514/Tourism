package com.example.tourism.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.tourism.R;
import com.example.tourism.common.DefineView;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.ui.fragment.PersonerFragment;
import com.example.tourism.utils.AppUtils;
import com.example.tourism.widget.CustomToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PersonalHolidayproblem extends BaseActivity implements DefineView {

    @BindView(R.id.custom_toolbar)
    CustomToolbar customToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holiday_problem);
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
        customToolbar.setOnLeftButtonClickLister(() -> finish());
    }

    @Override
    public void bindData() {

    }
}
