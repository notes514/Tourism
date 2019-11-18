package com.example.tourism.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.tourism.R;
import com.example.tourism.adapter.FixedPagerAdapter;
import com.example.tourism.common.DefineView;
import com.example.tourism.entity.TrHeadBean;
import com.example.tourism.ui.activity.PersonalDataActivity;
import com.example.tourism.ui.activity.SeachActivity;
import com.example.tourism.ui.fragment.base.BaseFragment;
import com.example.tourism.utils.AppUtils;
import com.example.tourism.widget.CustomToolbar;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 攻略库
 */
public class BrowseFragment extends BaseFragment implements DefineView {
    @BindView(R.id.custom_toolbar)
    CustomToolbar customToolbar;
    @BindView(R.id.browse_tablayout)
    TabLayout browseTablayout;
    @BindView(R.id.browse_view_pager)
    ViewPager browseViewPager;
    @BindView(R.id.status_view)
    View statusView;
    private Unbinder unbinder;
    private FixedPagerAdapter adapter;
    private List<Fragment> fragmentList;
    private List<TrHeadBean> headBeanList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_browse, container, false);
        unbinder = ButterKnife.bind(this, root);
        initView();
        initValidata();
        initListener();
        return root;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initValidata() {
        //获取状态栏高度
        int statusHeight = AppUtils.getStatusBarHeight(getActivity());
        //设置状态栏高度
        AppUtils.setStatusBarColor(statusView, statusHeight, R.color.color_blue);

        //不进行网络请求抓取数据，直接设置数据
        headBeanList = new ArrayList<>();
        headBeanList.add(new TrHeadBean("热门游记", "hot_heat"));
        headBeanList.add(new TrHeadBean("精华游记", "elite_ctime"));
        headBeanList.add(new TrHeadBean("行程计划", "start_heat"));
        bindData();
    }

    @Override
    public void initListener() {
        customToolbar.setOnLeftButtonClickLister(() -> openActivity(SeachActivity.class));
        customToolbar.setOnRightButtonClickLister(() -> openActivity(PersonalDataActivity.class));
    }

    @Override
    public void bindData() {
        adapter = new FixedPagerAdapter(getChildFragmentManager(), 0);
        adapter.setHeadBeanList(headBeanList);
        fragmentList = new ArrayList<>();
        for (int i = 0; i < headBeanList.size(); i++) {
            BaseFragment fragment = BrowsePageFragment.newInstance(headBeanList.get(i));
            fragmentList.add(fragment);
        }
        adapter.setFragmentList(fragmentList);
        browseViewPager.setAdapter(adapter);
        //使用setupWithViewPager可以让TabLayout和ViewPager联动
        browseTablayout.setupWithViewPager(browseViewPager);
        //设置TabLayout的模式（MODE_SCROLLABLE：可进行滑动）
//        browseTablayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind(); //解绑
    }

}