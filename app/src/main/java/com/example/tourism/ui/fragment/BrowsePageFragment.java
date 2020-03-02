package com.example.tourism.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.adapter.PageRecyclerAdapter;
import com.example.tourism.application.InitApp;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.biz.TravelsDataManger;
import com.example.tourism.common.DefineView;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.TrHeadBean;
import com.example.tourism.entity.TravelsBean;
import com.example.tourism.ui.fragment.base.BaseFragment;
import com.example.tourism.utils.AppUtils;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;
import com.scwang.smart.refresh.layout.listener.OnMultiListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 好货页面
 */
public class BrowsePageFragment extends BaseFragment implements DefineView {

    @BindView(R.id.page_recyclerView)
    RecyclerView pageRecyclerView;
    @BindView(R.id.tv_tips)
    TextView tvTips;
    @BindView(R.id.page_swipeRefreshLayout)
    SmartRefreshLayout pageSwipeRefreshLayout;
    @BindView(R.id.tv_loading_content)
    TextView tvLoadingContent;
    @BindView(R.id.loading_line)
    LinearLayout loadingLine;
    @BindView(R.id.tv_empty_content)
    TextView tvEmptyContent;
    @BindView(R.id.empty_line)
    LinearLayout emptyLine;
    @BindView(R.id.tv_erro_content)
    TextView tvErroContent;
    @BindView(R.id.error_line)
    LinearLayout errorLine;
    private Unbinder unbinder;
    private static final String KEY = "BrowsePage";
    private TrHeadBean trHeadBean;
    //网络请求api
    private ServerApi api;
    private LinearLayoutManager layoutManager;
    private PageRecyclerAdapter adapter;
    private List<TravelsBean> travelsList = new ArrayList<>();
    private int lastItem; //记录列表的数量值
    private boolean isMore = true; //解决上拉加载重复的bug
    private int index = 1; //上来加载索引

