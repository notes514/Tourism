package com.example.tourism.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.tourism.common.DefineView;
import com.example.tourism.ui.activity.PersonalHolidayproblem;
import com.example.tourism.ui.activity.PersonalMyCollection;
import com.example.tourism.ui.activity.PersonalSubscriptions;
import com.example.tourism.ui.activity.PersonalTalk;
import com.example.tourism.ui.activity.PersonalhomepageActivity;
import com.example.tourism.ui.fragment.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static android.widget.Toast.LENGTH_LONG;

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
    @BindView(R.id.user_message)
    ImageView userMessage;
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
    @BindView(R.id.prl_view)
    PullRefreshLayout pullRefreshLayout;
    @BindView(R.id.btn_mycollection)
    FrameLayout btnMycollection;
    @BindView(R.id.btn_holidayprbolem)
    FrameLayout btnHolidayPrbolem;
    @BindView(R.id.btn_mysubscriptions)
    FrameLayout btnMysubscriptions;


    private Unbinder unbinder;

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

        initValidata();
        initValidata();
        bindData();
        return root;
    }

    public void show() {
        Toast.makeText(getContext(), "刷新成功", LENGTH_LONG).show();
    }

    @OnClick({R.id.user_message, R.id.btn_mycollection, R.id.btn_holidayprbolem,R.id.btn_mysubscriptions,R.id.user_homepage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_mycollection:
                Intent intent = new Intent(PersonerFragment.this.getActivity(), PersonalMyCollection.class);
                startActivity(intent);
                break;
            case R.id.user_message:
                Intent btn_homepage = new Intent(PersonerFragment.this.getActivity(), PersonalTalk.class);
                startActivity(btn_homepage);
                break;
            case R.id.btn_holidayprbolem:
                Intent btn_holiday_prbolem = new Intent(PersonerFragment.this.getActivity(), PersonalHolidayproblem.class);
                startActivity(btn_holiday_prbolem);
                break;
            case R.id.btn_mysubscriptions:
                Intent btn_my_subscriptions = new Intent(PersonerFragment.this.getActivity(), PersonalSubscriptions.class);
                startActivity(btn_my_subscriptions);
                break;
            case R.id.user_homepage:
                Intent btn_user_homepage = new Intent(PersonerFragment.this.getActivity(), PersonalhomepageActivity.class);
                startActivity(btn_user_homepage);
                break;

        }
    }

    @Override
    public void initView() {

    }

    @Override
    public void initValidata() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void bindData() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind(); //解绑
    }

}