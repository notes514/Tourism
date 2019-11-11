package com.example.tourism.ui.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tourism.R;
import com.example.tourism.common.DefineView;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.ui.fragment.PersonalPayDetailFragment;
import com.example.tourism.widget.CustomToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonalOpenmembershipActivity extends BaseActivity implements DefineView {

    @BindView(R.id.custom_toolbar)
    CustomToolbar customToolbar;
    //    @BindView(R.id.btn_re)
//    ImageView btnRe;
//    @BindView(R.id.btn_zhifu)
//    Button btnZhifu;
    private TextView shoujia;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_open_membership);
        ButterKnife.bind(this);
        initView();
        show();
        initListener();
        //添加删除线
        shoujia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }

    public void initView() {
        shoujia = (TextView) findViewById(R.id.shoujia);
    }

    @Override
    public void initValidata() {

    }

    @Override
    public void initListener() {
        customToolbar.setOnLeftButtonClickLister(() ->finish());
    }

    @Override
    public void bindData() {

    }

    private void show() {
        mButton = (Button) this.findViewById(R.id.btn_zhifu);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonalPayDetailFragment payDetailFragment = new PersonalPayDetailFragment();
                payDetailFragment.show(getSupportFragmentManager(), "payDetailFragment");
            }
        });
    }
}
