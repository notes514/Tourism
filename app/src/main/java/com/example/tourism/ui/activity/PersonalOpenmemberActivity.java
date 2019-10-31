package com.example.tourism.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tourism.R;
import com.example.tourism.ui.activity.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonalOpenmemberActivity extends BaseActivity {

    @BindView(R.id.btn_open)
    Button btnOpen;
    @BindView(R.id.tv_exchange_member)
    TextView tvExchangeMember;
    @BindView(R.id.btn_member_return_arrow)
    ImageView btnMemberReturnArrow;
    private TextView tvNums2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_open_member);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btn_open, R.id.tv_exchange_member,R.id.btn_member_return_arrow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_open:
                Intent intent = new Intent(PersonalOpenmemberActivity.this, PersonalOpenmembershipActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_exchange_member:
                Intent intent1 = new Intent(PersonalOpenmemberActivity.this, PersonalOnpenMemberExchangeActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_member_return_arrow:
                finish();
                break;
        }
    }

}
