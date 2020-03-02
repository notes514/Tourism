package com.example.tourism.ui.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.tourism.MainActivity;
import com.example.tourism.R;
import com.example.tourism.application.InitApp;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.User;
import com.example.tourism.ui.activity.RegisterActivity;
import com.example.tourism.ui.fragment.base.BaseFragment;
import com.example.tourism.utils.AppUtils;
import com.example.tourism.widget.LoadingDialog;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignIn2Fragment extends BaseFragment implements View.OnClickListener ,PlatformActionListener {
    @BindView(R.id.user_name)
    EditText user_name;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.signin)
    Button signin;
    @BindView(R.id.tv_register)
    TextView tv_register;
    @BindView(R.id.weibo_signin)
    ImageView weibo_signin;
    private View view;
    private Unbinder unbinder;
    //正在加载dialog
    private LoadingDialog loadingDialog;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Toast.makeText(getActivity(), "授权登陆成功", Toast.LENGTH_SHORT).show();
                    Platform platform = (Platform) msg.obj;
                    String userId = platform.getDb().getUserId();//获取用户账号
                    String userName = platform.getDb().getUserName();//获取用户名字
                    String userIcon = platform.getDb().getUserIcon();//获取用户头像
                    String userGender = platform.getDb().getUserGender(); //获取用户性别，m = 男, f = 女，如果没有设置性别,默认返回null
                    Toast.makeText(getActivity(), "用户名：" + userName + "  性别：" + userGender, Toast.LENGTH_SHORT).show();

                    //下面就可以利用获取的用户信息登录自己的服务器或者做自己想做的事啦!
                    //。。。
                    break;
                case 2:
                    Toast.makeText(getContext(), "授权登陆失败", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(getContext(), "授权登陆取消", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_sign_in2, container, false);
         unbinder = ButterKnife.bind(this,view);
         this.setTargetFragment(new PersonerFragment(),0);
         signin.setOnClickListener(view -> {
             if(user_name.getText().length() < 1){
                 AppUtils.getToast("请输入用户名");
             } else if(password.getText().length() < 1 ){
                 AppUtils.getToast("请输入密码");
             }else {
                 loadingDialog = new LoadingDialog(getContext(), "正在登录...");
                 loadingDialog.show();
                 login();
             }

         });

         tv_register.setOnClickListener(view -> {
             Intent intent = new Intent(getActivity(), RegisterActivity.class);
             startActivity(intent);
         });

            weibo_signin.setOnClickListener(view -> loginByWeibo());
         return view;
    }

    private void login() {
        ServerApi serverApi = RetrofitManger.getInstance().getRetrofit(RequestURL.ip_port).create(ServerApi.class);
        //向上造型
        Map<String, Object> map = new HashMap<>();
        map.put("userAccountName",user_name.getText().toString());
        map.put("password", password.getText().toString());
        Call<ResponseBody> requestBodyCall = serverApi.postASync("login", map);
        requestBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    //暂存response.body().string()流数据，防止流数据关闭
                    String result = response.body().string();
                    JSONObject json = new JSONObject(result);
                    if (json.getString("RESULT").equals("S")) {
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Userdata",Context.MODE_PRIVATE);
                        //步骤2： 实例化SharedPreferences.Editor对象
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        //步骤3：将获取过来的值放入文件
                        editor.putString("userAccountName", user_name.getText().toString());
                        editor.putString("password",password.getText().toString());
                        //步骤4：提交
                        editor.commit();
                        //加载关闭
                        loadingDialog.dismiss();
                        MainActivity.user = RetrofitManger.retrofitManger.getGson().fromJson(json.getString("ONE_DETAIL"),User.class);
                        getActivity().finish();
                        AppUtils.getToast(json.getString("TIPS"));
                    } else {
                        loadingDialog.dismiss();
                        AppUtils.getToast(json.getString("TIPS"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //请求失败是回调
                Log.d(InitApp.TAG, "Throwable: " + t.toString());
                loadingDialog.dismiss();
            }
        });

    }

    private void loginByWeibo(){
        Platform sinaWeibo = ShareSDK.getPlatform(SinaWeibo.NAME);
        sinaWeibo.setPlatformActionListener(this);
        sinaWeibo.SSOSetting(false);//true表示不使用SSO方式授权
        if (! sinaWeibo.isClientValid()){
            Toast.makeText(getActivity(),"新浪微博未安装,请先安装新浪微博", Toast.LENGTH_SHORT).show();
        }
        authorize(sinaWeibo);
    }

    /**
     * 授权
     */
    private void authorize(Platform platform){
        if (platform == null){
            return;
        }
        if (platform.isAuthValid()){  //如果授权就删除授权资料
            platform.removeAccount(true);
        }
        platform.showUser(null);  //授权并获取用户信息
    }

    /**
     * 授权成功的回调
     */

    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        Message message = Message.obtain();
        message.what = 1;
        message.obj = platform;
        mHandler.sendMessage(message);
    }

    /**
     * 授权错误的回调
     *
     * @param platform
     * @param i
     * @param throwable
     */

    public void onError(Platform platform, int i, Throwable throwable) {
        Message message = Message.obtain();
        message.what = 2;
        message.obj = platform;
        mHandler.sendMessage(message);
    }

    /**
     * 授权取消的回调
     *
     * @param platform
     * @param i
     */
    public void onCancel(Platform platform, int i) {
        Message message = Message.obtain();
        message.what = 3;
        message.obj = platform;
        mHandler.sendMessage(message);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind(); //解绑
    }

    @Override
    public void onClick(View view) {

    }

}
