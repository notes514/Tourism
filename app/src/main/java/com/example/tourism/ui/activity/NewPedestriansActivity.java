package com.example.tourism.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;

import com.example.tourism.R;
import com.example.tourism.common.DefineView;
import com.example.tourism.database.bean.ContactsBean;
import com.example.tourism.database.bean.TripBean;
import com.example.tourism.ui.activity.base.BaseActivity;
import com.example.tourism.utils.AppUtils;
import com.example.tourism.utils.DaoManger;
import com.example.tourism.widget.CustomToolbar;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewPedestriansActivity extends BaseActivity implements DefineView {

    @BindView(R.id.custom_toolbar)
    CustomToolbar customToolbar;
    @BindView(R.id.id_Card)
    EditText idCard;
    @BindView(R.id.et_add_name)
    EditText etAddName;

    private DaoManger daoManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pedestrians);
        ButterKnife.bind(this);
        initView();
        initValidata();
        initListener();
        bindData();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initValidata() {
        daoManger = DaoManger.getInstance();
    }

    @Override
    public void initListener() {
        customToolbar.setOnLeftButtonClickLister(new CustomToolbar.OnLeftButtonClickLister() {
            @Override
            public void OnClick() {
                finish();
            }
        });

        customToolbar.setOnRightButtonClickLister(() -> {
            TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();
            TripBean tripBean = new TripBean(null,etAddName.getText().toString(),idCard.getText().toString(),"");
            try {
                if (idCard.getText().length() < 18){
                    AppUtils.getToast("请输入正确的身份证");
                }else if(etAddName.getText().length() < 1){
                    AppUtils.getToast("名字");
                }else {
                    Intent i = new Intent();
                    daoManger.getsDaoSession().getTripBeanDao().insert(tripBean);
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
