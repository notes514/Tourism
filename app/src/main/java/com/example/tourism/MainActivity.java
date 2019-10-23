package com.example.tourism;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.example.tourism.common.DefineView;
import com.example.tourism.entity.User;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.LinkedList;
import java.util.List;

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
