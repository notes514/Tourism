package com.example.tourism.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.adapter.PageRecyclerAdapter;
import com.example.tourism.application.InitApp;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.biz.TravelsDataManger;
import com.example.tourism.common.DefineView;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.TrHeadBean;
import com.example.tourism.entity.TravelsBean;
import com.example.tourism.ui.fragment.base.BaseFragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 好货页面
 */
public class BrowsePageFragment extends BaseFragment implements DefineView {

    @BindView(R.id.page_recyclerView)
    RecyclerView pageRecyclerView;
    @BindView(R.id.loading_line)
    LinearLayout loadingLine;
    @BindView(R.id.empty_line)
    LinearLayout emptyLine;
    @BindView(R.id.error_line)
    LinearLayout errorLine;
    private Unbinder unbinder;
    private static final String KEY = "BrowsePage";
    private TrHeadBean trHeadBean;
    private LinearLayoutManager layoutManager;
    private PageRecyclerAdapter adapter;
    private List<TravelsBean> travelsList = new ArrayList<>();

    public static BrowsePageFragment newInstance(TrHeadBean trHeadBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY, trHeadBean);
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
        initListener();
        return root;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initValidata() {
        //资源文件隐藏显示
        pageRecyclerView.setVisibility(View.INVISIBLE);
        emptyLine.setVisibility(View.INVISIBLE);
        errorLine.setVisibility(View.INVISIBLE);
        loadingLine.setVisibility(View.VISIBLE);

        Bundle bundle = getArguments();
        if (bundle != null) {
            trHeadBean = (TrHeadBean) bundle.getSerializable(KEY);
        }
        //设置线性布局管理器
        layoutManager = new LinearLayoutManager(getContext());
        //设置布局垂直显示
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        //设置分割线(无)
        //设置适配器布局管理器
        pageRecyclerView.setLayoutManager(layoutManager);
        //创建适配器
        adapter = new PageRecyclerAdapter(getContext());
        if ("热门游记".equals(trHeadBean.getTitle())) {
            getTravels(trHeadBean.getHref());
        }
        if ("精华游记".equals(trHeadBean.getTitle())) {
            getTravels(trHeadBean.getHref());
        }
        if ("行程计划".equals(trHeadBean.getTitle())) {
            getTravels(trHeadBean.getHref());
        }
    }

    @Override
    public void initListener() {
    }

    @Override
    public void bindData() {
        if (travelsList != null && travelsList.size() > 0) {
            if (loadingLine != null && emptyLine != null && errorLine != null && pageRecyclerView != null) {
                loadingLine.setVisibility(View.INVISIBLE);
                emptyLine.setVisibility(View.INVISIBLE);
                errorLine.setVisibility(View.INVISIBLE);
                pageRecyclerView.setVisibility(View.VISIBLE);
                //设置数据
                adapter.setTravelsBeans(travelsList);
                pageRecyclerView.setAdapter(adapter);
            }
        } else {
            pageRecyclerView.setVisibility(View.INVISIBLE);
            loadingLine.setVisibility(View.INVISIBLE);
            errorLine.setVisibility(View.INVISIBLE);
            emptyLine.setVisibility(View.VISIBLE);
        }
    }

    private void getTravels(String url) {
        ServerApi api = RetrofitManger.getInstance().getFileRetrofit(RequestURL.html).create(ServerApi.class);
        Call<ResponseBody> call = api.getNAsync(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String message = response.body().string();
                    Document document = Jsoup.parse(message, RequestURL.html);
                    travelsList = new TravelsDataManger().getTravels(document);
                    Log.d(InitApp.TAG, "travelsList: "  + travelsList);
                    if (travelsList != null && travelsList.size() > 0) {
                        bindData();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind(); //解绑
    }
}