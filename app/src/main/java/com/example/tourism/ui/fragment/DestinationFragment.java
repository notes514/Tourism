package com.example.tourism.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.adapter.BrowseCountryAdapter;
import com.example.tourism.adapter.BrowseRegionAdapter;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.common.DefineView;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.Constant;
import com.example.tourism.entity.Country;
import com.example.tourism.entity.Region;
import com.example.tourism.entity.RegionType;
import com.example.tourism.entity.ScenicRegion;
import com.example.tourism.ui.activity.ActivitySpotActivity;
import com.example.tourism.ui.fragment.base.BaseFragment;
import com.example.tourism.utils.AppUtils;
import com.example.tourism.utils.DbManger;
import com.example.tourism.utils.MySqliteHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nostra13.universalimageloader.core.ImageLoader.TAG;

/**
 * 浏览历史
 */
public class DestinationFragment extends BaseFragment implements DefineView {
    TextView textDestination;
    @BindView(R.id.status_view)
    View statusView;
    private Unbinder unbinder;
    private SearchView searchView;
    private RecyclerView regionRecycleView;
    private RecyclerView countryRecycleView;
    private Context context;
    private Button search;
    List<Region> regions = new ArrayList<Region>();
    List<RegionType> mlist = new ArrayList<>();
    List<Country> countries = new ArrayList<>();
    BrowseCountryAdapter browseCountryAdapter;
    List<Country> cc = new ArrayList<>();
    BrowseRegionAdapter browseRegionAdapter;
    List<ScenicRegion> scenicRegions = new ArrayList<>();
    String temp = null;
    private MySqliteHelper helper;
    private SQLiteDatabase db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "abconCreateView: ", null);
        View root = inflater.inflate(R.layout.fragment_destination, container, false);
        unbinder = ButterKnife.bind(this, root);
        initView();
        initValidata();
        bindData();
        getData();

        searchView = (SearchView) root.findViewById(R.id.search_view);
        searchView.findViewById(R.id.search_plate).setBackground(null);
        searchView.findViewById(R.id.submit_area).setBackground(null);
        TextView textView = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        textView.setTextSize(15);
        textView.setHintTextColor(Color.WHITE);
//        regionRecycleView = (RecyclerView) root.findViewById(R.id.browse_region);
//        countryRecycleView = root.findViewById(R.id.browse_country);
        search = root.findViewById(R.id.search);
        // 4. 设置点击键盘上的搜索按键后的操作（通过回调接口）
        // 参数 = 搜索框输入的内容
        helper = new MySqliteHelper(getActivity());
        db = helper.getWritableDatabase();


