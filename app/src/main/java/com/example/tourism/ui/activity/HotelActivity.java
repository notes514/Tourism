package com.example.tourism.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.webkit.JavascriptInterface;

import androidx.annotation.Nullable;

import com.example.tourism.R;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.utils.AppUtils;
import com.example.tourism.utils.HTML5CustomWebView;
import com.example.tourism.utils.StatusBarUtil;

/**
 * 酒店二级页面
 * Name:laodai
 * Time:2019.11.16
 */
public class HotelActivity extends BaseActivity {
    private HTML5CustomWebView customWebView;
    private String url = "";
    private String title = "百度一下，你就知道";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_layout);
        //设置状态栏颜色
        StatusBarUtil.setColor(this, AppUtils.getColor(R.color.color_dividing_line));
        //获取URL
        url = this.getIntent().getStringExtra("url");

        customWebView = new HTML5CustomWebView(this, HotelActivity.this, title, url);
        customWebView.setDownloadListener((url, userAgent, contentDisposition, mimetype, contentLength) -> {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        //准备javascript注入
        customWebView.addJavascriptInterface(new Js2JavaInterface(),"Js2JavaInterface");
        if (savedInstanceState != null) {
            customWebView.restoreState(savedInstanceState);
        } else {
            if (url != null) {
                customWebView.loadUrl(url);
            }
        }
        setContentView(customWebView.getLayout());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (customWebView != null) {
            customWebView.saveState(outState);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (customWebView != null) {
            customWebView.onResume();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (customWebView != null) {
            customWebView.stopLoading();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (customWebView != null) {
            customWebView.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (customWebView != null) {
            customWebView.doDestroy();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (customWebView != null) {
            if(customWebView.canGoBack()){
                customWebView.goBack();
            }else{
                customWebView.releaseCustomview();
            }
        }
        super.onBackPressed();
    }

    /**
     * JS注入回调
     */
    public class Js2JavaInterface {
        private Context context;
        private String TAG = "Js2JavaInterface";

        @JavascriptInterface
        public void showProduct(String productId) {
            if (productId != null) {
                AppUtils.getToast("");
            } else {
                AppUtils.getToast("");
            }
        }
    }

}
