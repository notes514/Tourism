package com.example.tourism.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tourism.R;
import com.example.tourism.application.InitApp;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.common.DefineView;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.User;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.widget.CustomToolbar;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.tourism.MainActivity.user;

public class PersonalSystemSetupActivity extends BaseActivity implements DefineView {
    @BindView(R.id.img_userHeadPortrait)
    ImageView imgUserHeadPortrait;
    @BindView(R.id.custom_toolbar)
    CustomToolbar customToolbar;
    @BindView(R.id.tv_nikename)
    TextView tvNikename;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.btn_Lin)
    LinearLayout btnLin;
    @BindView(R.id.btn_Real_name)
    FrameLayout btnRealName;
    @BindView(R.id.btn_Accountsecurity)
    FrameLayout btnAccountsecurity;
    @BindView(R.id.btn_Accountcorrelation)
    FrameLayout btnAccountcorrelation;
    @BindView(R.id.btn_Paymentequipment)
    FrameLayout btnPaymentequipment;
    @BindView(R.id.btn_Logindevice)
    FrameLayout btnLogindevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_system_setup);
        ButterKnife.bind(this);
        initListener();
        //设置圆形图像
        Glide.with(this).load(R.drawable.personal_blue_bj)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(imgUserHeadPortrait);

        if (user == null) {
            btnAccountcorrelation.setVisibility(View.GONE);
            btnAccountsecurity.setVisibility(View.GONE);
            btnLogindevice.setVisibility(View.GONE);
            btnPaymentequipment.setVisibility(View.GONE);
            btnRealName.setVisibility(View.GONE);
            btnLin.setVisibility(View.GONE);
        } else if (user != null) {

            ServerApi api = RetrofitManger.getInstance().getRetrofit(RequestURL.ip_port).create(ServerApi.class);
            Map map = new HashMap();
            map.put("userId", RequestURL.vUserId);
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
                            tvNikename.setText(user.getUserAccountName());
                            tvTel.setText(user.getUserTellphone());

                            ImageLoader.getInstance().displayImage(RequestURL.ip_images + user.getUserPicUrl(), imgUserHeadPortrait, InitApp.getOptions());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
            btnAccountcorrelation.setVisibility(View.VISIBLE);
            btnAccountsecurity.setVisibility(View.VISIBLE);
            btnLogindevice.setVisibility(View.VISIBLE);
            btnPaymentequipment.setVisibility(View.VISIBLE);
            btnRealName.setVisibility(View.VISIBLE);
            btnLin.setVisibility(View.VISIBLE);
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
        customToolbar.setOnLeftButtonClickLister(() -> finish());
    }

    @Override
    public void bindData() {

    }

    @OnClick(R.id.btn_Lin)
    public void onViewClicked() {
        Intent intent = new Intent(PersonalSystemSetupActivity.this, PersonalDataActivity.class);
        startActivity(intent);
    }
}
