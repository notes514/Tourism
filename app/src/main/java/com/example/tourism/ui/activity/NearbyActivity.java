package com.example.tourism.ui.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

import com.brtbeacon.sdk.BRTBeacon;
import com.brtbeacon.sdk.BRTBeaconManager;
import com.brtbeacon.sdk.BRTThrowable;
import com.brtbeacon.sdk.callback.BRTBeaconManagerListener;
import com.example.tourism.R;
import com.example.tourism.application.InitApp;
import com.example.tourism.ui.activity.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NearbyActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.toolBar)
    Toolbar toolbar;
    @BindView(R.id.notice)
    TextView notice;
    @BindView(R.id.exhibition_area_1)
    TextView exhibition_area_1;
    @BindView(R.id.exhibition_area_2)
    TextView exhibition_area_2;
    @BindView(R.id.exhibition_area_3)
    TextView exhibition_area_3;
    @BindView(R.id.exhibition_area_4)
    TextView exhibition_area_4;
    @BindView(R.id.exhibition_area_5)
    TextView exhibition_area_5;
    @BindView(R.id.exhibition_area_6)
    TextView exhibition_area_6;

    private List<BRTBeacon> brtBeacons = new ArrayList<>();
    private List<String> macAddress = new ArrayList<>();

    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);
        ButterKnife.bind(this);
        exhibition_area_1.setOnClickListener(this);
        exhibition_area_2.setOnClickListener(this);
        exhibition_area_3.setOnClickListener(this);
        exhibition_area_4.setOnClickListener(this);
        exhibition_area_5.setOnClickListener(this);
        exhibition_area_6.setOnClickListener(this);

        //跑马灯效果
        notice.setSelected(true);
        initToolbar();
        macAddress.add("EF:D5:C7:C8:14:FB");
        macAddress.add("FE:CB:23:2A:B6:4B");
        macAddress.add("F9:50:C1:76:EC:5F");
        checkBluetoothValid();
        startScan();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initToolbar(){
        //初始化工具栏
        toolbar.setTitle(getString(R.string.nearby_activity_title));
        toolbar.setTitleTextColor(getColor(R.color.color_white));
        toolbar.setNavigationOnClickListener(view -> finish());
        toolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            switch (id){
                case R.id.leaderboard:
                    showLeaderboard(0);
            }
            return false;
        });
    }

    /**
     * 检测手机蓝牙是否开启
     */
    private void checkBluetoothValid() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if(adapter == null) {
            AlertDialog dialog = new AlertDialog.Builder(this).setTitle("错误").setMessage("你的设备不具备蓝牙功能!").create();
            dialog.show();
            return;
        }
        if(!adapter.isEnabled()) {
            AlertDialog dialog = new AlertDialog.Builder(this).setTitle("提示")
                    .setMessage("蓝牙设备未打开,请开启此功能后重试!")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                            startActivityForResult(intent,1);
                        }
                    })
                    .create();
            dialog.show();
        }
    }

    /**
     * 计算与BRTBeacon之间的距离
     */
    private double calculateDistance(int txPower,int rssi){
        double distance = (double) rssi / (double) txPower;
        return distance;
    }

    /**
     *开始扫描周边Beacon
     */
    private void startScan(){
        InitApp.getInstance().getBRTBeaconManager().setBRTBeaconManagerListener(beaconManagerListener);
        InitApp.getInstance().getBRTBeaconManager().setPowerMode(BRTBeaconManager.POWER_MODE_LOW_POWER);
        InitApp.getInstance().getBRTBeaconManager().startRanging();
    }

    private BRTBeaconManagerListener beaconManagerListener = new BRTBeaconManagerListener() {
        @Override
        public void onUpdateBeacon(ArrayList<BRTBeacon> arrayList) {
            List<BRTBeacon> result = new ArrayList<>();
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).isBrightBeacon()){
                    result.add(arrayList.get(i));
                }
            }
            // Beacon信息更新
            if (result.size()==0){
                Log.d("message", "附近没有Beacon ~ ~ ~");
            }else {
//                Collections.sort(result, new Comparator<BRTBeacon>() {
//                    @Override
//                    public int compare(BRTBeacon brtBeacon, BRTBeacon t1) {
//                        return brtBeacon.getRssi()*(-1)-t1.getRssi()*(-1);
//                    }
//                });
//                tv_count.setText("附近有"+result.size()+"处旅游景点");
//                tv_distance.setText("最近距您"+calculateDistance(result.get(0).getMeasuredPower(),result.get(0).getRssi())+"m");
                Log.d("message", result.size()+"");
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i).macAddress.equals("F9:50:C1:76:EC:5F")){
//                        view.setHeight((int) calculateDistance(result.get(0).getMeasuredPower(),result.get(0).getRssi()));
                        //Toast.makeText(NearbyActivity.this,"你目前正在展览区1",Toast.LENGTH_SHORT).show();
                        exhibition_area_1.setAlpha((float) calculateDistance(result.get(i).getMeasuredPower(),result.get(i).getRssi()));
                    }else if (result.get(i).macAddress.equals("FE:CB:23:2A:B6:4B")){
                        //Toast.makeText(NearbyActivity.this,"你目前正在展览区2",Toast.LENGTH_SHORT).show();
                        exhibition_area_2.setAlpha((float) calculateDistance(result.get(i).getMeasuredPower(),result.get(i).getRssi()));
                    }else if (result.get(i).macAddress.equals("EF:D5:C7:C8:14:FB")){
                        //Toast.makeText(NearbyActivity.this,"你目前正在展览区5",Toast.LENGTH_SHORT).show();
                        exhibition_area_5.setAlpha((float) calculateDistance(result.get(i).getMeasuredPower(),result.get(i).getRssi()));
                    }
                }
            }
        }

        @Override
        public void onNewBeacon(BRTBeacon brtBeacon) {
            // 发现一个新的Beacon
        }

        @Override
        public void onGoneBeacon(BRTBeacon brtBeacon) {
            // 一个Beacon消失
        }

        @Override
        public void onError(BRTThrowable brtThrowable) {

        }
    };

    private void test(){
        exhibition_area_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NearbyActivity.this,ShowExhibitsDetialActivity.class);
                intent.putExtra("exhibitsId",1);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        InitApp.getInstance().getBRTBeaconManager().stopRanging();
    }

    @Override
    public void onClick(View view) {
        int id  = view.getId();
        switch (id){
            case R.id.exhibition_area_1:
                showLeaderboard(0);
                break;
            case R.id.exhibition_area_2:
                showLeaderboard(1);
                break;
            case R.id.exhibition_area_3:
                showLeaderboard(2);
                break;
            case R.id.exhibition_area_4:
                showLeaderboard(3);
                break;
            case R.id.exhibition_area_5:
                showLeaderboard(4);
                break;
            case R.id.exhibition_area_6:
                showLeaderboard(5);
                break;
            default:
                break;
        }
    }

    private void showLeaderboard(int page){
        Intent intent = new Intent(this,LeaderboardActivity.class);
        intent.putExtra("page",page);
        startActivity(intent);
    }
}
