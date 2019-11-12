package com.example.tourism.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.example.tourism.R;

/**
 *
 * @ClassName: com.example.animationloading.LoadingDialog
 * @Description: 动画加载Dialog
 * @author zhaokaiqiang
 * @date 2014-10-27 下午4:42:52
 *
 */
public class LoadingDialogActivity extends Dialog {
    private Context context;

    public LoadingDialogActivity(Context context) {
        super(context, R.style.dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_dialog_layout);
    }

}
