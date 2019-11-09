package com.example.tourism.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.tourism.R;
import com.example.tourism.adapter.FixedPagerAdapter;
import com.example.tourism.application.ServerApi;
import com.example.tourism.common.DefineView;
import com.example.tourism.entity.SeachBean;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.ui.fragment.SeachDetailsPageFragment;
import com.example.tourism.ui.fragment.base.BaseFragment;
import com.example.tourism.utils.AppUtils;
import com.example.tourism.widget.CustomToolbar;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 全部订单页
 * Name:laodai
 * Time:2019.10.29
 */
public class SeachDetailsActivity extends BaseActivity implements DefineView {
    @BindView(R.id.ct_toolbar)
    CustomToolbar ctToolbar;
    @BindView(R.id.tl_seach_details)
    TabLayout tlSeachDetails;
    @BindView(R.id.vp_seach_details)
    ViewPager vpSeachDetails;
    private FixedPagerAdapter adapter;
    private List<Fragment> fragmentList;
    private List<SeachBean> seachBeanList;
    //网络请求api
    private ServerApi api;
    //布局索引
    private int index;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seach_details_layout);
        ButterKnife.bind(this);
        initValidata();
        initListener();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initValidata() {
        //获取用户输入的搜索内容
        String content = this.getIntent().getStringExtra("content");
        //显示标题栏
        ctToolbar.setMyTitle(content);

        seachBeanList = new ArrayList<>();
        seachBeanList.add(new SeachBean("推荐", 0, content));
        seachBeanList.add(new SeachBean("国内游", 1, "国内游"));
        seachBeanList.add(new SeachBean("出境游", 2, "出境游"));
        seachBeanList.add(new SeachBean("自由行", 3, "自由行"));
        seachBeanList.add(new SeachBean("跟团游", 4, "跟团游"));
        seachBeanList.add(new SeachBean("主题游", 5, "主题游"));
        seachBeanList.add(new SeachBean("周边游", 6, "周边游"));
        seachBeanList.add(new SeachBean("一日游", 7, "一日游"));
        seachBeanList.add(new SeachBean("定制游", 8, "定制游"));
        //绑定并显示数据
        bindData();
        //跳转到指定页
        if (content.equals("国内游")) {
            vpSeachDetails.setCurrentItem(1);
        } else if (content.equals("出境游")) {
            vpSeachDetails.setCurrentItem(2);
        } else if (content.equals("自由行")) {
            vpSeachDetails.setCurrentItem(3);
        } else if (content.equals("跟团游")) {
            vpSeachDetails.setCurrentItem(4);
        } else if (content.equals("主题游")) {
            vpSeachDetails.setCurrentItem(5);
        } else if (content.equals("周边游")) {
            vpSeachDetails.setCurrentItem(6);
        } else if (content.equals("一日游")) {
            vpSeachDetails.setCurrentItem(7);
        } else if (content.equals("定制游")) {
            vpSeachDetails.setCurrentItem(8);
        } else {
            vpSeachDetails.setCurrentItem(0);
        }
    }

    @Override
    public void initListener() {
        ctToolbar.setOnLeftButtonClickLister(() -> finish());
        ctToolbar.setOnRightButtonClickLister(() -> {
            AppUtils.getToast("点击了更多!");
        });
    }

    @Override
    public void bindData() {
        adapter = new FixedPagerAdapter(this.getSupportFragmentManager(), 2);
        adapter.setSeachBeanList(seachBeanList);
        fragmentList = new ArrayList<>();
        for (int i = 0; i < seachBeanList.size(); i++) {
            BaseFragment fragment = SeachDetailsPageFragment.newInstance(seachBeanList.get(i));
            fragmentList.add(fragment);
        }
        adapter.setFragmentList(fragmentList);
        vpSeachDetails.setAdapter(adapter);
        //使用setupWithViewPager可以让TabLayout和ViewPager联动
        tlSeachDetails.setupWithViewPager(vpSeachDetails);
        //设置TabLayout的模式（MODE_SCROLLABLE：可进行滑动）
        tlSeachDetails.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

}
