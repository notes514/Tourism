package com.example.tourism.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.adapter.RecyclerViewAdapter;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.common.DefineView;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.ScenicSpot;
import com.example.tourism.ui.fragment.base.BaseFragment;
import com.example.tourism.utils.AppUtils;
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
 * 攻略库
 */
public class PlaneTicketFragment extends BaseFragment implements DefineView {
    @BindView(R.id.tv_one_way_trip)
    TextView tvOneWayTrip;
    @BindView(R.id.tv_return)
    TextView tvReturn;
    @BindView(R.id.tv_multipath)
    TextView tvMultipath;
    @BindView(R.id.tv_start_land)
    TextView tvStartLand;
    @BindView(R.id.tv_end_land)
    TextView tvEndLand;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_shipping_space)
    TextView tvShippingSpace;
    @BindView(R.id.cb_children)
    CheckBox cbChildren;
    @BindView(R.id.cb_baby)
    CheckBox cbBaby;
    @BindView(R.id.btn_seach)
    Button btnSeach;
    @BindView(R.id.textView4)
    TextView textView4;
    @BindView(R.id.rv_recommend)
    RecyclerView rvRecommend;
    //控件绑定
    private Unbinder unbinder;

    //网络请求api
    private ServerApi api;
    //景区数据集
    private List<ScenicSpot> scenicSpotList;
    //适配器
    private RecyclerViewAdapter rAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_air_ticket_layout, container, false);
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

    }

    @Override
    public void initListener() {
        //创建网格布局管理器
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        //设置管理器竖向显示
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        //设置布局管理器
        rvRecommend.setLayoutManager(gridLayoutManager);
        //创建适配器对象
        rAdapter = new RecyclerViewAdapter(getContext(), 5);

        //网络请求
        api = RetrofitManger.getInstance().getRetrofit(RequestURL.ip_port).create(ServerApi.class);
        Map<String, Object> map = new HashMap<>();
        map.put("pStr", "跟团游");
        Call<ResponseBody> scenicSpotCall = api.getASync("searchArea", map);
        scenicSpotCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String message = response.body().string();
                    JSONObject json = new JSONObject(message);
                    scenicSpotList = RetrofitManger.getInstance().getGson().fromJson(json.getString(RequestURL.TWO_DATA),
                            new TypeToken<List<ScenicSpot>>() {
                            }.getType());
                    bindData();
                } catch (Exception e) {
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
    public void bindData() {
        if (scenicSpotList == null) return;
        rAdapter.setScenicSpotList(scenicSpotList);
        rvRecommend.setAdapter(rAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind(); //解绑
    }

    @OnClick({R.id.tv_one_way_trip, R.id.tv_return, R.id.tv_multipath, R.id.tv_start_land, R.id.tv_end_land, R.id.tv_date, R.id.btn_seach})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_one_way_trip:
                break;
            case R.id.tv_return:
                break;
            case R.id.tv_multipath:
                break;
            case R.id.tv_start_land:
                break;
            case R.id.tv_end_land:
                break;
            case R.id.tv_date:
                break;
            case R.id.btn_seach:
                break;
        }
    }
}