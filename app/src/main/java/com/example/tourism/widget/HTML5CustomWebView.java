package com.example.tourism.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.Browser;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.tourism.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义WebView
 * Name：laodai
 * Time:2019.11.15
 */
public class HTML5CustomWebView extends WebView {
    //上下文
    private Context mContext;
    private Activity mActivity;
    //内部类WebChromeClient
    private MyWebChromeClient mWebChromeClient;
    //自定义View
    private View mCustomView;
    //帧布局1
    private FrameLayout mCustomViewContainer;
    private WebChromeClient.CustomViewCallback mCustomViewCallback;
    //帧布局2
    private FrameLayout mContentView;
    //帧布局3
    private FrameLayout mBrowserFrameLayout;
    //帧布局4
    private FrameLayout frame_progress;
    //帧布局
    private FrameLayout mLayout;
    //显示正在加载布局
    private TextView webview_tv_progress;
    //判断是否旋转 默认为false
    private boolean isRefresh = false;
    //标题
    private String mTitle;
    //Url地址
    private String mUrl;
    private TextView wv_tv_title;
    private CustomToolbar customToolbar;

    /**
     * 构造方法
     * @param context 上下文
     * @param activity
     * @param pTitle 标题
     * @param pUrl URL
     */
    public HTML5CustomWebView(Context context, Activity activity, String pTitle, String pUrl) {
        super(context);
        mActivity = activity;
        this.mTitle=pTitle;
        this.mUrl=pUrl;
        init(context);
    }

    /**
     * 构造方法 只含有上下文和activity
     * @param context
     * @param activity
     */
    public HTML5CustomWebView(Context context, Activity activity) {
        super(context);
        mActivity = activity;
        init(context);
    }


