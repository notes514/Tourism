package com.example.tourism.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tourism.R;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.common.DefineView;
import com.example.tourism.common.RequestURL;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.utils.AppUtils;
import com.example.tourism.widget.CustomToolbar;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.tourism.MainActivity.user;

public class PersonalChangePassword extends BaseActivity implements DefineView {

    @BindView(R.id.old_password)
    EditText oldPassword;
    @BindView(R.id.new_password)
    EditText newPassword;
    @BindView(R.id.second_new_password)
    EditText secondNewPassword;
    @BindView(R.id.btn_preservation)
    Button btnPreservation;
    @BindView(R.id.custom_toolbar)
    CustomToolbar customToolbar;

    private ServerApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_change_password);
        ButterKnife.bind(this);
        initListener();
        bindData();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initValidata() {

    }

    @Override
    public void initListener() {
        customToolbar.setOnLeftButtonClickLister(() ->finish());
    }

    @Override
    public void bindData() {

    }

    @OnClick({R.id.btn_preservation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_preservation:
                api = RetrofitManger.getInstance().getRetrofit(RequestURL.ip_port).create(ServerApi.class);
                Map<String, Object> map = new HashMap<>();
                map.put("userId", RequestURL.vUserId);
                map.put("oldPass", oldPassword.getText().toString());
                map.put("newPass", newPassword.getText().toString());
                if (!newPassword.getText().toString().equals(secondNewPassword.getText().toString())) {
                    AppUtils.getToast("第二次输入密码不正确！");
                } else {
                    Call<ResponseBody> call = api.getASync("updateUserPassWord", map);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            AppUtils.getToast("修改成功！!");
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                }
                user = null;
                RequestURL.vUserId = "";
                Intent intent = new Intent(PersonalChangePassword.this,SignInActivity.class);
                startActivity(intent);
                break;
        }
    }
}
