package com.example.tourism.application;

import android.app.Application;

/**
 * 全局Application类,作为全局数据的配置以及相关参数数据初始化工作
 *
 */
public class InitApp extends Application {
    private static final String TAG = "InitApp";
    private static InitApp instance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        this.instance = instance;
    }

    /**
     * 全局请求单例（单例模式）
     */
    public static InitApp getInstance() {
        return instance;
    }

}