    public HTML5CustomWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HTML5CustomWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化init
        init(context);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init(Context context) {

        mContext = context;
        mLayout = new FrameLayout(context);
        //初始化布局
        mBrowserFrameLayout = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.common_custom_screen, null);
        //绑定并实例化控件
        mCustomViewContainer = mBrowserFrameLayout.findViewById(R.id.fullscreen_custom_content);
        customToolbar = mBrowserFrameLayout.findViewById(R.id.custom_toolbar);
        mContentView = mBrowserFrameLayout.findViewById(R.id.main_content);
        frame_progress = mBrowserFrameLayout.findViewById(R.id.frame_progress);
        webview_tv_progress = mBrowserFrameLayout.findViewById(R.id.webview_tv_progress);
		FrameLayout.LayoutParams COVER_SEREEN_PARAMS = new FrameLayout.LayoutParams(
		        ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);
		mLayout.addView(mBrowserFrameLayout, COVER_SEREEN_PARAMS);
		//创建WebChromeClient对象
		mWebChromeClient = new MyWebChromeClient();
		setWebChromeClient(mWebChromeClient);
		setWebViewClient(new MyWebViewClient());
		//创建WebSettings对象(获取getSettings)
        WebSettings webSettings = this.getSettings();
        //开启javascript
        webSettings.setJavaScriptEnabled(true);
        //开启DOM
        webSettings.setDomStorageEnabled(true);
        //设置编码utf-8
        webSettings.setDefaultTextEncodingName("utf-8");
        //web页面处理
        //支持文件流
        webSettings.setAllowFileAccess(true);
        //支持缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        //调整适合WebView的大小
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        //屏幕自适应网页，如果没有这个，在低分辨率的手机上显示可能会异常(已被弃用)
        webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        //提高网页加载速度，暂时阻塞图片加载，然后网页加载号了，在进行加载图片
        webSettings.setBlockNetworkImage(true);
        //开启缓存机制
        webSettings.setAppCacheEnabled(true);
        mContentView.addView(this);
    }

    //获取布局
    public FrameLayout getmLayout() {
        return mLayout;
    }

    //设置布局
    public void setmLayout(FrameLayout mLayout) {
        this.mLayout = mLayout;
    }

    public boolean inCustomView() {
        return (mCustomView != null);
    }

    public void doDestroy() {
        //清除网页访问留下的缓存，由于内核缓存是全局的因此这个方法不仅仅针对webview而是针对整个应用程序.
        clearCache(true);
        //清除当前webview访问的历史记录，只会webview访问历史记录里的所有记录除了当前访问记录.
        clearHistory();
        //这个api仅仅清除自动完成填充的表单数据，并不会清除WebView存储到本地的数据。
        clearFormData();
        clearView();
        freeMemory();
        destroy();
    }

    /**
     * 释放WebView
     */
    public void releaseCustomview() {
        if (mWebChromeClient != null) {
            mWebChromeClient.onHideCustomView();
        }
        stopLoading();
    }

    /**
     * 关闭该web页面
     */
    public void closeAdWebPage() {
        if(HTML5CustomWebView.this.canGoBack()){
            HTML5CustomWebView.this.goBack();
            return;
        }
        this.stopLoading();
        freeMemory();
        mActivity.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (HTML5CustomWebView.this.canGoBack()) {
                HTML5CustomWebView.this.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 内部类:
     * WebChromeClient是辅助WebView处理Javascript的对话框，网站图标，网站title，加载进度等
     */
    private class MyWebChromeClient extends WebChromeClient {
        private Bitmap mDefaultVideoPoster;

        @Override
        public void onShowCustomView(View view,
                                     CustomViewCallback callback) {
            super.onShowCustomView(view, callback);
            HTML5CustomWebView.this.setVisibility(View.GONE);
            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            mCustomViewContainer.addView(view);
            mCustomView = view;
            mCustomViewCallback = callback;
            mCustomViewContainer.setVisibility(View.VISIBLE);
        }

        @Override
        public void onHideCustomView() {
            if (mCustomView == null) {
                return;
            }
            mCustomView.setVisibility(View.GONE);
            mCustomViewContainer.removeView(mCustomView);
            mCustomView = null;
            mCustomViewContainer.setVisibility(View.GONE);
            mCustomViewCallback.onCustomViewHidden();
            HTML5CustomWebView.this.setVisibility(View.VISIBLE);
            super.onHideCustomView();
        }

        /**
         * 网页加载标题回调
         * @param view
         * @param title
         */
        @Override
        public void onReceivedTitle(WebView view, String title) {
            Log.d("zttjiangqq", "当前网页标题为:" + title);
            wv_tv_title.setText(title);
        }

        /**
         * 网页加载进度回调
         * @param view
         * @param newProgress
         */
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            // 设置进行进度
            ((Activity) mContext).getWindow().setFeatureInt(
                    Window.FEATURE_PROGRESS, newProgress * 100);
            webview_tv_progress.setText("正在加载,已完成" + newProgress + "%...");
            webview_tv_progress.postInvalidate(); // 刷新UI
            Log.d("zttjiangqq", "进度为:" + newProgress);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                                 JsResult result) {
            return super.onJsAlert(view, url, message, result);

        }
    }

    /**
     * 内部类:
     * WebViewClient就是帮助WebView处理各种通知、请求事件的。
     * 打开网页时不调用系统浏览器， 而是在本WebView中显示：
     */
    private class MyWebViewClient extends WebViewClient {
        /**
         * 加载过程中 拦截加载的地址url
         * @param view
         * @param url  被拦截的url
         * @return
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.i("zttjiangqq", "-------->shouldOverrideUrlLoading url:" + url);
            //这边因为考虑到之前项目的问题，这边拦截的url过滤掉了zttmall://开头的地址
            //在其他项目中 大家可以根据实际情况选择不拦截任何地址，或者有选择性拦截
            if(!url.startsWith("zttmall://")){
                Uri mUri = Uri.parse(url);
                List<String> browerList = new ArrayList<String>();
                browerList.add("http");
                browerList.add("https");
                browerList.add("about");
                browerList.add("javascript");
                if (browerList.contains(mUri.getScheme())) {
                    return false;
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    //如果另外的应用程序WebView，我们可以进行重用
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Browser.EXTRA_APPLICATION_ID,
                            mActivity.getPackageName());
                    try {
                        mActivity.startActivity(intent);
                        return true;
                    } catch (ActivityNotFoundException ex) {
                    }
                }
                return false;
            }else {
                return true;
            }
        }
        /**
         * 页面加载过程中，加载资源回调的方法
         * @param view
         * @param url
         */
        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
            Log.i("zttjiangqq", "-------->onLoadResource url:" + url);
        }
        /**
         * 页面加载完成回调的方法
         * @param view
         * @param url
         */
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.i("zttjiangqq", "-------->onPageFinished url:" + url);
            if (isRefresh) {
                isRefresh = false;
            }
            // 加载完成隐藏进度界面,显示WebView内容
            frame_progress.setVisibility(View.GONE);
            mContentView.setVisibility(View.VISIBLE);
            // 关闭图片加载阻塞
            view.getSettings().setBlockNetworkImage(false);
            view.loadUrl("javascript:sendMessage(123)");

        }
        /**
         * 页面开始加载调用的方法
         * @param view
         * @param url
         * @param favicon
         */
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.d("zttjiangqq", "onPageStarted:-----------"+url);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onScaleChanged(WebView view, float oldScale, float newScale) {
            super.onScaleChanged(view, oldScale, newScale);
            HTML5CustomWebView.this.requestFocus();
            HTML5CustomWebView.this.requestFocusFromTouch();
        }

    }

}
