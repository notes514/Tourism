package com.example.tourism.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.adapter.SpotAdapter;
import com.example.tourism.adapter.TravelModeAdapter;
import com.example.tourism.entity.Country;
import com.example.tourism.entity.ScenicRegion;
import com.example.tourism.entity.ScenicSpot;
import com.example.tourism.entity.TravelMode;

import java.util.ArrayList;
import java.util.List;

public class ActivitySpotActivity extends Activity  {

    private TextView searchView;
    private RecyclerView travelMode;
    private RecyclerView travelSpot;
    private List<TravelMode> travelModes = new ArrayList<>();
    private List<ScenicSpot> scenicSpots = new ArrayList<>();
    private ImageView imageView;
    TravelModeAdapter travelModeAdapter;
    SpotAdapter spotAdapter;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot);

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
        ScenicRegion country = (ScenicRegion) getIntent().getSerializableExtra("country");
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

                List<ScenicSpot> temp = new ArrayList<>();
                if (position==0){
                    for (int i=0;i<scenicSpots.size();i++){
                        ScenicSpot scenicSpot = scenicSpots.get(i);
                        if (scenicSpot.getScenicSpotDescribe().equals("推荐")){
                            temp.add(scenicSpot);
                        }
                    }
                }else {
                    for (int i = 0; i < scenicSpots.size(); i++) {
                        ScenicSpot scenicSpot = scenicSpots.get(i);
                        if (scenicSpot.getRegionId() == position) {
                            temp.add(scenicSpot);
                        }
                    }
                }

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                travelSpot.setLayoutManager(linearLayoutManager);
                spotAdapter = new SpotAdapter(temp);
                travelSpot.setAdapter(spotAdapter);
            }
        });
        travelMode.setAdapter(travelModeAdapter);




        startRecommand();
    }

//    private Integer scenicSpotId;
//    private Integer regionId;
//    private String scenicSpotTheme;
//    private String scenicSpotPicUrl;
//    private Double scenicSpotPrice;
//    private Integer travelMode;
//    private String startLand;
//    private String endLand;
//    private Integer scenicSpotState;
//    private String scenicSpotDescribe;

    public void startRecommand(){
        for (int i=scenicSpots.size()-1;i>0;i--){
            ScenicSpot scenicSpot=scenicSpots.get(i);
            if (scenicSpot.getScenicSpotDescribe().equals("非推荐")){
                scenicSpots.remove(scenicSpot);
            }
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        travelSpot.setLayoutManager(linearLayoutManager);
        spotAdapter = new SpotAdapter(scenicSpots);
        travelSpot.setAdapter(spotAdapter);
    }

    public void getData(){
        travelModes.add(new TravelMode(0,"推荐"));
        travelModes.add(new TravelMode(1,"跟团游"));
        travelModes.add(new TravelMode(2,"自由行"));
        travelModes.add(new TravelMode(3,"当地玩乐"));
        travelModes.add(new TravelMode(4,"包车游"));
        travelModes.add(new TravelMode(5,"门票"));

        scenicSpots.add(new ScenicSpot(0,0,"0自费0差评 豪华五星+海景温泉酒店 祭火大典+雪山印象丽江表演+冰川公园","hhhhhh",1557.0,1,"南宁","玉龙雪山 游双古城",1,"推荐"));
        scenicSpots.add(new ScenicSpot(1,0,"国旅国庆庆典预售开始 下单就减，儿童更优惠！送娱乐项目 私人游艇+敞篷吉普","hhhhhh",1604.0,1,"南宁","游艇 玉龙雪山",1,"推荐"));
        scenicSpots.add(new ScenicSpot(2,0,"云南大理丽江5+N天自由行 特色客栈 增Jeep旅拍+接送机+土鸡火锅","hhhhhh",1800.0,2,"南宁","游艇 玉龙雪山",1,"非推荐"));
        scenicSpots.add(new ScenicSpot(3,1,"国庆特卖双飞三亚5日 下单立减 五星海景房蜈支洲+南山+天涯海角 排队","hhhhhh",1980.0,1,"南宁","海景房",1,"推荐"));
        scenicSpots.add(new ScenicSpot(4,1,"三亚5日0购物0自费海景房+赠：486/人表演+车技+蜈支洲南山天涯天堂","hhhhhh",2417.0,1,"南宁","海景房",1,"推荐"));
        scenicSpots.add(new ScenicSpot(5,1,"三亚亚龙湾5天4万连住天域/美高梅/瑞吉/喜来登/红树林等可选 含多项赠送","hhhhhh",1503.0,2,"南宁","豪华型 亚龙湾",1,"非推荐"));
        scenicSpots.add(new ScenicSpot(6,2,"0自费！美食！住五星！成都-峨眉山-乐山-都江堰-熊猫乐园-黄龙溪古镇5日游","hhhhhh",1859.0,1,"南宁","峨眉山 都江堰",1,"推荐"));
        scenicSpots.add(new ScenicSpot(7,2,"成都+重庆五日自由行；含机票+动车票+酒店+景区接送车，送两江夜游+长江索道","hhhhhh",2230.0,2,"南宁","城市夜游 多商圈",1,"非推荐"));
        scenicSpots.add(new ScenicSpot(8,2,"100%纯玩团！0自费0购物！九寨沟+黄龙2日游！一价全包+颜值导游+三环接送","hhhhhh",300.0,1,"南宁","九寨沟",1,"非推荐"));

    }



}
