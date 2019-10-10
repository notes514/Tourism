package com.example.tourism.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


/**
 * 引导页面类
 */
public class StartPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建子线程
        new Thread(() -> {
            try {
                Thread.sleep(100); //使程序休眠一秒
                Intent intent = new Intent(StartPageActivity.this, WelcomeActivity.class);
                startActivity(intent);
                finish();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }

}
