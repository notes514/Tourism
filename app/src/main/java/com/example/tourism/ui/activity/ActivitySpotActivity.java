package com.example.tourism.ui.activity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.adapter.SpotAdapter;
import com.example.tourism.adapter.TravelModeAdapter;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.common.DefineView;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.Constant;
import com.example.tourism.entity.Country;
import com.example.tourism.entity.ScenicRegion;
import com.example.tourism.entity.ScenicSpot;
import com.example.tourism.entity.TravelMode;
import com.example.tourism.entity.User;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.utils.DbManger;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.greenrobot.eventbus.EventBus.TAG;

public class ActivitySpotActivity extends BaseActivity implements DefineView{


    private Unbinder unbinder;
    private TextView searchView;
    private RecyclerView travelMode;
    private RecyclerView travelSpot;
    private List<TravelMode> travelModes = new ArrayList<>();
    private List<ScenicSpot> scenicSpots = new ArrayList<>();
    private ImageView imageView;
    ScenicRegion country;
    TravelModeAdapter travelModeAdapter;
    SpotAdapter spotAdapter;
    Context context;



    @Override
    public void initView() {
        //默认初始工具栏为透明


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot);

        initView();
        initValidata();
        bindData();
        unbinder = ButterKnife.bind(this, this);
        searchView = (TextView) findViewById(R.id.search_view);
        travelMode = (RecyclerView) findViewById(R.id.travel_mode);
        travelSpot = (RecyclerView) findViewById(R.id.travel_spot);
        imageView = findViewById(R.id.back);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivitySpotActivity.this.finish();
            }
        });
        country = (ScenicRegion) getIntent().getSerializableExtra("country");
        searchView.setText(country.getRegionName());
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivitySpotActivity.this.finish();
            }
        });

        getData();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        travelMode.setLayoutManager(linearLayoutManager);
        travelModeAdapter = new TravelModeAdapter(travelModes);
        travelModeAdapter.setmOnItemClickListener(new TravelModeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                RetrofitManger retrofit = RetrofitManger.getInstance();
                ServerApi serverApi = retrofit.getRetrofit(RequestURL.ip_port).create(ServerApi.class);
                Map<String,Object> map=new HashMap<>();
                map.put("regionId",country.getRegionId());
                Call<ResponseBody> scenicRegionCall = serverApi.getASync(RequestURL.ip_port+"queryScenicByRegionId",map);
                scenicRegionCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String m = response.body().string();
                            List<ScenicSpot> scenicSpots = new Gson().fromJson(m,new TypeToken<List<ScenicSpot>>(){}.getType());
                            List<ScenicSpot> temp = null;
                            if (position==0){
                                temp = new ArrayList<>();
                                for (int i=0;i<scenicSpots.size();i++){
                                    ScenicSpot scenicSpot = scenicSpots.get(i);
                                    if (scenicSpot.getScenicSpotDescribe().equals("推荐")){
                                        temp.add(scenicSpot);
                                    }
                                }
                            }else {
                                temp = new ArrayList<>();
                                for (int i = 0; i < scenicSpots.size(); i++) {
                                    ScenicSpot scenicSpot = scenicSpots.get(i);
                                    if (scenicSpot.getTravelMode() == position) {
                                        temp.add(scenicSpot);
                                    }
                                }
                            }

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            travelSpot.setLayoutManager(linearLayoutManager);
                            spotAdapter = new SpotAdapter(ActivitySpotActivity.this,temp);
                            travelSpot.setAdapter(spotAdapter);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d(ImageLoader.TAG, "onFailure: "+t.getMessage());
                    }
                });


            }
        });
        travelMode.setAdapter(travelModeAdapter);

        startRecommand();
    }


    public void startRecommand(){

        RetrofitManger retrofit = RetrofitManger.getInstance();
        ServerApi serverApi = retrofit.getRetrofit(RequestURL.ip_port).create(ServerApi.class);
        Map<String,Object> map=new HashMap<>();
        map.put("regionId",country.getRegionId());
        Call<ResponseBody> scenicRegionCall = serverApi.getASync(RequestURL.ip_port+"queryScenicByRegionId",map);
        scenicRegionCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String m = response.body().string();
                    List<ScenicSpot> scenicSpots = new Gson().fromJson(m,new TypeToken<List<ScenicSpot>>(){}.getType());
                    for (int i=scenicSpots.size()-1;i>=0;i--){
                        ScenicSpot scenicSpot=scenicSpots.get(i);
                        if (!scenicSpot.getScenicSpotDescribe().equals("推荐")){
                            scenicSpots.remove(scenicSpot);
                        }
                    }
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    travelSpot.setLayoutManager(linearLayoutManager);
                    spotAdapter = new SpotAdapter(ActivitySpotActivity.this,scenicSpots);
                    travelSpot.setAdapter(spotAdapter);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(ImageLoader.TAG, "onFailure: "+t.getMessage());
            }
        });
    }

    public void getData() {
        travelModes.add(new TravelMode(0, "推荐"));
        travelModes.add(new TravelMode(1, "跟团游"));
        travelModes.add(new TravelMode(2, "自由行"));
        travelModes.add(new TravelMode(3, "当地玩乐"));
        travelModes.add(new TravelMode(4, "包车游"));
        travelModes.add(new TravelMode(5, "门票"));

    }

}
