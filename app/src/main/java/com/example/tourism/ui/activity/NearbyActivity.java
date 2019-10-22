package com.example.tourism.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.tourism.R;
import com.example.tourism.application.InitApp;
import com.example.tourism.common.RequestURL;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NearbyActivity extends BaseActivity {

    @BindView(R.id.toolBar)
    Toolbar toolbar;
    @BindView(R.id.imageView)
    ImageView imageView;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);
        unbinder = ButterKnife.bind(this, this);
        //setSupportActionBar(toolbar);
        setActionBar(toolbar);
        //显示返回按钮 禁用标题
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
         initMap();
    }

    private void initMap(){
        ImageLoader.getInstance().displayImage(RequestURL.ip_images+"images/map.jpg",imageView, InitApp.getOptions());
    }
}
