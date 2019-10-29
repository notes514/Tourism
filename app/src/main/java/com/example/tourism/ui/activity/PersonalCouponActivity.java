package com.example.tourism.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.tourism.R;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.ui.fragment.PersonalEdenvelopesFragment;
import com.example.tourism.ui.fragment.PersonalFailureFragment;
import com.example.tourism.ui.fragment.PersonalVolumereductionFragment;
import com.example.tourism.ui.fragment.PersonalVoucherFragment;
import com.example.tourism.ui.fragment.PersonerFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonalCouponActivity extends BaseActivity {
    @BindView(R.id.btn_return_arrow)
    ImageView btnReturnArrow;
    private ViewPager viewPager;
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private PersonalFragmentAdapter mFragmentAdapter;

    //Tab显示内容TextView
    private TextView edenvelopesTv, volumereductionTv, voucherTv, failureTv;
    //Tab的那个引导线
    private ImageView tablineIv;

    //四个Fragment页面
    private PersonalEdenvelopesFragment edenvelopesFg;
    private PersonalVolumereductionFragment volumereductionFg;
    private PersonalVoucherFragment voucherFg;
    private PersonalFailureFragment failureFg;

    //ViewPager的当前选中页
    private int currentIndex;

    //屏幕的宽度
    private int screenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_coupon);
        ButterKnife.bind(this);
        findById();
        init();
        initTabLineWidth();
    }

    private void findById() {
        edenvelopesTv = (TextView) this.findViewById(R.id.edenvelopesTv);
        volumereductionTv = (TextView) this.findViewById(R.id.volumereductionTv);
        voucherTv = (TextView) this.findViewById(R.id.voucherTv);
        failureTv = (TextView) this.findViewById(R.id.failureTv);

        tablineIv = (ImageView) this.findViewById(R.id.iv_tabline);
        viewPager = (ViewPager) this.findViewById(R.id.viewpager);
    }

    private void init() {
        edenvelopesFg = new PersonalEdenvelopesFragment();
        volumereductionFg = new PersonalVolumereductionFragment();
        voucherFg = new PersonalVoucherFragment();
        failureFg = new PersonalFailureFragment();
        //将四个页面添加到容器里面
        mFragmentList.add(edenvelopesFg);
        mFragmentList.add(volumereductionFg);
        mFragmentList.add(voucherFg);
        mFragmentList.add(failureFg);

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
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 4) + currentIndex
                            * (screenWidth / 4));

                } else if (currentIndex == 1 && position == 0) // 1->0
                {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / 4) + currentIndex
                            * (screenWidth / 4));

                } else if (currentIndex == 1 && position == 1) // 1->2
                {
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 4) + currentIndex
                            * (screenWidth / 4));
                } else if (currentIndex == 2 && position == 1) // 2->1
                {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / 4) + currentIndex
                            * (screenWidth / 4));
                }else if (currentIndex == 3 && position == 2) // 2->1
                {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / 4) + currentIndex
                            * (screenWidth / 4));
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
                        edenvelopesTv.setTextColor(Color.BLUE);
                        break;
                    case 1:
                        volumereductionTv.setTextColor(Color.BLUE);
                        break;
                    case 2:
                        voucherTv.setTextColor(Color.BLUE);
                        break;
                    case 3:
                        failureTv.setTextColor(Color.BLUE);
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
        lp.width = screenWidth / 4;
        tablineIv.setLayoutParams(lp);
    }

    /**
     * 重置颜色
     */
    private void resetTextView() {
        edenvelopesTv.setTextColor(Color.BLACK);
        volumereductionTv.setTextColor(Color.BLACK);
        voucherTv.setTextColor(Color.BLACK);
        failureTv.setTextColor(Color.BLACK);
    }

    @OnClick(R.id.btn_return_arrow)
    public void onViewClicked() {
        finish();
    }
}
