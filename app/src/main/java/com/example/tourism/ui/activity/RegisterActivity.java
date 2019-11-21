package com.example.tourism.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tourism.R;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.common.RequestURL;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.utils.AppUtils;
import com.example.tourism.widget.CustomToolbar;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity {
    private static final String TAG = "MainActivity1";
    @BindView(R.id.custom_toolbar)
    CustomToolbar customToolbar;
    @BindView(R.id.user_name)
    EditText username;
    @BindView(R.id.tell_phone)
    EditText tellphone;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.btn_register)
    Button register;
    //网络请求api
    private ServerApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        customToolbar.setOnLeftButtonClickLister(() -> finish());

        api = RetrofitManger.getInstance().getRetrofit(RequestURL.ip_port).create(ServerApi.class);

        register.setOnClickListener(view -> {
            if (username.getText().length() < 1) {
                AppUtils.getToast("请输入用户名");
                return;
            }
            if (tellphone.getText().length() < 1) {
                AppUtils.getToast("手机号");
                return;
            }
            if (password.getText().length() < 1) {
                AppUtils.getToast("请输入密码");
                return;
            }
            if (password.getText().length() < 6) {
                AppUtils.getToast("密码不能小于6位数");
                return;
            }
            String regex1 = "1[0-9]{10}";
            boolean flag = tellphone.getText().toString().matches(regex1);
            if (flag) {
                //向上造型
                Map<String, Object> map = new HashMap<>();
                map.put("userAccountName", username.getText().toString());
                Call<ResponseBody> post = api.postASync("qeuryUserAccountName", map);
                post.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        //请求成功时回调
                        try {
                            String message = response.body().string();
                            JSONObject json = new JSONObject(message);
                            if (json.getString(RequestURL.RESULT).equals("S")) {
                                register();
                            } else {
                                AppUtils.getToast(json.getString(RequestURL.TIPS));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        //请求失败时回调
                        Log.d(TAG, "Throwable: " + t.toString());
                    }
                });

            } else {
                AppUtils.getToast("请输入正确的手机号码");
            }

        });
    }

    private void register() {
        //向上造型
        Map<String, Object> map = new HashMap<>();
        map.put("userAccountName", username.getText().toString());
        map.put("userTellphone",tellphone.getText().toString());
        map.put("password",password.getText().toString());
        map.put("userPicUrl", "images/title.png");

        Call<ResponseBody> post = api.postASync("register", map);
        post.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //请求成功时回调
                try {
                    String message = response.body().string();
                    JSONObject json = new JSONObject(message);
                    if (json.getString(RequestURL.RESULT).equals("S")) {
                        AppUtils.getToast(json.getString(RequestURL.TIPS));
                        finish();
                    } else {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //请求失败时回调
                Log.d(TAG, "Throwable: " + t.toString());
            }
        });
    }

}
