package com.example.tourism.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.R;
import com.example.tourism.adapter.RecyclerAdapter;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.common.DefineView;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.Contacts;
import com.example.tourism.entity.Order;
import com.example.tourism.entity.OrderDetails;
import com.example.tourism.entity.Passenger;
import com.example.tourism.entity.User;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.utils.AppUtils;
import com.example.tourism.widget.CustomToolbar;
import com.example.tourism.widget.MyScrollView;
import com.example.tourism.widget.PayDialogFragment;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
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

public class OrderCancelLayoutActivity extends BaseActivity implements DefineView {
    @BindView(R.id.custom_toolbar)
    CustomToolbar customToolbar;
    @BindView(R.id.tv_order_cancel)
    TextView tvOrderCancel;
    @BindView(R.id.cb_commit)
    CheckBox cbCommit;
    @BindView(R.id.cb_wait_for)
    CheckBox cbWaitFor;
    @BindView(R.id.cb_consumption)
    CheckBox cbConsumption;
    @BindView(R.id.cb_complete)
    CheckBox cbComplete;
    @BindView(R.id.ll_order_state)
    LinearLayout llOrderState;
    @BindView(R.id.productName)
    TextView productName;
    @BindView(R.id.tripMode)
    TextView tripMode;
    @BindView(R.id.departurePeriod)
    TextView departurePeriod;
    @BindView(R.id.amountPayable)
    TextView amountPayable;
    @BindView(R.id.lianxigongyingshang)
    LinearLayout lianxigongyingshang;
    @BindView(R.id.tv_contacts)
    TextView tvContacts;
    @BindView(R.id.tv_phone_number)
    TextView tvPhoneNumber;
    @BindView(R.id.tv_qq)
    TextView tvQq;
    @BindView(R.id.tv_remark)
    TextView tvRemark;
    @BindView(R.id.rv_trip)
    RecyclerView rvTrip;
    @BindView(R.id.tv_adult_passenger)
    TextView tvAdultPassenger;
    @BindView(R.id.tv_room_difference)
    TextView tvRoomDifference;
    @BindView(R.id.tv_total_orders)
    TextView tvTotalOrders;
    @BindView(R.id.tv_amount_payable)
    TextView tvAmountPayable;
    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;
    @BindView(R.id.tv_order_date)
    TextView tvOrderDate;
    @BindView(R.id.tv_supplier)
    TextView tvSupplier;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.scroll)
    MyScrollView scroll;
    @BindView(R.id.ll_content_subject)
    LinearLayout llContentSubject;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_cost_details)
    TextView tvCostDetails;
    @BindView(R.id.btn_reserve)
    Button btnReserve;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.iv_left_back)
    ImageView ivLeftBack;
    @BindView(R.id.tv_loading_content)
    TextView tvLoadingContent;
    @BindView(R.id.loading_line)
    ConstraintLayout loadingLine;
    //网络请求api
    private ServerApi api;
    //订单编号
    private int orderId;
    //订单状态
    private int orderState;
    //订单详情类
    private OrderDetails orderDetails;
    //订单类
    private Order order;
    //联系人类
    private Contacts contacts;
    //出行人List集合
    private List<Passenger> passengerList;
    //用户实体
    private User user;
    //总金额
    private int totalSum;
    //适配器
    private RecyclerAdapter rAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_cancel_layout);
        ButterKnife.bind(this);
        initView();
        initValidata();
        initListener();
    }

    @Override
    public void initView() {
    }

    @Override
    public void initValidata() {
        //加载隐藏资源文件
        llContentSubject.setVisibility(View.GONE);
        llBottom.setVisibility(View.GONE);
        loadingLine.setVisibility(View.VISIBLE);
        tvOrderCancel.setVisibility(View.GONE);
        llOrderState.setVisibility(View.VISIBLE);
        llBottom.setVisibility(View.VISIBLE);
        btnCancel.setVisibility(View.GONE);

        //获取订单编号
        orderId = this.getIntent().getIntExtra("orderId", 0);
        orderState = this.getIntent().getIntExtra("orderState", 0);

        if (orderState == 0) {
            cbCommit.setChecked(true);
            llBottom.setVisibility(View.VISIBLE);
        } else if (orderState == 1) {
            cbWaitFor.setChecked(true);
            btnCancel.setVisibility(View.VISIBLE);
            llBottom.setVisibility(View.VISIBLE);
        } else if (orderState == 2) {
            cbConsumption.setChecked(true);
            llBottom.setVisibility(View.GONE);
        } else if (orderState == 5) {
            llBottom.setVisibility(View.GONE);
            cbComplete.setChecked(true);
        } else if (orderState == 6) {
            tvOrderCancel.setVisibility(View.VISIBLE);
            llOrderState.setVisibility(View.GONE);
            llBottom.setVisibility(View.GONE);
        }

        //创建线性布局管理器
        //设置线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        //设置管理器竖向显示
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        //设置布局管理器
        rvTrip.setLayoutManager(layoutManager);
        //创建适配器对象
        rAdapter = new RecyclerAdapter(this, 1);

        //执行网络请求
        api = RetrofitManger.getInstance().getRetrofit(RequestURL.ip_port).create(ServerApi.class);
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        Call<ResponseBody> oCall = api.getASync("queryByOrderDedails", map);
        oCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String message = response.body().string();
                    JSONObject json = new JSONObject(message);
                    if (json.getString(RequestURL.RESULT).equals("S")) {
                        orderDetails = RetrofitManger.getInstance().getGson().fromJson(json.getString(RequestURL.ONE_DATA),
                                OrderDetails.class);
                        order = RetrofitManger.getInstance().getGson().fromJson(json.getString(RequestURL.TWO_DATA),
                                Order.class);
                        contacts = RetrofitManger.getInstance().getGson().fromJson(json.getString(RequestURL.THREE_DATA),
                                Contacts.class);
                        bindData();
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

        map.clear();
        map.put("orderId", orderId);
        Call<ResponseBody> queryByPassengerCall = api.getASync("queryByPassenger", map);
        queryByPassengerCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String message = response.body().string();
                    JSONObject json = new JSONObject(message);
                    if (json.getString(RequestURL.RESULT).equals("S")) {
                        passengerList = RetrofitManger.getInstance().getGson().fromJson(json.getString(RequestURL.ONE_DATA),
                                new TypeToken<List<Passenger>>() {
                                }.getType());
                        if (passengerList == null) return;
                        rAdapter.setPassengerList(passengerList);
                        rvTrip.setAdapter(rAdapter);
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

        map.clear();
        map.put("userId", RequestURL.vUserId);
        Call<ResponseBody> queryByUserInformationCall = api.getASync("queryByUserInformation", map);
        queryByUserInformationCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String message = response.body().string();
                    JSONObject json = new JSONObject(message);
                    if (json.getString(RequestURL.RESULT).equals("S")) {
                        user = RetrofitManger.getInstance().getGson().fromJson(json.getString(RequestURL.ONE_DATA), User.class);
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

    @Override
    public void initListener() {
        customToolbar.setOnLeftButtonClickLister(() -> finish());
    }

    @Override
    public void bindData() {
        //订单信息
        if (order != null) {
            //显示布局
            //加载隐藏资源文件
            loadingLine.setVisibility(View.GONE);
            llContentSubject.setVisibility(View.VISIBLE);

            //订单内容信息
            productName.setText(order.getOrderContent());
            tripMode.setText(order.getTripMode());
            departurePeriod.setText(order.getDepartDate());
            int price = (int) (order.getOrderPrice() + 0);
            amountPayable.setText("¥" + price);
            //订单号
            tvOrderNumber.setText("20191122000" + order.getOrderNumber());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = format.format(order.getOrderDate());
            tvOrderDate.setText(date);
            //供应商
            tvSupplier.setText(order.getSupplier());
            //价格明细
            tvAdultPassenger.setText("¥" + price + " x " + passengerList.size());
            //房差
            tvRoomDifference.setText("¥681");
            //订单总额
            totalSum = price * passengerList.size() + 681;
            tvTotalOrders.setText("¥" + totalSum);
            //应付金额
            tvAmountPayable.setText("¥" + totalSum);
            //总价
            tvPrice.setText(totalSum + "");
        }
        //联系人信息
        if (contacts != null) {
            tvContacts.setText(contacts.getContactsName());
            tvPhoneNumber.setText("+86-" + contacts.getCellPhoneNumber());
            tvQq.setText(contacts.getQqNumber());
            tvRemark.setText(contacts.getRemarks());
        }
    }

    @OnClick({R.id.btn_cancel, R.id.btn_reserve})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
//                AlertDialog dialog = new AlertDialog.Builder(OrderCancelLayoutActivity.this)
//                        .setTitle("提示")
//                        .setMessage("您确定要取消订单吗")
//                        .setNegativeButton("取消", (dialog1, which) -> {
//                        })
//                        .setPositiveButton("确定", (dialog12, which) -> {
//                            Map<String, Object> map = new HashMap<>();
//                            map.put("orderId", orderId);
//                            map.put("orderState", 5);
//                            Call<ResponseBody> queryByPassengerCall = api.getASync("updateByOrderState", map);
//                            queryByPassengerCall.enqueue(new Callback<ResponseBody>() {
//                                @Override
//                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                                    try {
//                                        String message = response.body().string();
//                                        JSONObject json = new JSONObject(message);
//                                        if (json.getString(RequestURL.RESULT).equals("S")) {
//
//                                        } else {
//                                            AppUtils.getToast(json.getString(RequestURL.TIPS));
//                                        }
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//                                }
//                            });
//                        })
//                        .create();
//                dialog.show();
                break;
            case R.id.btn_reserve:
                //立即支付
                FragmentManager fm = getSupportFragmentManager();
                PayDialogFragment payDialogFragment = new PayDialogFragment(orderId, totalSum, order.getOrderContent(), 1);
                payDialogFragment.show(fm, "payDialog");
//                PersonalPayDetailFragment payDetailFragment = new PersonalPayDetailFragment("¥" + totalSum + ".00", order.getOrderId());
//                payDetailFragment.show(getSupportFragmentManager(), "payDetailFragment");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //此处可以根据两个Code进行判断，本页面和结果页面跳过来的值
        if (requestCode == 1 && resultCode == 3) {
            finish();
        }
    }

}
