package com.example.tourism;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.common.DefineView;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.User;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.ui.fragment.DestinationFragment;
import com.example.tourism.ui.fragment.HomeFragment;
import com.example.tourism.ui.fragment.SignIn2Fragment;
import com.example.tourism.utils.AppUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements DefineView {
    public static User user;

    private static final int BRTMAP_PERMISSION_CODE = 100;
    private static final List<String> permissionsNeedCheck;
    static {
        permissionsNeedCheck = new LinkedList<>();
        permissionsNeedCheck.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        permissionsNeedCheck.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionsNeedCheck.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_browse, R.id.navigation_destination, R.id.navigation_order, R.id.navigation_personer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        checkPermission();
        temp();
        SharedPreferences sharedPreferences = getSharedPreferences("Userdata",Context.MODE_PRIVATE);

        if (sharedPreferences != null){
            sharedPreferences= getSharedPreferences("Userdata", Context.MODE_PRIVATE);
            String userId = sharedPreferences.getString("userAccountName","");
            String userId1 = sharedPreferences.getString("password","");
            login(userId,userId1);
            Log.d("wwww",userId);
        }
    }

    private void login(String userId,String userId1) {

//        RetrofitManger retrofitManger = new RetrofitManger();
//        serverApi = retrofitManger.getRetrofit(InitApp.ip_port).create(ServerApi.class);
        ServerApi serverApi = RetrofitManger.getInstance().getRetrofit(RequestURL.ip_port).create(ServerApi.class);
        //向上造型
        Map<String, Object> map = new HashMap<>();
        map.put("userAccountName",userId);
        map.put("password",userId1);
        Call<ResponseBody> requestBodyCall = serverApi.postASync("login", map);
        requestBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    //暂存response.body().string()流数据，防止流数据关闭
                    String result = response.body().string();
                    JSONObject json = new JSONObject(result);
                    user = RetrofitManger.getInstance().getGson().fromJson(json.getString("ONE_DETAIL"),User.class);
                    if (user != null) RequestURL.vUserId = user.getUserId()+"";
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //请求失败是回调
                AppUtils.getToast(t.toString());
            }
        });

    }


    public void temp(){
        Intent intent=getIntent();
        String str=intent.getStringExtra("location");

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

    /**
     * 判断当前系统的SDK版本是否大于23
     */
    private void checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//判断当前系统的SDK版本是否大于23
            List<String> permissionNeedRequest = new LinkedList<>();
            for (String permssion: permissionsNeedCheck) {
                if(ActivityCompat.checkSelfPermission(this, permssion) != PackageManager.PERMISSION_GRANTED) {
                    permissionNeedRequest.add(permssion);
                }
            }
            if (permissionNeedRequest.isEmpty()) {
                return;
            }

            ActivityCompat.requestPermissions(this, permissionNeedRequest.toArray(new String[0]), BRTMAP_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在requestPermissions时传入
            case BRTMAP_PERMISSION_CODE:
                boolean isAllGrant = true;
                for (int grantResult: grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        isAllGrant = false;
                        break;
                    }
                }
                if (!isAllGrant) {
                    Toast.makeText(getApplicationContext(), "获取位置权限失败，请手动前往设置开启", Toast.LENGTH_SHORT).show();
                }

                break;
            default:
                break;
        }
    }
}
