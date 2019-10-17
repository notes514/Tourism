package com.example.tourism.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.tourism.R;
import com.example.tourism.ui.fragment.PersonerFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonalSubscriptions extends Activity {

    @BindView(R.id.btn_return_arrow)
    ImageView btnReturnArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_subscriptions);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_return_arrow)
    public void onViewClicked() {
        Intent intent = new Intent(PersonalSubscriptions.this, PersonerFragment.class);
        finish();
    }
}
