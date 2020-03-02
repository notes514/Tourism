package com.example.tourism.ui.fragment.strategycommunit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.adapter.RecyclerViewAdapter;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.biz.AuthorTravelsDataManger;
import com.example.tourism.common.DefineView;
import com.example.tourism.entity.AuthorTravelsBean;
import com.example.tourism.ui.fragment.base.BaseFragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.tourism.common.RequestURL.html;
import static com.example.tourism.common.RequestURL.user_html;

public class TravelsFragment extends BaseFragment implements DefineView {
    @BindView(R.id.ft_recyclerview)
    RecyclerView ftRecyclerview;
    private Unbinder unbinder;
    private ServerApi api;
    private RecyclerViewAdapter adapter;
    private List<AuthorTravelsBean> authorTravelsBeanList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_travels, container, false);
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        ftRecyclerview.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerViewAdapter(getContext(),11);
        api = RetrofitManger.getInstance().getRetrofit(html).create(ServerApi.class);
        Call<ResponseBody> call = api.getNAsync(user_html + "123426612@qunar");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String mag = response.body().string();
                    Document document = Jsoup.parse(mag, html);
                    authorTravelsBeanList = new AuthorTravelsDataManger().getAuthorTravelsBeans(document);
                    if (authorTravelsBeanList != null && authorTravelsBeanList.size() > 0) {
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
    public void initListener() {

    }

    @Override
    public void bindData() {
        adapter.setAuthorTravelsBeanList(authorTravelsBeanList);
        ftRecyclerview.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解绑
        unbinder.unbind();
    }
}
