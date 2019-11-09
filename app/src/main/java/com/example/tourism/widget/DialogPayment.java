package com.example.tourism.widget;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tourism.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class DialogPayment extends Dialog {
    @BindView(R.id.iv_clear)
    ImageView ivClear;
    @BindView(R.id.tv_payment_method)
    TextView tvPaymentMethod;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.btn_payment)
    Button btnPayment;
    private Activity activity;

    public DialogPayment(Activity activity) {
        super(activity, R.style.ActionSheetDialogStyle);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_dialog_layout);
        //控件绑定
        ButterKnife.bind(this);

        setViewLocation();
        setCanceledOnTouchOutside(false);//外部点击取消
    }

    /**
     * 设置dialog位于屏幕底部
     */
    private void setViewLocation(){
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

    @OnClick({R.id.iv_clear, R.id.tv_payment_method, R.id.btn_payment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_clear:
                btnClose();
                this.cancel();
                break;
            case R.id.tv_payment_method:
                btnPaymentMethod();
                break;
            case R.id.btn_payment:
                btnPayment();
                this.cancel();
                break;
        }
    }

    /**
     * 关闭
     */
    public abstract void btnClose();

    /**
     * 选择支付方式
     */
    public abstract void btnPaymentMethod();

    /**
     * 立即支付
     */
    public abstract void btnPayment();

}
