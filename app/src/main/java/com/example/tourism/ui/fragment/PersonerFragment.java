package com.example.tourism.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.tourism.R;
import com.example.tourism.common.DefineView;
import com.example.tourism.ui.fragment.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 个人中心
 */
public class PersonerFragment extends BaseFragment implements DefineView {

    @BindView(R.id.text_personer)
    TextView textPersoner;
    private Unbinder unbinder;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_personer, container, false);
        unbinder = ButterKnife.bind(this, root);
        initView();
        initValidata();
        initValidata();
        bindData();
        return root;
    }

    @Override
    public void initView() {
        textPersoner.setText("个人中心");
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind(); //解绑
    }

}