//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        regionRecycleView.setLayoutManager(linearLayoutManager);
//        browseRegionAdapter = new BrowseRegionAdapter(regions);
//        browseRegionAdapter.setmOnItemClickListener(new BrowseRegionAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                Region region = regions.get(position);
//                List<RegionType> regionTypes = new ArrayList<>();
//
//
//                for (int i=0;i<mlist.size();i++){
//                    if (region.getRegionId()==mlist.get(i).getRegionTypeId()){
//                        regionTypes.add(mlist.get(i));
//                    }
//                }
//                String url = "http://192.168.42.39:8080/api/";
//                RetrofitManger retrofit = RetrofitManger.getInstance();
//                ServerApi serverApi = retrofit.getRetrofit(url).create(ServerApi.class);
//                Map<String,Object> map=new HashMap<>();
//                Call<ResponseBody> scenicRegionCall = serverApi.getASync(url+"queryAllScenicRegion",map);
//                scenicRegionCall.enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        try {
//                            String m = response.body().string();
//                            scenicRegions = new Gson().fromJson(m,new TypeToken<List<ScenicRegion>>(){}.getType());
//                            browseCountryAdapter = new BrowseCountryAdapter(regionTypes,scenicRegions);
//                            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
//                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//                            countryRecycleView.setLayoutManager(layoutManager);
////给RecyclerView设置适配器
//                            countryRecycleView.setAdapter(browseCountryAdapter);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        Log.d(TAG, "onFailure: "+t.getMessage());
//                    }
//                });
//
////                browseCountryAdapter = new BrowseCountryAdapter(regionTypes,countries);
////                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
////                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
////                countryRecycleView.setLayoutManager(layoutManager);
//////给RecyclerView设置适配器
////                countryRecycleView.setAdapter(browseCountryAdapter);
//
//            }
//        });
//        //完成adapter设置
//        //browseRegionAdapter.notifyDataSetChanged();
//        regionRecycleView.setAdapter(browseRegionAdapter);
//
//        getType();

        replaceFragment(new SearchSpotAdapterFragment());


        return root;
    }


    public void getData() {

        regions.add(new Region(0, "热门"));
        regions.add(new Region(1, "国内"));
        regions.add(new Region(2, "港澳台"));
        regions.add(new Region(3, "日韩"));
        regions.add(new Region(4, "东南亚"));
        regions.add(new Region(5, "欧洲"));
        regions.add(new Region(6, "美洲"));


        mlist.add(new RegionType(0, "国内热门"));
        mlist.add(new RegionType(0, "国际热门"));
        mlist.add(new RegionType(1, "华东地区"));
        mlist.add(new RegionType(1, "西南地区"));
        mlist.add(new RegionType(1, "西北地区"));
        mlist.add(new RegionType(1, "华北地区"));
        mlist.add(new RegionType(1, "东北地区"));
        mlist.add(new RegionType(2, "港澳台推荐目的地"));
        mlist.add(new RegionType(2, "港澳台玩法"));

        countries.add(new Country(0, "丽江", R.drawable.defaultbg, "西南地区 国内热门"));
        countries.add(new Country(1, "三亚", R.drawable.defaultbg, "华南地区 国内热门"));
        countries.add(new Country(2, "成都", R.drawable.defaultbg, "西南地区 国内热门"));
        countries.add(new Country(3, "日本", R.drawable.defaultbg, "日韩 国际热门"));
        countries.add(new Country(4, "越南", 0, "东南亚 国际热门"));
        countries.add(new Country(5, "泰国", 0, "东南亚 国际热门"));
        countries.add(new Country(6, "厦门", R.drawable.defaultbg, "华东地区"));
        countries.add(new Country(7, "上海", R.drawable.defaultbg, "华东地区"));

        countries.add(new Country(8, "青海湖", 0, "西北地区"));
        countries.add(new Country(9, "西宁", 0, "西北地区"));
        countries.add(new Country(10, "北海道", 0, "日韩"));
        countries.add(new Country(11, "澳门", 0, "港澳台推荐目的地"));

        //getRetr("");

    }


    private void replaceFragment(Fragment fragment) {
        //1.实例化FragmentManager对象
        FragmentManager fragmentManager = getFragmentManager();
        //2.实例化FragmentTransaction对象
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //3.设置事务运用
        fragmentTransaction.replace(R.id.body_layout, fragment);
        //4.提交事务
        fragmentTransaction.commit();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initValidata() {
        //获取状态栏高度
        int statusHeight = AppUtils.getStatusBarHeight(getActivity());
        //设置状态栏高度
        AppUtils.setStatusBarColor(statusView, statusHeight, R.color.color_blue);
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
    public void onStart() {
        super.onStart();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                FragmentSearchListFragment fragmentSearchListFragment = new FragmentSearchListFragment();
                Bundle data = new Bundle();
                data.putString("newText", newText);
                if (newText.length() > 0) {
                    fragmentSearchListFragment.setArguments(data);
                    replaceFragment(fragmentSearchListFragment);
                } else {
                    replaceFragment(new SearchSpotAdapterFragment());
                }

                RetrofitManger retrofit = RetrofitManger.getInstance();
                ServerApi serverApi = retrofit.getRetrofit(RequestURL.ip_port).create(ServerApi.class);
                Map<String, Object> map = new HashMap<>();
                Call<ResponseBody> scenicRegionCall = serverApi.getASync(RequestURL.ip_port + "queryAllScenicRegion", map);
                scenicRegionCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String m = response.body().string();
                            scenicRegions = new Gson().fromJson(m, new TypeToken<List<ScenicRegion>>() {
                            }.getType());

                            for (int i = 0; i < scenicRegions.size(); i++) {
                                ScenicRegion scenicRegion = scenicRegions.get(i);
                                if (newText.equals(scenicRegion.getRegionName())) {
                                    temp = newText;

                                    break;
                                } else {
                                    temp = null;
                                }
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });
                return false;
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (temp != null) {
                    RetrofitManger retrofit = RetrofitManger.getInstance();
                    ServerApi serverApi = retrofit.getRetrofit(RequestURL.ip_port).create(ServerApi.class);
                    Map<String, Object> map = new HashMap<>();
                    Call<ResponseBody> scenicRegionCall = serverApi.getASync(RequestURL.ip_port + "queryAllScenicRegion", map);
                    scenicRegionCall.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                String m = response.body().string();
                                scenicRegions = new Gson().fromJson(m, new TypeToken<List<ScenicRegion>>() {
                                }.getType());


                                for (int i = 0; i < scenicRegions.size(); i++) {
                                    ScenicRegion scenicRegion = scenicRegions.get(i);
                                    if (temp.equals(scenicRegion.getRegionName())) {

                                        String selectSql = "select * from " + Constant.TABLE_NAME;
                                        Cursor cursor = DbManger.selectSQL(db, selectSql, null);
                                        List<ScenicRegion> sc = DbManger.cursorToList(cursor);
                                        for (int j = 0; j < sc.size(); j++) {
                                            if (sc.get(j).getRegionId() == scenicRegion.getRegionId()) {
                                                DbManger.execSQL(db, "delete from ScenicRegion_Data where scenicRegionId = " + sc.get(j).getRegionId() + ";");
                                            }
                                        }
                                        String sql = "insert into " + Constant.TABLE_NAME + " values (" + scenicRegion.getRegionId() + ",'" + scenicRegion.getRegionName() + "')";
                                        DbManger.execSQL(db, sql);//执行语句
                                        Log.e(TAG, "ssssonResponse: " + cursor.getCount(), null);
                                        Intent intent = new Intent(DestinationFragment.this.getContext(), ActivitySpotActivity.class);
                                        intent.putExtra("country", scenicRegion);
                                        DestinationFragment.this.getContext().startActivity(intent);
                                    }
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.d(TAG, "onFailure: " + t.getMessage());
                        }
                    });

                } else {
//                    Toast t;
////                    t = Toast.makeText(getActivity(),null,Toast.LENGTH_SHORT);
////                    t.setText("无法找到你想找的");
////                    t.show();
                    AppUtils.getToast("无法找到你想找的");
                }
            }

        });
        Log.e(TAG, "abconStart: ", null);
    }

}