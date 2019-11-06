package com.example.tourism.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.adapter.RecyclerViewAdapter;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.common.DefineView;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.ScenicSpot;
import com.example.tourism.ui.activity.SeachActivity;
import com.example.tourism.ui.fragment.base.BaseFragment;
import com.example.tourism.utils.AppUtils;
import com.example.tourism.widget.CustomToolbar;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 用户订单
 */
public class OrderFragment extends BaseFragment implements DefineView {
    @BindView(R.id.rv_trip)
    RecyclerView rvTrip;
    @BindView(R.id.rv_recommend)
    RecyclerView rvRecommend;
    @BindView(R.id.custom_toolbar)
    CustomToolbar customToolbar;
    @BindView(R.id.btn_add_trip)
    Button btnAddTrip;
    @BindView(R.id.status_view)
    View statusView;
    @BindView(R.id.ll_states_bar)
    LinearLayout llStatesBar;
    @BindView(R.id.nsv_scollview)
    NestedScrollView nsvScollview;
    @BindView(R.id.loading_line)
    LinearLayout loadingLine;
    @BindView(R.id.empty_line)
    LinearLayout emptyLine;
    @BindView(R.id.error_line)
    LinearLayout errorLine;
    private Unbinder unbinder;
    //请求api
    private ServerApi api;
    //返回实体数据
    private List<ScenicSpot> scenicSpotList;
    private List<ScenicSpot> scenictList;

    //适配器
    private RecyclerViewAdapter qAdapter;
    private RecyclerViewAdapter rAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_order, container, false);
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
        nsvScollview.setVisibility(View.GONE);
        btnAddTrip.setVisibility(View.GONE);
        loadingLine.setVisibility(View.VISIBLE);
        emptyLine.setVisibility(View.GONE);
        errorLine.setVisibility(View.GONE);
        //获取状态栏高度
        int statusHeight = AppUtils.getStatusBarHeight(getActivity());
        //设置状态栏高度
        AppUtils.setStatusBarColor(statusView, statusHeight, R.color.color_blue);
        //获取标题栏高度，并设置View的高度
        customToolbar.post(() -> {
            int barHeight = customToolbar.getHeight();
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) llStatesBar.getLayoutParams();
            layoutParams.height = (statusHeight + barHeight);
            llStatesBar.setLayoutParams(layoutParams);
        });

        //设置线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        //设置管理器竖向显示
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        //设置布局管理器
        rvTrip.setLayoutManager(layoutManager);
        //创建适配器对象
        qAdapter = new RecyclerViewAdapter(getContext(), 3);

        //创建网格布局管理器
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        //设置管理器竖向显示
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        //设置布局管理器
        rvRecommend.setLayoutManager(gridLayoutManager);
        //创建适配器对象
        rAdapter = new RecyclerViewAdapter(getContext(), 5);

        //网络请求加载预定信息
        api = RetrofitManger.getInstance().getRetrofit(RequestURL.ip_port).create(ServerApi.class);
        Map<String, Object> qMap = new HashMap<>();
        qMap.put("userId", 1);
        Call<ResponseBody> qCall = api.getASync("queryAllTrips", qMap);
        qCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String message = response.body().string();
                    JSONObject json = new JSONObject(message);
                    if (json.getString(RequestURL.RESULT).equals("S")) {
                        scenicSpotList = RetrofitManger.getInstance().getGson().fromJson(json.getString(RequestURL.ONE_DATA),
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

        //网络请求加载推荐
        Map<String, Object> rMap = new HashMap<>();
        rMap.put("pStr", "南");
        Call<ResponseBody> rCall = api.getASync("searchArea", rMap);
        rCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String message = response.body().string();
                    JSONObject json = new JSONObject(message);
                    if (json.getString(RequestURL.RESULT).equals("S")) {
                        scenictList = RetrofitManger.getInstance().getGson().fromJson(json.getString(RequestURL.TWO_DATA),
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
                AppUtils.getToast(t.toString());
                emptyLine.setVisibility(View.GONE);
                nsvScollview.setVisibility(View.GONE);
                btnAddTrip.setVisibility(View.GONE);
                loadingLine.setVisibility(View.GONE);
                errorLine.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void initListener() {

    }

    @Override
    public void bindData() {
        if (scenicSpotList.size() > 0) {
            //设置数据
            qAdapter.setScenicSpotList(scenicSpotList);
            //设置适配器
            rvTrip.setAdapter(qAdapter);
        }

        if (scenictList.size() > 0) {
            //设置数据
            rAdapter.setScenicSpotList(scenictList);
            //设置适配器
            rvRecommend.setAdapter(rAdapter);
            //页面加载显示
            loadingLine.setVisibility(View.GONE);
            emptyLine.setVisibility(View.GONE);
            errorLine.setVisibility(View.GONE);
            nsvScollview.setVisibility(View.VISIBLE);
            btnAddTrip.setVisibility(View.VISIBLE);
        } else {
            nsvScollview.setVisibility(View.GONE);
            btnAddTrip.setVisibility(View.GONE);
            loadingLine.setVisibility(View.GONE);
            errorLine.setVisibility(View.GONE);
            emptyLine.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind(); //解绑
    }

    @OnClick(R.id.btn_add_trip)
    public void onViewClicked() {
        AppUtils.getToast("添加行程");
        openActivity(SeachActivity.class);
    }
}