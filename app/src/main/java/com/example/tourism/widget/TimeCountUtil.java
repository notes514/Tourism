package com.example.tourism.widget;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.Button;

public class TimeCountUtil extends CountDownTimer {
    private Button mButton;

    public TimeCountUtil(Button button, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mButton = button;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        // 按钮不可用
        mButton.setEnabled(false);
        mButton.setBackgroundColor(Color.parseColor("#dddddd"));
        String showText = millisUntilFinished / 1000 + "秒后可重新发送";
        mButton.setText(showText);
    }

    @Override
    public void onFinish() {
        // 按钮设置可用
        mButton.setEnabled(true);
        mButton.setBackgroundColor(Color.parseColor("#83D2FA"));
        mButton.setText("重新获取验证码");
    }

}
