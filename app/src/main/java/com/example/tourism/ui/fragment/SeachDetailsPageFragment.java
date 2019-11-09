package com.example.tourism.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.adapter.RecyclerViewAdapter;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.common.DefineView;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.ScenicSpot;
import com.example.tourism.entity.SeachBean;
import com.example.tourism.ui.fragment.base.BaseFragment;
import com.example.tourism.utils.AppUtils;
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
public class SeachDetailsPageFragment extends BaseFragment implements DefineView {
    @BindView(R.id.page_recyclerView)
    RecyclerView pageRecyclerView;
    @BindView(R.id.tv_tips)
    TextView tvTips;
    @BindView(R.id.loading_line)
    LinearLayout loadingLine;
    @BindView(R.id.empty_line)
    LinearLayout emptyLine;
    @BindView(R.id.error_line)
    LinearLayout errorLine;
    private Unbinder unbinder;
    private static final String KEY = "SeachDetails";
    private SeachBean seach;
    //网络请求接口
    private ServerApi api;
    //适配器
    private RecyclerViewAdapter rAdapter;
    //景区数据
    private List<ScenicSpot> scenicSpotList;

    public static SeachDetailsPageFragment newInstance(SeachBean seachBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY, seachBean);
        SeachDetailsPageFragment fragment = new SeachDetailsPageFragment();
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
            seach = (SeachBean) bundle.getSerializable(KEY);
        }
        //创建线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        //设置竖向显示
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        //设置管理器
        pageRecyclerView.setLayoutManager(layoutManager);
        //添加Android自带的分割线
        pageRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        //创建适配器，并指定子布局显示
        rAdapter = new RecyclerViewAdapter(getContext(), 6);

        api = RetrofitManger.getInstance().getRetrofit(RequestURL.ip_port).create(ServerApi.class);
        Map<String, Object> map = new HashMap<>();
        map.put("pStr", seach.getsContent());
        Call<ResponseBody> call = api.getASync("searchArea", map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String message = response.body().string();
                    JSONObject json = new JSONObject(message);
                    if (json.getString(RequestURL.RESULT).equals("S")) {
                        scenicSpotList = RetrofitManger.getInstance().getGson().fromJson(json.getString(RequestURL.TWO_DATA),
                                new TypeToken<List<ScenicSpot>>() {
                                }.getType());
                        bindData();
                    } else {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                AppUtils.getToast(t.toString());
                emptyLine.setVisibility(View.GONE);
                pageRecyclerView.setVisibility(View.GONE);
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
            loadingLine.setVisibility(View.GONE);
            pageRecyclerView.setVisibility(View.VISIBLE);
            emptyLine.setVisibility(View.GONE);
            errorLine.setVisibility(View.GONE);
            rAdapter.setScenicSpotList(scenicSpotList);
            pageRecyclerView.setAdapter(rAdapter);
        } else {
            pageRecyclerView.setVisibility(View.GONE);
            loadingLine.setVisibility(View.GONE);
            emptyLine.setVisibility(View.VISIBLE);
            errorLine.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind(); //解绑
    }

}