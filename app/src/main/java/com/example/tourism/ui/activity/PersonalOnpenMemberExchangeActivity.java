package com.example.tourism.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.tourism.R;
import com.example.tourism.ui.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonalOnpenMemberExchangeActivity extends BaseActivity {

    @BindView(R.id.btn_return)
    ImageView btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_open_member_exchange);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_return)
    public void onViewClicked() {
        finish();
    }
}
