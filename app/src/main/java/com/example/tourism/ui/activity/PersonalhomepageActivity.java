package com.example.tourism.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.tourism.R;
import com.example.tourism.application.InitApp;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.User;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.ui.fragment.PersonalContentFragment;
import com.example.tourism.ui.fragment.PersonalFriendFragment;
import com.example.tourism.ui.fragment.PersonalVideoFragment;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.tourism.MainActivity.user;

public class PersonalhomepageActivity extends BaseActivity {
    @BindView(R.id.user_return_arrow)
    ImageView userReturnArrow;
    @BindView(R.id.personal_user_name)
    TextView personalUserName;
    private ImageView hBack;
    private ImageView hHead;

    private ViewPager viewPager;
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private PersonalFragmentAdapter mFragmentAdapter;

    //Tab显示内容TextView
    private TextView contentTv, videoTv, friendTv;
    //Tab的那个引导线
    private ImageView tablineIv;

    //三个Fragment页面
    private PersonalContentFragment contentFg;
    private PersonalVideoFragment videoFg;
    private PersonalFriendFragment friendFg;

    //ViewPager的当前选中页
    private int currentIndex;

    //屏幕的宽度
    private int screenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_homepage);
        ButterKnife.bind(this);

        hBack = (ImageView) findViewById(R.id.h_back);
        hHead = (ImageView) findViewById(R.id.h_head);
        //设置背景磨砂效果
        Glide.with(this).load(R.drawable.personal_blue_bj)
                .bitmapTransform(new BlurTransformation(this, 25), new CenterCrop(this))
                .into(hBack);
        //设置圆形图像
        Glide.with(this).load(R.drawable.ic_launcher_background)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(hHead);

        findById();
        init();
        initTabLineWidth();

        if (user == null) {

        }else if (user !=null){
            //网络请求
            ServerApi api = RetrofitManger.getInstance().getRetrofit(RequestURL.ip_port).create(ServerApi.class);
            Map map = new HashMap();
            map.put("userId", "1");
            Call<ResponseBody> personalData = api.getASync("queryByUserInformation", map);
            personalData.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String message = response.body().string();
                        JSONObject json = new JSONObject(message);
                        if (json.getString("RESULT").equals("S")) {
                            User user = RetrofitManger.getInstance().getGson().fromJson(json.getString("ONE_DETAIL"), User.class);
                            Log.d(InitApp.TAG, "UserAccountName: " + user.getUserAccountName());
                            personalUserName.setText(user.getUserAccountName());
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

    }
    private void findById() {
        contentTv = (TextView) this.findViewById(R.id.contentTv);
        videoTv = (TextView) this.findViewById(R.id.videoTv);
        friendTv = (TextView) this.findViewById(R.id.friendTv);
        tablineIv = (ImageView) this.findViewById(R.id.iv_tabline);
        viewPager = (ViewPager) this.findViewById(R.id.viewpager);
    }

    private void init() {
        contentFg = new PersonalContentFragment();
        videoFg = new PersonalVideoFragment();
        friendFg = new PersonalFriendFragment();
        //将三个页面添加到容器里面
        mFragmentList.add(contentFg);
        mFragmentList.add(videoFg);
        mFragmentList.add(friendFg);

        //重写一个FragmentAdapter继承FragmentPagerAdapter，需要FragmentManager和存放页面的容器过去
        mFragmentAdapter = new PersonalFragmentAdapter(this.getSupportFragmentManager(), mFragmentList);
        //ViewPager绑定监听器
        viewPager.setAdapter(mFragmentAdapter);
        //ViewPager设置默认当前的项
        viewPager.setCurrentItem(0);
        //ViewPager设置监听器，需要重写onPageScrollStateChanged，onPageScrolled，onPageSelected三个方法
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int state) {
                Log.i("PageScroll：", "onPageScrollStateChanged" + ":" + state);
            }

            @Override
            public void onPageScrolled(int position, float offset,
                                       int offsetPixels) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tablineIv.getLayoutParams();
                Log.i("mOffset", "offset:" + offset + ",position:" + position);
                /**
                 * 利用currentIndex(当前所在页面)和position(下一个页面)以及offset来
                 * 设置mTabLineIv的左边距 滑动场景：
                 * 记3个页面,
                 * 从左到右分别为0,1,2
                 * 0->1; 1->2; 2->1; 1->0
                 */
                if (currentIndex == 0 && position == 0)// 0->1
                {
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3));

                } else if (currentIndex == 1 && position == 0) // 1->0
                {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3));

                } else if (currentIndex == 1 && position == 1) // 1->2
                {
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3));
                } else if (currentIndex == 2 && position == 1) // 2->1
                {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3));
                }
                tablineIv.setLayoutParams(lp);
            }

            /**
             * 将当前选择的页面的标题设置字体颜色为蓝色
             */
            @Override
            public void onPageSelected(int position) {
                Log.i("PageScroll：", "onPageSelected" + ":" + position);
                resetTextView();
                switch (position) {
                    case 0:
                        contentTv.setTextColor(Color.BLUE);
                        break;
                    case 1:
                        videoTv.setTextColor(Color.BLUE);
                        break;
                    case 2:
                        friendTv.setTextColor(Color.BLUE);
                        break;
                }
                currentIndex = position;
            }
        });

    }

    private void initTabLineWidth() {
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dpMetrics);
        screenWidth = dpMetrics.widthPixels;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tablineIv.getLayoutParams();
        lp.width = screenWidth / 3;
        tablineIv.setLayoutParams(lp);
    }

    /**
     * 重置颜色
     */
    private void resetTextView() {
        contentTv.setTextColor(Color.BLACK);
        videoTv.setTextColor(Color.BLACK);
        friendTv.setTextColor(Color.BLACK);
    }

    @OnClick(R.id.user_return_arrow)
    public void onViewClicked() {
        finish();
    }
}
