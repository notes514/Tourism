package com.example.tourism.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
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
import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalDataActivity extends BaseActivity implements DefineView {

    @BindView(R.id.user_head_portrait)
    CircleImageView userHeadPortrait;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.tv_eamil)
    TextView tvEamil;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.btn_personal_Logout)
    Button btnPersonalLogout;
    @BindView(R.id.custom_toolbar)
    CustomToolbar customToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        ButterKnife.bind(this);
        initListener();


        //设置圆形图像
        Glide.with(this).load(R.drawable.personal_blue_bj)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(userHeadPortrait);


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
                        tvUsername.setText(user.getUserAccountName());
                        tvEamil.setText(user.getEmail());
                        tvAddress.setText(user.getNagaiAddr());
                        tvSex.setText(user.getUserSex());
                        tvTel.setText(user.getUserTellphone());

                        ImageLoader.getInstance().displayImage(RequestURL.ip_images + user.getUserPicUrl(), userHeadPortrait, InitApp.getOptions());
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

    public void show() {
        finish();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initValidata() {

    }

    @Override
    public void initListener() {
        customToolbar.setOnLeftButtonClickLister(() -> show());
    }

    @Override
    public void bindData() {

    }
}
