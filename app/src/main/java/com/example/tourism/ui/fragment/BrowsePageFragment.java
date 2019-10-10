package com.example.tourism.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.adapter.PageRecyclerAdapter;
import com.example.tourism.common.DefineView;
import com.example.tourism.ui.fragment.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 好货页面
 */
public class BrowsePageFragment extends BaseFragment implements DefineView {

    @BindView(R.id.page_recyclerView)
    RecyclerView pageRecyclerView;
    private Unbinder unbinder;
    private static final String KEY = "EXART";
    private PageRecyclerAdapter adapter;
    private List<Good> goodList;
    private List<GoodPic> goodPicList;

    public static BrowsePageFragment newInstance(String string) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY, string);
        BrowsePageFragment fragment = new BrowsePageFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_browse_page, container, false);
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
        goodList = new ArrayList<>();
        goodPicList = new ArrayList<>();
        goodPicList.add(new GoodPic(R.drawable.poster_one));
        goodPicList.add(new GoodPic(R.drawable.poster_two));
        goodPicList.add(new GoodPic(R.drawable.poster_three));
        goodList.add(new Good(R.drawable.poster_one, "laodai", "3天前", "RecyclerView嵌套RecyclerView的条目，项目中可能会经常有这样的需求，现在已无需先计算子RecyclerView的高度，简单实用。", goodPicList, 99));
        //设置线性布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        //设置布局垂直显示
        manager.setOrientation(RecyclerView.VERTICAL);
        //设置分割线(无)
        //设置适配器布局管理器
        pageRecyclerView.setLayoutManager(manager);
        //创建适配器
        adapter = new PageRecyclerAdapter(getContext());
        adapter.setGoodList(goodList);
        pageRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind(); //解绑
    }
}