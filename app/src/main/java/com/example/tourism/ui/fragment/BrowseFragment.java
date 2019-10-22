package com.example.tourism.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.tourism.R;
import com.example.tourism.adapter.FixedPagerAdapter;
import com.example.tourism.application.InitApp;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.biz.HeadDataManger;
import com.example.tourism.common.DefineView;
import com.example.tourism.entity.TrHeadBean;
import com.example.tourism.ui.fragment.base.BaseFragment;
import com.google.android.material.tabs.TabLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 目的地
 */
public class BrowseFragment extends BaseFragment implements DefineView, ViewPager.OnPageChangeListener {

    @BindView(R.id.browse_tablayout)
    TabLayout browseTablayout;
    @BindView(R.id.browse_view_pager)
    ViewPager browseViewPager;
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
        return root;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initValidata() {
        //不进行网络请求抓取数据，直接设置数据
        headBeanList = new ArrayList<>();
        headBeanList.add(new TrHeadBean("热门游记", "travelbook/list.htm?order=hot_heat"));
        headBeanList.add(new TrHeadBean("精华游记", "travelbook/list.htm?page=1&order=elite_ctime"));
        headBeanList.add(new TrHeadBean("行程计划", "travelbook/list.htm?page=1&order=start_heat"));
        bindData();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void bindData() {
        adapter = new FixedPagerAdapter(getChildFragmentManager());
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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind(); //解绑
    }

}