package com.example.tourism.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.tourism.R;
import com.example.tourism.ui.fragment.PersonerFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonalTalk extends Activity {

    @BindView(R.id.btn_return_arrow)
    ImageView btnReturnArrow;
    @BindView(R.id.btn_personal_data)
    ImageView btnPersonalData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_talk);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_return_arrow,R.id.btn_personal_data})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.btn_return_arrow:
                finish();
                break;
            case R.id.btn_personal_data:
                Intent intent = new Intent(PersonalTalk.this, PersonalDataActivity.class);
                startActivity(intent);
                break;
        }

    }
}
