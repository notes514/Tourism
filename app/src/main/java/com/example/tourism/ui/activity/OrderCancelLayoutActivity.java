package com.example.tourism.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.tourism.R;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.common.DefineView;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.Contacts;
import com.example.tourism.entity.Order;
import com.example.tourism.entity.OrderDetails;
import com.example.tourism.entity.Passenger;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.utils.AppUtils;
import com.example.tourism.widget.CustomToolbar;
import com.example.tourism.widget.DialogPayment;
import com.example.tourism.widget.MyScrollView;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
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
    @BindView(R.id.tv_passenger_name)
    TextView tvPassengerName;
    @BindView(R.id.tv_identify_cardId)
    TextView tvIdentifyCardId;
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
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_cost_details)
    TextView tvCostDetails;
    @BindView(R.id.btn_reserve)
    Button btnReserve;
    @BindView(R.id.iv_left_back)
    ImageView ivLeftBack;
    @BindView(R.id.loading_line)
    ConstraintLayout loadingLine;
    @BindView(R.id.ll_content_subject)
    LinearLayout llContentSubject;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    //网络请求api
    private ServerApi api;
    //订单编号
    private int orderId;
    //订单详情类
    private OrderDetails orderDetails;
    //订单类
    private Order order;
    //联系人类
    private Contacts contacts;
    //出行人类
    private Passenger passenger;
    //底部弹出框
    private DialogPayment dialogPayment;

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

        //获取订单编号
        orderId = this.getIntent().getIntExtra("orderId", 0);
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
                        passenger = RetrofitManger.getInstance().getGson().fromJson(json.getString(RequestURL.FOUR_DATA),
                                Passenger.class);
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
            llBottom.setVisibility(View.VISIBLE);

            //订单内容信息
            productName.setText(order.getOrderContent());
            tripMode.setText(order.getTripMode());
            departurePeriod.setText(order.getDepartDate());
            int price = (int) (order.getOrderPrice() + 0);
            amountPayable.setText("¥" + price);
            //订单号
            tvOrderNumber.setText("18776620191101719293513300" + order.getOrderNumber());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = format.format(order.getOrderDate());
            tvOrderDate.setText(date);
            //供应商
            tvSupplier.setText(order.getSupplier());
            //价格明细
            tvAdultPassenger.setText("¥" + price + " x 1");
            //房差
            tvRoomDifference.setText("¥681");
            //订单总额
            tvTotalOrders.setText("¥" + (price + 681));
            //应付金额
            tvAmountPayable.setText("¥" + (price + 681));
            //总价
            tvPrice.setText("¥" + (price + 681));
        }
        //联系人信息
        if (contacts != null) {
            tvContacts.setText(contacts.getContactsName());
            tvPhoneNumber.setText("+86-" + contacts.getCellPhoneNumber());
            tvQq.setText(contacts.getQqNumber());
            tvRemark.setText(contacts.getRemarks());
        }
        //旅客信息
        if (passenger != null) {
            tvPassengerName.setText(passenger.getPassengerName() + "(成人 >= 12)");
            tvIdentifyCardId.setText(passenger.getIdentityCard());
        }
    }

    @OnClick({R.id.btn_cancel, R.id.btn_reserve})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                break;
            case R.id.btn_reserve:
                //立即支付
                new DialogPayment(this) {
                    @Override
                    public void btnClose() {
                        AppUtils.getToast("点击关闭");
                    }

                    @Override
                    public void btnPaymentMethod() {
                        AppUtils.getToast("支付方式");
                    }

                    @Override
                    public void btnPayment() {
                        AppUtils.getToast("立即支付");
                    }
                }.show();
                break;
        }
    }
}
