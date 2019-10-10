package com.example.tourism.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.tourism.R;

/**
 * 自定义Toolbar
 *
 * Name：laodai
 */
public class CustomToolbar extends Toolbar {
    private ImageView toolbarRightButton;
    private ImageView toolbarLeftButton;
    private TextView toolbarTitle;
    private View mChildView;
    private Drawable leftButtonIcon;
    private Drawable rightButtonIcon;
    private String title;
    private OnLeftButtonClickLister onLeftButtonClickLister;
    private OnRightButtonClickLister onRightButtonClickLister;

    /**
     * 返回按钮监听接口
     */
    public interface OnLeftButtonClickLister {
        void OnClick();
    }

    /**
     * 更多按钮监听接口
     */
    public interface OnRightButtonClickLister {
        void OnClick();
    }

    public CustomToolbar(Context context) {
        this(context, null, 0);
    }

    public CustomToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属性
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomToolbar, defStyleAttr, 0);
        leftButtonIcon = ta.getDrawable(R.styleable.CustomToolbar_leftButtonIcon);
        rightButtonIcon = ta.getDrawable(R.styleable.CustomToolbar_rightButtonIcon);
        title = ta.getString(R.styleable.CustomToolbar_myTitle);
        ta.recycle();
        initView(); //初始化视图
        initListener(); //初始化监听器
    }

    /**
     * 初始化
     */
    private void initView() {
        if (mChildView == null) {
            mChildView = View.inflate(getContext(), R.layout.custom_toolbar_layout, null);
            toolbarRightButton = mChildView.findViewById(R.id.toolbar_rightButton);
            toolbarLeftButton = mChildView.findViewById(R.id.toolbar_leftButton);
            toolbarTitle = mChildView.findViewById(R.id.toolbar_title);
            addView(mChildView);
            if (leftButtonIcon != null) {
                toolbarLeftButton.setImageDrawable(leftButtonIcon);
            }
            if (rightButtonIcon != null) {
                toolbarRightButton.setImageDrawable(rightButtonIcon);
            }
            if (title != null) {
                toolbarTitle.setText(title);
            }
        }

    }

    /**
     * 下拉监听
     */
    private void initListener() {
        toolbarLeftButton.setOnClickListener(view -> onLeftButtonClickLister.OnClick());

        toolbarRightButton.setOnClickListener(view -> onRightButtonClickLister.OnClick());
    }

    public void setMyTitle(String title) {
        toolbarTitle.setText(title);
    }

    public void setMyTitle(int resId) {
        toolbarTitle.setText(resId);
    }

    /**
     * 设置左边按钮图标
     *
     * @param leftButtonIcon
     */
    public void setLeftButtonIcon(Drawable leftButtonIcon) {
        this.leftButtonIcon = leftButtonIcon;
    }

    /**
     * 设置右边按钮图标
     * @param rightButtonIcon
     */
    public void setRightButtonIcon(Drawable rightButtonIcon) {
        this.rightButtonIcon = rightButtonIcon;
    }

    public void setOnLeftButtonClickLister(OnLeftButtonClickLister onLeftButtonClickLister) {
        this.onLeftButtonClickLister = onLeftButtonClickLister;
    }

    public void setOnRightButtonClickLister(OnRightButtonClickLister onRightButtonClickLister) {
        this.onRightButtonClickLister = onRightButtonClickLister;
    }

}
