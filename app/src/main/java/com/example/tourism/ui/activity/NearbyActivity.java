package com.example.tourism.ui.activity;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.brtbeacon.sdk.BRTBeacon;
import com.brtbeacon.sdk.BRTBeaconManager;
import com.brtbeacon.sdk.BRTThrowable;
import com.brtbeacon.sdk.callback.BRTBeaconManagerListener;
import com.example.tourism.R;
import com.example.tourism.application.InitApp;
import com.example.tourism.common.RequestURL;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NearbyActivity extends BaseActivity {

    @BindView(R.id.toolBar)
    Toolbar toolbar;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.view1)
    TextView view1;
    @BindView(R.id.view2)
    TextView view2;
    @BindView(R.id.view5)
    TextView view5;

    private Unbinder unbinder;
    private List<BRTBeacon> brtBeacons = new ArrayList<>();
    private List<String> macAddress = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);
        unbinder = ButterKnife.bind(this, this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //setSupportActionBar(toolbar);
        //setActionBar(toolbar);
        macAddress.add("EF:D5:C7:C8:14:FB");
        macAddress.add("FE:CB:23:2A:B6:4B");
        macAddress.add("F9:50:C1:76:EC:5F");
        //显示返回按钮 禁用标题
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        checkBluetoothValid();
        startScan();
        test();
        initMap();
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
                        view1.setAlpha((float) calculateDistance(result.get(i).getMeasuredPower(),result.get(i).getRssi()));
                    }else if (result.get(i).macAddress.equals("FE:CB:23:2A:B6:4B")){
                        //Toast.makeText(NearbyActivity.this,"你目前正在展览区2",Toast.LENGTH_SHORT).show();
                        view2.setAlpha((float) calculateDistance(result.get(i).getMeasuredPower(),result.get(i).getRssi()));
                    }else if (result.get(i).macAddress.equals("EF:D5:C7:C8:14:FB")){
                        //Toast.makeText(NearbyActivity.this,"你目前正在展览区5",Toast.LENGTH_SHORT).show();
                        view5.setAlpha((float) calculateDistance(result.get(i).getMeasuredPower(),result.get(i).getRssi()));
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
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NearbyActivity.this,ShowExhibitsDetialActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initMap(){
        ImageLoader.getInstance().displayImage(RequestURL.ip_images+"images/map.jpg", imageView, InitApp.getOptions());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        InitApp.getInstance().getBRTBeaconManager().stopRanging();
    }
}
