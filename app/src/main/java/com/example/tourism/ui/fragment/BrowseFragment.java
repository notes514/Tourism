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
import com.example.tourism.ui.fragment.base.BaseFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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
    private String[] title;
    private List<Fragment> fragmentList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_browse, container, false);
        unbinder = ButterKnife.bind(this, root);
        initView();
        initValidata();
        initValidata();
        bindData();
        return root;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initValidata() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void bindData() {
        title = new String[]{"精选", "关注", "特价游", "亲子游学", "徒步登山", "滑雪", "深度游", "自驾"};
        adapter = new FixedPagerAdapter(getChildFragmentManager());
        adapter.setTitle(title);
        fragmentList = new ArrayList<>();
        for (int i = 0; i < title.length; i++) {
            BaseFragment fragment = BrowsePageFragment.newInstance(title[i]);
            fragmentList.add(fragment);
        }
        adapter.setFragmentList(fragmentList);
        browseViewPager.setAdapter(adapter);
        //使用setupWithViewPager可以让TabLayout和ViewPager联动
        browseTablayout.setupWithViewPager(browseViewPager);
        //设置TabLayout的模式（MODE_SCROLLABLE：可进行滑动）
        browseTablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
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