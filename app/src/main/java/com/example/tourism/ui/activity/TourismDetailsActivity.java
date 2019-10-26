package com.example.tourism.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.tourism.R;
import com.example.tourism.adapter.VPagerFragmentAdapter;
import com.example.tourism.application.InitApp;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.common.DefineView;
import com.example.tourism.common.RequestURL;
import com.example.tourism.ui.fragment.details.EvaluateFragment;
import com.example.tourism.ui.fragment.details.PictureFragment;
import com.example.tourism.ui.fragment.details.TourismRealFragment;
import com.example.tourism.utils.AppUtils;
import com.example.tourism.utils.StatusBarUtil;
import com.example.tourism.widget.ChildAutoViewPager;
import com.example.tourism.widget.MyScrollView;
import com.example.tourism.widget.ViewBundle;
import com.youth.banner.Banner;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 商品详情类
 * Name:laodai
 * Time:2019.10.23
 */
public class TourismDetailsActivity extends AppCompatActivity implements DefineView {
    //图片轮播
    @BindView(R.id.banner)
    Banner banner;
    //标题内容
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.ty_smbol)
    TextView tySmbol;
    //价格
    @BindView(R.id.ty_price)
    TextView tyPrice;
    //人数
    @BindView(R.id.ty_everyone)
    TextView tyEveryone;
    //特惠说明
    @BindView(R.id.fl_red_envelopes)
    FrameLayout flRedEnvelopes;
    //店铺红包
    @BindView(R.id.fl_shop_red_envelopes)
    FrameLayout flShopRedEnvelopes;
    //说明
    @BindView(R.id.fl_explain)
    FrameLayout flExplain;
    //日期选择 RecyclerView
    @BindView(R.id.rv_date)
    RecyclerView rvDate;
    //更多日期选项
    @BindView(R.id.tv_more_dates)
    TextView tvMoreDates;
    //参考交通
    @BindView(R.id.tv_traffic)
    TextView tvTraffic;
    //行程天数
    @BindView(R.id.tv_trip)
    TextView tvTrip;
    //出发到达
    @BindView(R.id.tv_arrive)
    TextView tvArrive;
    //住宿标准
    @BindView(R.id.tv_stay)
    TextView tvStay;
    //占位浮动栏
    @BindView(R.id.tv_show)
    TextView tvShow;
    //底部ViewPager
    @BindView(R.id.buttom_child_viewPager)
    ChildAutoViewPager buttomChildViewPager;
    //图文详情
    @BindView(R.id.tv_info_imagetext)
    TextView tvInfoImagetext;
    //产品实拍
    @BindView(R.id.tv_info_product)
    TextView tvInfoProduct;
    //评价详情
    @BindView(R.id.tv_info_evaluate)
    TextView tvInfoEvaluate;
    //游标
    @BindView(R.id.iv_corsor)
    ImageView ivCorsor;
    //浮动栏
    @BindView(R.id.layout_classify)
    LinearLayout layoutClassify;
    //滚动ScrollView
    @BindView(R.id.my_scrollview)
    MyScrollView myScrollview;
    //状态栏
    @BindView(R.id.status_view)
    View statusView;
    //顶部toobar部分
    @BindView(R.id.imageView_one)
    ImageView imageViewOne;
    @BindView(R.id.imageView_two)
    ImageView imageViewTwo;
    @BindView(R.id.imageView_three)
    ImageView imageViewThree;
    @BindView(R.id.back_left_image)
    ImageView backLeftImage;
    @BindView(R.id.details_title_text)
    TextView detailsTitleText;
    @BindView(R.id.shopping_chart_image)
    ImageView shoppingChartImage;
    @BindView(R.id.more_image)
    ImageView moreImage;
    @BindView(R.id.details_toolbar)
    ConstraintLayout detailsToolbar;
    //底部toobar部分
    @BindView(R.id.customer_service_line)
    LinearLayout customerServiceLine;
    @BindView(R.id.collection_image)
    ImageView collectionImage;
    @BindView(R.id.collection_line)
    LinearLayout collectionLine;
    @BindView(R.id.btn_shapping_chart)
    Button btnShappingChart;
    @BindView(R.id.btn_reserve)
    Button btnReserve;
    @BindView(R.id.iv_back_top)
    ImageView ivBackTop;
    //保存顶部状态栏的高度
    private int statusHeight;
    //保存顶部标题栏的高度
    private int toolbarHeight;
    //保存轮播图的高度
    private int bHeight;
    //保存筛选栏的高度
    private int classifyHeight;
    // bmpw: 表示游标的宽度，mCurrentIndex: 表示当前所在的页面
    private int bmpw = 0;
    private int mCurrentIndex = 0;
    private int fixLeftMargin;
    private LinearLayout.LayoutParams params;
    //底部ViewPager设置
    private List<Fragment> mDatas;
    private VPagerFragmentAdapter bAdapter;
    private PictureFragment pictureFragment;
    private TourismRealFragment tourismRealFragment;
    private EvaluateFragment evaluateFragment;
    // 记录底部ViewPager距离顶部的高度
    private int vpagerTopDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tourism_details_layout);
        ButterKnife.bind(this);
        initView();
        initValidata();
        initValidata();
        bindData();
        initImg();
    }

    /**
     * 获取一个月内的日期和时间
     */
    private void getDate() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        System.out.println("年份: "+year);
        int month = calendar.get(Calendar.MONTH) + 1;
        System.out.println("月份: "+month);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println("月: "+day);
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        String[] array = new String[]{"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        System.out.println(year+"年"+month+"月"+day+"日 "+array[week-1]);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initValidata() {
        //设置状态栏透明
        StatusBarUtil.setTransparentForWindow(this);
        //设置状态栏高度
        statusHeight = this.getResources().getDimensionPixelSize(this.getResources().getIdentifier("status_bar_height", "dimen", "android"));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusHeight);
        statusView.setLayoutParams(params);
        statusView.setBackgroundResource(R.color.color_blue);
        statusView.getBackground().mutate().setAlpha(0);
        detailsToolbar.getBackground().mutate().setAlpha(0);
        //设置标题栏隐藏
        detailsTitleText.setVisibility(TextView.GONE);
        imageViewOne.setBackgroundResource(R.drawable.select_bar_translucent);
        imageViewTwo.setBackgroundResource(R.drawable.select_bar_translucent);
        imageViewThree.setBackgroundResource(R.drawable.select_bar_translucent);

        //设置RecyclerView
        //设置线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置Data横向显示
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        //将布局管理设置到控件中
        rvDate.setLayoutManager(layoutManager);
        //创建适配器对象

        //获取轮播图的高度
        //获取顶部标题栏的高度
        detailsToolbar.post(() -> toolbarHeight = detailsToolbar.getHeight() + statusHeight);
        Log.d("initView", "initView: " + statusHeight);
        toolbarHeight = 89;
        bHeight = 500;
        //浮动栏初始化时隐藏
        layoutClassify.setVisibility(View.INVISIBLE);
        //获取浮动栏控件的高度
        layoutClassify.post(() -> classifyHeight = layoutClassify.getHeight());
        //底部ViewPager
        if (mDatas == null) mDatas = new ArrayList<>();
        pictureFragment = PictureFragment.newInstance(new ViewBundle(buttomChildViewPager));
        tourismRealFragment = TourismRealFragment.newInstance(new ViewBundle(buttomChildViewPager));
        evaluateFragment = EvaluateFragment.newInstance(new ViewBundle(buttomChildViewPager));
        mDatas.add(pictureFragment);
        mDatas.add(tourismRealFragment);
        mDatas.add(evaluateFragment);
        //设置ViewPager适配器
        bAdapter = new VPagerFragmentAdapter(getSupportFragmentManager(), mDatas);
        buttomChildViewPager.setAdapter(bAdapter);
        //缓存
        buttomChildViewPager.setOffscreenPageLimit(mDatas.size());
        //侦听器
        buttomChildViewPager.addOnPageChangeListener(new ButtomPageChangeListener());
        //设置ViewPager
        //设置滚动监听
        myScrollview.setOnScrollListener(new MyScrollView.OnScrollListener() {
            @Override
            public void onScrollView(int l, int t, int oldl, int oldt) {
                //设置status，toobar颜色透明渐变
                float detalis = t > bHeight ? bHeight : (t > 30 ? t : 0);
                int alpha = (int) (detalis / bHeight * 255);
                setUpdateActionBar(alpha);
                if (alpha < 200) {
                    imageViewOne.setBackgroundResource(R.drawable.select_bar_translucent);
                    imageViewTwo.setBackgroundResource(R.drawable.select_bar_translucent);
                    imageViewThree.setBackgroundResource(R.drawable.select_bar_translucent);
                } else {
                    imageViewOne.setBackgroundResource(R.drawable.select_bar_transparent);
                    imageViewTwo.setBackgroundResource(R.drawable.select_bar_transparent);
                    imageViewThree.setBackgroundResource(R.drawable.select_bar_transparent);
                }
                //获取高度
                vpagerTopDistance = buttomChildViewPager.getTop() - classifyHeight - statusView.getHeight()
                        - detailsToolbar.getHeight();
                //设置浮动栏距离顶部的高度
                //设置浮动栏
                int translation = Math.max(t, vpagerTopDistance);
                layoutClassify.setTranslationY(translation);
                layoutClassify.setVisibility(View.VISIBLE);
                //设置返回顶部
                if (t >= vpagerTopDistance) {
                    ivBackTop.setVisibility(View.VISIBLE);
                } else {
                    ivBackTop.setVisibility(View.GONE);
                }
            }
        });
        myScrollview.smoothScrollTo(0, 0);

        //获取景区详情图片
        api = RetrofitManger.getInstance().getRetrofit(RequestURL.ip_port).create(ServerApi.class);
        Call<ResponseBody> picCall = api.getNAsync("");
        picCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

        //获取景区详情数据
        api = RetrofitManger.getInstance().getRetrofit(RequestURL.ip_port).create(ServerApi.class);
        Map<String, Object> map = new HashMap<>();
        Call<ResponseBody> dataCall = api.getASync("", map);
        dataCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    private ServerApi api;

    @Override
    public void initListener() {

    }

    @Override
    public void bindData() {

    }

    /**
     * 设置状态栏和标题栏颜色渐变
     * @param alpha
     * @throws Exception
     */
    private void setActionBar(int alpha) throws Exception {
        if (statusView != null && detailsToolbar == null) {
            throw new Exception("状态栏和标题栏为空！");
        }
        statusView.getBackground().mutate().setAlpha(alpha);
        detailsToolbar.getBackground().mutate().setAlpha(alpha);
    }

    /**
     * 实时更新状态栏标题栏颜色渐变
     *
     * @param alpha
     */
    private void setUpdateActionBar(int alpha) {
        try { //捕获异常
            setActionBar(alpha);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.tv_info_imagetext, R.id.tv_info_product, R.id.tv_info_evaluate, R.id.btn_shapping_chart,
            R.id.btn_reserve, R.id.iv_back_top})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_info_imagetext:
                buttomChildViewPager.setCurrentItem(0);
                break;
            case R.id.tv_info_product:
                buttomChildViewPager.setCurrentItem(1);
                break;
            case R.id.tv_info_evaluate:
                buttomChildViewPager.setCurrentItem(2);
                break;
            case R.id.btn_shapping_chart:
                AppUtils.getToast("加入购物车成功！");
                break;
            case R.id.btn_reserve:
                AppUtils.getToast("立即购买！");
                break;
            case R.id.iv_back_top:
                //返回顶部
                AppUtils.getToast("点击可返回顶部！");
                break;
        }
    }

    /**
     * 内部类实现底部ViewPager监听
     */
    private class ButtomPageChangeListener implements ViewPager.OnPageChangeListener {

        /**
         * 滑动的过程
         * @param position
         * @param positionOffset
         * @param positionOffsetPixels
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // 滑动过程
            if (mCurrentIndex == 0 && position == 0) {
                params.leftMargin = (int) (mCurrentIndex * bmpw + positionOffset * bmpw)
                                        +fixLeftMargin;
            } else if (mCurrentIndex == 1 && position == 0) {
                params.leftMargin = (int) (mCurrentIndex * bmpw + (positionOffset - 1) * bmpw)
                                        +fixLeftMargin;
            } else if (mCurrentIndex == 1 && position == 1) {
                params.leftMargin = (int) (mCurrentIndex * bmpw + positionOffset * bmpw)
                                        +fixLeftMargin;
            } else if (mCurrentIndex == 2 && position == 1) {
                params.leftMargin = (int) (mCurrentIndex * bmpw + (positionOffset -1 ) * bmpw)
                                        +fixLeftMargin;
            }
            ivCorsor.setLayoutParams(params);
            //重置当前高度
            buttomChildViewPager.resetHeight(position);
        }

        @Override
        public void onPageSelected(int position) {
            setChangeTv(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    /**
     * 初始化底部指示器imageView
     */
    private void initImg() {
        Display disPlay = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        disPlay.getMetrics(outMetrics);
        int mScreen1_4 = outMetrics.widthPixels / 4;
        bmpw = outMetrics.widthPixels / 3;
        fixLeftMargin = (bmpw - mScreen1_4) / 2;
        ViewGroup.LayoutParams layoutParams = ivCorsor.getLayoutParams();
        layoutParams.width = mScreen1_4;
        ivCorsor.setLayoutParams(layoutParams);
        /**
         * 设置左侧固定距离
         */
        params = (LinearLayout.LayoutParams) ivCorsor.getLayoutParams();
        params.leftMargin = fixLeftMargin;
        ivCorsor.setLayoutParams(layoutParams);
    }

    /**
     * 改变游动条颜色
     * @param position
     */
    private void setChangeTv(int position) {
        tvInfoImagetext.setTextColor(Color.parseColor("#666666"));
        tvInfoProduct.setTextColor(Color.parseColor("#666666"));
        tvInfoEvaluate.setTextColor(Color.parseColor("#666666"));
        switch (position) {
            case 0:
                tvInfoImagetext.setTextColor(Color.parseColor("#FF7198"));
                break;
            case 1:
                tvInfoProduct.setTextColor(Color.parseColor("#FF7198"));
                break;
            case 2:
                tvInfoEvaluate.setTextColor(Color.parseColor("#FF7198"));
                break;
        }
        mCurrentIndex = position;
    }

}
