package com.example.tourism.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;

import com.example.tourism.R;
import com.example.tourism.adapter.PhotoPagerAdapter;
import com.example.tourism.application.InitApp;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.utils.StatusBarUtil;
import com.example.tourism.widget.ViewPagerFixed;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

import static com.example.tourism.utils.AppUtils.getColor;

public class BigImaActivity extends BaseActivity{

    private PhotoView photoView;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_ima);
        StatusBarUtil.setColor(this,getColor(R.color.color_black_translucent));
        initView();

    }

    private void initView(){
        photoView = findViewById(R.id.photoview);

        String pic = getIntent().getStringExtra("pic");

        ImageLoader.getInstance().displayImage(pic, photoView, InitApp.getOptions());

        photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.finish();
    }
}
