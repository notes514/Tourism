package com.example.tourism.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.tourism.R;
import com.example.tourism.adapter.RecyclerViewAdapter;
import com.example.tourism.adapter.SecondaryMenuItemAdapter;
import com.example.tourism.application.InitApp;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.common.DefineView;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.HotTopicsBean;
import com.example.tourism.entity.ScenicSpot;
import com.example.tourism.entity.SecondaryMenu;
import com.example.tourism.ui.activity.HotelActivity;
import com.example.tourism.ui.activity.LocationActivity;
import com.example.tourism.ui.activity.NearbyActivity;
import com.example.tourism.ui.activity.RomanticJourneyActivity;
import com.example.tourism.ui.activity.SeachActivity;
import com.example.tourism.ui.activity.SecondaryActivity;
import com.example.tourism.ui.activity.StrategyCommunityActivity;
import com.example.tourism.ui.fragment.base.BaseFragment;
import com.example.tourism.utils.AppUtils;
import com.example.tourism.utils.StatusBarUtil;
import com.example.tourism.widget.GlideImageLoader;
import com.google.gson.reflect.TypeToken;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;
import com.scwang.smart.refresh.layout.listener.OnMultiListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.json.JSONObject;

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

/**
 * 首页
 */
public class HomeFragment extends BaseFragment implements DefineView {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.ll_hotel_brown)
    LinearLayout llHotelBrown;
    @BindView(R.id.ll_plane_ticket)
    LinearLayout llPlaneTicket;
    @BindView(R.id.ll_train)
    LinearLayout llTrain;
    @BindView(R.id.ll_bus)
    LinearLayout llBus;
    @BindView(R.id.ll_piao)
    LinearLayout llPiao;
    @BindView(R.id.gridView)
    GridView gridView;
    @BindView(R.id.showNearby)
    TextView showNearby;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.tv_more)
    TextView tvMore;
    @BindView(R.id.rv_theme)
    RecyclerView rvTheme;
    @BindView(R.id.tv_show)
    TextView tvShow;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.status_view)
    View statusView;
    @BindView(R.id.tv_diqu)
    TextView tvDiqu;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_clear)
    ImageView ivClear;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    @BindView(R.id.ll_toolbar)
    LinearLayout llToolbar;
    @BindView(R.id.ll_state_toolbar)
    LinearLayout llStateToolbar;
    @BindView(R.id.hfragment)
    RelativeLayout hfragment;
    private int statusHeight;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private List<String> images = new ArrayList<>();
    private List<SecondaryMenu> secondaryMenuList = new ArrayList<>();
    private List<ScenicSpot> allScenicSpots = new ArrayList<>();
    private List<HotTopicsBean> hotTopicsBeanList;
    private RecyclerViewAdapter tAdapter;
    private RecyclerViewAdapter rAdapter;
    private SecondaryMenuItemAdapter adapter1;
    private Unbinder unbinder;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    AMapLocationListener mLocationListener;
    //网络请求api
    private ServerApi api;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, root);
        initView();
        initValidata();
        initListener();
        initLocationText();
        initListen();
        initLocation();
        initLocations();
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void initListen() {

        mLocationListener = aMapLocation -> {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
                    Log.d("ss", "fasd: " + aMapLocation.getCity());
                    if (aMapLocation.getCity() != null) {
                        if (tvDiqu.getText().equals("")) {
                            tvDiqu.setText(aMapLocation.getCity());
                        }
                        mLocationClient.stopLocation();
                    }
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        };
    }

    public void initLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getActivity().getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();

        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        mLocationOption.setOnceLocationLatest(true);

        mLocationOption.setHttpTimeOut(900000);
        mLocationOption.setLocationCacheEnable(false);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
    }

    public void initLocations() {
        tvDiqu.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), LocationActivity.class);
            startActivityForResult(intent, 0);
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initView() {
        initToolBar();
        initRefreshLayout();
        initBanner();
        initSecondaryMenu();
    }

    @SuppressLint("NewApi")
    @Override
    public void initValidata() {
        //创建网格布局管理器
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(), 3);
        //设置管理器竖向显示
        gridLayoutManager1.setOrientation(RecyclerView.VERTICAL);
        //设置布局管理器
        rvTheme.setLayoutManager(gridLayoutManager1);
        //创建适配器对象
        tAdapter = new RecyclerViewAdapter(getContext(), 9);

        //创建网格布局管理器
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getContext(), 2);
        //设置管理器竖向显示
        gridLayoutManager2.setOrientation(RecyclerView.VERTICAL);
        //设置布局管理器
        recyclerView.setLayoutManager(gridLayoutManager2);
        //创建适配器对象
        rAdapter = new RecyclerViewAdapter(getContext(), 5);

        hotTopicsBeanList = new ArrayList<>();
        hotTopicsBeanList.add(new HotTopicsBean("images/deng.jpg", "徒步登上", "#初级登山",
                "#户外活动", "#徒步体验"));
        hotTopicsBeanList.add(new HotTopicsBean("images/romantic.jpg", "浪漫·之旅", "#行万里路",
                "#激情刺激", "#星辰大海"));
        hotTopicsBeanList.add(new HotTopicsBean("images/depth.jpg", "深度体验", "#摄影/拍摄",
                "#轻奢游", "#义工旅行"));
        //设置数据
        tAdapter.setHotTopicsBeanList(hotTopicsBeanList);
        rvTheme.setAdapter(tAdapter);

        onRefreshss();
    }

    @Override
    public void initListener() {
        initLocation();
        showNearby();
        etSearch.setOnFocusChangeListener((view, b) -> {
            if (b) {
                // 此处为得到焦点时的处理内容
                openActivity(SeachActivity.class);
            } else {
                // 此处为失去焦点时的处理内容
            }
        });
        tvDiqu.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), LocationActivity.class);
            startActivityForResult(intent, 0);
        });

        //点击酒店监听
        llHotelBrown.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), HotelActivity.class);
            intent.putExtra("url", RequestURL.hotel_url);
            startActivity(intent);
        });
        //点击机票监听
        llPlaneTicket.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), HotelActivity.class);
            intent.putExtra("url", RequestURL.flight_url);
            startActivity(intent);
        });
        //火车票点击监听
        llTrain.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), HotelActivity.class);
            intent.putExtra("url", RequestURL.train_url);
            startActivity(intent);
        });
        //车票点击监听
        llBus.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), HotelActivity.class);
            intent.putExtra("url", RequestURL.bus_url);
            startActivity(intent);
        });
        //门票点击监听
        llPiao.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), HotelActivity.class);
            intent.putExtra("url", RequestURL.piao_url);
            startActivity(intent);
        });
    }

    @Override
    public void bindData() {
        if (allScenicSpots == null) return;
        rAdapter.setScenicSpotList(allScenicSpots);
        recyclerView.setAdapter(rAdapter);
        //刷新适配器
        rAdapter.notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initToolBar() {
        //设置状态栏透明
        StatusBarUtil.setTransparentForWindow(getActivity());
        //获取状态栏高度
        statusHeight = AppUtils.getStatusBarHeight(getActivity());
        //设置状态栏高度
        AppUtils.setStatusBarColor(statusView, statusHeight, R.color.color_blue);
        //设置透明度为0
        statusView.getBackground().mutate().setAlpha(0);
        llToolbar.getBackground().mutate().setAlpha(0);
        int bHeight = 400;
        //设置滚动监听
        scrollView.setOnScrollChangeListener((View.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            //设置status，toobar颜色透明渐变
            float detalis = scrollY > bHeight ? bHeight : (scrollY > 30 ? scrollY : 0);
            int alpha = (int) (detalis / bHeight * 255);
            AppUtils.setUpdateActionBar(statusView, llToolbar, alpha);
        });
    }

    public void initLocationText() {
        tvDiqu.setText(sharedPreferences.getString("location", ""));
    }

    private void initRefreshLayout() {
        //设置 Header 为 贝塞尔雷达 样式
        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        //设置 Footer 为 球脉冲 样式
        refreshLayout.setRefreshFooter(new BallPulseFooter(getContext()).setSpinnerStyle(SpinnerStyle.Translate)
                .setAnimatingColor(AppUtils.getColor(R.color.color_blue)));

        refreshLayout.setOnMultiListener(new OnMultiListener() {
            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                if (llStateToolbar == null) return;
                llStateToolbar.setVisibility(View.GONE);
            }

            @Override
            public void onHeaderReleased(RefreshHeader header, int headerHeight, int maxDragHeight) {
                if (llStateToolbar == null) return;
                llStateToolbar.setVisibility(View.GONE);
            }

            @Override
            public void onHeaderStartAnimator(RefreshHeader header, int headerHeight, int maxDragHeight) {
                if (llStateToolbar == null) return;
                llStateToolbar.setVisibility(View.GONE);
            }

            @Override
            public void onHeaderFinish(RefreshHeader header, boolean success) {
                if (llStateToolbar == null) return;
                llStateToolbar.setVisibility(View.GONE);
                if (success) {
                    AppUtils.getToast("为您推荐了9条内容");
                    //设置数据
                    rAdapter.setScenicSpotList(allScenicSpots);
                    //刷新适配器
                    rAdapter.notifyDataSetChanged();
                } else {
                    AppUtils.getToast("数据更新失败");
                }
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
                //网络请求加载预定信息
                if (success) {
                    rAdapter.loadMore(allScenicSpots);
                } else {
                    AppUtils.getToast("加载失败");
                }

            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                api = RetrofitManger.getInstance().getRetrofit(RequestURL.ip_port).create(ServerApi.class);
                Call<ResponseBody> qCall = api.getNAsync("onFooterFinish");
                qCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String message = response.body().string();
                            JSONObject json = new JSONObject(message);
                            if (json.getString(RequestURL.RESULT).equals("S")) {
                                allScenicSpots = RetrofitManger.getInstance().getGson().fromJson(json.getString(RequestURL.ONE_DATA),
                                        new TypeToken<List<ScenicSpot>>() {
                                        }.getType());
                                if (allScenicSpots.size() == 0) return;
                                refreshLayout.finishLoadMore(true);
                            } else {
                                refreshLayout.finishLoadMore(false);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                api = RetrofitManger.getInstance().getRetrofit(RequestURL.ip_port).create(ServerApi.class);
                Call<ResponseBody> qCall = api.getNAsync("onRefresh");
                qCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String message = response.body().string();
                            JSONObject json = new JSONObject(message);
                            if (json.getString(RequestURL.RESULT).equals("S")) {
                                //清除数据
                                allScenicSpots.clear();
                                allScenicSpots = RetrofitManger.getInstance().getGson().fromJson(json.getString(RequestURL.ONE_DATA),
                                        new TypeToken<List<ScenicSpot>>() {
                                        }.getType());
                                if (allScenicSpots.size() == 0) return;
                                refreshLayout.finishRefresh(true);
                            } else {
                                refreshLayout.finishRefresh(false);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
                if (llStateToolbar == null) return;
                llStateToolbar.setVisibility(View.VISIBLE);
                Log.d(InitApp.TAG, "isTwoLevel: " + newState.isTwoLevel);
                Log.d(InitApp.TAG, "isDragging: " + newState.isDragging);
                Log.d(InitApp.TAG, "isOpening: " + newState.isOpening);
                Log.d(InitApp.TAG, "isFinishing: " + newState.isFinishing);
                Log.d(InitApp.TAG, "isReleaseToOpening: " + newState.isReleaseToOpening);
//                if (!oldState.isReleaseToOpening) {
//                    llStateToolbar.setVisibility(View.GONE);
//                }
//                if (oldState.isFinishing) {
//                    llStateToolbar.setVisibility(View.VISIBLE);
//                }
            }
        });
    }

    private void initBanner() {
        //设置banner样式
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        images.add(RequestURL.ip_images + "/images/banner/banner1.jpg");
        images.add(RequestURL.ip_images + "/images/banner/banner2.jpg");
        images.add(RequestURL.ip_images + "/images/banner/banner3.jpg");
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    private void initSecondaryMenu() {
        secondaryMenuList.add(new SecondaryMenu(R.drawable.menu_1, getString(R.string.menu_1)));
        secondaryMenuList.add(new SecondaryMenu(R.drawable.menu_2, getString(R.string.menu_2)));
        secondaryMenuList.add(new SecondaryMenu(R.drawable.menu_3, getString(R.string.menu_3)));
        secondaryMenuList.add(new SecondaryMenu(R.drawable.menu_4, getString(R.string.menu_4)));
        secondaryMenuList.add(new SecondaryMenu(R.drawable.menu_5, getString(R.string.menu_5)));
        secondaryMenuList.add(new SecondaryMenu(R.drawable.menu_6, getString(R.string.menu_6)));
        secondaryMenuList.add(new SecondaryMenu(R.drawable.menu_7, getString(R.string.menu_7)));
        secondaryMenuList.add(new SecondaryMenu(R.drawable.menu_8, getString(R.string.menu_8)));
        adapter1 = new SecondaryMenuItemAdapter(getContext(), secondaryMenuList);
        gridView.setAdapter(adapter1);
        gridView.setOnItemClickListener((adapterView, view, i, l) -> {
            if (i == 4) {
                openActivity(RomanticJourneyActivity.class);
            } else {
                Toast.makeText(getContext(), secondaryMenuList.get(i).menu_name, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), SecondaryActivity.class);
                intent.putExtra("travel_mode", (i + 1));
                intent.putExtra("menu_name", secondaryMenuList.get(i).menu_name);
                startActivity(intent);
            }
        });
    }

    private void showNearby() {
        linearLayout.setOnClickListener(view -> {
            Toast.makeText(getContext(), "查看附近景点", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), NearbyActivity.class);
            startActivity(intent);
        });
    }

    private void onRefreshss() {
        allScenicSpots.clear();
        //网络请求加载预定信息
        api = RetrofitManger.getInstance().getRetrofit(RequestURL.ip_port).create(ServerApi.class);
        Call<ResponseBody> qCall = api.getNAsync("onRefresh");
        qCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String message = response.body().string();
                    JSONObject json = new JSONObject(message);
                    if (json.getString(RequestURL.RESULT).equals("S")) {
                        allScenicSpots = RetrofitManger.getInstance().getGson().fromJson(json.getString(RequestURL.ONE_DATA),
                                new TypeToken<List<ScenicSpot>>() {
                                }.getType());
                        bindData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        sharedPreferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        //步骤2： 实例化SharedPreferences.Editor对象
        editor = sharedPreferences.edit();
        //步骤3：将获取过来的值放入文件
    }

    @Override
    public void onResume() {
        super.onResume();
        initLocationText();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 || resultCode == 1) {
            String mLocation = data.getStringExtra("location");
            String temp = sharedPreferences.getString("location", "");
            if (!mLocation.equals(temp)) {
                editor.remove("location");
                editor.putString("location", mLocation);
                editor.commit();
            }
            editor.putString("location", mLocation);
            //步骤4：提交
            editor.commit();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind(); //解绑
    }
}