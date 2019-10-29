package com.example.tourism.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.tourism.R;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.ui.fragment.PersonerFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PersonalHolidayproblem extends BaseActivity {

    @BindView(R.id.btn_return_arrow)
    ImageView btnReturnArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holiday_problem);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_return_arrow)
    public void onViewClicked() {
        Intent intent = new Intent(PersonalHolidayproblem.this, PersonerFragment.class);
        finish();
    }
}
