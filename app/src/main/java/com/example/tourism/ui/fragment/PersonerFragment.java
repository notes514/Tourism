package com.example.tourism.ui.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.tourism.R;
import com.example.tourism.application.InitApp;
import com.example.tourism.common.DefineView;
import com.example.tourism.common.RequestURL;
import com.example.tourism.ui.activity.AllOrderActivity;
import com.example.tourism.ui.activity.PersonalCouponActivity;
import com.example.tourism.ui.activity.PersonalDataActivity;
import com.example.tourism.ui.activity.PersonalHolidayproblem;
import com.example.tourism.ui.activity.PersonalMyCollection;
import com.example.tourism.ui.activity.PersonalOpenmemberActivity;
import com.example.tourism.ui.activity.PersonalSubscriptions;
import com.example.tourism.ui.activity.PersonalSystemSetupActivity;
import com.example.tourism.ui.activity.PersonalhomepageActivity;
import com.example.tourism.ui.activity.SignInActivity;
import com.example.tourism.ui.fragment.base.BaseFragment;
import com.example.tourism.utils.AppUtils;
import com.example.tourism.widget.CircleImageView;
import com.example.tourism.widget.CustomToolbar;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;
import com.scwang.smart.refresh.layout.listener.OnMultiListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static com.example.tourism.MainActivity.user;

/**
 * 个人中心
 */
