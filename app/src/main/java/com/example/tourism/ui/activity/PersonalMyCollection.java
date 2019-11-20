package com.example.tourism.ui.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.adapter.RecyclerViewAdapter;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.common.DefineView;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.ScenicSpot;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.widget.CustomToolbar;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalMyCollection extends BaseActivity implements DefineView {

    @BindView(R.id.custom_toolbar)
    CustomToolbar customToolbar;
    @BindView(R.id.recyclerView_collect)
    RecyclerView recyclerViewCollect;
    private ServerApi api;
    private List<ScenicSpot> scenicSpotList;
    private RecyclerViewAdapter rAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_my_collection);
        ButterKnife.bind(this);
        initView();
        initValidata();
        initListener();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initValidata() {
        //设置线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器显示方向
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        //设置分割线
        //设置布局显示
        recyclerViewCollect.setLayoutManager(layoutManager);
        //创建适配器
        rAdapter = new RecyclerViewAdapter(this, 12);

        api = RetrofitManger.getInstance().getRetrofit(RequestURL.ip_port).create(ServerApi.class);
        Map<String, Object> map = new HashMap<>();
        map.put("userId", RequestURL.vUserId);
        Call<ResponseBody> call = api.getASync("queryAllUserCollection", map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String message = response.body().string();
                    JSONObject json = new JSONObject(message);
                    if (json.getString(RequestURL.RESULT).equals("S")) {
                        scenicSpotList = RetrofitManger.getInstance().getGson().fromJson(json.getString(RequestURL.ONE_DATA),
                                new TypeToken<List<ScenicSpot>>() {
                                }.getType());
                        bindData();
                    }
                } catch (Exception e) {
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
        customToolbar.setOnLeftButtonClickLister(() -> finish());
    }

    @Override
    public void bindData() {
        if (scenicSpotList.size() > 0) {
            //设置数据
            rAdapter.setScenicSpotList(scenicSpotList);
            recyclerViewCollect.setAdapter(rAdapter);
        }
    }
}
