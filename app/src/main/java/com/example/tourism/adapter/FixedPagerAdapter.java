package com.example.tourism.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.tourism.entity.TrHeadBean;

import java.util.ArrayList;
import java.util.List;

public class FixedPagerAdapter extends FragmentStatePagerAdapter {
    //字符串数组
    private List<String> sList = new ArrayList<>();
    //数组标签
    private List<TrHeadBean> headBeanList = new ArrayList<>();
    //集合碎片
    private List<Fragment> fragmentList = new ArrayList<>();
    //类型
    private int type;

    public FixedPagerAdapter(@NonNull FragmentManager fm, int type) {
        super(fm);
        this.type = type;
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
        if (type == 0) {
            return headBeanList.get(position).getTitle();
        }
        if (type == 1) {
            return sList.get(position);
        }
        return null;
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

    public void setHeadBeanList(List<TrHeadBean> headBeanList) {
        this.headBeanList = headBeanList;
    }

    public void setsList(List<String> sList) {
        this.sList = sList;
    }
}
