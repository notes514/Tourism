package com.example.tourism.ui.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tourism.R;
import com.example.tourism.ui.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonalOpenmembershipActivity extends BaseActivity {

    @BindView(R.id.btn_re)
    ImageView btnRe;
    private TextView shoujia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_open_membership);
        ButterKnife.bind(this);
        initView();
        //添加删除线
        shoujia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }

    private void initView() {
        shoujia = (TextView) findViewById(R.id.shoujia);
    }

    @OnClick(R.id.btn_re)
    public void onViewClicked() {
        finish();
    }
}
