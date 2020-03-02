package com.example.tourism.widget;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.example.tourism.R;
import com.example.tourism.application.RetrofitManger;
import com.example.tourism.application.ServerApi;
import com.example.tourism.common.RequestURL;
import com.example.tourism.payment.OrderInfoUtil2_0;
import com.example.tourism.payment.PayResult;
import com.example.tourism.ui.activity.SuccessfulPaymentActivity;
import com.example.tourism.utils.AppUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayDialogFragment extends DialogFragment implements PasswordEditText.PasswordFullListener {
    private RelativeLayout reRoot;
    private RelativeLayout llPayDetail;
    private TextView tvCancel;
    private TextView tvPayMoney;
    private RelativeLayout rlWxPay;
    private ImageView ivWxIcon;
    private RadioButton rbWxPay;
    private RelativeLayout rlAliPay;
    private ImageView ivAliIcon;
    private RadioButton rbAliPay;
    private RelativeLayout rlBalancePay;
    private ImageView ivBalanceIcon;
    private TextView tvBalancePay;
    private TextView tvBalancePayContext;
    private TextView tvBalance;
    private RadioButton rbBalancePay;
    private Button btnPay;
    private PasswordEditText passwordEdt;
    private LinearLayout keyboard;
    private LinearLayout llPayPassword;

    public static final int WX_PAY = 0;
    public static final int ALI_PAY = 1;
    public static final int BALANCE_PAY = 2;

    private int payType = 0;//支付方式，默认微信
    private double payMoney = 0;//支付金额
    private double balance = 0;//余额

    private Boolean haveBalance = true;
    private Boolean haveAliPay = true;
    private Boolean haveWXPay = true;

    //网络请求api
    private ServerApi api;
    //订单编号
    private int orderId;
    //需要支付的金额
    private int price;
    //商品名称
    private String productName;
    //
    private int requestCode;

    /**
     * 用于支付宝支付业务的入参 app_id。
     */
    public static final String APPID = "2016101400687697";
    /**
     *  pkcs8 格式的商户私钥。
     *
     * 	如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个，如果两个都设置了，本 Demo 将优先
     * 	使用 RSA2_PRIVATE。RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议商户使用
     * 	RSA2_PRIVATE。
     *
     * 	建议使用支付宝提供的公私钥生成工具生成和获取 RSA2_PRIVATE。
     * 	工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static final String RSA2_PRIVATE = "";
    public static final String RSA_PRIVATE = "";

    private static final int SDK_PAY_FLAG = 1;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        orderPayment();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(getContext(), "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    public PayDialogFragment(int orderId, int price, String productName, int requestCode) {
        this.orderId = orderId;
        this.price = price;
        this.productName = productName;
        this.requestCode = requestCode;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.fragment_pay_dialog_detail);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部。
        final Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.AnimBottom);
        final WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.height = getActivity().getWindowManager().getDefaultDisplay().getHeight() * 3 / 6;
        window.setAttributes(lp);
        //绑定控件
        reRoot = (RelativeLayout) dialog.findViewById(R.id.re_root);
        llPayDetail = (RelativeLayout) dialog.findViewById(R.id.ll_pay_detail); //付款详情
        tvCancel = (TextView) dialog.findViewById(R.id.tv_cancel);
        tvPayMoney = (TextView) dialog.findViewById(R.id.tv_pay_money);
        rlWxPay = (RelativeLayout) dialog.findViewById(R.id.rl_wx_pay);
        ivWxIcon = (ImageView) dialog.findViewById(R.id.iv_wx_icon);
        rbWxPay = (RadioButton) dialog.findViewById(R.id.rb_wx_pay);
        rlAliPay = (RelativeLayout) dialog.findViewById(R.id.rl_ali_pay);
        ivAliIcon = (ImageView) dialog.findViewById(R.id.iv_ali_icon);
        rbAliPay = (RadioButton) dialog.findViewById(R.id.rb_ali_pay);
        rlBalancePay = (RelativeLayout) dialog.findViewById(R.id.rl_balance_pay);
        ivBalanceIcon = (ImageView) dialog.findViewById(R.id.iv_balance_icon);
        tvBalancePay = (TextView) dialog.findViewById(R.id.tv_balance_pay);
        tvBalancePayContext = (TextView) dialog.findViewById(R.id.tv_balance_pay_context);
        tvBalance = (TextView) dialog.findViewById(R.id.tv_balance);
        rbBalancePay = (RadioButton) dialog.findViewById(R.id.rb_balance_pay);
        btnPay = (Button) dialog.findViewById(R.id.btn_pay);
        passwordEdt = (PasswordEditText) dialog.findViewById(R.id.passwordEdt);
        keyboard = (LinearLayout) dialog.findViewById(R.id.keyboard);
        llPayPassword = (LinearLayout) dialog.findViewById(R.id.ll_pay_password); //密码支付

        tvPayMoney.setText(price+"");

        //点击监听
        llPayDetail.setOnClickListener(listener);
        llPayPassword.setOnClickListener(listener);
        //设置支付详情点击事件
        rlWxPay.setOnClickListener(listener);
        rlAliPay.setOnClickListener(listener);
        rlBalancePay.setOnClickListener(listener);
        tvBalancePay.setOnClickListener(listener);
        tvCancel.setOnClickListener(listener);
        btnPay.setOnClickListener(listener);
        //设置密码输入点击事件

        passwordEdt.setPasswordFullListener(this);
        setItemClickListener(keyboard);
        return dialog;
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Animation slide_left_to_left = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_left_to_left);
            Animation slide_right_to_left = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_right_to_left);
            Animation slide_left_to_right = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_left_to_right);
            Animation slide_left_to_left_in = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_left_to_left_in);
            switch (view.getId()) {
                case R.id.rl_wx_pay:
                    rbWxPay.setChecked(true);
                    rbAliPay.setChecked(false);
                    rbBalancePay.setChecked(false);
                    payType = WX_PAY;
                    break;
                case R.id.rl_ali_pay:
                    rbWxPay.setChecked(false);
                    rbAliPay.setChecked(true);
                    rbBalancePay.setChecked(false);
                    payType = ALI_PAY;
                    break;
                case R.id.rl_balance_pay:
                    rbWxPay.setChecked(false);
                    rbAliPay.setChecked(false);
                    rbBalancePay.setChecked(true);
                    payType = BALANCE_PAY;
                    break;
                case R.id.tv_cancel:
                    dismiss();
                    break;
                case R.id.btn_pay:
                    if (payType == WX_PAY) {

                    } else if (payType == ALI_PAY) {
                        payV2(view);
                    } else if (payType == BALANCE_PAY) {
                        llPayDetail.startAnimation(slide_left_to_left);
                        llPayDetail.setVisibility(View.GONE);
                        llPayPassword.startAnimation(slide_right_to_left);
                        llPayPassword.setVisibility(View.VISIBLE);
                    }

                    break;
                default:
                    break;
            }
            if (view instanceof TextView) {
                if ("确认付款".equals(((TextView) view).getText().toString().trim()) ||
                    "余额支付".equals(((TextView) view).getText().toString().trim())) {
                    passwordEdt.addPassword("");
                    return;
                }
                String number = ((TextView) view).getText().toString().trim();
                passwordEdt.addPassword(number);
            }
            if (view instanceof ImageView) {
                passwordEdt.deletePassword();
            }
        }
    };

    /**
     * 给每一个自定义数字键盘上的View 设置点击事件
     *
     * @param view
     */
    private void setItemClickListener(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                //不断的给里面所有的View设置setOnClickListener
                View childView = ((ViewGroup) view).getChildAt(i);
                setItemClickListener(childView);
            }
        } else {
            view.setOnClickListener(listener);
        }
    }

    //正在加载dialog
    private LoadingDialog loadingDialog;

    @Override
    public void passwordFull(String password) {
        loadingDialog = new LoadingDialog(getContext(), "正在支付...");
        loadingDialog.show();
        new Handler().postDelayed(() -> {
            api = RetrofitManger.getInstance().getRetrofit(RequestURL.ip_port).create(ServerApi.class);
            Map<String, Object> map = new HashMap<>();
            map.put("userId", RequestURL.vUserId);
            map.put("orderId", orderId);
            map.put("password", password);
            Call<ResponseBody> call = api.getASync("orderPaymentPassWord", map);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String message = response.body().string();
                        JSONObject json = new JSONObject(message);
                        if (json.getString(RequestURL.RESULT).equals("S")) {
                            loadingDialog.dismiss();
                            Intent intent = new Intent(getContext(), SuccessfulPaymentActivity.class);
                            intent.putExtra("price", price);
                            intent.putExtra("orderId", orderId);
                            startActivityForResult(intent, requestCode);
                            dismiss();
                        } else {
                            passwordEdt.addPassword("");
                            passwordEdt.setText("");
                            setDialog("提示", json.getString(RequestURL.TIPS), "重新输入");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }, 1000);

    }

    /**
     * 支付宝支付业务示例
     */
    public void payV2(View v) {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
//            showAlert(this, getString(R.string.error_missing_appid_rsa_private));
            return;
        }

        /*
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo 的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2, price, productName);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(getActivity());
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }

    private void orderPayment() {
        api = RetrofitManger.getInstance().getRetrofit(RequestURL.ip_port).create(ServerApi.class);
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
                        Intent intent = new Intent(getContext(), SuccessfulPaymentActivity.class);
                        intent.putExtra("price", price);
                        intent.putExtra("orderId", orderId);
                        startActivityForResult(intent, requestCode);
                        dismiss();
                    } else {
                        AppUtils.getToast(RequestURL.TIPS);
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

    private void setDialog(String title, String message, String bStr){
        AlertDialog.Builder builder  = new AlertDialog.Builder(getContext());
        builder.setTitle(title) ;
        builder.setMessage(message) ;
        builder.setPositiveButton(bStr ,  null );
        builder.show();
    }

}
