package com.example.tourism.ui.activity;

import android.os.Bundle;
import android.app.Activity;

import com.example.tourism.R;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.widget.CustomToolbar;
import com.example.tourism.widget.MyScrollView;

import android.widget.LinearLayout;
import android.widget.TextView;

public class OrderCancelLayoutActivity extends BaseActivity {

    private CustomToolbar customToolbar;
    private MyScrollView scroll;
    private LinearLayout contactsupplier;
    //产品名称
    private TextView productName;
    //出行方式
    private TextView tripMode;
    //出发团期
    private TextView departurePeriod;
    //应付金额
    private TextView amountPayable;
    //联系人
    private TextView contacts;
    //联系人电话
    private TextView phoneNumber;
    //联系人QQ
    private TextView qqNumber;
    //联系人留言
    private TextView contactsComment;
    //旅客姓名
    private TextView passengerName;
    //旅客身份证号
    private TextView passengerNumber;
    //成人旅客
    private TextView adultP;
    //订单总额
    private TextView orderTotal;
    //房差
    private TextView roomDifference;
    //订单编号
    private TextView orderNumber;
    //订单时间
    private TextView orderTime;
    //提供商
    private TextView supplier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_cancel_layout);

        customToolbar = (CustomToolbar) findViewById(R.id.custom_toolbar);
        scroll = (MyScrollView) findViewById(R.id.scroll);
        contactsupplier = findViewById(R.id.lianxigongyingshang);
        productName = (TextView) findViewById(R.id.productName);
        tripMode = (TextView) findViewById(R.id.tripMode);
        departurePeriod = (TextView) findViewById(R.id.departurePeriod);
        amountPayable = (TextView) findViewById(R.id.amountPayable);
        contacts = (TextView) findViewById(R.id.contacts);
        phoneNumber = (TextView) findViewById(R.id.phoneNumber);
        qqNumber = (TextView) findViewById(R.id.qqNumber);
        contactsComment = (TextView) findViewById(R.id.contactsComment);
        passengerName = (TextView) findViewById(R.id.passengerName);
        passengerNumber = (TextView) findViewById(R.id.passengerNumber);
        adultP = (TextView) findViewById(R.id.adultP);
        roomDifference = (TextView) findViewById(R.id.roomDifference);
        orderTotal = (TextView) findViewById(R.id.orderTotal);
        amountPayable = (TextView) findViewById(R.id.amountPayable);
        orderNumber = (TextView) findViewById(R.id.orderNumber);
        orderTime = (TextView) findViewById(R.id.orderTime);
        supplier = (TextView) findViewById(R.id.supplier);



    }

}
