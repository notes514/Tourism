package com.example.tourism.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.tourism.R;
import com.example.tourism.adapter.ScenicSpotItemAdapter;
import com.example.tourism.adapter.SecondaryMenuItemAdapter;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.ScenicSpot;
import com.example.tourism.entity.SecondaryMenu;
import com.example.tourism.common.DefineView;
import com.example.tourism.ui.activity.NearbyActivity;
import com.example.tourism.ui.fragment.base.BaseFragment;
import com.example.tourism.widget.GlideImageLoader;
import com.google.gson.reflect.TypeToken;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.header.BezierRadarHeader;
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
    private List<ScenicSpot> scenicSpots = new ArrayList<>();
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
        //设置 Header 为 贝塞尔雷达 样式
        refreshLayout.setRefreshHeader(new BezierRadarHeader(getContext()).setEnableHorizontalDrag(true)
                .setPrimaryColorId(R.color.mask_tags_8));
        //设置 Footer 为 球脉冲 样式
        refreshLayout.setRefreshFooter(new BallPulseFooter(getContext()).setSpinnerStyle(SpinnerStyle.Translate)
                .setAnimatingColor(0xFF1DA8FE));
//        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()).setSpinnerStyle(SpinnerStyle.Translate));
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

        initBanner();
        initSecondaryMenu();
        initRecyclerView();
        showNearby();
    }

    @Override
    public void initValidata() {

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

    private void initBanner(){
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1569479180656&di=50f624a13bed482a50d36bdcf129b98a&imgtype=0&src=http%3A%2F%2Fimg.mp.itc.cn%2Fupload%2F20160927%2F25a93eefe4054ba6a78cf3a712a38402_th.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1569479180656&di=cc55f3e8b1f43ec3e24c3febbe7a4874&imgtype=0&src=http%3A%2F%2Fm.tuniucdn.com%2Ffilebroker%2Fcdn%2Folb%2F65%2F0f%2F650fc903e6c5641063acafd392b10b45_w800_h0_c0_t0.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1569479180655&di=64c3f08cafc98c73e64cf37c42193dca&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F80980a7239def04f2763601f340f8de11f3875e1d427c-zjtsfD_fw658");
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
        secondaryMenuList.add(new SecondaryMenu(R.drawable.defaultbg,"周边游"));
        secondaryMenuList.add(new SecondaryMenu(R.drawable.defaultbg,"一日游"));
        secondaryMenuList.add(new SecondaryMenu(R.drawable.defaultbg,"自由行"));
        secondaryMenuList.add(new SecondaryMenu(R.drawable.defaultbg,"景点·门票"));
        secondaryMenuList.add(new SecondaryMenu(R.drawable.defaultbg,"浪漫之旅"));
        secondaryMenuList.add(new SecondaryMenu(R.drawable.defaultbg,"当地向导"));
        secondaryMenuList.add(new SecondaryMenu(R.drawable.defaultbg,"定制旅行"));
        secondaryMenuList.add(new SecondaryMenu(R.drawable.defaultbg,"亲子·游学"));
        adapter1 = new SecondaryMenuItemAdapter(getContext(),secondaryMenuList);
        gridView.setAdapter(adapter1);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(),secondaryMenuList.get(i).menu_name,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initRecyclerView(){
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        adapter2 = new ScenicSpotItemAdapter(getContext(),scenicSpots);
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
                    Log.d("@@@", "请求成功！");
                    //String data = response.body().string();
                    //Log.d("@@@",data);
                    //JSONObject jsonObject = new JSONObject(data);
                    scenicSpots = RetrofitManger.getInstance().getGson().fromJson(response.body().string(),
                            new TypeToken<List<ScenicSpot>>(){}.getType());
                    Log.d("@@@",scenicSpots.size()+"");
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
        int l = scenicSpots.size();
        for (int i = 1; i <= 10; i++) {
            //scenicSpots.add(new ScenicSpot(R.drawable.defaultbg,i+l+""));
        }
        adapter2.notifyDataSetChanged();
    }
}