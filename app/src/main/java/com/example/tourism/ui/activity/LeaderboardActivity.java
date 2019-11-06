package com.example.tourism.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.tourism.R;
import com.example.tourism.adapter.ExhibitsItemAdapter;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.Exhibits;
import com.example.tourism.utils.AppUtils;
import com.example.tourism.utils.StatusBarUtil;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.gson.reflect.TypeToken;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaderboardActivity extends AppCompatActivity {
    @BindView(R.id.toolBar)
    Toolbar toolbar;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private int exhibitionAreaId = 0;
    private List<Exhibits> exhibits;
    private LinearLayoutManager linearLayoutManager;
    private ExhibitsItemAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        ButterKnife.bind(this);
        exhibitionAreaId = (int) getIntent().getExtras().get("exhibitionAreaId");
        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        initToolBar();
        queryByExhibitionArea();
        initSpinner();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void initToolBar() {
        StatusBarUtil.setTransparentForWindow(this);
        //扩张时候的title颜色
        collapsingToolbarLayout.setExpandedTitleColor(getColor(R.color.color_black));
        //收缩后在Toolbar上显示时的title的颜色
        collapsingToolbarLayout.setCollapsedTitleTextColor(getColor(R.color.color_white));
        toolbar.setTitle(AppUtils.getStringArray(R.array.exhibition_area)[exhibitionAreaId-1]);
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    private void queryByExhibitionArea(){
        ServerApi api = RetrofitManger.getInstance().getRetrofit(RequestURL.ip_port).create(ServerApi.class);
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("exhibitionAreaId",exhibitionAreaId+"");
        Call<ResponseBody> exhibitsCall = api.getASync("queryByExhibitionArea",hashMap);
        exhibitsCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String data = response.body().string();
                    Log.d("@@@",data);
                    JSONObject jsonObject = new JSONObject(data);
                    exhibits = RetrofitManger.getInstance().getGson().fromJson(
                            jsonObject.getString("ONE_DETAIL"),
                            new TypeToken<List<Exhibits>>(){}.getType());
                    Log.d("@@@", exhibits.size()+"");
                    initRecyclerView(exhibits);
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

    private void initRecyclerView(List<Exhibits> exhibits){
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ExhibitsItemAdapter(this,exhibits);
        adapter.orderByCommentCount(exhibits);
        recyclerView.setAdapter(adapter);
    }

    private void initSpinner(){
        spinner.setDropDownVerticalOffset(120);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (exhibits == null || adapter == null) return;
                if (i == 0) {
                    adapter.orderByCommentCount(exhibits);
                }else if (i == 1) {
                    adapter.orderByLikeCount(exhibits);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
