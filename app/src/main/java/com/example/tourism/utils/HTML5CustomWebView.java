package com.example.tourism.utils;


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

public class HTML5CustomWebView extends WebView {
	private Context mContext;
	private Activity mActivity;
	private MyWebChromeClient mWebChromeClient;
	private View mCustomView;
	private FrameLayout mCustomViewContainer;
	private WebChromeClient.CustomViewCallback mCustomViewCallback;
	private FrameLayout mContentView;
	private FrameLayout mBrowserFrameLayout;
	private FrameLayout frame_progress;
	private FrameLayout mLayout;
	private TextView webview_tv_progress;
	private boolean isRefresh = false; // 是否旋转
	private String mTitle;
	private String mUrl;
	private TextView wv_tv_title;
	private void init(Context context) {
		mContext = context;
		mLayout = new FrameLayout(context);
		mBrowserFrameLayout = (FrameLayout) LayoutInflater.from(context)
				.inflate(R.layout.common_custom_screen, null);
		mContentView = (FrameLayout) mBrowserFrameLayout
				.findViewById(R.id.main_content);
		mCustomViewContainer = (FrameLayout) mBrowserFrameLayout
				.findViewById(R.id.fullscreen_custom_content);
		frame_progress = (FrameLayout) mBrowserFrameLayout
				.findViewById(R.id.frame_progress);
		webview_tv_progress = (TextView) frame_progress
				.findViewById(R.id.webview_tv_progress);
		final FrameLayout.LayoutParams COVER_SCREEN_PARAMS = new FrameLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);
		mLayout.addView(mBrowserFrameLayout, COVER_SCREEN_PARAMS);
		mWebChromeClient = new MyWebChromeClient();
		setWebChromeClient(mWebChromeClient);
		setWebViewClient(new MyWebViewClient());
		WebSettings webSettings = this.getSettings();
		webSettings.setJavaScriptEnabled(true);  //开启javascript
		webSettings.setDomStorageEnabled(true);  //开启DOM
		webSettings.setDefaultTextEncodingName("utf-8"); //设置编码
		// // web页面处理
		webSettings.setAllowFileAccess(true);// 支持文件流
//		webSettings.setSupportZoom(true);// 支持缩放
//		webSettings.setBuiltInZoomControls(true);// 支持缩放
		webSettings.setUseWideViewPort(true);// 调整到适合webview大小
		webSettings.setLoadWithOverviewMode(true);// 调适合整到webview大小
		webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);// 屏幕自适应网页,如果没有这个，在低分辨率的手机上显示可能会异常
		webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
		//提高网页加载速度，暂时阻塞图片加载，然后网页加载好了，在进行加载图片
		webSettings.setBlockNetworkImage(true);
		//开启缓存机制
		webSettings.setAppCacheEnabled(true);
		mContentView.addView(this);

	}
	public HTML5CustomWebView(Context context, Activity activity, String pTitle, String pUrl) {
		super(context);
		mActivity = activity;
		this.mTitle=pTitle;
		this.mUrl=pUrl;
		init(context);
	}

	public HTML5CustomWebView(Context context, Activity activity) {
		super(context);
		mActivity = activity;
		init(context);
	}

	public HTML5CustomWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public HTML5CustomWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	public FrameLayout getLayout() {
		return mLayout;
	}
	public boolean inCustomView() {
		return (mCustomView != null);
	}

	public void doDestroy() {
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