    public static BrowsePageFragment newInstance(TrHeadBean trHeadBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY, trHeadBean);
        BrowsePageFragment fragment = new BrowsePageFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_browse_page, container, false);
        unbinder = ButterKnife.bind(this, root);
        initView();
        initValidata();
        initListener();
        return root;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initValidata() {
        //资源文件隐藏显示
        pageSwipeRefreshLayout.setVisibility(View.INVISIBLE);
        pageRecyclerView.setVisibility(View.INVISIBLE);
        emptyLine.setVisibility(View.INVISIBLE);
        errorLine.setVisibility(View.INVISIBLE);
        loadingLine.setVisibility(View.VISIBLE);

        Bundle bundle = getArguments();
        if (bundle != null) {
            trHeadBean = (TrHeadBean) bundle.getSerializable(KEY);
        }

//        //设置swipeRefreshLayout的进度条的背景颜色
//        pageSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.color_white);
//        //设置进度条颜色
//        pageSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
//        //设置进度条的偏移量
//        pageSwipeRefreshLayout.setProgressViewOffset(false, 0, 80);

        //设置线性布局管理器
        layoutManager = new LinearLayoutManager(getContext());
        //设置布局垂直显示
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        //设置分割线(无)
        //设置适配器布局管理器
        pageRecyclerView.setLayoutManager(layoutManager);
        //创建适配器
        adapter = new PageRecyclerAdapter(getContext());
        if ("热门游记".equals(trHeadBean.getTitle())) {
            getTravels(trHeadBean.getHref());
        }
        if ("精华游记".equals(trHeadBean.getTitle())) {
            getTravels(trHeadBean.getHref());
        }
        if ("行程计划".equals(trHeadBean.getTitle())) {
            getTravels(trHeadBean.getHref());
        }
    }

    private List<TravelsBean> onTravel = new ArrayList<>();

    @Override
    public void initListener() {
        //实现下拉刷新监听接口
//        pageSwipeRefreshLayout.setOnRefreshListener(() -> new Handler().postDelayed(() -> {
//            if (pageSwipeRefreshLayout.isRefreshing()) {
//                adapter.notifyDataSetChanged();
//                pageSwipeRefreshLayout.setRefreshing(false);
//            }
//        }, 1000));

        //设置 Header 为 贝塞尔雷达 样式
        pageSwipeRefreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        //设置 Footer 为 球脉冲 样式
        pageSwipeRefreshLayout.setRefreshFooter(new BallPulseFooter(getContext()).setSpinnerStyle(SpinnerStyle.Translate)
                .setAnimatingColor(AppUtils.getColor(R.color.color_blue)));

        pageSwipeRefreshLayout.setOnMultiListener(new OnMultiListener() {
            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
            }

            @Override
            public void onHeaderReleased(RefreshHeader header, int headerHeight, int maxDragHeight) {

            }

            @Override
            public void onHeaderStartAnimator(RefreshHeader header, int headerHeight, int maxDragHeight) {

            }

            @Override
            public void onHeaderFinish(RefreshHeader header, boolean success) {
                if (success) {
                    AppUtils.getToast("刷新成功");
                } else {
                    AppUtils.getToast("加载失败");
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
                    adapter.addAllTravels(onTravel);
                } else {
                    AppUtils.getToast("加载失败");
                }

            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (index == 39) return;
                api = RetrofitManger.getInstance().getRetrofit(RequestURL.html).create(ServerApi.class);
                Call<ResponseBody> call = api.getNAsync("travelbook/list.htm?page=" + index + "&order=" + trHeadBean.getTitle());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String message = response.body().string();
                            Document document = Jsoup.parse(message, RequestURL.html);
                            onTravel = new TravelsDataManger().getTravels(document);
                            if (onTravel == null) return;
                            refreshLayout.finishLoadMore(true);
                            index += 1;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        AppUtils.getToast(t.toString());
                    }
                });
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(1000);
            }

            @Override
            public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {

            }
        });

    }

    @Override
    public void bindData() {
        if (travelsList != null && travelsList.size() > 0) {
            if (loadingLine != null && emptyLine != null && errorLine != null && pageRecyclerView != null) {
                loadingLine.setVisibility(View.INVISIBLE);
                emptyLine.setVisibility(View.INVISIBLE);
                errorLine.setVisibility(View.INVISIBLE);
                pageSwipeRefreshLayout.setVisibility(View.VISIBLE);
                pageRecyclerView.setVisibility(View.VISIBLE);
                //设置数据
                adapter.setTravelsBeans(travelsList);
                pageRecyclerView.setAdapter(adapter);
            }
        } else {
            pageRecyclerView.setVisibility(View.INVISIBLE);
            loadingLine.setVisibility(View.INVISIBLE);
            errorLine.setVisibility(View.INVISIBLE);
            emptyLine.setVisibility(View.VISIBLE);
        }
    }

    private void getTravels(String url) {
        ServerApi api = RetrofitManger.getInstance().getFileRetrofit(RequestURL.html).create(ServerApi.class);
        Call<ResponseBody> call = api.getNAsync("travelbook/list.htm?page=1&order=" + url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String message = response.body().string();
                    Document document = Jsoup.parse(message, RequestURL.html);
                    travelsList = new TravelsDataManger().getTravels(document);
                    Log.d(InitApp.TAG, "travelsList: " + travelsList);
                    if (travelsList != null && travelsList.size() > 0) {
                        bindData();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void queryHotHeat(int item) {
        api = RetrofitManger.getInstance().getRetrofit(RequestURL.html).create(ServerApi.class);
        Call<ResponseBody> call = api.getNAsync("travelbook/list.htm?page=" + item + 1 + "&order=hot_heat");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String message = response.body().string();
                    Document document = Jsoup.parse(message, RequestURL.html);
                    List<TravelsBean> travels = new TravelsDataManger().getTravels(document);
                    //添加数据
                    travelsList.addAll(travels);
                    //刷新适配器
                    adapter.notifyDataSetChanged();
                    isMore = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                AppUtils.getToast(t.toString());
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind(); //解绑
    }
}