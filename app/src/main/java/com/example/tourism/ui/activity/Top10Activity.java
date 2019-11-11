package com.example.tourism.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.tourism.R;
import com.example.tourism.adapter.Top10ItemAdapter;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.Exhibits;
import com.example.tourism.utils.AppUtils;
import com.example.tourism.utils.StatusBarUtil;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.reflect.TypeToken;

import android.util.Log;
import android.widget.ImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Top10Activity extends AppCompatActivity {

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolBar)
    Toolbar toolbar;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private Bitmap bitmap;
    private Top10ItemAdapter adapter;
    private List<Exhibits> exhibits = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_10);
        ButterKnife.bind(this);
        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        initToolBar();
        initTabLayout();
        queryAllExhibits();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initToolBar() {
        StatusBarUtil.setTransparentForWindow(this);
        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.top_10);
        imageView.setImageBitmap(bitmap);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                int more = palette.getVibrantColor(Color.WHITE);
                coordinatorLayout.setBackgroundColor(more);
                collapsingToolbarLayout.setContentScrimColor(more);
            }
        });
        //扩张时候的title颜色
        collapsingToolbarLayout.setExpandedTitleColor(getColor(R.color.color_black));
        //收缩后在Toolbar上显示时的title的颜色
        collapsingToolbarLayout.setCollapsedTitleTextColor(getColor(R.color.color_white));
        toolbar.setTitle(getString(R.string.top_10));
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    private void initTabLayout(){
        // 添加 tab item
        tabLayout.addTab(tabLayout.newTab().setText(AppUtils.getStringArray(R.array.tab_item)[0]));
        tabLayout.addTab(tabLayout.newTab().setText(AppUtils.getStringArray(R.array.tab_item)[1]));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                initRecyclerView(exhibits,tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void queryAllExhibits(){
        ServerApi api = RetrofitManger.getInstance().getRetrofit(RequestURL.ip_port).create(ServerApi.class);
        Call<ResponseBody> exhibitsCall = api.getNAsync("queryAllExhibits");
        exhibitsCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String data = response.body().string();
                    Log.d("222",data);
                    JSONObject jsonObject = new JSONObject(data);
                    exhibits = RetrofitManger.getInstance().getGson().fromJson(jsonObject.getString("ONE_DETAIL"),
                            new TypeToken<List<Exhibits>>(){}.getType());
                    if (exhibits == null) return;
                    Log.d("222", exhibits.size()+"");
                    initRecyclerView(exhibits,0);
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

    private void initRecyclerView(List<Exhibits> exhibits,int orderType){
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new Top10ItemAdapter(this,exhibits,orderType);
        if (orderType == 0) {
            adapter.orderByCommentCount(exhibits);
        }else if (orderType == 1) {
            adapter.orderByLikeCount(exhibits);
        }
        recyclerView.setAdapter(adapter);
    }
}
