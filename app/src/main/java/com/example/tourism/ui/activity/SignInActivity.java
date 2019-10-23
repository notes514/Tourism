package com.example.tourism.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.tourism.R;
import com.example.tourism.adapter.SignInFragmentViewpageAdapter;
import com.example.tourism.ui.fragment.PersonerFragment;
import com.example.tourism.ui.fragment.SignIn2Fragment;
import com.example.tourism.ui.fragment.SignInFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignInActivity extends FragmentActivity {
    @BindView(R.id.tv_return)
    TextView textView;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, PersonerFragment.class);
                finish();
            }
        });
        SignInFragmentViewpageAdapter signInFragmentViewpageAdapter = new SignInFragmentViewpageAdapter(getSupportFragmentManager());
        ArrayList<Fragment> datas = new ArrayList<Fragment>();
        datas.add(new SignInFragment());
        datas.add(new SignIn2Fragment());
        signInFragmentViewpageAdapter.setDatas(datas);
        viewPager.setAdapter(signInFragmentViewpageAdapter);
        ArrayList<String> titles = new ArrayList<String>();
        titles.add("手机动态密码登录");
        titles.add("用户密码登录");
        signInFragmentViewpageAdapter.setTitles(titles);
        tabLayout.setupWithViewPager(viewPager);
    }

}
