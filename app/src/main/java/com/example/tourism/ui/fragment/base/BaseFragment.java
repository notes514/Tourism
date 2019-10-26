package com.example.tourism.ui.fragment.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * 基类fragment
 *
 * Name：laodai
 */
public class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 对当前Activity跳转进行封装，重用
     *
     * @param tClass
     */
    protected void openActivity(Class<?> tClass) {
        Intent intent = new Intent(getContext(), tClass);
        this.startActivity(intent);
    }

}
