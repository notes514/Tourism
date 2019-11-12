package com.example.tourism.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.tourism.R;
import com.example.tourism.application.InitApp;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.common.DefineView;
import com.example.tourism.common.RequestURL;
import com.example.tourism.entity.Order;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.utils.AppUtils;
import com.example.tourism.widget.CustomToolbar;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 订单支付成功页面
 * Name:laodai
 * Time:2019.11.12
 */
public class SuccessfulPaymentActivity extends BaseActivity implements DefineView {
    @BindView(R.id.custom_toolbar)
    CustomToolbar customToolbar;
    @BindView(R.id.iv_successful)
    ImageView ivSuccessful;
    @BindView(R.id.tv_successful)
    TextView tvSuccessful;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.btn_determine)
    Button btnDetermine;
    private int orderId;
    private String price;
    //网路请求api
    private ServerApi api;
    //
    private Order order;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_payment_layout);
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
        price = this.getIntent().getStringExtra("price");
        orderId = this.getIntent().getIntExtra("orderId", 0);

        api = RetrofitManger.getInstance().getRetrofit(RequestURL.ip_port).create(ServerApi.class);
        Map<String, Object> map = new HashMap<>();
        map.put("userId", RequestURL.vUserId);
        map.put("orderId", orderId);
        Call<ResponseBody> call = api.getASync("queryByOrder", map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String message = response.body().string();
                    JSONObject json = new JSONObject(message);
                    if (json.getString(RequestURL.RESULT).equals("S")) {
                        order = RetrofitManger.getInstance().getGson().fromJson(json.getString(RequestURL.ONE_DATA),
                                Order.class);
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
        if (order != null) {
            ImageLoader.getInstance().displayImage(RequestURL.ip_images + "images/success.jpg", ivSuccessful, InitApp.getOptions());
            tvOrderNumber.setText("18776620191101719293513300" + order.getOrderNumber());
            tvPrice.setText(price);
        }
    }

    @OnClick(R.id.btn_determine)
    public void onViewClicked() {
        finish();
    }
}
