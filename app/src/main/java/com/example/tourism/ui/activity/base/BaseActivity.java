package com.example.tourism.ui.activity.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.tourism.R;
import com.example.tourism.utils.AppUtils;
import com.example.tourism.utils.StatusBarUtil;

import java.lang.reflect.Field;

/**
 * 基类 Activity
 *
 * Name：laodai
 */
@SuppressLint("Registered")
public class BaseActivity extends FragmentActivity {

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this, AppUtils.getColor(R.color.color_blue));
    }

    /**
     * 获取状态栏的高度
     * @return
     */
    protected int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 对当前Activity跳转进行封装，重用
     *
     * @param tClass
     */
    protected void openActivity(Class<?> tClass) {
        Intent intent = new Intent(this, tClass);
        this.startActivity(intent);
    }

}
