package com.example.tourism.ui.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.Constant;
import com.example.tourism.entity.ScenicRegion;
import com.example.tourism.ui.activity.ActivitySpotActivity;
import com.example.tourism.utils.DbManger;
import com.example.tourism.utils.MySqliteHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.io.Serializable;
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
    private MySqliteHelper helper;
    private SQLiteDatabase db;
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
        helper = new MySqliteHelper(getActivity());
        db = helper.getWritableDatabase();

        RetrofitManger retrofit = RetrofitManger.getInstance();
        ServerApi serverApi = retrofit.getRetrofit(RequestURL.ip_port).create(ServerApi.class);
        Map<String,Object> map=new HashMap<>();
        Call<ResponseBody> scenicRegionCall = serverApi.getASync(RequestURL.ip_port+"queryAllScenicRegion",map);
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
                    SearchListAdapter searchListAdapter = new SearchListAdapter(1,temp);
                    searchListAdapter.setmOnItemClickListener(new SearchListAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
//                            RetrofitManger retrofit = RetrofitManger.getInstance();
//                            ServerApi serverApi = retrofit.getRetrofit(RequestURL.ip_port).create(ServerApi.class);
//                            Map<String,Object> map=new HashMap<>();
//                            Call<ResponseBody> scenicRegionCall = serverApi.getASync(RequestURL.ip_port+"queryAllScenicRegion",map);
//                            scenicRegionCall.enqueue(new Callback<ResponseBody>() {
//                                @Override
//                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                                    try {
//                                        String m = response.body().string();
//                                        scenicRegions = new Gson().fromJson(m, new TypeToken<List<ScenicRegion>>() {
//                                        }.getType());
//                                        for (int i = 0; i < scenicRegions.size(); i++) {
//                                            ScenicRegion scenicRegion = scenicRegions.get(i);
//                                            String selectSql = "select * from " + Constant.TABLE_NAME;
//                                            Cursor cursor = DbManger.selectSQL(db, selectSql, null);
//                                            List<ScenicRegion> sc = DbManger.cursorToList(cursor);
//                                            for (int j = 0; j < sc.size(); j++) {
//                                                if (sc.get(j).getRegionId() == scenicRegion.getRegionId()) {
//                                                    DbManger.execSQL(db, "delete from ScenicRegion_Data where scenicRegionId = " + sc.get(j).getRegionId() + ";");
//                                                    break;
//                                                }
//                                            }
//                                            String sql = "insert into " + Constant.TABLE_NAME + " values (" + scenicRegion.getRegionId() + ",'" + scenicRegion.getRegionName() + "')";
//                                            DbManger.execSQL(db, sql);//执行语句
//                                            Log.e(ImageLoader.TAG, "ssssonResponse: " + cursor.getCount(), null);
//                                            break;
//                                        }
//
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                                    Log.d(ImageLoader.TAG, "onFailure: "+t.getMessage());
//                                }
//                            });
                            String selectSql = "select * from " + Constant.TABLE_NAME;
                            Cursor cursor = DbManger.selectSQL(db, selectSql, null);
                            List<ScenicRegion> sc = DbManger.cursorToList(cursor);
                            for (int j = 0; j < sc.size(); j++) {
                                if (sc.get(j).getRegionId() == temp.get(position).getRegionId()) {
                                    DbManger.execSQL(db, "delete from ScenicRegion_Data where scenicRegionId = " + sc.get(j).getRegionId() + ";");
                                    break;
                                }
                            }
                            String sql = "insert into " + Constant.TABLE_NAME + " values (" + temp.get(position).getRegionId() + ",'" + temp.get(position).getRegionName() + "')";
                            DbManger.execSQL(db, sql);//执行语句
                            Log.e(ImageLoader.TAG, "ssssonResponse: " + cursor.getCount(), null);
                            Intent intent = new Intent(FragmentSearchListFragment.this.getContext(),ActivitySpotActivity.class);
                            intent.putExtra("country",temp.get(position));
                            FragmentSearchListFragment.this.getContext().startActivity(intent);
                        }
                    });
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
