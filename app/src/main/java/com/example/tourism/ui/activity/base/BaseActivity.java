package com.example.tourism.ui.activity.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.tourism.R;
import com.example.tourism.widget.StatusBarUtil;

import java.lang.reflect.Field;


/**
 * 基类 Activity
 *
 * Name：laodai
 */
@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this,true);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this,0x55000000);
        }
    }

    /**
     * 设置沉浸式状态栏
     */
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ViewGroup linear_bar = this.findViewById(R.id.bar_layout);
            int statusHeight = getStatusBarHeight();
            linear_bar.post(() -> {
                int titleHeight = linear_bar.getHeight();
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) linear_bar.getLayoutParams();
                params.height = statusHeight + titleHeight;
                linear_bar.setLayoutParams(params);
            });
        }
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
