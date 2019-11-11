package com.example.tourism.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.tourism.R;
import com.example.tourism.adapter.SecondaryScenicSpotItemAdapter;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.ScenicSpot;
import com.example.tourism.utils.AppUtils;
import com.example.tourism.utils.StatusBarUtil;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.gson.reflect.TypeToken;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.palette.graphics.Palette;
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

public class SecondaryActivity extends AppCompatActivity {

    @BindView(R.id.toolBar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    private Unbinder unbinder;
    private int type = 0;
    private List<ScenicSpot> scenicSpots = new ArrayList<>();
    private Bitmap bitmap;
    private SecondaryScenicSpotItemAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);
        unbinder = ButterKnife.bind(this, this);
        type = getIntent().getExtras().getInt("travel_mode");
        queryByTravelMode(type);
        initToolBar();
        updateUI(type);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void initToolBar() {
        StatusBarUtil.setTransparentForWindow(this);
        //扩张时候的title颜色
        collapsingToolbarLayout.setExpandedTitleColor(getColor(R.color.color_black));
        //收缩后在Toolbar上显示时的title的颜色
        collapsingToolbarLayout.setCollapsedTitleTextColor(getColor(R.color.color_white));
        toolbar.setTitle(getIntent().getStringExtra("menu_name"));
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    private void updateUI(int type){
        if (type == 1){
            bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.langmanzhilv);
        }else if (type == 2){
            bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.langmanzhilv);
        }else if (type == 3){
            bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.langmanzhilv);
        }else if (type == 4){
            bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.langmanzhilv);
        }else if (type == 5){
            bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.langmanzhilv);
        }else if (type == 6){
            bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.langmanzhilv);
        }else if (type == 7){
            bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.langmanzhilv);
        }else if (type == 8){
            bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.qinziyou);
        }
        if (bitmap != null){
            imageView.setImageBitmap(bitmap);
            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    int more = palette.getVibrantColor(Color.WHITE);
                    coordinatorLayout.setBackgroundColor(more);
                    collapsingToolbarLayout.setContentScrimColor(more);
                }
            });
        }

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
                    if (scenicSpots == null) return;
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
