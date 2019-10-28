package com.example.tourism.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;

import com.example.tourism.R;
import com.example.tourism.adapter.PhotoPagerAdapter;
import com.example.tourism.widget.ViewPagerFixed;

import java.util.ArrayList;

public class BigImageActivity extends AppCompatActivity {
    private ViewPagerFixed viewPager;
    private TextView tvNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_image);
        initView();

    }

    private void initView(){
        viewPager = findViewById(R.id.viewpager);
        tvNum = findViewById(R.id.tv_num);

        //接收图片数据及位置
        final ArrayList<String> imgData = getIntent().getStringArrayListExtra("imgData");
        int clickPosition = getIntent().getIntExtra("clickPosition",0);
        //添加适配器
        PhotoPagerAdapter viewPagerAdapter = new PhotoPagerAdapter(getSupportFragmentManager(), imgData);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(clickPosition);//设置选中图片位置

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tvNum.setText(String.valueOf(position + 1) + "/" + imgData.size());
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}
