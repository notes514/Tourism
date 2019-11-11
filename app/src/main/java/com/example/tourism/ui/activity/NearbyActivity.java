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
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.ExhibitionArea;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.utils.AppUtils;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private ExhibitionArea exhibitionArea;

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
        macAddress.add("F9:50:C1:76:EC:5F");//1
        macAddress.add("FE:CB:23:2A:B6:4B");//2
        macAddress.add("E7:44:02:EA:FE:A9");//3
        macAddress.add("C0:74:27:97:83:AD");//4
        macAddress.add("EF:D5:C7:C8:14:FB");//5
        macAddress.add("E7:87:4D:07:1A:47");//黑色1
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
                    openActivity(Top10Activity.class);
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
            Log.d("222",arrayList.size()+"");
//            List<BRTBeacon> result = new ArrayList<>();
//            for (int i = 0; i < arrayList.size(); i++) {
//                if (arrayList.get(i).isBrightBeacon()){
//                    result.add(arrayList.get(i));
//                }
//            }
//            // Beacon信息更新
//            if (result.size()==0){
//                Log.d("message", "附近没有Beacon ~ ~ ~");
//            }else {
//                Log.d("message", result.size()+"");
//                for (int i = 0; i < result.size(); i++) {
//                    if (result.get(i).macAddress.equals("F9:50:C1:76:EC:5F")){
////                        view.setHeight((int) calculateDistance(result.get(0).getMeasuredPower(),result.get(0).getRssi()));
//                        //Toast.makeText(NearbyActivity.this,"你目前正在展览区1",Toast.LENGTH_SHORT).show();
//                        exhibition_area_1.setAlpha((float) calculateDistance(result.get(i).getMeasuredPower(),result.get(i).getRssi()));
//                    }else if (result.get(i).macAddress.equals("FE:CB:23:2A:B6:4B")){
//                        //Toast.makeText(NearbyActivity.this,"你目前正在展览区2",Toast.LENGTH_SHORT).show();
//                        exhibition_area_2.setAlpha((float) calculateDistance(result.get(i).getMeasuredPower(),result.get(i).getRssi()));
//                    }else if (result.get(i).macAddress.equals("EF:D5:C7:C8:14:FB")){
//                        //Toast.makeText(NearbyActivity.this,"你目前正在展览区5",Toast.LENGTH_SHORT).show();
//                        exhibition_area_5.setAlpha((float) calculateDistance(result.get(i).getMeasuredPower(),result.get(i).getRssi()));
//                    }
//                }
//            }
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onNewBeacon(BRTBeacon brtBeacon) {
            // 发现一个新的Beacon
            if (brtBeacon.getMacAddress().equals("F9:50:C1:76:EC:5F")){
                AppUtils.getToast("进入"+brtBeacon.getName());
                exhibition_area_1.setBackgroundColor(getColor(R.color.color_blue));
                showByExhibitionArea(1);
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onGoneBeacon(BRTBeacon brtBeacon) {
            // 一个Beacon消失
            if (brtBeacon.getMacAddress().equals("F9:50:C1:76:EC:5F")){
                AppUtils.getToast("离开"+brtBeacon.getName());
                exhibition_area_1.setBackgroundColor(getColor(R.color.map_bg_3));
            }
        }

        @Override
        public void onError(BRTThrowable brtThrowable) {

        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        InitApp.getInstance().getBRTBeaconManager().stopRanging();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        InitApp.getInstance().getBRTBeaconManager().startRanging();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //InitApp.getInstance().getBRTBeaconManager().stopRanging();
    }

    @Override
    public void onClick(View view) {
        int id  = view.getId();
        switch (id){
            case R.id.exhibition_area_1:
                showByExhibitionArea(1);
                break;
            case R.id.exhibition_area_2:
                showByExhibitionArea(2);
                break;
            case R.id.exhibition_area_3:
                showByExhibitionArea(3);
                break;
            case R.id.exhibition_area_4:
                showByExhibitionArea(4);
                break;
            case R.id.exhibition_area_5:
                showByExhibitionArea(5);
                break;
            case R.id.exhibition_area_6:
                showByExhibitionArea(6);
                break;
            default:
                break;
        }
    }

    private void showByExhibitionArea(int exhibitionAreaId){
        queryExhibitionArea(exhibitionAreaId);
        Intent intent = new Intent(this,LeaderboardActivity.class);
        intent.putExtra("exhibitionAreaId",exhibitionAreaId);
        startActivity(intent);
    }

    private void queryExhibitionArea(int exhibitionAreaId){
        ServerApi api = RetrofitManger.getInstance().getRetrofit(RequestURL.ip_port).create(ServerApi.class);
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("exhibitionAreaId",exhibitionAreaId+"");
        Call<ResponseBody> exhibitsCall = api.getASync("queryExhibitionArea",hashMap);
        exhibitsCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String data = response.body().string();
                    Log.d("@@@",data);
                    JSONObject jsonObject = new JSONObject(data);
                    exhibitionArea = RetrofitManger.getInstance().getGson().fromJson(
                            jsonObject.getString("ONE_DETAIL"),
                            new TypeToken<ExhibitionArea>(){}.getType());
                    if (exhibitionArea == null) return;
                    Log.d("@@@", exhibitionArea.getExhibitionAreaName());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("@@@","请求失败！");
                Log.d("@@@",t.getMessage());
            }
        });
    }
}
