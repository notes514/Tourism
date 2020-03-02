package com.example.tourism.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.ArrayRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import java.util.Locale;

/**
 * APP工具类
 *
 * Name: laodai
 * Time:2019.09.28
 */
public class AppUtils {
    private static Context mContext; //上下文
    private static Thread mThread;   //线程
    //在主线程中执行
    private static Handler mHandler = new Handler(Looper.getMainLooper());

    /**
     * 在Application中初始化
     * @param context 上下文
     */
    public static void init(Context context) {
        mContext = context;
        mThread = Thread.currentThread();
    }

    /**
     * 上下文请求单例
     * @return
     */
    public static Context getAppContext() {
        return mContext;
    }

    /**
     * 获取assets类资源
     */
    public static AssetManager getAssets() {
        return mContext.getAssets();
    }

    /**
     * getDimension()是基于当前DisplayMetrics进行转换，
     * 获取指定资源id对应的尺寸。文档里并没说这里返回的就是像素，
     * 要注意这个函数的返回值是float，像素肯定是int。
     *
     * @param id
     * @return
     */
    public static float getDimension(int id) {
        return getResource().getDimension(id);
    }

    /**
     * 获取系统存在资源
     *
     * @return 返回系统存在资源
     */
    public static Resources getResource() {
        return mContext.getResources();
    }

    /**
     * 获取资源ID
     *
     * @param resId 传入的资源ID
     * @return
     */
    public static Drawable getDrawable(int resId) {
        return ContextCompat.getDrawable(mContext, resId);
    }

    /**
     * 获取颜色值
     *
     * @param resId 传入的颜色值
     * @return 返回颜色值
     */
    public static int getColor(int resId) {
        return ContextCompat.getColor(mContext, resId);
    }

    /**
     * 整数参数转字符串
     *
     * @param resId 传入的整数
     * @return 返回一串字符
     */
    public static String getString(@StringRes int resId) {
        return mContext.getResources().getString(resId);
    }

    /**
     * 整数参数转字符串数组
     *
     * @param resId 传入的整数
     * @return 返回一串字符数组
     */
    public static String[] getStringArray(@ArrayRes int resId) {
        return mContext.getResources().getStringArray(resId);
    }

    /**
     * 获取当前线程运行的对象
     *
     * @return
     */
    public static boolean isUIThread() {
        return Thread.currentThread() == mThread;
    }

    /**
     * 更新UI
     *
     * @param runnable
     */
    public static void runOnUI(Runnable runnable) {
        mHandler.post(runnable);
    }

    /**
     * 定时器，隔delayMills秒去执行一次runnable
     *
     * @param runnable
     * @param delayMills
     */
    public static void runOnUIDelayed(Runnable runnable, long delayMills) {
        mHandler.postDelayed(runnable, delayMills);
    }

    /**
     * 弹出提示框
     *
     * @param value
     */
    public static void getToast(String value) {
        Toast.makeText(mContext, value, Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取当前手机系统语言
     *
     * @return 返回当前手机系统语言。例如：当前设置的是"中文-中国"，则返回"zh-CN"
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 返回系统版本号
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机号型号
     *
     * @return 返回手机号型号
     */
    public static String getSystemModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 返回手机厂商
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * 获取状态栏高度
     *
     * @param context 目标Context
     */
    public static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    /**
     * 设置状态栏高度，并设置其主题颜色
     * @param sView 状态栏
     * @param statusHeight 高度
     * @param color 颜色
     */
    public static void setStatusBarColor(View sView, int statusHeight, int color) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusHeight);
        sView.setLayoutParams(params);
        sView.setBackgroundResource(color);
    }

    /**
     * 设置状态栏和标题栏颜色渐变
     *
     * @param alpha 透明度值
     * @throws Exception
     */
    private static void setActionBar(View sView, View tView, int alpha) throws Exception {
        if (sView != null && tView == null) {
            throw new Exception("状态栏和标题栏为空！");
        }
        sView.getBackground().mutate().setAlpha(alpha);
        tView.getBackground().mutate().setAlpha(alpha);
    }

    /**
     * 实时更新状态栏标题栏颜色渐变
     *
     * @param alpha
     */
    public static void setUpdateActionBar(View sView, View tView, int alpha) {
        try { //捕获异常
            setActionBar(sView, tView, alpha);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
