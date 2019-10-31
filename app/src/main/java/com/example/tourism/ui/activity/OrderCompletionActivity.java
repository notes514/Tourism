package com.example.tourism.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.tourism.R;
import com.example.tourism.common.DefineView;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.widget.CustomToolbar;
import com.example.tourism.widget.MyScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderCompletionActivity extends BaseActivity implements DefineView {
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_date_place_days)
    TextView tvDatePlaceDays;
    @BindView(R.id.tv_room_difference)
    TextView tvRoomDifference;
    @BindView(R.id.tv_choice)
    TextView tvChoice;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.et_qq)
    EditText etQq;
    @BindView(R.id.et_wechat)
    EditText etWechat;
    @BindView(R.id.tv_trip)
    TextView tvTrip;
    @BindView(R.id.et_adult)
    EditText etAdult;
    @BindView(R.id.iv_forward)
    ImageView ivForward;
    @BindView(R.id.switch1)
    Switch switch1;
    @BindView(R.id.view_requirement)
    View viewRequirement;
    @BindView(R.id.et_requirement)
    EditText etRequirement;
    @BindView(R.id.checkBox)
    CheckBox checkBox;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.order_scrollView)
    MyScrollView orderScrollView;
    @BindView(R.id.custom_toolbar)
    CustomToolbar customToolbar;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_cost_details)
    TextView tvCostDetails;
    @BindView(R.id.btn_reserve)
    Button btnReserve;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_completion_layout);
        ButterKnife.bind(this);

    }

    @Override
    public void initView() {

    }

    @Override
    public void initValidata() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void bindData() {

    }


}
