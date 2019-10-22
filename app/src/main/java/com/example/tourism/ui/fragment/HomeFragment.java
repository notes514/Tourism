package com.example.tourism.ui.fragment;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.brtbeacon.sdk.BRTBeacon;
import com.brtbeacon.sdk.BRTBeaconManager;
import com.brtbeacon.sdk.BRTThrowable;
import com.brtbeacon.sdk.callback.BRTBeaconManagerListener;
import com.example.tourism.R;
import com.example.tourism.adapter.ScenicSpotItemAdapter;
import com.example.tourism.adapter.SecondaryMenuItemAdapter;
import com.example.tourism.application.InitApp;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.ScenicSpot;
import com.example.tourism.entity.SecondaryMenu;
import com.example.tourism.common.DefineView;
import com.example.tourism.ui.activity.NearbyActivity;
import com.example.tourism.ui.activity.SecondaryActivity;
import com.example.tourism.ui.fragment.base.BaseFragment;
import com.example.tourism.utils.StatusBarUtil;
import com.example.tourism.widget.GlideImageLoader;
import com.google.gson.reflect.TypeToken;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.header.BezierRadarHeader;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;
import com.scwang.smart.refresh.layout.listener.OnMultiListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 首页
 */
public class HomeFragment extends BaseFragment implements DefineView {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.toolBar)
    Toolbar toolbar;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.gridView)
    GridView gridView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<String> images = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private List<SecondaryMenu> secondaryMenuList = new ArrayList<>();
    private List<ScenicSpot> allScenicSpots = new ArrayList<>();
    private List<ScenicSpot> secondaryScenicSpots = new ArrayList<>();
    private SecondaryMenuItemAdapter adapter1;
    private ScenicSpotItemAdapter adapter2;
    private Unbinder unbinder;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, root);
        initView();
        initValidata();
        bindData();
        return root;
    }

    @Override
    public void initView() {
        //默认初始工具栏为透明
        toolbar.setAlpha(0);
        initRefreshLayout();
        initScrollView();
        initBanner();
        initSecondaryMenu();
        showNearby();
    }

    @Override
    public void initValidata() {
        queryAllScenicSpot();
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

    private void initRefreshLayout(){
        //设置 Header 为 贝塞尔雷达 样式
//        refreshLayout.setRefreshHeader(new BezierRadarHeader(getContext()).setEnableHorizontalDrag(true)
//                .setPrimaryColorId(R.color.mask_tags_8));
        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        //设置 Footer 为 球脉冲 样式
        refreshLayout.setRefreshFooter(new BallPulseFooter(getContext()).setSpinnerStyle(SpinnerStyle.Translate)
                .setAnimatingColor(0xFF1DA8FE));
        refreshLayout.setOnMultiListener(new OnMultiListener() {
            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                //toolbar.setAlpha(1 - Math.min(percent, 1));
                //工具栏透明处理
                //toolbar.setAlpha(1 - Math.min(percent, 1));

                //状态栏透明处理
                //StatusBarUtil.setTranslucentStatus(HomeFragment.this.getActivity());
            }

            @Override
            public void onHeaderReleased(RefreshHeader header, int headerHeight, int maxDragHeight) {

            }

            @Override
            public void onHeaderStartAnimator(RefreshHeader header, int headerHeight, int maxDragHeight) {

            }

            @Override
            public void onHeaderFinish(RefreshHeader header, boolean success) {
                Log.d("@@@", "刷新完成！");
            }

            @Override
            public void onFooterMoving(RefreshFooter footer, boolean isDragging, float percent, int offset, int footerHeight, int maxDragHeight) {

            }

            @Override
            public void onFooterReleased(RefreshFooter footer, int footerHeight, int maxDragHeight) {

            }

            @Override
            public void onFooterStartAnimator(RefreshFooter footer, int footerHeight, int maxDragHeight) {

            }

            @Override
            public void onFooterFinish(RefreshFooter footer, boolean success) {
                //add();
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(1000);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(3000);
            }

            @Override
            public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {

            }
        });
    }

    private void initScrollView(){
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == 0){
                    //处于顶部时 工具栏透明
                    toolbar.setAlpha(0);
                }else if (scrollY > 0 && scrollY < toolbar.getHeight()){
                    //下拉 并且 正在显示工具栏
                    toolbar.setAlpha(((float) scrollY/(float) toolbar.getHeight()));
                }else if (scrollY >= toolbar.getHeight()){
                    toolbar.setAlpha(1);
                }
            }

        });
    }

    private void initBanner(){
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        images.add(RequestURL.ip_images+"/images/banner/banner1.jpg");
        images.add(RequestURL.ip_images+"/images/banner/banner2.jpg");
        images.add(RequestURL.ip_images+"/images/banner/banner3.jpg");
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        titles.add("1");
        titles.add("2");
        titles.add("3");
        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    private void initSecondaryMenu(){
        secondaryMenuList.add(new SecondaryMenu(R.drawable.menu_1,"周边游"));
        secondaryMenuList.add(new SecondaryMenu(R.drawable.menu_2,"一日游"));
        secondaryMenuList.add(new SecondaryMenu(R.drawable.menu_3,"自由行"));
        secondaryMenuList.add(new SecondaryMenu(R.drawable.menu_4,"景点·门票"));
        secondaryMenuList.add(new SecondaryMenu(R.drawable.menu_5,"浪漫之旅"));
        secondaryMenuList.add(new SecondaryMenu(R.drawable.menu_6,"当地向导"));
        secondaryMenuList.add(new SecondaryMenu(R.drawable.menu_7,"定制旅行"));
        secondaryMenuList.add(new SecondaryMenu(R.drawable.menu_8,"亲子·游学"));
        adapter1 = new SecondaryMenuItemAdapter(getContext(),secondaryMenuList);
        gridView.setAdapter(adapter1);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(),secondaryMenuList.get(i).menu_name,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), SecondaryActivity.class);
                intent.putExtra("travel_mode", (i+1));
                intent.putExtra("menu_name",secondaryMenuList.get(i).menu_name);
                startActivity(intent);
            }
        });
    }

    private void initRecyclerView(){
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        //recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        adapter2 = new ScenicSpotItemAdapter(getContext(),allScenicSpots);
        recyclerView.setAdapter(adapter2);
    }

    private void queryAllScenicSpot(){
        ServerApi api = RetrofitManger.getInstance().getRetrofit(RequestURL.ip_port).create(ServerApi.class);
        HashMap hashMap = new HashMap();
        hashMap.put("","");
        Call<ResponseBody> scenicSpotCall = api.getASync("queryAllScenicSpot",hashMap);
        scenicSpotCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    //String data = response.body().string();
                    //Log.d("@@@",data);
                    //JSONObject jsonObject = new JSONObject(data);
                    allScenicSpots = RetrofitManger.getInstance().getGson().fromJson(response.body().string(),
                            new TypeToken<List<ScenicSpot>>(){}.getType());
                    initRecyclerView();
                } catch (IOException e) {
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



    private void showNearby(){
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"查看附近景点",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), NearbyActivity.class);
                startActivity(intent);
            }
        });
    }

    private void add(){
        int l = allScenicSpots.size();
        for (int i = 1; i <= 10; i++) {
            //scenicSpots.add(new ScenicSpot(R.drawable.defaultbg,i+l+""));
        }
        adapter2.notifyDataSetChanged();
    }

}