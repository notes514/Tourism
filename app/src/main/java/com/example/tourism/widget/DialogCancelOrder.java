package com.example.tourism.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.tourism.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class DialogCancelOrder extends Dialog {
    private TextView tvCancel;
    private LinearLayout llDoNot;
    private RadioButton rbDoNot;
    private LinearLayout llPriductInformation;
    private RadioButton rbPriductInformation;
    private LinearLayout llPriceReduction;
    private RadioButton rbPriceReduction;
    private LinearLayout llOther;
    private RadioButton rbOther;
    private Button btnPay;

    private Context context;

    public static final int WX_PAY = 0;
    public static final int ALI_PAY = 1;
    public static final int BALANCE_PAY = 2;
    private Activity activity;

    private int payType = 0;//支付方式，默认微信
    private OnPayClickListener listener;
    private double payMoney = 0;//支付金额
    private double balance = 0;//余额

    private Boolean haveBalance = true;
    private Boolean haveAliPay = true;
    private Boolean haveWXPay = true;

    public DialogCancelOrder(Activity activity) {
        super(activity, R.style.ActionSheetDialogStyle);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_order_cancel_layout);

        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        llDoNot = (LinearLayout) findViewById(R.id.ll_do_not);
        rbDoNot = (RadioButton) findViewById(R.id.rb_do_not);
        llPriductInformation = (LinearLayout) findViewById(R.id.ll_priduct_information);
        rbPriductInformation = (RadioButton) findViewById(R.id.rb_priduct_information);
        llPriceReduction = (LinearLayout) findViewById(R.id.ll_price_reduction);
        rbPriceReduction = (RadioButton) findViewById(R.id.rb_price_reduction);
        llOther = (LinearLayout) findViewById(R.id.ll_other);
        rbOther = (RadioButton) findViewById(R.id.rb_other);
        btnPay = (Button) findViewById(R.id.btn_pay);


    }

    /**
     * 设置dialog位于屏幕底部
     */
    private void setViewLocation() {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;

        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.x = 0;
        lp.y = height;
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        onWindowAttributesChanged(lp);
    }

    public interface OnPayClickListener {
        void onPayClick(int payType);
    }

}
