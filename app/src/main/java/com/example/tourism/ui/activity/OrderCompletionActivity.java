package com.example.tourism.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.tourism.R;
import com.example.tourism.application.InitApp;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.common.DefineView;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.Order;
import com.example.tourism.entity.ScenicDetails;
import com.example.tourism.entity.ScenicSpot;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.utils.AppUtils;
import com.example.tourism.utils.CTextUtils;
import com.example.tourism.widget.CustomToolbar;
import com.example.tourism.widget.MyScrollView;
import com.github.mikephil.charting.formatter.IFillFormatter;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderCompletionActivity extends BaseActivity implements DefineView {
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_date_place_days)
    TextView tvDatePlaceDays;
    @BindView(R.id.tv_room_difference)
    TextView tvRoomDifference;
    @BindView(R.id.tv_choice)
    TextView tvChoice;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.et_qq)
    EditText etQq;
    @BindView(R.id.et_wechat)
    EditText etWechat;
    @BindView(R.id.tv_trip)
    TextView tvTrip;
    @BindView(R.id.et_adult)
    EditText etAdult;
    @BindView(R.id.iv_forward)
    ImageView ivForward;
    @BindView(R.id.switch_order)
    Switch switchOrder;
    @BindView(R.id.view_requirement)
    View viewRequirement;
    @BindView(R.id.et_requirement)
    EditText etRequirement;
    @BindView(R.id.checkBox)
    CheckBox checkBox;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.order_scrollView)
    MyScrollView orderScrollView;
    @BindView(R.id.custom_toolbar)
    CustomToolbar customToolbar;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_cost_details)
    TextView tvCostDetails;
    @BindView(R.id.btn_reserve)
    Button btnReserve;

    //请求api
    private ServerApi api;
    private ScenicSpot scenicSpot;
    private ScenicDetails scenicDetails;
    //景区编号
    private int scenicSpotId;
    //人数
    private int number;
    //日期
    private String date;
    //联系人姓名
    private String name;
    //手机
    private String phoneNumber;
    //qq号
    private String qq;
    //微信号
    private String wechat;
    //身份证号
    private String identfityCardId;
    //备注（留言）
    private String remarks;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_completion_layout);
        ButterKnife.bind(this);
        initValidata();
        initListener();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initValidata() {
        //初始隐藏用户留言输入框
        viewRequirement.setVisibility(View.GONE);
        etRequirement.setVisibility(View.GONE);
        //设置checkBox为默认选中状态
        checkBox.setChecked(true);
        //获取景区详情编号
        scenicSpotId = this.getIntent().getIntExtra("scenicSpotId", 0);
        //获取人数
        number = this.getIntent().getIntExtra("number", 0);
        //获取出行日期
        date = this.getIntent().getStringExtra("date");
        //网络请求
        api = RetrofitManger.getInstance().getRetrofit(RequestURL.ip_port).create(ServerApi.class);
        Map<String, Object> map = new HashMap<>();
        map.put("scenicSpotId", scenicSpotId);
        Call<ResponseBody> queryCall = api.getASync("queryByScenicDetailsAndPic", map);
        queryCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String message = response.body().string();
                    JSONObject json = new JSONObject(message);
                    Log.d(InitApp.TAG, "onResponse: " + message);
                    if (json.getString(RequestURL.RESULT).equals("S")) {
                        //获取景区信息
                        scenicSpot = RetrofitManger.getInstance().getGson().fromJson(json.getString(RequestURL.ONE_DATA),
                                ScenicSpot.class);
                        //获取景区详情信息
                        scenicDetails = RetrofitManger.getInstance().getGson().fromJson(json.getString(RequestURL.TWO_DATA),
                                ScenicDetails.class);
                        if (scenicDetails != null) {
                            bindData();
                        }
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

    @Override
    public void initListener() {
        customToolbar.setOnLeftButtonClickLister(() -> finish());
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void bindData() {
        //景区主题
        tvContent.setText(scenicSpot.getScenicSpotTheme());
        //日期、出发地点、天数
        tvDatePlaceDays.setText(date + " " +
                CTextUtils.getAutomaticInterceptString(scenicDetails.getDepartArrive(), "-", 0) +
                " " + scenicDetails.getTravelDays());
        //总价
        tvPrice.setText("¥"+scenicSpot.getScenicSpotPrice());
    }

    @OnClick({R.id.tv_choice, R.id.tv_trip, R.id.switch_order, R.id.checkBox, R.id.tv_cost_details, R.id.btn_reserve})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_choice:
                break;
            case R.id.tv_trip:
                break;
            case R.id.switch_order:
                if (switchOrder.isChecked()) {
                    AppUtils.getToast("开启");
                    viewRequirement.setVisibility(View.VISIBLE);
                    etRequirement.setVisibility(View.VISIBLE);
                    remarks = etRequirement.getText().toString();
                } else {
                    AppUtils.getToast("关闭");
                    viewRequirement.setVisibility(View.GONE);
                    etRequirement.setVisibility(View.GONE);
                }
                break;
            case R.id.checkBox:
                break;
            case R.id.tv_cost_details:
                break;
            case R.id.btn_reserve:
                //获取联系人数据
                name = etName.getText().toString();
                phoneNumber = etPhoneNumber.getText().toString();
                qq = etQq.getText().toString();
                wechat = etWechat.getText().toString();
                identfityCardId = etAdult.getText().toString();
                remarks = etRequirement.getText().toString();
                if (checkBox.isChecked()) {
                    if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phoneNumber)) {
                        AppUtils.getToast("请输入联系人信息");
                        return;
                    }
                    if (TextUtils.isEmpty(identfityCardId)) {
                        AppUtils.getToast("请输入出行人信息");
                        return;
                    }
                    if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phoneNumber) && !TextUtils.isEmpty(identfityCardId)) {
                        Log.d("cellPhoneNumber", "onViewClicked: " + name + ", "+ phoneNumber + ", "+ identfityCardId);
                        //生成联系人信息
                        Map<String, Object> map = new HashMap<>();
                        map.put("contactsName", name);
                        map.put("cellPhoneNumber", phoneNumber);
                        map.put("qqNumber", qq);
                        map.put("wechatNumber", wechat);
                        map.put("remarks", remarks);
                        Call<ResponseBody> addCall = api.postASync("addByContacts", map);
                        addCall.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                try {
                                    String message = response.body().string();
                                    JSONObject json = new JSONObject(message);
                                    if (json.getString(RequestURL.RESULT).equals("S")) {
                                        AppUtils.getToast(json.getString(RequestURL.TIPS));
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });

                        String travelMode = "";
                        if (scenicSpot.getTravelMode() == 0) {
                            travelMode = "国内游";
                        } else if (scenicSpot.getTravelMode() == 1) {
                            travelMode = "出境游";
                        } else if (scenicSpot.getTravelMode() == 2) {
                            travelMode = "自由行";
                        } else if (scenicSpot.getTravelMode() == 3) {
                            travelMode = "跟团游";
                        } else if (scenicSpot.getTravelMode() == 4) {
                            travelMode = "主题游";
                        } else if (scenicSpot.getTravelMode() == 5) {
                            travelMode = "周边游";
                        } else if (scenicSpot.getTravelMode() == 6) {
                            travelMode = "一日游";
                        } else if (scenicSpot.getTravelMode() == 7) {
                            travelMode = "定制游";
                        }
                        //生成订单
                        map.clear();
                        map.put("orderContent", tvContent.getText().toString());
                        map.put("orderNumber", 1);
                        map.put("orderState", 1);
                        map.put("tripMode", travelMode);
                        map.put("departDate", date + " 出发");
                        map.put("departDays", scenicDetails.getTravelDays());
                        map.put("tirpInformation", "成人 " + number);
                        map.put("orderPrice", scenicSpot.getScenicSpotPrice());
                        map.put("supplier", "中旅集团私人定制中心");
                        Call<ResponseBody> orderCall = api.postASync("addByOrder", map);
                        orderCall.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                try {
                                    String message = response.body().string();
                                    JSONObject json = new JSONObject(message);
                                    if (json.getString(RequestURL.RESULT).equals("S")) {
                                        AppUtils.getToast(json.getString(RequestURL.TIPS));
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
                } else {
                    AppUtils.getToast("请同意");
                }
                break;
        }
    }
}
