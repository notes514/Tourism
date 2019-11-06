package com.example.tourism.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.baoyz.widget.PullRefreshLayout;
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
import com.example.tourism.ui.activity.PersonalhomepageActivity;
import com.example.tourism.ui.activity.SignInActivity;
import com.example.tourism.ui.fragment.base.BaseFragment;
import com.example.tourism.utils.AppUtils;
import com.example.tourism.widget.CustomToolbar;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static android.widget.Toast.LENGTH_LONG;
import static com.example.tourism.MainActivity.user;

/**
 * 个人中心
 */
public class PersonerFragment extends BaseFragment implements DefineView {
    @BindView(R.id.h_back)
    ImageView hBack;
    @BindView(R.id.h_head)
    ImageView hHead;
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
    @BindView(R.id.prl_view)
    PullRefreshLayout pullRefreshLayout;
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

    private Unbinder unbinder;
    public static final int Request_Code = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_personer, container, false);
        unbinder = ButterKnife.bind(this, root);
        //设置背景磨砂效果
        Glide.with(getContext()).load(R.drawable.personal_background)
                .bitmapTransform(new BlurTransformation(getContext(), 25), new CenterCrop(getContext()))
                .into(hBack);
        //设置圆形图像
        Glide.with(getContext()).load(R.drawable.personal_head_travel)
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .into(hHead);

        pullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //修改数据的代码，最后记得填上此行代码
                        pullRefreshLayout.setRefreshing(false);
                        show();
                    }
                }, 2000);
            }
        });

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
        }
        initValidata();
        initListener();
        bindData();
        return root;
    }

    public void show() {
        Toast.makeText(getContext(), "刷新成功", LENGTH_LONG).show();
    }

    @OnClick({R.id.btn_mycollection, R.id.btn_holidayprbolem, R.id.btn_mysubscriptions, R.id.user_homepage, R.id.user_name
            , R.id.btn_coupon, R.id.btn_member, R.id.user_follow, R.id.user_fans, R.id.fl_all_order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_mycollection:
                Intent intent = new Intent(PersonerFragment.this.getActivity(), PersonalMyCollection.class);
                startActivity(intent);
                break;
            case R.id.btn_holidayprbolem:
                Intent btn_holiday_prbolem = new Intent(PersonerFragment.this.getActivity(), PersonalHolidayproblem.class);
                startActivity(btn_holiday_prbolem);
                break;
            case R.id.btn_mysubscriptions:
                Intent btn_my_subscriptions = new Intent(PersonerFragment.this.getActivity(), PersonalSubscriptions.class);
                startActivity(btn_my_subscriptions);
                break;
            case R.id.btn_member:
                Intent btn_member1 = new Intent(PersonerFragment.this.getActivity(), PersonalOpenmemberActivity.class);
                startActivity(btn_member1);
                break;
            case R.id.btn_coupon:
                Intent btn_coupon1 = new Intent(PersonerFragment.this.getActivity(), PersonalCouponActivity.class);
                startActivity(btn_coupon1);
                break;
            case R.id.user_homepage:
                Intent btn_user_homepage = new Intent(PersonerFragment.this.getActivity(), PersonalhomepageActivity.class);
                startActivity(btn_user_homepage);
                break;
            case R.id.user_follow:
                Intent user_follow1 = new Intent(PersonerFragment.this.getActivity(), PersonalhomepageActivity.class);
                startActivity(user_follow1);
                break;
            case R.id.user_fans:
                Intent user_homepage1 = new Intent(PersonerFragment.this.getActivity(), PersonalhomepageActivity.class);
                startActivity(user_homepage1);
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
        }
    }

    private void show1() {
        Intent btn_data = new Intent(PersonerFragment.this.getActivity(), PersonalDataActivity.class);
        startActivity(btn_data);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initValidata() {
        //获取状态栏高度
        int statusHeight = AppUtils.getStatusBarHeight(getActivity());
        //设置状态栏高度
        AppUtils.setStatusBarColor(statusView, statusHeight, R.color.color_blue);
    }

    @Override
    public void initListener() {
        customToolbar.setOnRightButtonClickLister(() -> show1());
    }

    @Override
    public void bindData() {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind(); //解绑
    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        switch (requestCode){
//            case Request_Code:
////                User user = (User) data.getSerializableExtra("data");
////                Log.d("@@@@",data.getStringExtra("data"));
//                userName.setText(user.getUserAccountName());
//                break;
//                default:
//                    Log.d("@@@@",data.getStringExtra("无数据"));
//                    break;
//        }
//    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("22222", "onStart: ");
        if (user != null) {
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
        }
    }


//    @Override
//    public void onResume() {
//        super.onResume();
//        Log.d("22222", "onResume: ");
//    }
}