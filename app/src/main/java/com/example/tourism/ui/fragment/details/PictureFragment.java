package com.example.tourism.ui.fragment.details;


import android.annotation.SuppressLint;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.example.tourism.R;
import com.example.tourism.ui.fragment.base.BaseFragment;
import com.example.tourism.utils.HTML5CustomWebView;
import com.example.tourism.widget.ChildAutoViewPager;
import com.example.tourism.widget.ViewBundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 费用信息
 */
public class PictureFragment extends BaseFragment {
    private static final String KEY = "viewBundle";
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    //控件绑定
    private Unbinder unbinder;
    private ChildAutoViewPager viewPager;

    public static PictureFragment newInstance(ViewBundle viewBundle) {
        Bundle args = new Bundle();
        args.putParcelable(KEY, viewBundle);
        PictureFragment fragment = new PictureFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //如果getArguments 为空，则不执行
        if (getArguments() == null) return;
        ViewBundle viewBundle = getArguments().getParcelable(KEY);
        viewPager = viewBundle != null ? viewBundle.getViewPager() : null;

    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picture, container, false);
        if (viewPager != null) viewPager.setObjectForPosition(0, view);
        unbinder = ButterKnife.bind(this, view);

//        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
//        webViewPicture.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                // TODO Auto-generated method stub
//                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
//                view.loadUrl(url);
//                return true;
//            }
//        });
//
//        webViewPicture.setWebViewClient(new WebViewClient(){
//            @Override
//            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                handler.proceed();//接受所有网站的证书
//            }
//        });
//
//        //加载网页：https://zjjl4.package.qunar.com/user/detail.jsp?id=3524985628&tm=pc_jrth#ss-aboutCost
//        WebSettings webSettings = webViewPicture.getSettings();
//        webSettings.setJavaScriptEnabled(true); //开启javascript
//        webSettings.setBlockNetworkImage(false); //解决图片不显示
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
//            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
//        }
////        webViewPicture.loadUrl("https://zjjl4.package.qunar.com/user/detail.jsp?id=3524985628&tm=pc_jrth#ss-aboutCost");
//        //WebView加载web资源
//        webViewPicture.loadUrl("https://zjjl4.package.qunar.com/user/detail.jsp?id=3524985628&tm=pc_jrth#ss-aboutCost");

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解绑
        unbinder.unbind();
    }
}
