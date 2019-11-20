package com.example.tourism.ui.fragment.strategycommunit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tourism.R;
import com.example.tourism.common.DefineView;
import com.example.tourism.ui.fragment.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CommunityCommentsFragment extends BaseFragment implements DefineView {


    private Unbinder unbinder;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_community_comments, container, false);
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
    }

    @Override
    public void initListener() {

    }

    @Override
    public void bindData() {

    }

}


