package com.example.tourism.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.adapter.RecyclerViewAdapter;
import com.example.tourism.application.InitApp;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.common.DefineView;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.ScenicSpot;
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
    private Unbinder unbinder;
    //请求api
    private ServerApi api;
    //返回实体数据
    private List<ScenicSpot> scenicSpotList;
    //适配器
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
        //设置线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        //设置管理器竖向显示
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        //设置布局管理器
        rvTrip.setLayoutManager(layoutManager);
        //创建适配器对象
        rAdapter = new RecyclerViewAdapter(getContext(), 3);

        api = RetrofitManger.getInstance().getRetrofit(RequestURL.ip_port).create(ServerApi.class);
        Map<String, Object> map = new HashMap<>();
        map.put("userId", 1);
        Call<ResponseBody> qCall = api.getASync("queryAllTrips", map);
        qCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String message = response.body().string();
                    JSONObject json = new JSONObject(message);
                    if (json.getString(RequestURL.RESULT).equals("S")) {
                        scenicSpotList = RetrofitManger.getInstance().getGson().fromJson(json.getString(RequestURL.ONE_DATA),
                                new TypeToken<List<ScenicSpot>>(){}.getType());
                        if (scenicSpotList.size() > 0) bindData();
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
    public void initListener() {

    }

    @Override
    public void bindData() {
        //设置数据
        rAdapter.setScenicSpotList(scenicSpotList);
        //设置适配器
        rvTrip.setAdapter(rAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind(); //解绑
    }

    @OnClick(R.id.btn_add_trip)
    public void onViewClicked() {
        AppUtils.getToast("添加行程");
    }
}