package com.example.tourism.ui.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.tourism.R;
import com.example.tourism.adapter.ExhibitsItemAdapter;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.ExhibitionArea;
import com.example.tourism.entity.Exhibits;
import com.example.tourism.utils.AppUtils;
import com.example.tourism.utils.StatusBarUtil;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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
    @BindView(R.id.imageView)
    ImageView imageView;
    //@BindView(R.id.spinner)
    //Spinner spinner;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;


    private int exhibitionAreaId = 0;
    private ExhibitionArea exhibitionArea;
    private List<Exhibits> exhibits;
    private LinearLayoutManager linearLayoutManager;
    private ExhibitsItemAdapter adapter;
    private Bitmap bitmap;
    private Handler handler = new Handler();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        ButterKnife.bind(this);
        exhibitionAreaId = (int) getIntent().getExtras().get("exhibitionAreaId");
        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        initToolBar();
        queryExhibitionArea();
        queryByExhibitionArea();
        //initSpinner();
        // 启动线程执行下载任务
        Thread thread = new Thread() {
            public void run() {
                doSomething(); //处理得到结果了，这里一些内容保存在主类的成员变量中
                handler.post(updateUIRun); //高速UI线程可以更新结果了
            }
        };
        thread.start();
    }

    /**
     * 下载线程
     */
    Runnable updateUIRun = () -> {
        updateUI();
    };


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

    private void queryExhibitionArea(){
        ServerApi api = RetrofitManger.getInstance().getRetrofit(RequestURL.ip_port).create(ServerApi.class);
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("exhibitionAreaId",exhibitionAreaId+"");
        Call<ResponseBody> exhibitsCall = api.getASync("queryExhibitionArea",hashMap);
        exhibitsCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String data = response.body().string();
                    Log.d("@@@",data);
                    JSONObject jsonObject = new JSONObject(data);
                    exhibitionArea = RetrofitManger.getInstance().getGson().fromJson(
                            jsonObject.getString("ONE_DETAIL"),new TypeToken<ExhibitionArea>(){}.getType());
                    if (exhibitionArea == null) return;
                    Log.d("@@@", exhibitionArea.getExhibitionAreaAddress());
                    //test();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
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
                    if (exhibits == null) return;
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
        adapter.orderByLikeCount(exhibits);
        recyclerView.setAdapter(adapter);
    }

//    private void initSpinner(){
//        spinner.setDropDownVerticalOffset(120);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (exhibits == null || adapter == null) return;
//                if (i == 0) {
//                    adapter.orderByCommentCount(exhibits);
//                }else if (i == 1) {
//                    adapter.orderByLikeCount(exhibits);
//                }
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//    }

    private void doSomething(){
        bitmap = ImageLoader.getInstance().loadImageSync(RequestURL.ip_images+"/images/banner/banner1.jpg");
    }

    private void updateUI(){
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(updateUIRun);
    }
}
