package com.example.tourism.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tourism.R;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.User;
import com.example.tourism.utils.AppUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.RESULT;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity1";
    private String phone_number;
    @BindView(R.id.tv_return2)
    TextView textView;
    @BindView(R.id.user_name)
    EditText username;
    @BindView(R.id.tell_phone)
    EditText tellphone;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.btn_register)
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,SignInActivity.class);
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().equals("")){
                    AppUtils.getToast("请输入用户名");
                }else if(tellphone.getText().equals("")){
                    AppUtils.getToast("请输入您的电话号码");
                }else if (password.getText().length() < 6){
                    phone_number=tellphone.getText().toString().trim();
                    String num="[1][358]\\d{9}";
                    if(phone_number.matches(num)) { //matches():字符串是否在给定的正则表达式匹配
                        AppUtils.getToast("");
                    }else {
                        AppUtils.getToast("请输入正确的手机号码");
                    }
                }if (password.getText().length() < 6){
                    AppUtils.getToast("请输入密码且不能少于6位数");
                }else {
                    register();
                }

            }
        });

    }

    private void register(){
//        //创建Retrofit对象
//        Retrofit retrofit = new Retrofit.Builder()
//                //设置数据解析器
//                .addConverterFactory(GsonConverterFactory.create())
//                //设置网络请求的Url地址
//                .baseUrl(RequestURL.ip_port)
//                .build();
//        //创建网络请求接口实例
//        serverApi = retrofit.create(ServerApi.class);
        ServerApi serverApi = RetrofitManger.getInstance().getRetrofit(RequestURL.ip_port).create(ServerApi.class);

        //向上造型
        Map<String, Object> map = new HashMap<>();
        map.put("userAccountName", username.getText().toString());
        map.put("userTellphone",tellphone.getText().toString());
        map.put("password",password.getText().toString());

        Call<ResponseBody> post = serverApi.postASync("register", map);
        post.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //请求成功时回调
                try {
                    String result = response.body().string();
//                        Intent intent = new Intent(RegisterActivity.this,SignInActivity.class);
//                        startActivity(intent);
                        Log.d(TAG, "result: " + result);
                        finish();
                } catch (IOException e) {
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
