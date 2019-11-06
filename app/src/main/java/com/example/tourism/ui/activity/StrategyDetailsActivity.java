package com.example.tourism.ui.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    @BindView(R.id.loading_line)
    LinearLayout loadingLine;
    @BindView(R.id.empty_line)
    LinearLayout emptyLine;
    @BindView(R.id.error_line)
    LinearLayout errorLine;
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
                        Log.d(InitApp.TAG, "StrategyDetailsBean: " + strategy.getForewordList().size());
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

        //WebView设置
        detailsContent.setWebChromeClient(new MyWebChromeClient());
        detailsContent.setWebViewClient(new MyWebViewClient());

        WebSettings webSettings = detailsContent.getSettings();
        webSettings.setJavaScriptEnabled(true); //开启javascript
        webSettings.setDomStorageEnabled(true);  //开启DOM
        webSettings.setDefaultTextEncodingName("utf-8"); //设置编码
        // web页面处理
        webSettings.setAllowFileAccess(true);// 支持文件流
        //提高网页加载速度，暂时阻塞图片加载，然后网页加载好了，在进行加载图片
        webSettings.setBlockNetworkImage(true);
        //开启缓存机制
        webSettings.setAppCacheEnabled(true);

        //加载数据
        detailsContent.loadDataWithBaseURL(null, getNewContent(strategy.getContent()), "text_seach_layout/html", "UTF-8", null);

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
     * 设置状态栏和标题栏颜色渐变
     *
     * @param alpha
     * @throws Exception
     */
    private void setActionBar(int alpha) throws Exception {
        if (statusView != null && customToolbar == null) {
            throw new Exception("状态栏和标题栏为空！");
        }
        statusView.getBackground().mutate().setAlpha(alpha);
        customToolbar.getBackground().mutate().setAlpha(alpha);
    }

    /**
     * 实时更新状态栏标题栏颜色渐变
     *
     * @param alpha
     */
    private void setUpdateActionBar(int alpha) {
        try { //捕获异常
            setActionBar(alpha);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getNewContent(String htmltext) {
        Document doc = Jsoup.parse(htmltext);
        Elements elements = doc.getElementsByTag("img");

        for (Element element : elements) {
            element.attr("width", "100%").attr("height", "auto");
        }
        Log.d("VACK", "getNewContent: " + doc.toString());
        return doc.toString();
    }

    @OnClick({R.id.ll_shape, R.id.ll_collection, R.id.ll_comment, R.id.ll_foubles})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_shape:
                AppUtils.getToast("点击了分享！");
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

    class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.d("zttjiangqq", "网页开始加载:" + url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.d("zttjiangqq", "网页加载完成..." + url);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
            Log.d("zttjiangqq", "加载的资源:" + url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("zttjiangqq", "拦截到URL信息为:" + url);
            return super.shouldOverrideUrlLoading(view, url);

        }

    }

}
