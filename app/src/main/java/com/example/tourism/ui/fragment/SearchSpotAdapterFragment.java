package com.example.tourism.ui.fragment;

import android.util.Log;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.adapter.BrowseCountryAdapter;
import com.example.tourism.adapter.BrowseRegionAdapter;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.entity.Region;
import com.example.tourism.entity.RegionType;
import com.example.tourism.entity.ScenicRegion;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nostra13.universalimageloader.core.ImageLoader.TAG;

public class SearchSpotAdapterFragment extends Fragment {

    private RecyclerView regionRecycleView;
    private RecyclerView countryRecycleView;
    List<Region> regions = new ArrayList<Region>();
    List<RegionType> mlist = new ArrayList<>();
    BrowseCountryAdapter browseCountryAdapter;
    BrowseRegionAdapter browseRegionAdapter;
    List<ScenicRegion> scenicRegions = new ArrayList<>();
    String temp=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.search_spot_adapter, container, false);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        regionRecycleView = (RecyclerView) view.findViewById(R.id.browse_region);
        countryRecycleView = (RecyclerView) view.findViewById(R.id.browse_country);
        getData();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        regionRecycleView.setLayoutManager(linearLayoutManager);
        browseRegionAdapter = new BrowseRegionAdapter(regions);
        browseRegionAdapter.setmOnItemClickListener(new BrowseRegionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Region region = regions.get(position);
                List<RegionType> regionTypes = new ArrayList<>();


                for (int i=0;i<mlist.size();i++){
                    if (region.getRegionId()==mlist.get(i).getRegionTypeId()){
                        regionTypes.add(mlist.get(i));
                    }
                }
                String url = "http://192.168.42.39:8080/api/";
                RetrofitManger retrofit = RetrofitManger.getInstance();
                ServerApi serverApi = retrofit.getRetrofit(url).create(ServerApi.class);
                Map<String,Object> map=new HashMap<>();
                Call<ResponseBody> scenicRegionCall = serverApi.getASync(url+"queryAllScenicRegion",map);
                scenicRegionCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String m = response.body().string();
                            scenicRegions = new Gson().fromJson(m,new TypeToken<List<ScenicRegion>>(){}.getType());
                            browseCountryAdapter = new BrowseCountryAdapter(regionTypes,scenicRegions);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(SearchSpotAdapterFragment.this.getContext());
                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            countryRecycleView.setLayoutManager(layoutManager);
//给RecyclerView设置适配器
                            countryRecycleView.setAdapter(browseCountryAdapter);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d(TAG, "onFailure: "+t.getMessage());
                    }
                });

//                browseCountryAdapter = new BrowseCountryAdapter(regionTypes,countries);
//                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
//                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//                countryRecycleView.setLayoutManager(layoutManager);
////给RecyclerView设置适配器
//                countryRecycleView.setAdapter(browseCountryAdapter);

            }
        });
        //完成adapter设置
        //browseRegionAdapter.notifyDataSetChanged();
        regionRecycleView.setAdapter(browseRegionAdapter);

        getType();

    }

    public void getData(){

        regions.add(new Region(0,"热门"));
        regions.add(new Region(1,"国内"));
        regions.add(new Region(2,"港澳台"));
        regions.add(new Region(3,"日韩"));
        regions.add(new Region(4,"东南亚"));
        regions.add(new Region(5,"欧洲"));
        regions.add(new Region(6,"美洲"));


        mlist.add(new RegionType(0,"国内热门"));
        mlist.add(new RegionType(0,"国际热门"));
        mlist.add(new RegionType(1,"华东地区"));
        mlist.add(new RegionType(1,"西南地区"));
        mlist.add(new RegionType(1,"西北地区"));
        mlist.add(new RegionType(1,"华北地区"));
        mlist.add(new RegionType(1,"东北地区"));
        mlist.add(new RegionType(2,"港澳台推荐目的地"));
        mlist.add(new RegionType(2,"港澳台玩法"));

//        countries.add(new Country(0,"丽江",R.drawable.defaultbg,"西南地区 国内热门"));
//        countries.add(new Country(1,"三亚",R.drawable.defaultbg,"华南地区 国内热门"));
//        countries.add(new Country(2,"成都",R.drawable.defaultbg,"西南地区 国内热门"));
//        countries.add(new Country(3,"日本",R.drawable.defaultbg,"日韩 国际热门"));
//        countries.add(new Country(4,"越南",0,"东南亚 国际热门"));
//        countries.add(new Country(5,"泰国",0,"东南亚 国际热门"));
//        countries.add(new Country(6,"厦门",R.drawable.defaultbg,"华东地区"));
//        countries.add(new Country(7,"上海",R.drawable.defaultbg,"华东地区"));
//
//        countries.add(new Country(8,"青海湖",0,"西北地区"));
//        countries.add(new Country(9,"西宁",0,"西北地区"));
//        countries.add(new Country(10,"北海道",0,"日韩"));
//        countries.add(new Country(11,"澳门",0,"港澳台推荐目的地"));

        //getRetr("");

    }

    public void getType(){
        String url = "http://192.168.42.39:8080/api/";
        RetrofitManger retrofit = RetrofitManger.getInstance();
        ServerApi serverApi = retrofit.getRetrofit(url).create(ServerApi.class);
        Map<String,Object> map=new HashMap<>();
        Call<ResponseBody> scenicRegionCall = serverApi.getASync(url+"queryAllScenicRegion",map);
        List<RegionType> temp = new ArrayList<>();
        temp.addAll(mlist);
        for (int i=temp.size()-1;i>0;i--) {
            RegionType regionType = temp.get(i);
            if (regionType.getRegionTypeId() != 0) {
                temp.remove(regionType);
            }
        }
        scenicRegionCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String m = response.body().string();
                    scenicRegions = new Gson().fromJson(m,new TypeToken<List<ScenicRegion>>(){}.getType());


                    browseCountryAdapter = new BrowseCountryAdapter(temp,scenicRegions);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(SearchSpotAdapterFragment.this.getContext());
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    countryRecycleView.setLayoutManager(layoutManager);
//给RecyclerView设置适配器
                    countryRecycleView.setAdapter(browseCountryAdapter);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });


    }

}
