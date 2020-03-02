package com.example.tourism.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;

import com.example.tourism.R;
import com.example.tourism.adapter.RecyclerViewAdapter;
import com.example.tourism.application.InitApp;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.common.DefineView;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.ScenicSpot;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.utils.AppUtils;
import com.example.tourism.utils.StatusBarUtil;
import com.example.tourism.widget.CustomToolbar;
import com.example.tourism.widget.NestedExpandaleListView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 浪漫之旅二级页
 * Name:laodai
 * Time:2019.10.09
 */
public class RomanticJourneyActivity extends BaseActivity implements DefineView {
    @BindView(R.id.iv_romantic_pic)
    ImageView ivRomanticPic;
    @BindView(R.id.tv_position)
    TextView tvPosition;
    @BindView(R.id.tv_show)
    TextView tvShow;
    @BindView(R.id.elv_roantic)
    NestedExpandaleListView elvRoantic;
    @BindView(R.id.tb_romantic)
    TabLayout tbRomantic;
    @BindView(R.id.iv_expand_more)
    ImageView ivExpandMore;
    @BindView(R.id.layout_classify)
    LinearLayout layoutClassify;
    @BindView(R.id.nsv_scollview)
    NestedScrollView nsvScollview;
    @BindView(R.id.status_view)
    View statusView;
    @BindView(R.id.ct_toolbar)
    CustomToolbar ctToolbar;
    @BindView(R.id.iv_clear)
    ImageView ivClear;
    @BindView(R.id.tv_type1)
    TextView tvType1;
    @BindView(R.id.tv_type2)
    TextView tvType2;
    @BindView(R.id.tv_type3)
    TextView tvType3;
    @BindView(R.id.tv_type4)
    TextView tvType4;
    @BindView(R.id.tv_type5)
    TextView tvType5;
    @BindView(R.id.tv_type6)
    TextView tvType6;
    @BindView(R.id.view_translucent)
    View viewTranslucent;
    @BindView(R.id.ll_more)
    LinearLayout llMore;
    @BindView(R.id.ll_toolbar)
    LinearLayout llToolbar;
    @BindView(R.id.iv_left_back)
    ImageView ivLeftBack;
    @BindView(R.id.loading_line)
    ConstraintLayout loadingLine;
    @BindView(R.id.empty_line)
    LinearLayout emptyLine;
    @BindView(R.id.error_line)
    LinearLayout errorLine;
    //状态栏高度
    private int statusHeight;
    //滚动高度
    private int sHeight = 400;
    //记录底部RecyclerView距离顶部的高度
    private int rvTopDistance;
    //适配器
    private RecyclerViewAdapter rAdapter;
    //网络请求api
    private ServerApi api;
    //
    private List<RomanticBean> romanticBeanList;
    //景区集合
    private List<ScenicSpot> scenicSpotList;
    private List<List<ScenicSpot>> listList = new ArrayList<>();
    //适配器
    private ELVAdapter elvAdapter;
    private boolean mFlag = true;
    //获取tablayout距离顶部的高度
    private int height;
    //设置距离
    private int translation;
    //网络加载请求
    private boolean serverFlag = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_romantic_journey_layout);
        ButterKnife.bind(this);
        initView();
        initValidata();
        initListener();
    }

    @Override
    public void initView() {
    }

    @SuppressLint("NewApi")
    @Override
    public void initValidata() {
        //资源文件加载显示
        nsvScollview.setVisibility(View.GONE);
        llToolbar.setVisibility(View.GONE);
        loadingLine.setVisibility(View.VISIBLE);
        emptyLine.setVisibility(View.GONE);
        errorLine.setVisibility(View.GONE);

        String[] mTitles = {"星辰大海", "壕买买买", "明星结婚地", "激情刺激", "网红流行", "文艺小资"};
        for (int i = 0; i < mTitles.length; i++) {
            tbRomantic.addTab(tbRomantic.newTab().setText(mTitles[i]));
        }

        //设置状态栏透明
        StatusBarUtil.setTransparentForWindow(this);
        //获取状态栏高度
        statusHeight = AppUtils.getStatusBarHeight(this);
        //设置状态栏高度
        AppUtils.setStatusBarColor(statusView, statusHeight, R.color.color_blue);
        //设置透明度为0
        statusView.getBackground().mutate().setAlpha(0);
        ctToolbar.getBackground().mutate().setAlpha(0);

        //设置浮动栏隐藏
        layoutClassify.setVisibility(View.GONE);

        height = layoutClassify.getHeight() + statusView.getHeight() + ctToolbar.getHeight();
        //设置浮动栏距离顶部的高度
        layoutClassify.setTranslationY(321);
        layoutClassify.setVisibility(View.VISIBLE);

        //设置浮动栏滚动监听
        nsvScollview.setOnScrollChangeListener((View.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) ->
        {
            //设置status，toobar颜色透明渐变
            float detalis = scrollY > sHeight ? sHeight : (scrollY > 30 ? scrollY : 0);
            int alpha = (int) (detalis / sHeight * 255);
            AppUtils.setUpdateActionBar(statusView, ctToolbar, alpha);

            //获取高度
            rvTopDistance = elvRoantic.getTop() - layoutClassify.getHeight() - statusView.getHeight()
                    - ctToolbar.getHeight();
            height = layoutClassify.getHeight() + statusView.getHeight() + ctToolbar.getHeight();
            Log.d(InitApp.TAG, "height: " + height);

            //设置浮动栏距离顶部的高度
            translation = Math.max(scrollY, height);
            layoutClassify.setTranslationY(translation);
            layoutClassify.setVisibility(View.VISIBLE);
        });

        //创建适配器对象
        elvAdapter = new ELVAdapter(this);
        elvRoantic.setGroupIndicator(null);

        romanticBeanList = new ArrayList<>();
        romanticBeanList.add(new RomanticBean("星辰大海", "漫天星辰下，面朝大海，说出爱的誓言"));
        romanticBeanList.add(new RomanticBean("壕买买买", "在这里买买买，把旅费赚回来"));
        romanticBeanList.add(new RomanticBean("明星结婚地", "这里有你爱豆踩点的痕迹"));

        for (RomanticBean rom : romanticBeanList) {
            if (serverFlag) queryAllScenicSpot(rom.getTitle());
        }

    }

    private void queryAllScenicSpot(String type) {
        api = RetrofitManger.getInstance().getRetrofit(RequestURL.ip_port).create(ServerApi.class);
        //网络请求加载推荐
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        Call<ResponseBody> rCall = api.getASync("queryRomanticTypeScenicSpot", map);
        rCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String message = response.body().string();
                    JSONObject json = new JSONObject(message);
                    Log.d(InitApp.TAG, "message: " + message);
                    scenicSpotList = RetrofitManger.getInstance().getGson().fromJson(json.getString(RequestURL.ONE_DATA),
                            new TypeToken<List<ScenicSpot>>() {
                            }.getType());
                    listList.add(scenicSpotList);
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
    public void initListener() {
        ctToolbar.setOnLeftButtonClickLister(() -> finish());
        elvRoantic.setOnGroupClickListener((parent, v, groupPosition, id) -> false);

        //NestedExpandaleListView子view点击监听
        elvRoantic.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            AppUtils.getToast("你点击了第" + groupPosition + "个父和第" + childPosition + "个子");
            return true;
        });

        ivExpandMore.setOnClickListener(v1 ->
        {
            //返回顶部
            nsvScollview.fling(0);
            nsvScollview.smoothScrollTo(0, height + 218);
            if (mFlag) {
                mFlag = false;
                llMore.setVisibility(View.VISIBLE);
            } else {
                mFlag = true;
                llMore.setVisibility(View.GONE);
            }

        });

        viewTranslucent.setOnClickListener(v12 -> {
            mFlag = true;
            llMore.setVisibility(View.GONE);
        });

        //设置TabLayout点击监听
        tbRomantic.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void bindData() {
        if (listList.size() >= 3) {
            //显示数据
            loadingLine.setVisibility(View.GONE);
            nsvScollview.setVisibility(View.VISIBLE);
            llToolbar.setVisibility(View.VISIBLE);
            emptyLine.setVisibility(View.GONE);
            errorLine.setVisibility(View.GONE);

            elvAdapter.setRomanticBeanList(romanticBeanList);
            elvAdapter.setListList(listList);
            elvRoantic.setAdapter(elvAdapter);

            int groupCount = elvRoantic.getCount();
            for (int i = 0; i < groupCount; i++) {
                elvRoantic.expandGroup(i);
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    class ELVAdapter extends BaseExpandableListAdapter {
        private List<RomanticBean> romanticBeanList;
        private List<List<ScenicSpot>> listList;
        private Context context;
        private LayoutInflater inflater;

        public ELVAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        public void setRomanticBeanList(List<RomanticBean> romanticBeanList) {
            this.romanticBeanList = romanticBeanList;
        }

        public void setListList(List<List<ScenicSpot>> listList) {
            this.listList = listList;
        }

        @Override
        public int getGroupCount() {
            return romanticBeanList == null ? 0 : romanticBeanList.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return listList == null ? 0 : listList.get(groupPosition).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return romanticBeanList.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return listList.get(groupPosition).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            GroupViewHolder groupViewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_romantic_journey_layout, parent, false);
                groupViewHolder = new GroupViewHolder();
                groupViewHolder.view = (View) convertView.findViewById(R.id.view);
                groupViewHolder.tvTheme = (TextView) convertView.findViewById(R.id.tv_theme);
                groupViewHolder.tvExplain = (TextView) convertView.findViewById(R.id.tv_explain);
                groupViewHolder.tvMore = (TextView) convertView.findViewById(R.id.tv_more);
                convertView.setTag(groupViewHolder);
            } else {
                groupViewHolder = (GroupViewHolder) convertView.getTag();
            }

            if (groupPosition == 0) groupViewHolder.view.setVisibility(View.GONE);
            else groupViewHolder.view.setVisibility(View.VISIBLE);

            groupViewHolder.tvTheme.setText(romanticBeanList.get(groupPosition).title);
            groupViewHolder.tvExplain.setText(romanticBeanList.get(groupPosition).content);
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChildViewHolder childViewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_seach_details_layout, parent, false);
                childViewHolder = new ChildViewHolder();
                childViewHolder.ivTourismPic = (ImageView) convertView.findViewById(R.id.iv_tourism_pic);
                childViewHolder.tvTravelMode = (TextView) convertView.findViewById(R.id.tv_travel_mode);
                childViewHolder.tvEndLand = (TextView) convertView.findViewById(R.id.tv_end_land);
                childViewHolder.tvTourismContent = (TextView) convertView.findViewById(R.id.tv_tourism_content);
                childViewHolder.tvScore = (TextView) convertView.findViewById(R.id.tv_score);
                childViewHolder.tvNumber = (TextView) convertView.findViewById(R.id.tv_number);
                childViewHolder.tvExplain = (TextView) convertView.findViewById(R.id.tv_explain);
                childViewHolder.tvTourCity = (TextView) convertView.findViewById(R.id.tv_tour_city);
                childViewHolder.tvPrice = (TextView) convertView.findViewById(R.id.tv_price);
                convertView.setTag(childViewHolder);

            } else {
                childViewHolder = (ChildViewHolder) convertView.getTag();
            }

            ScenicSpot scenicSpot = listList.get(groupPosition).get(childPosition);

            ImageLoader.getInstance().displayImage(RequestURL.ip_images + scenicSpot.getScenicSpotPicUrl(),
                    childViewHolder.ivTourismPic, InitApp.getOptions());
            if (scenicSpot.getTravelMode() == 0) {
                childViewHolder.tvTravelMode.setText("国内游");
            } else if (scenicSpot.getTravelMode() == 1) {
                childViewHolder.tvTravelMode.setText("出境游");
            } else if (scenicSpot.getTravelMode() == 2) {
                childViewHolder.tvTravelMode.setText("自由行");
            } else if (scenicSpot.getTravelMode() == 3) {
                childViewHolder.tvTravelMode.setText("跟团游");
            } else if (scenicSpot.getTravelMode() == 4) {
                childViewHolder.tvTravelMode.setText("主题游");
            } else if (scenicSpot.getTravelMode() == 5) {
                childViewHolder.tvTravelMode.setText("周边游");
            } else if (scenicSpot.getTravelMode() == 6) {
                childViewHolder.tvTravelMode.setText("一日游");
            } else if (scenicSpot.getTravelMode() == 7) {
                childViewHolder.tvTravelMode.setText("定制游");
            }
            childViewHolder.tvEndLand.setText(scenicSpot.getStartLand() + "出发");
            childViewHolder.tvTourismContent.setText(scenicSpot.getScenicSpotTheme());
            childViewHolder.tvScore.setText(scenicSpot.getScenicSpotScore() + "分");
            childViewHolder.tvNumber.setText(scenicSpot.getScenicSpotSold() + "人已出行");
            childViewHolder.tvTourCity.setText(scenicSpot.getTourCity());
            childViewHolder.tvExplain.setText(scenicSpot.getScenicSpotExplain());
            int price = (int) (scenicSpot.getScenicSpotPrice() + 0);
            childViewHolder.tvPrice.setText(price + "");
            childViewHolder.tvPrice.setOnClickListener(v -> {
                Intent intent = new Intent(context, TourismDetailsActivity.class);
                intent.putExtra("scenicSpotId", scenicSpot.getScenicSpotId());
                context.startActivity(intent);
            });

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }

        class GroupViewHolder {
            private TextView tvTheme;
            private TextView tvExplain;
            private TextView tvMore;
            private View view;
        }

        class ChildViewHolder {
            private ImageView ivTourismPic;
            private TextView tvTravelMode;
            private TextView tvEndLand;
            private TextView tvTourismContent;
            private TextView tvScore;
            private TextView tvNumber;
            private TextView tvExplain;
            private TextView tvTourCity;
            private TextView tvPrice;
        }

    }

    class RomanticBean {
        private String title;
        private String content;

        public RomanticBean(String title, String content) {
            this.title = title;
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

}
