package com.example.tourism.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.example.tourism.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zhaokaiqiang
 * @ClassName: com.example.animationloading.LoadingDialog
 * @Description: 动画加载Dialog
 * @date 2014-10-27 下午4:42:52
 */
public class LoadingDialog extends Dialog {
    @BindView(R.id.tv_loading_content)
    TextView tvLoadingContent;
    private Context context;
    private String lContent;

    public LoadingDialog(Context context, String lContent) {
        super(context, R.style.dialog);
        this.context = context;
        this.lContent = lContent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_dialog_layout);
        //控件绑定
        ButterKnife.bind(this);
        //显示加载内容
        tvLoadingContent.setText(lContent);
    }

}
