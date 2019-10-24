package com.example.tourism.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.application.InitApp;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.biz.StrategyDetailsManger;
import com.example.tourism.common.DefineView;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.StrategyDetailsBean;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.utils.StatusBarUtil;
import com.example.tourism.widget.MyScrollView;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 欢迎广告类
 */
public class StrategyDetailsActivity extends BaseActivity implements DefineView {
    @BindView(R.id.iv_details)
    ImageView ivDetails;
    @BindView(R.id.tv_details_content)
    TextView tvDetailsContent;
    @BindView(R.id.iv_user)
    ImageView ivUser;
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
    @BindView(R.id.iv_details_left)
    ImageView ivDetailsLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_details_more)
    ImageView ivDetailsMore;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.iv_collection)
    ImageView ivCollection;
    @BindView(R.id.tv_cellotion)
    TextView tvCellotion;
    @BindView(R.id.iv_comment)
    ImageView ivComment;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.iv_foubles)
    ImageView ivFoubles;
    @BindView(R.id.tv_foubles)
    TextView tvFoubles;
    @BindView(R.id.status_view)
    View statusView;
    @BindView(R.id.details_toolbar)
    ConstraintLayout detailsToolbar;
    private int statusHeight;
    private StrategyDetailsBean strategy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strategy_details);
        ButterKnife.bind(this);
        initView();
        initValidata();
        initListener();
        bindData();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initValidata() {
        StatusBarUtil.setTransparentForWindow(this);
        //设置状态栏高度
        statusHeight = this.getResources().getDimensionPixelSize(this.getResources().getIdentifier("status_bar_height", "dimen", "android"));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusHeight);
        statusView.setLayoutParams(params);
        statusView.setBackgroundResource(R.color.color_blue);
        statusView.getBackground().mutate().setAlpha(0);
        detailsToolbar.getBackground().mutate().setAlpha(0);

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
                    Log.d(InitApp.TAG, "StrategyDetailsBean: " + strategy.getTravelsBean().toString());
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

    }

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

    }
}
