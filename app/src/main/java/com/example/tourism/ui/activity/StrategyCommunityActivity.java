package com.example.tourism.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.tourism.R;
import com.example.tourism.adapter.SignInFragmentViewpageAdapter;
import com.example.tourism.application.InitApp;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.biz.AuthorDataManger;
import com.example.tourism.common.DefineView;
import com.example.tourism.entity.AuthorBean;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.ui.fragment.SignIn2Fragment;
import com.example.tourism.ui.fragment.strategycommunit.CommunityCommentsFragment;
import com.example.tourism.ui.fragment.strategycommunit.CommunityListFragment;
import com.example.tourism.ui.fragment.strategycommunit.CommunityMapFragment;
import com.example.tourism.ui.fragment.strategycommunit.TravelsFragment;
import com.example.tourism.utils.AppUtils;
import com.example.tourism.utils.StatusBarUtil;
import com.example.tourism.widget.CircleImageView;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.tourism.common.RequestURL.html;
import static com.example.tourism.common.RequestURL.user_html;

/**
 * 用户详情类
 */
public class StrategyCommunityActivity extends BaseActivity implements DefineView {
    @BindView(R.id.custom_toolbar)
    Toolbar customToolbar;
    @BindView(R.id.s_c_tablayout)
    TabLayout sCTablayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.linearLayout6)
    LinearLayout linearLayout6;
    @BindView(R.id.head_portrait_image)
    CircleImageView headPortraitImage;
    @BindView(R.id.account_name_text)
    TextView accountNameText;
    @BindView(R.id.user_tag_text)
    TextView userTagText;
    @BindView(R.id.tv_autograph)
    TextView tvAutograph;
    @BindView(R.id.sc_fans)
    TextView scFans;
    @BindView(R.id.sc_follow)
    TextView scFollow;
    @BindView(R.id.goods_cardView)
    CardView goodsCardView;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    //网络请求api
    private ServerApi api;
    //用户实体
    private List<AuthorBean> authorBeanList;
    //获取用户编号
    private String strategyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strategy_community);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, AppUtils.getColor(R.color.colorPrimary));
        initView();
        initValidata();
        initListener();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initValidata() {
        //获取编号
        strategyId = this.getIntent().getStringExtra("strategyId");

        SignInFragmentViewpageAdapter signInFragmentViewpageAdapter = new SignInFragmentViewpageAdapter(getSupportFragmentManager());
        ArrayList<Fragment> datas = new ArrayList<Fragment>();
        datas.add(new TravelsFragment());
        datas.add(new CommunityListFragment());
        datas.add(new CommunityCommentsFragment());
        datas.add(new CommunityMapFragment());
        signInFragmentViewpageAdapter.setDatas(datas);
        viewPager.setAdapter(signInFragmentViewpageAdapter);
        ArrayList<String> titles = new ArrayList<String>();
        titles.add("游记");
        titles.add("榜单");
        titles.add("点评");
        titles.add("旅图");
        signInFragmentViewpageAdapter.setTitles(titles);
        sCTablayout.setupWithViewPager(viewPager);

        api = RetrofitManger.getInstance().getRetrofit(html).create(ServerApi.class);
        Call<ResponseBody> call = api.getNAsync(user_html + strategyId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String mag = response.body().string();
                    Document document = Jsoup.parse(mag, html);
                    authorBeanList = new AuthorDataManger().getAurhorBeans(document);
                    if (authorBeanList == null) return;
                    bindData();
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
        customToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void bindData() {
        ImageLoader.getInstance().displayImage(authorBeanList.get(0).getAvatarPic(),headPortraitImage, InitApp.getOptions());
        accountNameText.setText(authorBeanList.get(0).getName());
        scFans.setText(authorBeanList.get(0).getFans());
        scFollow.setText(authorBeanList.get(0).getFollow());
        tvAutograph.setText(authorBeanList.get(0).getSignature());
    }
}
