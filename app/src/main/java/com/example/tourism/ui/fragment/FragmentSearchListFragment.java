package com.example.tourism.ui.fragment;


import android.util.Log;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.adapter.BrowseCountryAdapter;
import com.example.tourism.adapter.SearchListAdapter;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.entity.ScenicRegion;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FragmentSearchListFragment extends Fragment {

    private RecyclerView searchList;
    private List<ScenicRegion> scenicRegions;
    Bundle info;
    String init;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_list,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchList = (RecyclerView) view.findViewById(R.id.search_list);
        info=getArguments();
        init=info.getString("newText");

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
                    List<ScenicRegion> temp = new ArrayList<>();
                    for (int i=0;i<scenicRegions.size();i++) {
                        ScenicRegion scenicRegion = scenicRegions.get(i);
                        Log.e(TAG, "avconResponse: "+info.toString(), null);
                        if (scenicRegion.getRegionName().contains(init)){
                            temp.add(scenicRegion);
                        }
                    }
                    SearchListAdapter searchListAdapter = new SearchListAdapter(temp);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(FragmentSearchListFragment.this.getContext());
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    searchList.setLayoutManager(layoutManager);
//给RecyclerView设置适配器
                    searchList.setAdapter(searchListAdapter);
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
