package com.example.tourism.ui.fragment;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.tourism.R;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.common.RequestURL;
import com.example.tourism.ui.activity.PersonalCustomStatusViewActivity;
import com.example.tourism.ui.activity.SuccessfulPaymentActivity;
import com.example.tourism.utils.AppUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * 底部弹窗Fragment
 */
public class PersonalPayDetailFragment extends DialogFragment {
    private RelativeLayout rePayWay, rePayDetail, reBalance;
    private LinearLayout LinPayWay,linPass;
    private ListView lv;
    private Button btnPay;
    private PersonalCustomStatusViewActivity customStatusView;
    private TextView tvOrderPrice;
    private TextView tvPrice;
    private ImageView imageCloseOne,imageCloseTwo;

    private String price;
    private int orderId;

    public PersonalPayDetailFragment(String price, int orderId) {
        this.price = price;
        this.orderId = orderId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.fragment_personal_pay_detail);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部。
        final Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.AnimBottom);
        final WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.height = getActivity().getWindowManager().getDefaultDisplay().getHeight() * 3 / 5;
        window.setAttributes(lp);
        initView(dialog);
        return dialog;
    }

    private void initView(Dialog dialog) {
        rePayWay = (RelativeLayout) dialog.findViewById(R.id.re_pay_way);
        rePayDetail = (RelativeLayout) dialog.findViewById(R.id.re_pay_detail);//付款详情
        LinPayWay = (LinearLayout) dialog.findViewById(R.id.lin_pay_way);//付款方式
        lv = (ListView) dialog.findViewById(R.id.lv_bank);//付款方式（银行卡列表）
        reBalance = (RelativeLayout) dialog.findViewById(R.id.re_balance);//付款方式（余额）
        btnPay = (Button) dialog.findViewById(R.id.btn_confirm_pay);
        linPass = (LinearLayout)dialog.findViewById(R.id.lin_pass);
        customStatusView =(PersonalCustomStatusViewActivity)dialog.findViewById(R.id.as_status);
        tvOrderPrice = (TextView) dialog.findViewById(R.id.tv_order_price);
        tvPrice = (TextView) dialog.findViewById(R.id.tv_price);
        imageCloseOne= (ImageView) dialog.findViewById(R.id.close_one);
        imageCloseTwo= (ImageView) dialog.findViewById(R.id.close_two);
        rePayWay.setOnClickListener(listener);
        reBalance.setOnClickListener(listener);
        btnPay.setOnClickListener(listener);
        imageCloseOne.setOnClickListener(listener);
        imageCloseTwo.setOnClickListener(listener);

        //显示数据
        tvOrderPrice.setText(price);
        tvPrice.setText(price);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Animation slide_left_to_left = AnimationUtils.loadAnimation(getActivity(), R.anim.personal_slide_left_to_left);
            Animation slide_right_to_left = AnimationUtils.loadAnimation(getActivity(), R.anim.personal_slide_right_to_left);
            Animation slide_left_to_right = AnimationUtils.loadAnimation(getActivity(), R.anim.personal_slide_left_to_right);
            Animation slide_left_to_left_in = AnimationUtils.loadAnimation(getActivity(), R.anim.personal_slide_left_to_left_in);
            switch (view.getId()) {
                case R.id.re_pay_way://选择方式
                    rePayDetail.startAnimation(slide_left_to_left);
                    rePayDetail.setVisibility(View.GONE);
                    LinPayWay.startAnimation(slide_right_to_left);
                    LinPayWay.setVisibility(View.VISIBLE);
                    break;
                case R.id.re_balance:
                    rePayDetail.startAnimation(slide_left_to_left_in);
                    rePayDetail.setVisibility(View.VISIBLE);
                    LinPayWay.startAnimation(slide_left_to_right);
                    LinPayWay.setVisibility(View.GONE);
                    break;
                case R.id.btn_confirm_pay://确认付款
                    rePayDetail.startAnimation(slide_left_to_left);
                    rePayDetail.setVisibility(View.GONE);
                    linPass.startAnimation(slide_right_to_left);
                    customStatusView.loadSuccess();
                    linPass.setVisibility(View.VISIBLE);

                    ServerApi api = RetrofitManger.getInstance().getRetrofit(RequestURL.ip_port).create(ServerApi.class);
                    Map<String, Object> map = new HashMap<>();
                    map.put("userId", RequestURL.vUserId);
                    map.put("orderId", orderId);
                    Call<ResponseBody> call = api.getASync("orderPayment", map);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                String message = response.body().string();
                                JSONObject json = new JSONObject(message);
                                if (json.getString(RequestURL.RESULT).equals("S")) {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            getDialog().dismiss();
                                            Intent intent = new Intent(getContext(), SuccessfulPaymentActivity.class);
                                            intent.putExtra("orderId", orderId);
                                            intent.putExtra("price", price);
                                            startActivity(intent);
                                        }
                                    }, 3000);
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
                    break;
                case R.id.close_one:
                    getDialog().dismiss();
                    break;
                case R.id.close_two:
                    getDialog().dismiss();
                    break;
                default:
                    break;
            }
        }
    };
}
