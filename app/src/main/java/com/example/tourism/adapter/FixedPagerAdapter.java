package com.example.tourism.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FixedPagerAdapter extends FragmentStatePagerAdapter {
    //数组标签
    private String[] title = new String[10];
    //集合碎片
    private List<Fragment> fragmentList = new ArrayList<>();

    public FixedPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    /**
     * 重写 实例化项
     * @param container 容器
     * @param position  索引
     * @return
     */
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Fragment fragment;
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        fragment = (Fragment) super.instantiateItem(container, position);
        return fragment;
    }

    /**
     * 销毁
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }

    public void setFragmentList(List<Fragment> fragmentList) {
        this.fragmentList = fragmentList;
    }

    public void setTitle(String[] title) {
        this.title = title;
    }
}
