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
import com.example.tourism.common.DefineView;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.Order;
import com.example.tourism.ui.fragment.base.BaseFragment;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

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
    @BindView(R.id.loading_line)
    LinearLayout loadingLine;
    @BindView(R.id.empty_line)
    LinearLayout emptyLine;
    @BindView(R.id.error_line)
    LinearLayout errorLine;
    @BindView(R.id.tv_empty_content)
    TextView tvEmptyContent;
    @BindView(R.id.tv_erro_content)
    TextView tvErroContent;
    private Unbinder unbinder;
    private static final String KEY = "AllOrderPage";
    private String pStr;
    //网络请求接口
    private ServerApi api;
    //实体
    private List<Order> orderList;
    //适配器
    private RecyclerViewAdapter adapter;

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

        //创建线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        //设置竖向显示
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        //设置管理器
        pageRecyclerView.setLayoutManager(layoutManager);
        //创建适配器，并指定子布局显示
        adapter = new RecyclerViewAdapter(getContext(), 2);

        if ("全部".equals(pStr)) { //全部订单
            queryByOrderState(1, 0);
        }
        if ("待付款".equals(pStr)) {
            queryByOrderState(1, 1);
        }
        if ("待消费".equals(pStr)) {
            queryByOrderState(1, 2);
        }
        if ("待评价".equals(pStr)) {
            queryByOrderState(1, 3);
        }
        if ("退款中".equals(pStr)) {
            queryByOrderState(1, 4);
        }

    }

    @Override
    public void initListener() {

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

    private void queryByOrderState(int userId, int orderState) {
        api = RetrofitManger.getInstance().getRetrofit(RequestURL.ip_port).create(ServerApi.class);
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
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
                        Log.d(InitApp.TAG, "onResponse: " + orderList.size());
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