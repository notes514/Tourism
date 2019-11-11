package com.example.tourism.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;
import com.example.tourism.R;
import com.example.tourism.common.DefineView;
import com.example.tourism.database.bean.ContactsBean;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.utils.AppUtils;
import com.example.tourism.utils.DaoManger;
import com.example.tourism.widget.CustomToolbar;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddContactActivity extends BaseActivity implements DefineView {
    @BindView(R.id.custom_toolbar)
    CustomToolbar customToolbar;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.et_qq)
    EditText etqq;
    @BindView(R.id.et_wechat)
    EditText etWechat;
    private String phone_number;
    private DaoManger daoManger;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        ButterKnife.bind(this);
        initView();
        initValidata();
        initListener();
    }
    @Override
    public void initView(){

    }

    @Override
    public void initValidata() {
        activity = this;
        daoManger = DaoManger.getInstance();
    }

    @Override
    public void initListener() {
        customToolbar.setOnLeftButtonClickLister(() -> finish());

        customToolbar.setOnRightButtonClickLister(() -> {
            TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();
            ContactsBean contactsBean = new ContactsBean(null,etName.getText().toString(),etPhoneNumber.getText().toString(),
                    etqq.getText().toString(),etWechat.getText().toString(), date);
            try {
                if (etName.getText().length() < 1){
                AppUtils.getToast("请输入用户名");
            }else if(etPhoneNumber.getText().length() < 11){
                AppUtils.getToast("请输入您的电话号码");
            }else {
                Intent i = new Intent();
                daoManger.getsDaoSession().getContactsBeanDao().insert(contactsBean);
                setResult(3,i);
                this.finish();
                AppUtils.getToast("添加成功！");
            }

            } catch (Exception e) {
                AppUtils.getToast("添加失败！");
            }
        });
    }

    @Override
    public void bindData() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent();
        setResult(3,i);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Intent i = new Intent();
            Bundle bundle = new Bundle();
            setResult(3,i.putExtras(bundle));
            finish();
            return true;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