public class PersonerFragment extends BaseFragment implements DefineView {
    @BindView(R.id.h_back)
    ImageView hBack;
    @BindView(R.id.user_line)
    ImageView userLine;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_val)
    TextView userVal;
    @BindView(R.id.user_follow)
    TextView userFollow;
    @BindView(R.id.user_fans)
    TextView userFans;
    @BindView(R.id.user_homepage)
    TextView userHomeage;
    @BindView(R.id.user_arrow)
    ImageView userarrow;
    @BindView(R.id.btn_mycollection)
    FrameLayout btnMycollection;
    @BindView(R.id.btn_holidayprbolem)
    FrameLayout btnHolidayPrbolem;
    @BindView(R.id.btn_mysubscriptions)
    FrameLayout btnMysubscriptions;
    @BindView(R.id.fl_all_order)
    FrameLayout flAllOrder;
    @BindView(R.id.btn_coupon)
    FrameLayout btnCoupon;
    @BindView(R.id.btn_member)
    FrameLayout btnMember;
    @BindView(R.id.user_follow_num)
    TextView userFollowNum;
    @BindView(R.id.user_fans_num)
    TextView userFansNum;
    @BindView(R.id.custom_toolbar)
    CustomToolbar customToolbar;
    @BindView(R.id.status_view)
    View statusView;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.prl_view)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.re)
    TextView re;
    @BindView(R.id.h_head)
    CircleImageView hHead;
    @BindView(R.id.btn_set_up)
    FrameLayout btnSetUp;
    @BindView(R.id.btn_trip)
    FrameLayout btnTrip;
    @BindView(R.id.ll_daifukuan)
    LinearLayout llDaifukuan;
    @BindView(R.id.ll_daixiaofei)
    LinearLayout llDaixiaofei;
    @BindView(R.id.ll_daipingjia)
    LinearLayout llDaipingjia;
    @BindView(R.id.ll_tuikuanzhon)
    LinearLayout llTuikuanzhon;

    private Unbinder unbinder;
    public static final int Request_Code = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_personer, container, false);
        unbinder = ButterKnife.bind(this, root);
        initValidata();
        initListener();
        bindData();
        initRefreshLayout();
        display();
        return root;
    }

    @OnClick({R.id.btn_mycollection, R.id.btn_holidayprbolem, R.id.btn_mysubscriptions, R.id.user_homepage,
            R.id.user_name, R.id.btn_coupon, R.id.btn_member, R.id.user_follow, R.id.user_fans, R.id.fl_all_order,
            R.id.re, R.id.btn_set_up, R.id.btn_trip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_trip:

                break;
            case R.id.btn_mycollection:
                openActivity(PersonalMyCollection.class);
                break;
            case R.id.btn_holidayprbolem:
                openActivity(PersonalHolidayproblem.class);
                break;
            case R.id.btn_mysubscriptions:
                openActivity(PersonalSubscriptions.class);
                break;
            case R.id.btn_member:
                openActivity(PersonalOpenmemberActivity.class);
                break;
            case R.id.btn_coupon:
                openActivity(PersonalCouponActivity.class);
                break;
            case R.id.btn_set_up: //点击跳转系统设置
                openActivity(PersonalSystemSetupActivity.class);
                break;
            case R.id.user_homepage:
                openActivity(PersonalhomepageActivity.class);
                break;
            case R.id.user_follow:
                openActivity(PersonalhomepageActivity.class);
                break;
            case R.id.user_fans:
                openActivity(PersonalhomepageActivity.class);
                break;
            case R.id.user_name:
                if (user == null) {
                    Intent i = new Intent(PersonerFragment.this.getActivity(), SignInActivity.class);
                    startActivityForResult(i, Request_Code);
                }
                break;
            case R.id.fl_all_order: //点击全部订单
                openActivity(AllOrderActivity.class);
                break;
            case R.id.re://点击登录
                openActivity(SignInActivity.class);
                break;

        }
    }

    @Override
    public void initView() {
        if (user == null) {
            userHomeage.setVisibility(View.GONE);
            userarrow.setVisibility(View.GONE);
            userLine.setVisibility(View.GONE);
            userVal.setVisibility(View.GONE);
            userName.setVisibility(View.GONE);
            userFans.setVisibility(View.GONE);
            userFollow.setVisibility(View.GONE);
            userFansNum.setVisibility(View.GONE);
            userFollowNum.setVisibility(View.GONE);
            re.setVisibility(View.VISIBLE);
            hHead.setImageResource(R.drawable.personal_head_travel);
        }
        if (user != null) {
            ImageLoader.getInstance().displayImage(RequestURL.ip_images + user.getUserPicUrl(), hHead, InitApp.getOptions());
            userName.setText(user.getUserAccountName());
            String tellphone = user.getUserTellphone();
            String replace = tellphone.substring(3, 7);
            String newreplace = tellphone.replace(replace, "****");
            userVal.setText(newreplace);
            userHomeage.setVisibility(View.VISIBLE);
            userarrow.setVisibility(View.VISIBLE);
            userLine.setVisibility(View.VISIBLE);
            userVal.setVisibility(View.VISIBLE);
            userName.setVisibility(View.VISIBLE);
            userFans.setVisibility(View.VISIBLE);
            userFollow.setVisibility(View.VISIBLE);
            userFansNum.setVisibility(View.VISIBLE);
            userFollowNum.setVisibility(View.VISIBLE);
            re.setVisibility(View.GONE);
        }
    }

    @Override
    public void initValidata() {
        customToolbar.setMyTitle("");
        //设置背景磨砂效果
        Glide.with(getContext()).load(R.drawable.personal_background)
                .bitmapTransform(new BlurTransformation(getContext(), 25), new CenterCrop(getContext()))
                .into(hBack);
        //设置圆形图像
        Glide.with(getContext()).load(R.drawable.personal_head_travel)
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .into(hHead);
        //获取状态栏高度
        int statusHeight = AppUtils.getStatusBarHeight(getActivity());
        //设置状态栏高度
        AppUtils.setStatusBarColor(statusView, statusHeight, R.color.color_blue);

        //设置透明度为0
        statusView.getBackground().mutate().setAlpha(0);
        customToolbar.getBackground().mutate().setAlpha(0);
        int bHeight = 400;
        //设置滚动监听
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollView.setOnScrollChangeListener((View.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                //设置status，toobar颜色透明渐变
                float detalis = scrollY > bHeight ? bHeight : (scrollY > 30 ? scrollY : 0);
                int alpha = (int) (detalis / bHeight * 255);
                AppUtils.setUpdateActionBar(statusView, customToolbar, alpha);
            });
        }

    }

    @Override
    public void initListener() {
        customToolbar.setOnRightButtonClickLister(() -> openActivity(PersonalDataActivity.class));

        llDaifukuan.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AllOrderActivity.class);
            intent.putExtra("index", 1);
            startActivity(intent);
        });
        llDaixiaofei.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AllOrderActivity.class);
            intent.putExtra("index", 2);
            startActivity(intent);
        });
        llDaipingjia.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AllOrderActivity.class);
            intent.putExtra("index", 3);
            startActivity(intent);
        });
        llTuikuanzhon.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AllOrderActivity.class);
            intent.putExtra("index", 4);
            startActivity(intent);
        });
    }

    @Override
    public void bindData() {

    }

    private void display() {
        if (user != null) {
            userName.setText(user.getUserAccountName());
            userVal.setText(user.getUserTellphone());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind(); //解绑
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case Request_Code:
//                User user = (User) data.getSerializableExtra("data");
//                Log.d("@@@@",data.getStringExtra("data"));
                userName.setText(user.getUserAccountName());
                break;
            default:
                Log.d("@@@@", data.getStringExtra("无数据"));
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
        Log.d("22222", "onStart: ");
        if (user != null) {
            //获取用户id
            RequestURL.vUserId = user.getUserId() + "";
            ImageLoader.getInstance().displayImage(RequestURL.ip_images + user.getUserPicUrl(), hHead, InitApp.getOptions());
            userName.setText(user.getUserAccountName());
            userVal.setText(user.getUserTellphone());
            userHomeage.setVisibility(View.VISIBLE);
            userarrow.setVisibility(View.VISIBLE);
            userLine.setVisibility(View.VISIBLE);
            userVal.setVisibility(View.VISIBLE);
            userName.setVisibility(View.VISIBLE);
            userFans.setVisibility(View.VISIBLE);
            userFollow.setVisibility(View.VISIBLE);
            userFansNum.setVisibility(View.VISIBLE);
            userFollowNum.setVisibility(View.VISIBLE);
            re.setVisibility(View.GONE);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
        Log.d("123456", "onResume: ");
    }

    //刷新头
    private void initRefreshLayout() {
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        //设置 Footer 为 球脉冲 样式
        smartRefreshLayout.setRefreshFooter(new BallPulseFooter(getContext()).setSpinnerStyle(SpinnerStyle.Translate)
                .setAnimatingColor(0xFF1DA8FE));
        smartRefreshLayout.setOnMultiListener(new OnMultiListener() {
            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                Log.d(InitApp.TAG, "offset: " + offset + "headerHeight: " + headerHeight + "maxDragHeight: " + maxDragHeight);
            }

            @Override
            public void onHeaderReleased(RefreshHeader header, int headerHeight, int maxDragHeight) {

            }

            @Override
            public void onHeaderStartAnimator(RefreshHeader header, int headerHeight, int maxDragHeight) {

            }

            @Override
            public void onHeaderFinish(RefreshHeader header, boolean success) {
                Log.d("@@@", "刷新完成！");
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
                //add();
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(1000);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(3000);
            }

            @Override
            public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {

            }
        });
    }

}