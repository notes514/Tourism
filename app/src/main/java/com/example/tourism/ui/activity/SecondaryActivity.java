package com.example.tourism.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.tourism.R;
import com.example.tourism.adapter.ScenicSpotItemAdapter;
import com.example.tourism.adapter.SecondaryScenicSpotItemAdapter;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.ScenicSpot;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.widget.CustomToolbar;
import com.google.gson.reflect.TypeToken;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondaryActivity extends BaseActivity {

    @BindView(R.id.toolBar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private Unbinder unbinder;
    private List<ScenicSpot> scenicSpots = new ArrayList<>();
    private SecondaryScenicSpotItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);
        unbinder = ButterKnife.bind(this, this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //toolbar.setTitle(getIntent().getStringExtra("menu_name"));
        queryByTravelMode(getIntent().getIntExtra("travel_mode",0));

    }

    private void queryByTravelMode(int travelMode){
        ServerApi api = RetrofitManger.getInstance().getRetrofit(RequestURL.ip_port).create(ServerApi.class);
        HashMap hashMap = new HashMap();
        hashMap.put("travelMode",travelMode+"");
        Call<ResponseBody> scenicSpotCall = api.getASync("queryByTravelMode",hashMap);
        scenicSpotCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d("@@@","请求成功！");
                    String data = response.body().string();
                    JSONObject jsonObject = new JSONObject(data);
                    scenicSpots = RetrofitManger.getInstance().getGson().fromJson(
                            jsonObject.getString("ONE_DETAIL"), new TypeToken<List<ScenicSpot>>(){}.getType());
                    initRecyclerView();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("@@@","请求失败！");
                Log.d("@@@",t.getMessage());
            }
        });
    }

    private void initRecyclerView(){
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        //recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        adapter = new SecondaryScenicSpotItemAdapter(SecondaryActivity.this,scenicSpots);
        recyclerView.setAdapter(adapter);
    }
}
