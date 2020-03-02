package com.example.tourism.ui.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.tourism.R;
import com.example.tourism.ui.fragment.base.BaseFragment;
import com.example.tourism.widget.TimeCountUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends BaseFragment {


    @BindView(R.id.edit_phone)
    EditText edit_phone;
    @BindView(R.id.edit_cord)
    EditText edit_cord;
    @BindView(R.id.btn_checkCode)
    Button btn_checkCode;
    @BindView(R.id.signin)
    Button signin;
    private View view;
    private String phone_number;
    private String cord_number;
    EventHandler eventHandler;
    private int time = 60;
    private boolean flag=true;
    private TimeCountUtil mTimeCountUtil;
    private Unbinder unbinder;


    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        unbinder = ButterKnife.bind(this,view);

        //这里的倒计时的时间 是 ：用第二参数 / 第三个三参数 = 倒计时为60秒
        mTimeCountUtil = new TimeCountUtil(btn_checkCode, 60000, 1000);

        btn_checkCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(judPhone()){//去掉左右空格获取字符串
                    mTimeCountUtil.start();
                    SMSSDK.getVerificationCode("86",phone_number);
                    edit_cord.requestFocus();
                }
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(judCord())
                    SMSSDK.submitVerificationCode("86",phone_number,cord_number);
                flag=false;
            }
        });

        EventHandler eventHandler = new EventHandler(){
            public void afterEvent(int event, int result, Object data){
                Message message = new Message();//创建一个对象
                message.arg1=event;
                message.arg2=result;
                message.obj=data;
                handler.sendMessage(message);
            }
        };
        SMSSDK.registerEventHandler(eventHandler);//注册短信回调（记得销毁，避免泄露内存）

        return view;
    }

    @Override
    public void onDestroy() { //销毁
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
        unbinder.unbind(); //解绑
    }

    /**
     * 使用Handler来分发Message对象到主线程中，处理事件
     */
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message message) {
            super.handleMessage(message);
            int event = message.arg1;
            int result = message.arg2;
            Object data = message.obj;
            if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){//获取验证码成功
                if (result == SMSSDK.RESULT_COMPLETE){
                    //回调完成
                    boolean smart = (Boolean)data;
                    if (smart){
                        Toast.makeText(getActivity().getApplicationContext(),"该手机号已经注册过，请重新输入",
                                Toast.LENGTH_LONG).show();
                        edit_phone.requestFocus();//焦点
                        return;
                    }
                }
            }
            //回调完成
            if (result==SMSSDK.RESULT_COMPLETE){
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){//提交验证码成功
                    Toast.makeText(getActivity().getApplicationContext(),"验证码输入正确", Toast.LENGTH_LONG).show();
                    getActivity().finish();
                }
            }else {
                if (flag){
                    btn_checkCode.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity().getApplicationContext(),"验证码获取失败请重新获取", Toast.LENGTH_LONG).show();
                    edit_phone.requestFocus();
                }
                else
                {
                    Toast.makeText(getActivity().getApplicationContext(),"验证码输入错误", Toast.LENGTH_LONG).show();
                }

            }
        }

    };


    private boolean judPhone() {
        if(TextUtils.isEmpty(edit_phone.getText().toString().trim()))
        {
            Toast.makeText(getActivity(),"请输入您的电话号码", Toast.LENGTH_LONG).show();
            edit_phone.requestFocus();
            return false;
        }
        else if(edit_phone.getText().toString().trim().length()!=11)
        {
            Toast.makeText(getActivity(),"您的电话号码位数不正确", Toast.LENGTH_LONG).show();
            edit_phone.requestFocus();
            return false;
        }
        else {
            phone_number=edit_phone.getText().toString().trim();
            String num="[1][358]\\d{9}";
            if(phone_number.matches(num))
                return true;
            else {
                Toast.makeText(getActivity(),"请输入正确的手机号码", Toast.LENGTH_LONG).show();
                return false;
            }
        }
    }

    private boolean judCord()
    {
        judPhone();
        if(TextUtils.isEmpty(edit_cord.getText().toString().trim()))
        {
            Toast.makeText(getActivity(),"请输入您的验证码", Toast.LENGTH_LONG).show();
            edit_cord.requestFocus();
            return false;
        }
        else if(edit_cord.getText().toString().trim().length()!=4)
        {
            Toast.makeText(getActivity(),"您的验证码位数不正确", Toast.LENGTH_LONG).show();
            edit_cord.requestFocus();

            return false;
        }
        else
        {
            cord_number=edit_cord.getText().toString().trim();
            return true;
        }

    }



}
