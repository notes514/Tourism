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
import com.example.tourism.adapter.RecyclerViewAdapter;
import com.example.tourism.application.InitApp;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.biz.TravelsDataManger;
import com.example.tourism.common.DefineView;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.Order;
import com.example.tourism.ui.fragment.base.BaseFragment;
import com.example.tourism.utils.AppUtils;
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

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
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
 * 目的地
 */
public class AllOrderPageFragment extends BaseFragment implements DefineView {

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
    private static final String KEY = "AllOrderPage";
    private String pStr;
    //网络请求接口
    private ServerApi api;
    //实体
    private List<Order> orderList;
    //适配器
    private RecyclerViewAdapter adapter;
    private int state = 0;

    public static AllOrderPageFragment newInstance(String pStr) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY, pStr);
        AllOrderPageFragment fragment = new AllOrderPageFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_browse_page, container, false);
        unbinder = ButterKnife.bind(this, root);
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
        pageRecyclerView.setVisibility(View.GONE);
        emptyLine.setVisibility(View.GONE);
        errorLine.setVisibility(View.GONE);
        loadingLine.setVisibility(View.VISIBLE);
        //使用Bundle获取传过来的数据
        Bundle bundle = getArguments();
        if (bundle != null) {
            pStr = (String) bundle.getSerializable(KEY);
        }

        if (RequestURL.vUserId.length() == 0) {
            loadingLine.setVisibility(View.GONE);
            emptyLine.setVisibility(View.VISIBLE);
            tvEmptyContent.setText("您还没有本地订单，快登录查看订单吧");
            return;
        }

        //创建线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        //列表在底部开始展示，反转后由上面开始展示
        layoutManager.setStackFromEnd(true);
        //列表翻转
        layoutManager.setReverseLayout(true);
        //设置竖向显示
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        //设置管理器
        pageRecyclerView.setLayoutManager(layoutManager);
        //创建适配器，并指定子布局显示
        adapter = new RecyclerViewAdapter(getContext(), 2);

        if ("全部".equals(pStr)) { //全部订单
            queryByOrderState(0);
        }
        if ("待付款".equals(pStr)) {
            queryByOrderState(1);
        }
        if ("待消费".equals(pStr)) {
            queryByOrderState(2);
        }
        if ("待评价".equals(pStr)) {
            queryByOrderState(3);
        }
        if ("退款中".equals(pStr)) {
            queryByOrderState(4);
        }

    }

    @Override
    public void initListener() {
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
                    adapter.setOrderList(orderList);
                    adapter.notifyDataSetChanged();
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

                } else {
                    AppUtils.getToast("加载失败");
                }

            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(100);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                if ("全部".equals(pStr)) { //全部订单
                    state = 0;
                }
                if ("待付款".equals(pStr)) {
                    state = 1;
                }
                if ("待消费".equals(pStr)) {
                    state = 2;
                }
                if ("待评价".equals(pStr)) {
                    state = 3;
                }
                if ("退款中".equals(pStr)) {
                    state = 4;
                }
                api = RetrofitManger.getInstance().getRetrofit(RequestURL.ip_port).create(ServerApi.class);
                Map<String, Object> map = new HashMap<>();
                map.put("userId", RequestURL.vUserId);
                map.put("orderState", state);
                Call<ResponseBody> allCall = api.getASync("queryByOrderState", map);
                allCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String message = response.body().string();
                            JSONObject json = new JSONObject(message);
                            if (json.getString(RequestURL.RESULT).equals("S")) {
                                orderList.clear();
                                orderList = RetrofitManger.getInstance().getGson().fromJson(json.getString(RequestURL.ONE_DATA),
                                        new TypeToken<List<Order>>() {
                                        }.getType());
                                if (orderList == null) return;
                                refreshLayout.finishRefresh(true);
                            } else {
                                pageRecyclerView.setVisibility(View.GONE);
                                errorLine.setVisibility(View.GONE);
                                loadingLine.setVisibility(View.GONE);
                                emptyLine.setVisibility(View.VISIBLE);
                                tvEmptyContent.setText(json.getString(RequestURL.TIPS));
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

            }
        });
    }

    @Override
    public void bindData() {
        if (orderList != null) {
            loadingLine.setVisibility(View.GONE);
            emptyLine.setVisibility(View.GONE);
            errorLine.setVisibility(View.GONE);
            pageRecyclerView.setVisibility(View.VISIBLE);
            //设置数据
            adapter.setOrderList(orderList);
            //设置适配器
            pageRecyclerView.setAdapter(adapter);
        }
    }

    private void queryByOrderState(int orderState) {
        api = RetrofitManger.getInstance().getRetrofit(RequestURL.ip_port).create(ServerApi.class);
        Map<String, Object> map = new HashMap<>();
        map.put("userId", RequestURL.vUserId);
        map.put("orderState", orderState);
        Call<ResponseBody> allCall = api.getASync("queryByOrderState", map);
        allCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String message = response.body().string();
                    JSONObject json = new JSONObject(message);
                    if (json.getString(RequestURL.RESULT).equals("S")) {
                        orderList = RetrofitManger.getInstance().getGson().fromJson(json.getString(RequestURL.ONE_DATA),
                                new TypeToken<List<Order>>() {
                                }.getType());
                        bindData();
                    } else {
                        pageRecyclerView.setVisibility(View.GONE);
                        errorLine.setVisibility(View.GONE);
                        loadingLine.setVisibility(View.GONE);
                        emptyLine.setVisibility(View.VISIBLE);
                        tvEmptyContent.setText(json.getString(RequestURL.TIPS));
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
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind(); //解绑
    }

}