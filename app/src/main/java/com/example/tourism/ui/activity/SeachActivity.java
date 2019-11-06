package com.example.tourism.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.adapter.RecyclerViewAdapter;
import com.example.tourism.application.InitApp;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.common.DefineView;
import com.example.tourism.common.RequestURL;
import com.example.tourism.database.bean.SeachContent;
import com.example.tourism.entity.ScenicRegion;
import com.example.tourism.entity.ScenicSpot;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.utils.AppUtils;
import com.example.tourism.utils.DaoManger;
import com.example.tourism.utils.DaoUtils;
import com.google.gson.reflect.TypeToken;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 模糊搜索页
 * Name：laodai
 * Time:2019.11.03
 */
public class SeachActivity extends BaseActivity implements DefineView {
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.iv_clear)
    ImageView ivClear;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.iv_delete)
    ImageView ivDelete;
    @BindView(R.id.tfl_lately)
    TagFlowLayout tflLately;
    @BindView(R.id.ll_lately)
    LinearLayout llLately;
    @BindView(R.id.tfl_hot_search)
    TagFlowLayout tflHotSearch;
    @BindView(R.id.ll_hot_search)
    LinearLayout llHotSearch;
    @BindView(R.id.tfl_are_searching)
    TagFlowLayout tflAreSearching;
    @BindView(R.id.ll_are_searching)
    LinearLayout llAreSearching;
    @BindView(R.id.rv_seach)
    RecyclerView rvSeach;
    //网络请求api
    private ServerApi api;
    //data
    private List<ScenicRegion> scenicRegionList;
    private List<ScenicSpot> scenicSpotList;
    //本地数据库表集合
    private List<SeachContent> seachContentList;
    //本地数据库表
    private SeachContent seachContent;
    //适配器
    private RecyclerViewAdapter rAdapter;
    //dao工具类
    private DaoManger daoManger;
    //布局加载
    private LayoutInflater inflater;
    private String[] mVals = new String[]
            {"Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView", "TextView", "Helloworld",
                    "Android", "Weclome Hello", "Button Text", "TextView"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_layout);
        ButterKnife.bind(this);
        initView();
        initValidata();
        initListener();
    }

    @Override
    public void initView() {
    }


    @Override
    public void initValidata() {
        //初始化布局加载管理
        inflater = LayoutInflater.from(getApplicationContext());
        //创建线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局纵向显示
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        //设置分割线(无)
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvSeach.addItemDecoration(dividerItemDecoration);
        //设置管理布局
        rvSeach.setLayoutManager(layoutManager);
        //创建适配器
        rAdapter = new RecyclerViewAdapter(this, 4);

        daoManger = DaoManger.getInstance();
        seachContentList =  daoManger.getsDaoSession().getSeachContentDao().loadAll();

        if (seachContentList.size() == 0) {
            llLately.setVisibility(View.GONE);
        } else {
            llLately.setVisibility(View.VISIBLE);
            //设置数据
            tflLately.setAdapter(new TagAdapter<SeachContent>(seachContentList) {
                @Override
                public View getView(FlowLayout parent, int position, SeachContent seachContent) {
                    TextView textView = (TextView) inflater.inflate(R.layout.text_seach_layout, parent, false);
                    textView.setText(seachContent.getContent());
                    return textView;
                }
            });
        }

        //创建旅拍热搜集合
        List<String> hotList = new ArrayList<>();
        hotList.add("跟团游");
        hotList.add("周边游");
        hotList.add("一日游");
        hotList.add("自由行");
        hotList.add("浪漫之旅");
        //设置数据
        tflHotSearch.setAdapter(new TagAdapter<String>(hotList) {
            @Override
            public View getView(FlowLayout parent, int position, String object) {
                TextView textView = (TextView) inflater.inflate(R.layout.text_seach_layout, parent, false);
                textView.setText(object);
                return textView;
            }
        });

        //创建旅拍热搜集合
        List<String> areList = new ArrayList<>();
        areList.add("南宁-青秀山");
        areList.add("南宁-大明山");
        areList.add("南宁动物园");
        areList.add("南宁-伊岭岩");
        areList.add("南宁海底世界");
        areList.add("龙虎山自然保护区");
        areList.add("花花大世界");
        areList.add("大龙湖景区");
        //设置数据
        tflAreSearching.setAdapter(new TagAdapter<String>(areList) {
            @Override
            public View getView(FlowLayout parent, int position, String str) {
                TextView textView = (TextView) inflater.inflate(R.layout.text_seach_layout, parent, false);
                textView.setText(str);
                return textView;
            }
        });

    }

    @Override
    public void initListener() {
        //实时监控
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //在编辑框改变前调用
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //在编辑框改变之时调用

            }

            @Override
            public void afterTextChanged(Editable s) {
                //在编辑框改变之后调用
                if (s.length() > 0) {
                    ivClear.setVisibility(View.VISIBLE);
                    //网络请求
                    api = RetrofitManger.getInstance().getRetrofit(RequestURL.ip_port).create(ServerApi.class);
                    Map<String, Object> map = new HashMap<>();
                    map.put("pStr", s.toString());
                    Call<ResponseBody> sCall = api.getASync("searchArea", map);
                    sCall.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                String message = response.body().string();
                                JSONObject json = new JSONObject(message);
                                if (json.getString(RequestURL.RESULT).equals("S")) {
                                    scenicRegionList = RetrofitManger.getInstance().getGson().fromJson(json.getString(RequestURL.ONE_DATA),
                                            new TypeToken<List<ScenicRegion>>(){}.getType());
                                    scenicSpotList = RetrofitManger.getInstance().getGson().fromJson(json.getString(RequestURL.TWO_DATA),
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
                } else {
                    ivClear.setVisibility(View.GONE);
                    rvSeach.setVisibility(View.GONE);
                    if (seachContentList.size() > 0) {
                        llLately.setVisibility(View.VISIBLE);
                    }
                    llHotSearch.setVisibility(View.VISIBLE);
                    llAreSearching.setVisibility(View.VISIBLE);
                }
            }
        });

        //搜索按钮监听接口
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                //点击搜索的时候隐藏软键盘
                hideKeyboard(etSearch);
                // 在这里写搜索的操作,一般都是网络请求数据
                //获取数据
                String seach = etSearch.getText().toString();
                SeachContent seachContent = new SeachContent(null, "url", seach);
                try {
                    daoManger.getsDaoSession().getSeachContentDao().insert(seachContent);
                } catch (Exception e) {
                    AppUtils.getToast("添加失败");
                }
                return true;
            }
            return false;
        });

        tflLately.setOnTagClickListener((view, position, parent) -> {
            AppUtils.getToast(mVals[position]);
            return true;
        });


        tflLately.setOnSelectListener(selectPosSet -> {
        });
    }

    @Override
    public void bindData() {
        llLately.setVisibility(View.GONE);
        llHotSearch.setVisibility(View.GONE);
        llAreSearching.setVisibility(View.GONE);
        rvSeach.setVisibility(View.VISIBLE);
        //设置数据
        rAdapter.setScenicSpotList(scenicSpotList);
        //设置适配器
        rvSeach.setAdapter(rAdapter);
    }

    @OnClick({R.id.iv_clear, R.id.tv_delete, R.id.iv_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_clear:
                //清除数据
                etSearch.setText("");
                rvSeach.setVisibility(View.GONE);
                llLately.setVisibility(View.VISIBLE);
                llHotSearch.setVisibility(View.VISIBLE);
                llAreSearching.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_delete:
                //取消关闭页面
                finish();
                break;
            case R.id.iv_delete:
                //点击清除搜索历史
                daoManger.getsDaoSession().getSeachContentDao().deleteAll();
                llLately.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 隐藏软键盘
     * @param view :一般为EditText
     */
    public void hideKeyboard(View view) {
        InputMethodManager manager = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}
