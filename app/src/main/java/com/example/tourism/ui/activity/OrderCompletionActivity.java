package com.example.tourism.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.adapter.RecyclerViewAdapter;
import com.example.tourism.application.InitApp;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.common.DefineView;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.Contacts;
import com.example.tourism.entity.Order;
import com.example.tourism.entity.Passenger;
import com.example.tourism.entity.ScenicDetails;
import com.example.tourism.entity.ScenicSpot;
import com.example.tourism.entity.TravellingPeopleBean;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.utils.AppUtils;
import com.example.tourism.utils.CTextUtils;
import com.example.tourism.widget.CustomToolbar;
import com.example.tourism.widget.LoadingDialog;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    @BindView(R.id.tv_room_difference_price)
    TextView tvRoomDifferencePrice;
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
    @BindView(R.id.rv_trip)
    RecyclerView rvTrip;
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
    NestedScrollView orderScrollView;
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
    //成人数
    private int adultNumber;
    //儿童数
    private int childrenNumber;
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
    private List<Contacts> contactsList;
    private List<Passenger> passengerList;
    private Order order;
    //适配器
    private RecyclerViewAdapter rAdapter;
    //出行人数据集
    private List<TravellingPeopleBean> peopleBeanList;
    //正在加载dialog
    private LoadingDialog loadingDialog;

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
        adultNumber = this.getIntent().getIntExtra("adultNumber", 0);
        childrenNumber = this.getIntent().getIntExtra("childrenNumber", 0);

        //创建线性布局管理器
        //设置线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        //设置管理器竖向显示
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        //设置布局管理器
        rvTrip.setLayoutManager(layoutManager);
        //创建适配器对象
        rAdapter = new RecyclerViewAdapter(this, 11);

        //创建出行人对象
        peopleBeanList = new ArrayList<>();
        for (int i = 0; i < adultNumber; i++) {
            peopleBeanList.add(new TravellingPeopleBean("成人", "请务必保证所填项与出游所持证件一致", "", 0));
        }
        if (childrenNumber > 0) {
            for (int i = 0; i < childrenNumber; i++) {
                peopleBeanList.add(new TravellingPeopleBean("儿童", "请务必保证所填项与出游所持证件一致", "", 0));
            }
        }
        //显示出行人信息
        rAdapter.setTravellingPeopleBeanList(peopleBeanList);
        rvTrip.setAdapter(rAdapter);

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
        //设置适配器点击监听
        rAdapter.setOnItemClickListener((view, object) -> {
            //选择出行人
            Intent intent2 = new Intent(OrderCompletionActivity.this, TravelerActivity.class);
            intent2.putExtra("adultNumber", adultNumber);
            intent2.putExtra("childrenNumber", childrenNumber);
            startActivityForResult(intent2, 2);
        });
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
        int price = (int) (scenicSpot.getScenicSpotPrice() + 0);
        tvPrice.setText(price + "");
    }

    @OnClick({R.id.tv_choice, R.id.tv_trip, R.id.switch_order, R.id.checkBox, R.id.tv_cost_details, R.id.btn_reserve})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_choice:
                //选择联系人
                Intent intent = new Intent(OrderCompletionActivity.this, ContactActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.tv_trip:
                //选择出行人
                Intent intent2 = new Intent(OrderCompletionActivity.this, TravelerActivity.class);
                intent2.putExtra("adultNumber", adultNumber);
                intent2.putExtra("childrenNumber", childrenNumber);
                startActivityForResult(intent2, 2);
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
                if (RequestURL.vUserId.length() == 0) {
                    AppUtils.getToast("请先登录");
                    return;
                }
                //获取联系人数据
                name = etName.getText().toString();
                phoneNumber = etPhoneNumber.getText().toString();
                qq = etQq.getText().toString();
                wechat = etWechat.getText().toString();
                //获取出行人信息
                //备注信息可选
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
                        //显示正在加载
                        loadingDialog = new LoadingDialog(this, "正在预定...");
                        loadingDialog.show();


                        Log.d(InitApp.TAG, "addContactsAndPassenger: 正在预定！！！" );
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
                        Map<String, Object> map = new HashMap<>();
                        map.put("userId", RequestURL.vUserId);
                        map.put("orderContent", tvContent.getText().toString());
                        map.put("orderNumber", 1);
                        map.put("orderState", 1);
                        map.put("passengerNumber", adultNumber + childrenNumber);
                        map.put("tripMode", travelMode);
                        map.put("departDate", date + " 出发");
                        map.put("departDays", scenicDetails.getTravelDays());
                        if (adultNumber > 0) map.put("tirpInformation", "成人 " + adultNumber);
                        if (adultNumber > 0 && childrenNumber > 0)
                            map.put("tirpInformation", "成人 " + adultNumber + " 儿童" + childrenNumber);
                        map.put("orderPrice", scenicSpot.getScenicSpotPrice());
                        map.put("supplier", "中旅集团私人定制中心");
                        Call<ResponseBody> orderCall = api.postASync("addByOrder", map);
                        orderCall.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                try {
                                    String message = response.body().string();
                                    JSONObject json = new JSONObject(message);
                                    Log.d(InitApp.TAG, "message: " + message);
                                    if (json.getString(RequestURL.RESULT).equals("S")) {

                                        queryNewestOrder();
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

    /**
     * 获取最新订单
     */
    private void queryNewestOrder() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", RequestURL.vUserId);
        Call<ResponseBody> oCall = api.getASync("queryNewestOrder", map);
        oCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String message = response.body().string();
                    JSONObject json = new JSONObject(message);
                    if (json.getString(RequestURL.RESULT).equals("S")) {
                        order = RetrofitManger.getInstance().getGson().fromJson(json.getString(RequestURL.ONE_DATA),
                                Order.class);
                        if (order != null) {
                            addContactsAndPassenger();
                        }
                    } else {
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

    /**
     * 新增联系人，出行人信息
     */
    private void addContactsAndPassenger() {
        //生成联系人信息
        Map<String, Object> map = new HashMap<>();
        map.put("userId", RequestURL.vUserId);
        map.put("orderId", order.getOrderId());
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

                        Log.d(InitApp.TAG, "addContactsAndPassenger: 执行了！！！" );
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

        //生成出行人信息
        if (peopleBeanList == null) return;
        map.clear();
        for (TravellingPeopleBean bean : peopleBeanList) {
            map.put("userId", RequestURL.vUserId);
            map.put("orderId", order.getOrderId());
            map.put("passengerName", bean.gettName());
            map.put("passengerType", bean.gettType());
            map.put("identityCard", bean.gettIdentitycard());
            Call<ResponseBody> pCall = api.postASync("addByPassenger", map);
            pCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String message = response.body().string();
                        JSONObject json = new JSONObject(message);
                        if (json.getString(RequestURL.RESULT).equals("S")) {
                        } else {
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

        map.clear();
        map.put("userId", RequestURL.vUserId);
        map.put("orderId", order.getOrderId());
        Call<ResponseBody> qCall = api.getASync("queryContactsAndPassenger", map);
        qCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    assert response.body() != null;
                    String message = response.body().string();
                    JSONObject json = new JSONObject(message);
                    if (json.getString(RequestURL.RESULT).equals("S")) {
                        contactsList = RetrofitManger.getInstance().getGson().fromJson(json.getString(RequestURL.ONE_DATA),
                                new TypeToken<List<Contacts>>(){}.getType());
                        passengerList = RetrofitManger.getInstance().getGson().fromJson(json.getString(RequestURL.TWO_DATA),
                                new TypeToken<List<Passenger>>(){}.getType());
                        if (contactsList != null && passengerList != null) {
                            addByOrderDedails();
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

    /**
     * 新增订单详情
     */
    private void addByOrderDedails() {
        //生成订单详情
        Map<String, Object> map = new HashMap<>();
        map.clear();
        map.put("orderId", order.getOrderId());
        map.put("contactsId", contactsList.get(0).getContactsId());
        map.put("passengerId", passengerList.get(0).getPassengerId());
        map.put("orderDetailsDescribe", "订单明细描述");
        Call<ResponseBody> dCall = api.postASync("addByOrderDedails", map);
        dCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String message = response.body().string();
                    JSONObject json = new JSONObject(message);
                    if (json.getString(RequestURL.RESULT).equals("S")) {
                        AppUtils.getToast(json.getString(RequestURL.TIPS));
                        //加载成功
                        loadingDialog.dismiss();
                        //执行跳转
                        Intent intent = new Intent(OrderCompletionActivity.this, AllOrderActivity.class);
                        intent.putExtra("index", 1);
                        startActivity(intent);
                    } else {
                        AppUtils.getToast(json.getString(RequestURL.TIPS));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //加载失败
                loadingDialog.dismiss();
                AppUtils.getToast(t.toString());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //此处可以根据两个Code进行判断，本页面和结果页面跳过来的值
        if (requestCode == 1 && resultCode == 3) {
            String cName = data.getStringExtra("cName");
            String cCellPhone = data.getStringExtra("cCellPhone");
            String cQq = data.getStringExtra("cQq");
            String cWechat = data.getStringExtra("cWechat");
            etName.setText(cName);
            etPhoneNumber.setText(cCellPhone);
            etQq.setText(cQq);
            etWechat.setText(cWechat);
        }
        if (requestCode == 2 && resultCode == 4) {
            Bundle bundle = data.getExtras();
            peopleBeanList.clear();
            peopleBeanList = (List<TravellingPeopleBean>) bundle.getSerializable("tripBeans");
            //显示出行人信息
            if (peopleBeanList == null) return;
            for (TravellingPeopleBean bean : peopleBeanList) {
                identfityCardId = bean.gettIdentitycard();
            }
            rAdapter.setTravellingPeopleBeanList(peopleBeanList);
            rvTrip.setAdapter(rAdapter);
        }
    }

}
