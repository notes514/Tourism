package com.example.tourism.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class SignInFragmentViewpageAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> datas;
    ArrayList<String> titles;
    public SignInFragmentViewpageAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setDatas(ArrayList<Fragment> datas){
        this.datas = datas;
    }

    public void  setTitles(ArrayList<String> titles){
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int arg0) {
        return datas == null ? null:datas.get(arg0);
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int arg0) {
        return titles == null ? null : titles.get(arg0);
    }
}
