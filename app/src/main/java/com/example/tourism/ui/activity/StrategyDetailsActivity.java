package com.example.tourism.ui.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.adapter.RecyclerViewAdapter;
import com.example.tourism.application.InitApp;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.biz.StrategyDetailsManger;
import com.example.tourism.common.DefineView;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.StrategyDetailsBean;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.utils.AppUtils;
import com.example.tourism.utils.StatusBarUtil;
import com.example.tourism.widget.CircleImageView;
import com.example.tourism.widget.CustomToolbar;
import com.example.tourism.widget.MyScrollView;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import scut.carson_ho.searchview.EditText_Clear;
import scut.carson_ho.searchview.SearchListView;

/**
 * 文章详情类
 * Name:laodai
 * Time:2019.10.24
 */
public class StrategyDetailsActivity extends BaseActivity implements DefineView {
    @BindView(R.id.iv_details)
    ImageView ivDetails;
    @BindView(R.id.tv_details_content)
    TextView tvDetailsContent;
    @BindView(R.id.iv_user)
    CircleImageView ivUser;
    @BindView(R.id.tv_accountName)
    TextView tvAccountName;
    @BindView(R.id.tv_nickName)
    TextView tvNickName;
    @BindView(R.id.tv_follow)
    TextView tvFollow;
    @BindView(R.id.rv_foreword)
    RecyclerView rvForeword;
    @BindView(R.id.details_content)
    WebView detailsContent;
    @BindView(R.id.details_scrollview)
    MyScrollView detailsScrollview;
    @BindView(R.id.status_view)
    View statusView;
    @BindView(R.id.custom_toolbar)
    CustomToolbar customToolbar;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.iv_collection)
    ImageView ivCollection;
    @BindView(R.id.tv_collection)
    TextView tvColletion;
    @BindView(R.id.iv_comment)
    ImageView ivComment;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.iv_foubles)
    ImageView ivFoubles;
    @BindView(R.id.tv_foubles)
    TextView tvFoubles;
    @BindView(R.id.ll_shape)
    LinearLayout llShape;
    @BindView(R.id.ll_collection)
    LinearLayout llCollection;
    @BindView(R.id.ll_comment)
    LinearLayout llComment;
    @BindView(R.id.ll_foubles)
    LinearLayout llFoubles;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.empty_line)
    LinearLayout emptyLine;
    @BindView(R.id.error_line)
    LinearLayout errorLine;
    @BindView(R.id.iv_left_back)
    ImageView ivLeftBack;
    @BindView(R.id.loading_line)
    ConstraintLayout loadingLine;
    private int statusHeight;
    private StrategyDetailsBean strategy;
    private RecyclerViewAdapter adapter;
    private LinearLayoutManager layoutManager;
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strategy_details);
        ButterKnife.bind(this);
        initView();
        initValidata();
        initListener();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initValidata() {
        //设置中间内容隐藏
        detailsScrollview.setVisibility(View.INVISIBLE);
        llBottom.setVisibility(View.INVISIBLE);
        loadingLine.setVisibility(View.VISIBLE);
        emptyLine.setVisibility(View.INVISIBLE);
        errorLine.setVisibility(View.INVISIBLE);
        ivLeftBack.setOnClickListener(v -> finish());
        //设置状态栏透明
        StatusBarUtil.setTransparentForWindow(this);
        //获取状态栏高度
        statusHeight = AppUtils.getStatusBarHeight(this);
        //设置状态栏高度
        AppUtils.setStatusBarColor(statusView, statusHeight, R.color.color_blue);
        //设置透明度为0
        statusView.getBackground().mutate().setAlpha(0);
        customToolbar.getBackground().mutate().setAlpha(0);
        customToolbar.setMyTitle("");

        int bHeight = 500;
        //设置scrollView滚动监听
        detailsScrollview.setOnScrollListener((l, t, oldl, oldt) -> {
            //设置status，toobar颜色透明渐变
            float detalis = t > bHeight ? bHeight : (t > 30 ? t : 0);
            int alpha = (int) (detalis / bHeight * 255);
            AppUtils.setUpdateActionBar(statusView, customToolbar, alpha);
            if (alpha > 100) {
                customToolbar.setMyTitle("攻略详情");
            } else {
                customToolbar.setMyTitle("");
            }
        });

        //设置线性布局管理器
        layoutManager = new LinearLayoutManager(this);
        //设置布局垂直显示
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        //设置分割线(无)
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL);
        rvForeword.addItemDecoration(dividerItemDecoration);
        //设置适配器布局管理器
        rvForeword.setLayoutManager(layoutManager);
        //创建适配器
        adapter = new RecyclerViewAdapter(this, 0);
        String travelsId = this.getIntent().getStringExtra("travelsId");

        //网络请求，抓取数据
        ServerApi api = RetrofitManger.getInstance().getRetrofit(RequestURL.html).create(ServerApi.class);
        Call<ResponseBody> call = api.getNAsync("youji/" + travelsId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String message = response.body().string();
                    Document document = Jsoup.parse(message, RequestURL.html);
                    strategy = new StrategyDetailsManger().getStrategyDetailsBeans(document);
                    if (strategy != null) {
                        bindData();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public void initListener() {
        customToolbar.setOnLeftButtonClickLister(() -> {
            this.finish();
        });
        customToolbar.setOnRightButtonClickLister(() -> {
            AppUtils.getToast("点击显示更多！");
        });

        ivUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StrategyDetailsActivity.this, StrategyCommunityActivity.class);
                intent.putExtra("strategyId", strategy.getStrategyId());
                startActivity(intent);
            }
        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void bindData() {
        //显示图片
        ImageLoader.getInstance().displayImage(strategy.getBackgroundUrl(), ivDetails, InitApp.getOptions());
        //显示文章标题
        tvDetailsContent.setText(strategy.getTravelsBean().getTitle());
        //显示用户头像
        ImageLoader.getInstance().displayImage(strategy.getTravelsBean().getUserPicUrl(), ivUser, InitApp.getOptions());
        tvAccountName.setText(strategy.getTravelsBean().getUserName());
        tvNickName.setText(strategy.getTravelsBean().getNickName());
        //显示底部数据
        tvShare.setText(strategy.getShare());
        tvColletion.setText(strategy.getCollection());
        tvColletion.setText(strategy.getTravelsBean().getComment());
        tvFoubles.setText(strategy.getTravelsBean().getFoubles());
        //设置数据
        adapter.setStringList(strategy.getForewordList());
        rvForeword.setAdapter(adapter);

//        //WebView设置
//        detailsContent.setWebChromeClient(new MyWebChromeClient());
//        detailsContent.setWebViewClient(new MyWebViewClient());
//        //这个是给图片设置点击监听的，如果你项目需要webview中图片，点击查看大图功能，可以这么添加
//        detailsContent.addJavascriptInterface(new JavaScriptInterface(this), "imagelistner");

        WebSettings webSettings = detailsContent.getSettings();
        webSettings.setJavaScriptEnabled(true); //开启javascript
        webSettings.setBlockNetworkImage(false); //解决图片不显示
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

//        WebSettings webSettings = detailsContent.getSettings();
//        webSettings.setJavaScriptEnabled(true);  //开启javascript
//        webSettings.setDomStorageEnabled(true);  //开启DOM
//        webSettings.setDefaultTextEncodingName("utf-8"); //设置编码
//        // // web页面处理
//        webSettings.setAllowFileAccess(true);// 支持文件流
////		webSettings.setSupportZoom(true);// 支持缩放
////		webSettings.setBuiltInZoomControls(true);// 支持缩放
//        webSettings.setUseWideViewPort(true);// 调整到适合webview大小
//        webSettings.setLoadWithOverviewMode(true);// 调适合整到webview大小
//        webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);// 屏幕自适应网页,如果没有这个，在低分辨率的手机上显示可能会异常
//        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
//        //提高网页加载速度，暂时阻塞图片加载，然后网页加载好了，在进行加载图片
//        webSettings.setBlockNetworkImage(true);
//        //开启缓存机制
//        webSettings.setAppCacheEnabled(true);

//        webSettings.setBuiltInZoomControls(true); // 显示放大缩小
//        webSettings.setSupportZoom(true); // 可以缩放

        //加载数据
        detailsContent.loadDataWithBaseURL(null, getNewContent(strategy.getContent()), "text/html", "UTF-8", null);

        if (strategy != null) {
            detailsContent.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    //设置中间内容隐藏
                    detailsScrollview.setVisibility(View.VISIBLE);
                    llBottom.setVisibility(View.VISIBLE);
                    loadingLine.setVisibility(View.INVISIBLE);
                    emptyLine.setVisibility(View.INVISIBLE);
                    errorLine.setVisibility(View.INVISIBLE);
                }
            });
        } else {
            //设置中间内容隐藏
            detailsScrollview.setVisibility(View.INVISIBLE);
            llBottom.setVisibility(View.INVISIBLE);
            loadingLine.setVisibility(View.INVISIBLE);
            emptyLine.setVisibility(View.VISIBLE);
            errorLine.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 将html文本内容中包含img标签的图片，宽度变为屏幕宽度，高度根据宽度比例自适应
     **/
    public static String getNewContent(String htmltext){
        try {
            Document doc= Jsoup.parse(htmltext);
            Elements elements=doc.getElementsByTag("img");
            for (Element element : elements) {
                element.attr("width","100%").attr("height","auto");
            }

            return doc.toString();
        } catch (Exception e) {
            return htmltext;
        }
    }

    @OnClick({R.id.ll_shape, R.id.ll_collection, R.id.ll_comment, R.id.ll_foubles})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_shape:
                AppUtils.getToast("点击了分享！");
                Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.share_layout);
                //绑定控件
                TextView tvWeixin =  dialog.findViewById(R.id.tv_weixin);
                TextView tvQq = dialog.findViewById(R.id.tv_qq);
                TextView tvWeibo = dialog.findViewById(R.id.tv_weibo);
                //显示
                dialog.show();
                break;
            case R.id.ll_collection:
                if (flag) {
                    flag = false;
                    ivCollection.setBackgroundResource(R.drawable.ic_collection_black_24dp);
                    AppUtils.getToast("取消收藏");
                } else {
                    flag = true;
                    AppUtils.getToast("收藏成功");
                    ivCollection.setBackgroundResource(R.drawable.ic_collection_red_24dp);
                }
                break;
            case R.id.ll_comment:
                AppUtils.getToast("点击了评论！");
                break;
            case R.id.ll_foubles:
                if (flag) {
                    flag = false;
                    ivFoubles.setBackgroundResource(R.drawable.icon_fabulous_black);
                } else {
                    flag = true;
                    ivFoubles.setBackgroundResource(R.drawable.icon_fabulous_bluish_green);
                }
                break;
        }
    }

    class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            Log.d("zttjiangqq", "加载进度发生变化:" + newProgress);
        }
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            imgReset();//重置webview中img标签的图片大小
            // html加载完成之后，添加监听图片的点击js函数
            addImageClickListner();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    /**
     * 对图片进行重置大小，宽度就是手机屏幕宽度，高度根据宽度比便自动缩放
     **/
    private void imgReset() {
        detailsContent.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var img = objs[i];   " +
                "    img.style.maxWidth = '100%'; img.style.height = 'auto';  " +
                "}" +
                "})()");
    }

    private void addImageClickListner() {
        // 这段js函数的功能就是，遍历所有的img节点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        detailsContent.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].οnclick=function()  " +
                "    {  "
                + "        window.imagelistner.openImage(this.src);  " +
                "    }  " +
                "}" +
                "})()");
    }

    public static class JavaScriptInterface {

        private Context context;

        public JavaScriptInterface(Context context) {
            this.context = context;
        }

        //点击图片回调方法
        //必须添加注解,否则无法响应
        @JavascriptInterface
        public void openImage(String img) {
            Log.i("TAG", "响应点击事件!");
            Intent intent = new Intent();
            intent.putExtra("image", img);
            intent.setClass(context, BigImageActivity.class);//BigImageActivity查看大图的类，自己定义就好
            context.startActivity(intent);
        }
    }

}
