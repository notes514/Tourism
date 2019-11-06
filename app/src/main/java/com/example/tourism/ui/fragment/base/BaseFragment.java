package com.example.tourism.ui.fragment.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tourism.R;
import com.example.tourism.utils.AppUtils;
import com.example.tourism.utils.StatusBarUtil;

/**
 * 基类fragment
 *
 * Name：laodai
 */
public class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        StatusBarUtil.setColor(getActivity(), AppUtils.getColor(R.color.color_blue));
        return view;
    }

    /**
     * 对当前Activity跳转进行封装，重用
     *
     * @param tClass
     */
    protected void openActivity(Class<?> tClass) {
        Intent intent = new Intent(getActivity(), tClass);
        this.startActivity(intent);
    }

}
