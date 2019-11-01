package com.example.tourism.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.tourism.R;
import com.example.tourism.adapter.FixedPagerAdapter;
import com.example.tourism.common.DefineView;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.ui.fragment.AllOrderPageFragment;
import com.example.tourism.ui.fragment.base.BaseFragment;
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
public class AllOrderActivity extends BaseActivity implements DefineView {
    @BindView(R.id.ct_toolbar)
    CustomToolbar ctToolbar;
    @BindView(R.id.tl_all_order)
    TabLayout tlAllOrder;
    @BindView(R.id.vp_all_order)
    ViewPager vpAllOrder;
    private FixedPagerAdapter adapter;
    private List<Fragment> fragmentList;
    private List<String> sList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_order_layout);
        ButterKnife.bind(this);
        initValidata();
        initListener();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initValidata() {
        //设置数据
        sList = new ArrayList<>();
        sList.add("全部");
        sList.add("待付款");
        sList.add("待消费");
        sList.add("待评价");
        sList.add("退款中");
        bindData();
    }

    @Override
    public void initListener() {
        ctToolbar.setOnLeftButtonClickLister(() -> finish());
    }

    @Override
    public void bindData() {
        adapter = new FixedPagerAdapter(this.getSupportFragmentManager(), 1);
        adapter.setsList(sList);
        fragmentList = new ArrayList<>();
        for (int i = 0; i < sList.size(); i++) {
            BaseFragment fragment = AllOrderPageFragment.newInstance(sList.get(i));
            fragmentList.add(fragment);
        }
        adapter.setFragmentList(fragmentList);
        vpAllOrder.setAdapter(adapter);
        //使用setupWithViewPager可以让TabLayout和ViewPager联动
        tlAllOrder.setupWithViewPager(vpAllOrder);
    }

}
