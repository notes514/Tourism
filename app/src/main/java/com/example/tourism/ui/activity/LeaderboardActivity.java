package com.example.tourism.ui.activity;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

import com.example.tourism.R;
import com.example.tourism.common.DefineView;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.ui.fragment.LeaderboardDetailFragment;
import com.example.tourism.utils.AppUtils;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LeaderboardActivity extends BaseActivity implements DefineView {
    @BindView(R.id.toolBar)
    Toolbar toolbar;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private int page = 0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        ButterKnife.bind(this);
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initView() {
        toolbar.setTitle(getString(R.string.leaderboard));
        toolbar.setTitleTextColor(getColor(R.color.color_white));
        toolbar.setNavigationOnClickListener(view -> finish());
        initViewPager();
    }

    @Override
    public void initValidata() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void bindData() {

    }

    private void initViewPager(){
        page = (int) getIntent().getExtras().get("page");
        tabLayout.setupWithViewPager(viewPager,true);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        String[] tabName = AppUtils.getStringArray(R.array.exhibition_area);
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < tabName.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(tabName[i]));
            fragments.add(new LeaderboardDetailFragment(i+1));
        }
        viewPager.setOffscreenPageLimit(6);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(),1) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return tabName[position];
            }
        });
        viewPager.setCurrentItem(page,true);
    }
}
